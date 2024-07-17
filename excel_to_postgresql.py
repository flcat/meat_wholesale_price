import pandas as pd
import psycopg2
from psycopg2 import sql
import yaml
import os
from datetime import datetime

# 설정 파일 로드
with open('config3.yaml', 'r') as f:
    config = yaml.safe_load(f)

# PostgreSQL 연결 설정
db_config = config['database']

def connect_to_db():
    return psycopg2.connect(
        host=db_config['host'],
        database=db_config['database'],
        user=db_config['user'],
        password=db_config['password']
    )

def create_table_if_not_exists(cursor, table_name):
    create_table_query = sql.SQL("""
    CREATE TABLE IF NOT EXISTS {} (
        id SERIAL PRIMARY KEY,
    date DATE,
    brand VARCHAR(255),
    origin VARCHAR(255),
    part VARCHAR(255),
    grade VARCHAR(255),
    price_kg INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (date, brand, origin, part, grade)
)
CREATE INDEX idx_meat_prices_date ON meat_prices(date)
CREATE INDEX idx_meat_prices_brand ON meat_prices(brand)
CREATE INDEX idx_meat_prices_origin ON meat_prices(origin)
CREATE INDEX idx_meat_prices_part ON meat_prices(part)
    """).format(sql.Identifier(table_name))
    
    cursor.execute(create_table_query)

def insert_data(cursor, table_name, data):
    insert_query = sql.SQL("""
    INSERT INTO {} (date, brand, origin, part, grade, price_kg)
    VALUES (%s, %s, %s, %s, %s, %s)
    ON CONFLICT (date, brand, origin, part, grade) DO UPDATE
    SET price_kg = EXCLUDED.price_kg
    """).format(sql.Identifier(table_name))
    
    # NaT 값을 가진 행 제거
    data = [row for row in data if not pd.isna(row[0])]
    
    cursor.executemany(insert_query, data)

def process_excel_file(file_path, table_name):
    # 현재 월 구하기 (예: '07월')
    current_month = datetime.now().strftime("%m월")
    
    # 엑셀 파일의 모든 시트 이름 가져오기
    xls = pd.ExcelFile(file_path)
    sheet_names = xls.sheet_names
    
    # 현재 월에 해당하는 시트 찾기
    if current_month in sheet_names:
        sheet_name = current_month
    else:
        # 현재 월 시트가 없으면 가장 최근 월 시트 사용
        month_sheets = [sheet for sheet in sheet_names if sheet.endswith('월')]
        if month_sheets:
            sheet_name = sorted(month_sheets)[-1]  # 가장 마지막 월 선택
        else:
            raise ValueError("No valid month sheet found in the Excel file")

    print(f"Processing sheet: {sheet_name}")
    
    # 선택된 시트에서 데이터 읽기
    df = pd.read_excel(file_path, sheet_name=sheet_name, engine='openpyxl')
    
    # 데이터 전처리
    df['기준일자'] = pd.to_datetime(df['기준일자'], format='%m-%d', errors='coerce').apply(lambda x: x.replace(year=datetime.now().year) if pd.notnull(x) else pd.NaT)
    df['가격KG'] = pd.to_numeric(df['가격KG'], errors='coerce')
    
    # NaN 및 NaT 값 처리
    df['기준일자'] = df['기준일자'].fillna(pd.NaT)
    df['조사업체'] = df['조사업체'].fillna('')
    df['원산지'] = df['원산지'].fillna('')
    df['부위'] = df['부위'].fillna('')
    df['등급'] = df['등급'].fillna('')
    df['가격KG'] = df['가격KG'].fillna(0)
    
    # NaT 값을 가진 행 제거
    df = df.dropna(subset=['기준일자'])
    
    return df.values.tolist()

def main():
    conn = connect_to_db()
    cursor = conn.cursor()
    
    try:
        
        # 일반 육류 가격 데이터 처리
        data = process_excel_file(config['file_path'] + config['file_name'], 'meat_prices')
        insert_data(cursor, 'meat_prices', data)
        
        conn.commit()
        print("Data successfully inserted into PostgreSQL database.")
    except Exception as e:
        conn.rollback()
        print(f"An error occurred: {e}")
        import traceback
        print(traceback.format_exc())
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    main()
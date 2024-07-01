import requests
import pandas as pd
from datetime import datetime
from bs4 import BeautifulSoup
from pandas import DataFrame
import re as r

meat_part = {'목심','차돌양지','사태','앞다리%2F전각'}
# url = "https://www.meatbox.co.kr/fo/product/productListPage.do?categorySeq=10005&articleCount=1000&itemCatName="+ meat_part +"&sellUnitType=B&listStyle=list?value=%22gf%22?searchText=gf"
file_path = '/Users/jaechankwon/Downloads/'
file_name = 'market_price.xlsx'
gf_grade = 'GF'
s_grade = 'S'
writer = pd.ExcelWriter(file_path + file_name, mode='a', engine='openpyxl', if_sheet_exists='overlay')
df = pd.read_excel(file_path + file_name)
date = datetime.today().strftime("%Y-%m-%d")

def crawling(soup) :
    items = soup.find_all("div", class_="detail_top")
    for item in items:
        prd_name = item.find('strong', class_='prd_name').text.replace(" ","")
        brand = r.findall('\\|[a-zA-Zㄱ-ㅎ가-힣]+', prd_name)
        print(prd_name)
        country = r.findall('\\-\\S[ㄱ-ㅎ가-힣]\\|', prd_name)
        print(*brand)
        print(*country)
        pp = r.compile('\\d+,\\d{3}원')
        price_stock = item.find_next('em', class_='pric_stock').get_text().replace("\n","").replace("\t","").replace("(","").replace(")","")
        prices = pp.findall(price_stock)
        grade = r.findall('\\-[A-Z]+', prd_name)
        # if prices[1:2] != "":
        print(*prices[1:2])
        # else:
        print(*prices[0:1])
        print(*grade)
        # print(item.get_text().replace("\n","").replace("\t","")+"  $ ")
        # data = pd.Series([date,brand,'','','','','','','',price,''])
        # df.loc[item] = data
# df.loc(date,brand,cattle_type,co  untry,part,prd_name,packaging_type,weight,price)
for i in range(len(meat_part)):
    url = "https://www.meatbox.co.kr/fo/product/productListPage.do?categorySeq=10005&articleCount=1000&itemCatName="+ list(meat_part)[i] +"&sellUnitType=B&listStyle=list?value=%22gf%22?searchText=gf"
    html = requests.get(url).text
    soup = BeautifulSoup(html, 'html5lib')
    crawling(soup)


'''
try:
    max_row = writer.sheets['Sheet1'].max_row
    if max_row == 2:
        df.to_excel(
            writer,
            sheet_name="Sheet1",
            startcol=1,
            startrow=3,
            index=False,
            na_rep = '',
            inf_rep = ''
            )
    else:
        df.to_excel(
            writer,
            sheet_name="Sheet1",
            startcol=1,
            startrow=max_row,
            index=False,
            na_rep = '',
            inf_rep = ''
            )

except:
    df.to_excel(
        writer,
        sheet_name='Sheet1',
        index=False,
        na_rep='',
        inf_rep=''
    )
writer._save()
writer.close()
'''
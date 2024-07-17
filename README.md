# 육류 도매 가격 크롤러

이 프로젝트는 두 가지 다른 소스에서 육류 도매 가격 정보를 자동으로 수집하고 엑셀 파일로 저장하는 웹 크롤러입니다.

## 주요 기능

1. 일반 육류 도매 가격 크롤링 (`meat_price_scrap.py`)
2. 금천 축산물 공판장 가격 크롤링 (`geumcheon_meat_price_scrap.py`)

## 기능 상세

### 1. 일반 육류 도매 가격 크롤러 (meat_price_scrap.py)

- 지정된 육류 부위별 가격 정보 크롤링
- 병렬 처리를 통한 효율적인 데이터 수집
- 수집된 데이터의 중복 제거 및 엑셀 파일 저장
- 로깅을 통한 크롤링 과정 모니터링

### 2. 금천 축산물 공판장 가격 크롤러 (geumcheon_meat_price_scrap.py)

- 금천 축산물 공판장의 가격 정보 크롤링
- Playwright를 사용한 동적 웹 페이지 처리
- 카테고리 및 부위별 데이터 수집
- 수집된 데이터의 엑셀 파일 저장

## 요구사항

- Python 3.7+
- 필요한 라이브러리: 
  - 공통: requests, pandas, numpy, BeautifulSoup, openpyxl, PyYAML
  - 금천 크롤러용: playwright

## 설치 방법

1. 저장소 클론 : git clone https://github.com/flcat/meat_wholesale_price.git
2. 프로젝트 디렉터리로 이동 : cd meat_wholesale_price
3. (선택사항) 가상 환경을 생성하고 활성화합니다 : python -m venv venv   source venv/bin/activate
         # Windows의 경우: venv\Scripts\activate 
5. 필요 라이브러리 설치 : pip install -r requirements.txt

## 사용 방법

1. `config.yaml`, `config2.yaml` 파일을 열어 필요한 설정을 수정합니다.
2. `meat_price_scrap.py`, `geumcheon_meat_price_scrap.py` 파일을 실행
   python meat_price_scrap.py
3. 크롤링이 완료되면 지정된 경로에 `market_price.xlsx` 파일이 생성 또는 업데이트됩니다.

## 구성 파일

- `meat_price_scrap.py`, `geumcheon_meat_price_scrap.py`: 메인 크롤링 스크립트
- `config.yaml`, `config2.yaml`: 설정 파일 (미트박스, 금천)
- `requirements.txt`: 필요한 Python 패키지 목록

## 주의사항

- 크롤링 시 웹사이트의 구조가 변경될 경우 코드 수정이 필요할 수 있습니다.
- 과도한 요청은 웹사이트에 부하를 줄 수 있으므로 적절한 간격을 두고 사용하세요.

## 라이선스

이 프로젝트는 MIT 라이선스 하에 제공됩니다.

## 시연

https://github.com/user-attachments/assets/2430aafc-c9bd-4b20-9dbb-45710fb464b0

https://github.com/user-attachments/assets/59cf2310-3ec4-4f85-af6f-dc99e0d46616



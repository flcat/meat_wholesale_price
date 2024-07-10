# 육류 도매가격 크롤러

이 프로젝트는 미트박스(www.meatbox.co.kr) 웹사이트에서 육류 도매가격 정보를 자동으로 수집하고 Excel 파일에 저장하는 크롤러입니다.

## 주요 기능

- 목심, 차돌양지, 사태, 앞다리/전각 부위의 가격 정보를 수집합니다.
- 수집된 데이터는 Excel 파일에 월별로 시트를 나누어 저장됩니다.
- 같은 월 내에서는 데이터가 누적되어 저장됩니다.
- [샘플] 제품은 수집 대상에서 제외됩니다.
- 병렬 처리를 통해 여러 부위의 데이터를 동시에 크롤링합니다.
- 크롤링 실패 시 자동으로 재시도합니다.
- 크롤링 진행 상황을 콘솔에 출력하고, 로그 파일에 기록합니다.

## 설치 방법

1. 저장소 클론 : git clone https://github.com/flcat/meat_wholesale_price.git
2. 프로젝트 디렉터리로 이동 : cd meat_wholesale_price
3. (선택사항) 가상 환경을 생성하고 활성화합니다 : python -m venv venv   source venv/bin/activate    # Windows의 경우: venv\Scripts\activate 
4. 필요 라이브러리 설치 : pip install -r requirements.txt

## 사용 방법

1. `config.yaml` 파일을 열어 필요한 설정을 수정합니다.
2. `meat_price_scrap.py` 파일을 실행
   python meat_price_scrap.py
3. 크롤링이 완료되면 지정된 경로에 `market_price.xlsx` 파일이 생성 또는 업데이트됩니다.

## 구성 파일

- `meat_price_scrap.py`: 메인 크롤링 스크립트
- `config.yaml`: 설정 파일 (URL, 파일 경로, 크롤링할 부위 등)
- `requirements.txt`: 필요한 Python 패키지 목록

## 주의사항

- 크롤링 시 웹사이트의 구조가 변경될 경우 코드 수정이 필요할 수 있습니다.
- 과도한 요청은 웹사이트에 부하를 줄 수 있으므로 적절한 간격을 두고 사용하세요.

## 라이선스

이 프로젝트는 MIT 라이선스 하에 제공됩니다.

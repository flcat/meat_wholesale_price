# 쇠고기 도매 가격 분석 프로젝트
# [https://gogiyo.com/charts](https://gogiyo.com/charts)

이 프로젝트는 쇠고기 도매 가격을 수집, 저장, 분석하는 통합 시스템입니다.

# 주요 기능

### 데이터 수집
- 일반 육류 도매 가격 크롤링 (meat_price_scrap.py)
- 금천 축산물 공판장 가격 크롤링 (geumcheon_meat_price_scrap.py)


### 데이터 저장
- 엑셀 파일에서 PostgreSQL 데이터베이스로 데이터 이관 (excel_to_postgresql.py)


### 데이터 분석 및 시각화
- Spring Boot 기반 웹 애플리케이션 (Java 소스 코드)


## 사용된 기술 스택
Python, Springboot 3.3.1, SpringJpa, PostgreSQL, AWS EC2, ACM, Nginx, Docker

## 시스템 요구사항

Python 3.7+
Java 21+
PostgreSQL 데이터베이스
Spring Boot 3.3+

## 설치 및 설정

저장소 클론:
  Copygit clone https://github.com/flcat/meat_wholesale_price.git

Python 의존성 설치:
  Copypip install -r requirements.txt
  
  Java 의존성은 Gradle을 통해 관리됩니다.


데이터베이스 설정:

  PostgreSQL 데이터베이스를 생성하고 접속 정보를 config3.yaml 파일에 설정하세요.


설정 파일:
  
  config3.yaml 파일을 생성하고 데이터베이스 접속 정보와 파일 경로를 설정하세요.



## 사용 방법

데이터 수집:

  Copypython meat_price_scrap.py
  python geumcheon_meat_price_scrap.py

데이터베이스 저장:

  Copypython excel_to_postgresql.py

웹 애플리케이션 실행:

  Spring Boot 애플리케이션을 실행하여 데이터 분석 및 시각화 기능을 사용할 수 있습니다.


## 프로젝트 구조

meat_price_scrap.py: 일반 육류 도매 가격 크롤링 스크립트
geumcheon_meat_price_scrap.py: 금천 축산물 공판장 가격 크롤링 스크립트
excel_to_postgresql.py: 엑셀 데이터를 PostgreSQL로 이관하는 스크립트
src/main/java/flcat/beef_wholesale_prices/: Java 소스 코드 디렉토리
beef/: 엔티티 및 리포지토리 클래스
config/: 설정 클래스
dto/: 데이터 전송 객체
service/: 비즈니스 로직 서비스
web/: 컨트롤러 클래스



## 주의사항

데이터 수집 시 웹사이트의 이용 약관을 준수하세요.
민감한 정보(예: 데이터베이스 접속 정보)는 공개 저장소에 직접 포함하지 마세요.

## 라이선스
이 프로젝트는 MIT 라이선스 하에 배포됩니다.
## 시연

https://github.com/user-attachments/assets/2430aafc-c9bd-4b20-9dbb-45710fb464b0

https://github.com/user-attachments/assets/59cf2310-3ec4-4f85-af6f-dc99e0d46616

[![Video Label](http://img.youtube.com/vi/V_oPJjiNjWc/0.jpg)](https://youtu.be/V_oPJjiNjWc)

이미지를 클릭하면 이동합니다.


[![Video Label](http://img.youtube.com/vi/QPOjjdn4uws/0.jpg)](https://youtu.be/QPOjjdn4uws)

이미지를 클릭하면 이동합니다.

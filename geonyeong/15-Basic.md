- [기본 개념](#기본-개념)
	- [프로세스와 쓰레드](#프로세스와-쓰레드)
		- [프로세스](#프로세스)
		- [쓰레드](#쓰레드)
	- [가상화](#가상화)
	- [동기/비동기](#동기비동기)
		- [동기](#동기)
		- [비동기](#비동기)
	- [응답 시간(Response Time)과 처리량(Throughput)](#응답-시간response-time과-처리량throughput)
		- [응답 시간 (Response Time)](#응답-시간-response-time)
		- [처리량 (Throughput)](#처리량-throughput)
		- [Reference](#reference)

# 기본 개념

## 프로세스와 쓰레드

실제 사례

- 프로그램을 설치해서 아이콘을 더블클릭해서 시작했을 때 새로운 창이 나타남
(한번 더 프로그램 아이콘을 더블클릭하면 다른 별도의 창이 열림)

### 프로세스

장점

- 개별 처리 독립성 보장

단점

- 생성시 overhead가 크다

### 쓰레드

장점

- 생성시 overhead가 낮다

단점

- 메모리 공간을 공유하기 때문에 의도하지 않는 데이터 읽기/쓰기가 발생할 수 있다

## 가상화

컴퓨터 공학에서 `가상`의 의미란 물리적으로 존재하지 않지만 '실제로 존재하는 것과 같다'
('실제가 아닌'의 의미보다는 '실제와 가깝다'의 의미)

## 동기/비동기

### 동기

- 누군가에게 일을 부탁한 다음 그 일이 잠자코 끝나기까지 기다리는 것은 동기
예) Back-end API 개발이 기다릴 때까지 기다리는 Front-end 개발자

### 비동기

- '끝나면 말해'라고 말해두고 다른 일을 하는 것은 비동기
예) Back-end 개발자에게 '끝나면 말해주세요'라고 말해두고 Front-end 설계 및 다른 문서작업을 진행하는 Front-end 개발자

## 응답 시간(Response Time)과 처리량(Throughput)

### 응답 시간 (Response Time)

- 검색 엔진에서 키워들르 입력해서 '검색'버튼 클릭 후 검색 결과가 표시되기까지 걸리는 시간

- 서비스를 이용하는 사용자 관점

### 처리량 (Throughput)

- 검색 엔진이 초당 받아 들이는 사용자 수

- 서비스 제공자 관점

### Reference

- [그림으로 공부하는 IT인프라](http://www.yes24.com/Product/Goods/95800974)







# RESTful API

Representational State Transfer





## 구성

- 자원 (RESOURCE) : URI
- 행위 (Verb) : HTTP Method
- 표현 (Representations)





## 특징

1. `Stateless` (무상태성)
    1. 들어오는 요청만 단순 처리 (쿠키, 세션 정보 별도 저장 관리 X)
    
    → 서비스 자유도 높아짐, 불필요한 정보 관리 X
    
2. `Cacheable` (캐시 가능)
    1. HTTP 라는 기존 웹표준 그대로 적용 → HTTP 의 캐싱기능 적용 가능
3. `Client - Server` 구조
    1. REST Server  : API 제공
    2. Client : 사용자 인증, Context(세션, 로그인정보) 등 직접관리
    3. 각각 역할 구분, 개발 내용 명확, 의존성 감소
4. `계층형 구조`
    1. 다중계층
    2. 보안, 로드밸런싱, 암호화 계층 추가 → 구조상의 유연성
    3. Proxy, Gateway 같은 네트워크 기반의 중간 매체 사용
    







## 디자인 가이드

- URI 는 정보의 자원을 표현
- 자원에 대한 행위는 HTTP Method (GET, POST, PUT, DELETE) 로 표현
    - ex) DELETE /members/1





**💡 주의점**

- ** 슬래시 구분자(/) 는 계층 관계를 나타내는 데 사용
  ex) http://restapi.example.com/animals/mammals/whales

- 행위(method)는 URL 에 포함 X

- URI 마지막 문자로 슬래시 (/) 포함 X

- 하이픈(-) 은 URI 가독성을 높이는 데 사용

- 밑줄 (_) 은 URI 에 사용하지 않음

- URI 경로에는 소문자

- 파일 확장자는 URI 에 포함시키지 않는다.
ex) [http://restapi.example.com/members/soccer/345/photo.jpg](http://restapi.example.com/members/soccer/345/photo.jpg) (X)



<aside>
📌 **리소스간 관계 표현 방법**

```
/리소스명/리소스 ID/관계가 있는 다른 리소스명

* 일반적
GET : /users/{userid}/devices (일반적으로 소유 ‘has’의 관계를 표현할 때)

* 구체적
GET : /users/{userid}/likes/devices (관계명이 애매하거나 구체적 표현이 필요할 때)
```









## HTTP Headers

- Content-Type
    - application/json 을 우선 제공









## HTTP Methods

- `GET`, `POST`, `PUT`, `DELETE` 는 반드시 제공
- `OPTIONS`
    - 현재 End-Point 가 제공가능한 API Method 응답
    
    ```java
    OPTIONS /users/hak
    
    HTTP/1.1 200 OK
    Allow: GET,PUT,DELETE,OPTIONS,HEAD
    ```
    
- `HEAD`
    - 요청에 대한 Header 정보만 응답
    - Body 없음
    
    ```java
    HEAD /users
    
    HTTP/1.1 200 OK
    Content-Type: application/json
    Content-Length: 120
    ```
    
- `PATCH`
    - PUT 대신 PATCH method 사용
    - 자원의 일부를 수정할 때는 PATCH가 목적에 맞는 method
    
    ```java
    // PUT 의 경우 바뀌지 않는 속성도 함께 보내야 함
    
    PUT /users/1
    {
        "name": "hak"
        "level": 11
    }
    
    ## PATCH 를 이용하여 변경하는 속성만 전달 ##
    
    // PATCH 요청
     PATCH /users/1
    {
        "level": 11
    }
    
    // PATCH 응답
    HTTP/1.1 200 OK
    {
        "name": "hak",
        "level": 11
    }
    ```
    







## HTTP 상태코드

정확한 응답의 상태코드만으로도 많은 정보 전달 가능

- 성공 응답 : `2XX`
- 실패 응답 : `4XX`
- `5XX` 에러는 절대 사용자에게 나타내선 안됨.

| 상태코드 |  |
| --- | --- |
| 200 [OK] | 클라이언트의 요청을 정상적으로 수행함 |
| 201 [Created] | 클라이언트가 어떠한 리소스 생성을 요청, 해당 리소스가 성공적으로 생성됨(POST를 통한 리소스 생성 작업 시) |
| 202 [Accepted] | 클라이언트에게 요청을 받은 후, 요청은 유효하나 서버가 아직 처리하지 않은 경우에 응답 (비동기 작업) |
| 204 [No Content] | 응답 body 가 필요 없는 자원 삭제 요청(DELETE) 같은 경우 응답 |
| 400 [Bad Request] | 클라이언트의 요청이 부적절 할 경우 사용하는 응답 코드<br /> 파라미터의 위치(path, query, body) , 사용자 입력 값, 에러 이유 등을 반드시 알린다. |
| 401 [Unauthorized] | 인증<br />클라이언트가 인증되지 않은 상태에서 보호된 리소스를 요청했을 때 사용하는 응답 코드<br/>(로그인 하지 않은 유저가 로그인 했을 때, 요청 가능한 리소스를 요청했을 때) |
| 403 [Forbidden] | 권한<br/>유저 인증상태와 관계 없이 응답하고 싶지 않은 리소스를 클라이언트가 요청했을 때 사용하는 응답 코드<br/>해당 요청은 유효하나 서버 작업 중 접근 허용되지 않은 자원을 조회하려는 경우 |
| 404 [Not Found] |  |
| 405 [Method Not Allowed] | 클라이언트가 요청한 리소스에서는 사용 불가능한 Method를 이용했을 경우 사용하는 응답 코드 |
| 409 [Conflict] | 해당 요청의 처리가 비즈니스 로직상 불가능하거나 모순이 생긴 경우 |
| 429 [Too Many Requests] |  |
| 301 | 클라이언트가 요청한 리소스에 대한 URI가 변경 되었을 때 사용하는 응답 코드<br />(응답 시 Location header에 변경된 URI 를 적어 줘야 합니다.) |
| 500 | 서버에 문제가 있을 경우 사용하는 응답 코드 (apache, nginx) <br />절대 사용자에게 나타내지 말 것 |





[참조]( https://sanghaklee.tistory.com/61)
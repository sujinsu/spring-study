# spring-study

> 궁금한 점이 생길때마다 목록 업데이트 
>
> 매주 끌리는 주제를 골라 정리해볼 계획 🔥
>
> 정리 시 아래의 내용 ✋

| 주제                                                   |        |
| ------------------------------------------------------ | ------ |
| :white_check_mark:  백엔드 기초                        | WEEK01 |
| :white_check_mark:  JPA                                | WEEK02 |
| :white_check_mark:  DAO, DTO, Entity and VO            | WEEK03 |
| :white_check_mark:  Wrapper Class : 형변환 & 제네릭    | WEEK04 |
| :white_check_mark:  메시지컨버터와 Spring mvc          | WEEK05 |
| :white_check_mark:  객체지향 디자인 패턴               | WEEK06 |
| :white_check_mark:  MockMvc                            | WEEK07 |
| :white_check_mark:  Api Gateway Rate Limit             | WEEK08 |
| :white_check_mark:  APM & DPM                          | WEEK09 |
| :white_check_mark: Swagger                             | WEEK10 |
| :white_check_mark: Spring Validation                   | WEEK11 |
| :white_check_mark: Spring AOP                          | WEEK12 |
| :white_check_mark: REST API                            | WEEK13 |
| :white_check_mark: Spring Batch                        | WEEK14 |
| :white_check_mark: DB Connection Pool & Context Switch | WEEK15 |
| :white_check_mark: Error & Exception                   | WEEK16 |
| :white_large_square:                                   |        |





# :pushpin: 궁금한 것들

### - 이중콜론 연산자 (::)

- `[인스턴스]::[메소드명(또는 new)]`

```java
# 리스트를 순회하면서 println을 하고자 할 때

import java.util.Arrays;
import java.util.List;
public class DoubleColonTest {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("김갑순", "김갑돌");
        // x를 건네고 받는 과정에서 x를 두 번 적게 된다.
        names.forEach(x -> System.out.println(x));
        // 아예 x들을 빼버리고 아래와 같이 작성할 수 있다.
        names.forEach(System.out::println);
    }
}
```

```java
# Stream의 map()을 사용해 새로운 스트림을 생성하고자 할 때

import java.util.Arrays;
import java.util.List;
public class DoubleColonTest {
    public String addNim(String s) {
        return s + "님";
    }
    public static void main(String[] args) {
        List<String> names = Arrays.asList("김갑순", "김갑돌");;
        DoubleColonTest dct = new DoubleColonTest();
        names.stream().map(x -> dct.addNim(x)).forEach(System.out::println); // 적용 전
        names.stream().map(dct::addNim).forEach(System.out::println); // 적용 후
    }
}
```





[참고1](http://yoonbumtae.com/?p=2776)

---

### - 즉시 로딩과 지연 로딩

- 지연로딩 (LAZY)

  - `@ManyToOne` 관계 매핑에서  `FetchType.LAZY`  :arrow_right: 프록시로 조회

  - SELECT 쿼리 따로따로  -> 네트워크 따로 타서 조회 -> 손해

    ex) 대부분 비즈니스 로직에서 Member 와 Team 을 같이 사용 :arrow_right:  EAGER 사용

- 즉시 로딩 (EAGER)

  - 대부분 JPA 구현체는 조인 사용 :arrow_right: SQL 한번에 조회
  - 실행결과 프록시 객체 X, 실제 객체 O



- 주의할 점

  - 실무에서는 LAZY를 쓰자! 테이블이 많아 예상치 못한 SQL 발생

  - JPQL에서 `N+1 `문제 발생 

    - 쿼리 1개 날렸는데 추가 쿼리 N개

  - JPQL fetch join이나, 엔티티 그래프 기능으로 해결하자.

    

[참고1](https://ict-nroo.tistory.com/132)



---

### - @Transactional

- CGLIB

- @Transactional 이 클래스 내지 메서드에 붙을 때 Spring은 해당 메서드에 대한 프록시 생성



[참고1](https://kafcamus.tistory.com/30)

[참고2](https://goddaehee.tistory.com/167)

---

### - @RequestBody & @RequestParam

- RequestBody

  - post 요청
- RequestParam
  - get 요청
  - 데이터를 저장하는 이름으로 메서드 변수명 설정
  - 변수 할당 사용 용이
  - url 상에서 데이터를  찾음


|                         | RequestBody | RequestParam |
| ----------------------- | ----------- | ------------ |
| 객체 생성               | 가능        | 불가능       |
| 각 변수별로 데이터 저장 | 불가능      | 가능         |



[참고1](https://ocblog.tistory.com/49)

---

### - RequestBody(요청) & ResponseBody(응답)





[참고1](https://devbox.tistory.com/entry/Spring-RequestBody-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98%EA%B3%BC-ReponseBody-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98%EC%9D%98-%EC%82%AC%EC%9A%A9)





---

### - WebSecurity & HttpSecurity

WebSecurity, HttpSecurity에 모두 설정을 한 경우 WebSecurity가 HttpSecurity보다

우선적으로 고려되기 때문에 HttpSecurity에 인가 설정은 무시된다.

-  Web
   - antMatchers에 파라미터로 넘겨주는 endpoints는 Spring Security Filter Chain을 거치지 않기 때문에 '인증' , '인가' 서비스가 모두 적용되지 않는다.
   - 일반적으로 로그인 페이지, public 페이지 등 인증, 인가 서비스가 필요하지 않은 endpoint에 사용
-  Http
   - antMatchers에 있는 endpoint에 대한 '인증'을 무시
   - 취약점에 대한 보안이 필요할 경우 HttpSecurity 설정을 사용

```java
@Override
public void configure(WebSecurity web) throws Exception {
    web
        .ignoring()
        .antMatchers("/publics/**");
}

@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/publics/**").hasRole("USER") // no effect
        .anyRequest().authenticated();
}
```



[참고1](https://velog.io/@gkdud583/HttpSecurity-WebSecurity%EC%9D%98-%EC%B0%A8%EC%9D%B4)





---

### - 인증 vs 인가

- hasanyRole 
- swagger Authorize

다음은 **antMatchers()** 로 지정할 수 있는 항목들입니다.

- hasRole() or hasAnyRole()
  특정 권한을 가지는 사용자만 접근할 수 있습니다.
- hasAuthority() or hasAnyAuthority()
  특정 권한을 가지는 사용자만 접근할 수 있습니다.
- hasIpAddress()
  특정 아이피 주소를 가지는 사용자만 접근할 수 있습니다.
- permitAll() or denyAll()
  접근을 전부 허용하거나 제한합니다.
- rememberMe()
  리멤버 기능을 통해 로그인한 사용자만 접근할 수 있습니다.
- anonymous()
  인증되지 않은 사용자가 접근할 수 있습니다.
- authenticated()
  인증된 사용자만 접근할 수 있습니다.



[참고1](https://devuna.tistory.com/59) :   antMatchers







---

### -  swagger Authorize

[참고1](https://lemontia.tistory.com/1027)







---

### - 서블릿 & JSP

- 서블릿
  - 동적 웹 애플리케이션 컴포넌트
  - HTML태그가 삽입된 자바 코드
  - 자바언어 -> 확장자 : .java 
  - html
  - Java Thread
  - MVC 중 Controller
  - UDP 보다 느림

- JSP
  - Java 코드가 들어있는 HTML 코드
  - 서블릿의 단점을 보완하고자 만든 서블릿 기반의 스크립트 기술
  - 프리젠테이션 층을 담당





[참고1](https://mangkyu.tistory.com/14)

[참고2](https://m.blog.naver.com/acornedu/221128616501)



---

### -  Querydsl

[참고1](https://tecoble.techcourse.co.kr/post/2021-08-08-basic-querydsl/)

- [Criteria Query(객체지향 쿼리 빌더) ](https://coding-start.tistory.com/90)





---

### - 디자인 패턴



---

### 자바 직렬화



[참고1](https://devlog-wjdrbs96.tistory.com/268)

---

인바운드 아웃바운드

업스트림 다운스트림

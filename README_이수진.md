# spring-study

> 궁금한 점이 생길때마다 목록 업데이트 
>
> 매주 끌리는 주제를 골라 정리해볼 계획 🔥
>
> 정리 시 아래의 내용 ✋

| 주제                                                         |        |
| ------------------------------------------------------------ | ------ |
| :white_check_mark:  백엔드 기초                              | WEEK01 |
| :white_check_mark:  JPA                                      | WEEK02 |
| :white_large_square:  QueryDSL                               |        |
| :white_large_square:  DAO, DTO, Entity and VO                |        |
| :white_large_square:  NotNull, NotBlank                      |        |
| :white_large_square:  이중콜론 연산자 (::)                   |        |
| :white_large_square:  즉시 로딩과 지연 로딩                  |        |
| :white_large_square:  @Transactional                         |        |
| :white_large_square:  @RestController & @Controller          |        |
| :white_large_square:  @RequestBody & @ ReqeustParam & @ModelAttrubute |        |
| :white_large_square:  WebSecurity & HttpSecurity             |        |
| :white_large_square:  인증 vs 인가                           |        |
| :white_large_square:  Swagger Authorize                      |        |
| :white_large_square:  서블릿 & jsp                           |        |
| :white_large_square:  디자인 패턴                            |        |







# :pushpin: 궁금한 것들

###  - DAO, DTO, Entity and VO

- **DAO** (Data Access Object)
  - repository package
  - 실제 DB에 접근하는 객체 (Persistence Layer) : DB에 data를 CRUD 하는 계층
  - SQL 사용 (개발자 직접 코딩)
- **DTO** (Data Transfer Object)
  - dto package
  - 계층간 데이터 교환을 위한 객체 (Java Beans)
  - DB에서 데이터를 얻어 Service나 Controller로 보낼 때 사용하는 객체
  - 로직 X, 순수한 데이터 객체, getter/setter 메서드만을 가짐
  - Request, Response 용 DTO 는 View 를 위한 클래스
    - toEntity() 를 통해 DTO에서 필요한 부분 이용해 엔티티 생성
    - controller layer에서 Response DTO 형태로 client 전달
  - cf) `VO(Value Object) vs DTO`
    - **VO** : 특정한 비즈니스값을 담는 객체
    - **DTO** : Layer간의 통신 용도로 오고가는 객체
    - VO는 DTO와 동일 개념이지만, read only 속성을 갖는다

- **Entity**
  - domain package
  - 실제 DB 테이블과 매칭될 클래스
  - @Entity, @Column, @Id 등을 이용



- Entity클래스와 DTO 클래스를 분리하는 이유
  - View Layer와 DB Layer를 철저하게 분리하기 위해
  - 테이블과 매핑된 Entity 변경 -> 여러 클래스에 영향
  - View와 통신하는 DTO클래스(Request/Response) 자주 변경되므로 분리
  - DTO는 Domain Model을 복사한 형태 + 다양한 Presentation Logic 추가
  - Domain Model 객체는 Persistent 만을 위해 사용

[참고](https://gmlwjd9405.github.io/2018/12/25/difference-dao-dto-entity.html)

---

### - NotNull, NotBlank





---

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

- @Transactional 이 클래스 내지 메서드에 붙을 때 Spring은 해당 메서드에 대한 프록시 생성



[참고1](https://kafcamus.tistory.com/30)

[참고2](https://goddaehee.tistory.com/167)



---

### - @RestController & @Controller

- **@RestController** 
  - @Controller + @ResponseBody 
  - json 형태로 객체 데이터 반환

- **@Controller** 
  - view 반환을 위해 사용
  - View를 반환하기 위해 ViewResolver가 사용
    ViewResolver에서 설정에 맞는 View를 찾아 렌더링
  - `@ResponseBody` 사용
    - 자바 객체를 HTTP 요청의 body 내용으로 매핑하는 역할



[참고1](https://joomn11.tistory.com/53)

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

![image-20220331172632061](../../../../Downloads/Rhea- 통합모니터링 백엔드 개발을 위한 준비.assets/image-20220331172632061.png)



![image-20220331172729343](../../../../Downloads/Rhea- 통합모니터링 백엔드 개발을 위한 준비.assets/image-20220331172729343.png)



![image-20220331172750057](../../../../Downloads/Rhea- 통합모니터링 백엔드 개발을 위한 준비.assets/image-20220331172750057.png)



[참고1](https://mangkyu.tistory.com/14)

[참고2](https://m.blog.naver.com/acornedu/221128616501)



---

### -  Querydsl

[참고1](https://tecoble.techcourse.co.kr/post/2021-08-08-basic-querydsl/)

- [Criteria Query(객체지향 쿼리 빌더) ](https://coding-start.tistory.com/90)





---

### - 디자인 패턴


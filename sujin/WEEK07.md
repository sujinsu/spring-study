# MockMVC

> 테스트에 필요한 기능만 가지는 가짜 객체를 만들어서 
>
> 애플리케이션 서버에 배포하지 않고도 요청 및 전송, 응답 기능을 제공해주는
>
> 스프링 MVC 동작을 재현할 수 있는 유틸리티 클래스



- 사용 방법
  -  build.gradle 에 `spring-boot-starter-test` 의존성 추가





### 💡 `WebMvcTest` vs `AutoConfigureMockMvc`

> 공통점 : 컨트롤러 테스트시, 서블릿 컨테이너 모킹
>
> cf ) 실제 객체와 비슷한 객체를 만드는 것을 모킹(Mocking)

| WebMvcTest                                                   | AutoConfigureMockMvc                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| 웹에서 테스트 힘든 컨트롤러를 테스트                         | 비슷                                                         |
| 선언할 경우 @Controller,@ControllerAdvice등을 사용 가능<br />\- 단, @Service, @Component, @Repository 등은 사용 불가 | 컨트롤러 뿐 아니라 @Service, @Repository 붙은 객체 모두 메모리에 올림<br />>> 간단하게 테스트할땐 WebMvc |
| 웹 상에서 요청, 응답에 대해 테스트 & 시큐리티, 필터까지 자동 테스트 & 수동으로 추가/ 삭제 |                                                              |
| Web에 집중할 수 있는 스프링 테스트 어노테이션                | MockMVC보다 세밀하게 제어하기 위해 사용                      |
| @SpringBootTest와 같이 사용될 수 없다.                       | 전체애플리케이션 구성 로드시 SpringBootTest 와 AutoConfigureMockMvc 사용 |







### 📌 **@AutoCOnfigureMockMvc**

```
< @SpringBootTest >
웹 애플리케이션 테스트를 지원하는 webEnvironment 속성이 있다. 
기본값 WebEnvironment.MOCK 설정 >> 서블릿 컨테이너가 모킹

- WebEnvironment.MOCK : 아무런 설정이 없을 시 적용되는 디폴트 설정이다. mock 서블릿 환경으로 내장톰캣이 구동되지 않는다.(브라우저에서 접속되지 않는다.)
- WebEnvironment.RANDOM_PORT : 스프링부트를 직접 구동시킨 것처럼 내장톰캣이 구동되나 랜덤포트로 구동된다.
- WebEnvironment.DEFINED_PORT : 정의된 포트로 내장톰캣이 구동된다.
- WebEnvironment.NONE : WebApplicationType.NONE으로 구동된다



🎈 의존성 주입 🎈
@SpringBootTest(webEnvironment=WebEnvironment.MOCK) 설정으로 모킹한 객체를 
의존성 주입받으려면 **@AutoCOnfigureMockMvc**를 클래스 위에 추가 해야한다.

```



## 대표 예시

```java
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @AutoConfigureMockMvc : 의존성 주입
// @SpringBootTest : 기본값 WebEnvironment.MOCK
@AutoConfigureMockMvc
@SpringBootTest
class MockMvcTest(){
    
   @Autowired
    MockMvc mockMvc;

    @Test
    public void testController() throws Exception{

        mockMvc.perforem(get("test"))
            // 요청 설정 메서드
            .param("query", "부대찌개")
            .cookie("쿠키 값")
            .header("헤더 값:)
            .contentType(MediaType.APPLICATION.JSON)
            .content("json으로");
                    
            // 검증 메서드
            .andExpect(status().isOk()) 
            .andExpect(content().string("expect json값"))
            .andExpect(view().string("뷰이름"));
                
            // 기타 메소드
            .andDo(print()) 
        	.andDo(log());
    } 
}


```





### 요청 설정 메서드

- param / params : 쿼리 스트링 설정
- cookie : 쿠키 설정
- requestAttr : 요청 스코프 객체 설정
- sessionAttr : 세션 스코프 객체 설정
- content : 요청 본문 설정
- header / headers : 요청 헤더 설정
- contentType : 본문 타입 설정



### 검증 메서드

- status : 상태 코드 검증
- header : 응답 header 검증
- content : 응답 본문 검증
- cookie : 쿠키 상태 검증
- view : 컨트롤러 반환한 뷰 이름 검증
- redirectedUrl(Pattern) : 리다이렉트 대상의 경로 검증
- model : 스프링 MVC 모델 상태 검증
- request : 세션 스코프, 비동기 처리, 요청 스코프 상태 검증
- forwardedUrl : 이동 대상의 경로 검증



### 기타 메서드

- andDo() : print, log 를 사용할 수 있는 메서드
- print() : 실행 결과를 지정해준 대상으로 출력
  - default = System.out
- log() : 실행 결과를 디버깅 레벨로 출력, 레벨은 org.springframework.test.web.servlet.result



### 검증 속성

- 응답 상태 코드

  - isOk() : 상태코드 200인지 확인

  - isNotFound() : 상태코드 404인지 확인

  - isMethodNotAllowed() : 상태코드 405인지 확인

  - isInternalServerError() : 상태코드 500인지 확인

  - is(int status) : 임의로 지정한 상태 코드인지 확인

- 모델 검증

  ex ) andExpect(model().attribute("userId","fly123"))

  - attribteExists(String key) : key에 해당하는 데이터가 model에 있는지 검증
  - attribute(String key, Object value1) : key에 해당하는 value가 입력해준 value1과 일치하는지 

- 뷰 이름 검증

  ex ) andExpect(view().name("index"))

  -  name(String viewName) : 응답된 뷰 이름이 viewName과 일치한지 확인

- 리다이렉트 검증

  ex ) andExpect(redirectedUrl("/foodList?name=부대찌개"))

  - redirectUrl : 해당 url로 리다이렉트 되었는가?









---

## **GET**

```java
@RestController
public class MyController {

  @GetMapping("/")
  public String MyIntroduce(@RequestParam String name, @RequestParam String id){
    return name + "입니다 " + id;
  }
}
```
```java
@RunWith(SpringRunner.class)
@WebMvcTest(MyController.class)
public class MyControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void 테스트_MyIntroduce() throws Exception {

    MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

    info.add("name", "수진");
    info.add("id", "sujin");

    mockMvc.perform(get("/")       
        .params(info))            
        .andExpect(status().isOk())   
        .andExpect(content().string("수진입니다 sujin"))
        .andDo(print());            
  }
```

- `perform()`

  - 요청을 전송하는 역할
  - RequestBuilder 객체를 인자로 받음
    - RequestBuilder 객체는 MockMvcRequestBuilder의 정적 메소드를 이용해서 생성
  - 결과 : ResultActions 객체
    - 리턴값 검증 및 확인 가능 >> andExpect() 메소드 이용

- `get("/")`

  - HTTP 메소드 결정 : get(), post(), put(), delete()
  - 인자 : 경로

- `params(info)`

  - 키=값 의 파라미터 전달
  - 여러개일 때 params(), 하나일때 param()

- ` andExpect()`

  - 상태코드 ( `status()`)

    - `isOk()` : 200
    - `isNotFound()` : 404
    - `isMethodNotAllowed()`: 405
    - `isInternalServerError()` : 500
    - `is(int status)` : status 상태 코드

  - 뷰 (view())

    - 리턴하는 뷰 이름을 검증

      ex) view().name("index") : 리턴하는 뷰 이름이 index인가?

  - 리다이렉트 ( `redirect()` )

    - 리다이렉트 응답 검증

      ex) redirectUrl("/index") : '/index' 로 리다이렉트 되었는가

  - 모델 정보( `model()`)

    - 컨트롤러에서 저장한 모델들의 정보 검증

  - 응답 정보 검증 (`content()`)

    - 응답에 대한 정보 검증

- `andDo(print())`

  - 요청/응답 전체 메세지 확인



## **POST**

```java
public class Info {

  private String name;
  private String id;

  public Info(String name, String id) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }
```
```java
  public String getId() {
    return id;
  }
}
@RestController
@RequestMapping("/mock")
public class MyController {

  // ...

  @PostMapping("/")
  public String blogPost(@RequestBody Info info){
    return info.getName() + "입니다. " + info.getId();
  }

}

```
```java
@RunWith(SpringRunner.class)
@WebMvcTest(MyController.class)
public class MyControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  // ...

  @Test
  public void 테스트_POST() throws Exception {

    String content = objectMapper.writeValueAsString(new Info("수진","sujin"));

    mockMvc.perform(post("/mock")
        .content(content)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("수진입니다. sujin"))
        .andDo(print());
  }
```







[참고1](https://elevatingcodingclub.tistory.com/61)

[참고2](https://velog.io/@jkijki12/Spring-MockMvc)
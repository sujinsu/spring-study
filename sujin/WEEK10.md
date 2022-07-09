# Swagger



Swagger 버전 2 : http://localhost:8080/swagger-ui.html





### 기본 세팅

https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui : 2.9.2 버전 사용량 많음

- 의존성 추가

```
    // swagger
    implementation ('io.springfox:springfox-swagger2:2.9.2')
    implementation ('io.springfox:springfox-swagger-ui:2.9.2')
```







### API 문서 자동화



- domain
  - @ApiParam(value="" , required="")
    - value : 저장되어야 할 값에 대한 설명 명시
    - required : 필수 여부 지정



- model

```
@ApiModel
public class SearchVO {

    @ApiModelProperty(value="아이디")
    private String id;

    @ApiModelProperty(value="이메일")
    private String email;

    @ApiModelProperty(value="페이지")
    private int page;
}
```





- Controller
  - @ApiOperation(value="", notes="")

```java
  @ApiOperation(value = "사용자 정보등록" , notes = "상세한 사용자 정보에 대해서 출력한다.")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "email", value = "사용자의 이메일을 입력한다", required = false, dataType = "SearchVO", paramType = "string", defaultValue = ""),
          @ApiImplicitParam(name = "id", value = "사용자의 id값", required = false, dataType = "SearchVO", paramType = "string", defaultValue = ""),
          @ApiImplicitParam(name = "page", value = "페이지 숫자", required = false, dataType = "SearchVO", paramType = "int", defaultValue = ""),
  })
  @RequestMapping(method = RequestMethod.POST, path = "/postRequest")
  public SearchVO postRequest(@RequestBody SearchVO searchVO){
      return searchVO;
  }
```





- @Api
  - 해당 클래스가 Swagger 리소스라는 것을 명시
  - value
    - 태그를 작성
  - tags
    - 사용하여 여러 개의 태그를 정의할 수도 있습니다.
- @ApiOperation
  - 한 개의 operation(즉 API URL과 Method)을 선언
    - value
      - API에 대한 간략한 설명(제목)을 작성
    - notes
      - 더 자세한 설명을 작성.
- @ApiResponse
  - operation의 가능한 response를 명시
    - code
      - 응답코드를 작성합니다.
    - message
      - 응답에 대한 설명을 작성합니다.
    - responseHeaders
      - 헤더를 추가할 수도 있습니다.
- @ApiParam
  - 파라미터에 대한 정보를 명시합니다.
    - value
      - 파라미터 정보를 작성합니다.
    - required
      - 필수 파라미터이면 true, 아니면 false를 작성합니다.
    - example
      - 테스트를 할 때 보여줄 예시를 작성합니다.







- SwaggerConfig
  - @EnableSwagger2   : Swagger2 버전을 활성화 하겠다는 어노테이션
  - Docket : Swagger 설정의 핵심이 되는 Bean (API 자체에 대한 스펙은 컨트롤러에서 작성)
    https://victorydntmd.tistory.com/341 





**Docket**

- useDefaultResponseMessages()
  - false로 설정하면, swagger에서 제공해주는 응답코드 ( 200,401,403,404 )에 대한 기본 메시지를 제거합니다.
  - 불필요한 응답코드와 메시지를 제거하기 위함이며, 컨트롤러에서 명시해줄 것입니다.
- groupName()
  - Docket Bean이 한 개일 경우
    - 기본 값은 default이므로, 생략 가능
  - 여러 Docket Bean을 생성했을 경우
    - groupName이 충돌하지 않아야 하므로, 여기서는 각 Docket Bean의 버전을 명시해줬습니다.
- select()
  - ApiSelectorBuilder를 생성합니다.
- apis()
  - api 스펙이 작성되어 있는 패키지를 지정합니다.
  - 즉, 컨트롤러가 존재하는 패키지를 basepackage로 지정하여, RequestMapping( GetMapping, PostMapping ... )이 선언된 API를 문서화합니다.
  - ex) apis(ReqiestHandlersSelectors.basePackages(“kr.co.kds.bard.management”))
    - 패키지 이하를 모두 적용
  - @ApiIgnore 어노테이션을 추가하여 제외시킬 수 있음.
- paths()
  - apis()로 선택되어진 API중 특정 path 조건에 맞는 API들을 다시 필터링하여 문서화합니다.
- apiInfo()
  - 제목, 설명 등 문서에 대한 정보들을 보여주기 위해 호출합니다.
  - 파라미터 정보는 다음과 같습니다.
- public ApiInfo( title, description, version, termsOfServiceUrl, contact, license, licenseUrl, vendorExtensions )
- groupName()
  - Docket Bean이 한 개일 경우 생략해도 상관없으나, 둘 이상일 경우 충돌을 방지해야 하므로 설정해줘야 한다.





https://velog.io/@banjjoknim/Swagger 

 https://victorydntmd.tistory.com/341 

- response 전역 설정

https://m.blog.naver.com/jogilsang/222112773633 









### UI Config

http://javadox.com/io.springfox/springfox-swagger-common/2.9.2/springfox/documentation/swagger/web/UiConfigurationBuilder.html 



cf) deepLinking : https://swagger.io/docs/open-source-tools/swagger-ui/usage/deep-linking/





```
Could not resolve: io.springfox:springfox-swagger2
Could not resolve: io.springfox:springfox-swagger-ui
Could not resolve: io.springfox:springfox-swagger2
Could not resolve: io.springfox:springfox-swagger-ui

Deprecated Gradle features were used in this build, making it incompatible with Gradle 7.0.
```
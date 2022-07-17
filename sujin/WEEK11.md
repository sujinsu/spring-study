### 유효성 검사 목적

올바르지 않은 데이터를 걸러내고 보안을 유지

Client의 데이터는 조작이 쉬움 & 모든 데이터가 정상적인 방식으로 들어오지 X

|                  | 타입                                                         | 제약 조건                                                    | 설명                                                         |
| ---------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| @AssertTrue      | Boolean, boolean                                             | 값이 항상 True 여야 한다.                                    |                                                              |
| @AssertFalse     | Boolean, boolean                                             | 값이 항상 False 여야 한다                                    |                                                              |
| @DecimalMax      | 실수 제외 숫자 클래스.                                       | 지정된 최대 값보다 작거나 같아야 하는 숫자이다.              | String : value (max 값을 지정한다.)                          |
| @DecimalMin      | 실수 제외 숫자 클래스.                                       | 지정된 최소 값보다 크거나 같아야하는 숫자이다.               | String : value (min 값을 지정한다.)                          |
| @Digits          | BigDecimalBigIntegerCharSequencebyte, short, int, long, 이에 대응하는 Wrapper 클래스 | 허용된 범위 내의 숫자이다.                                   | int : integer (이 숫자에 허용되는 최대 정수 자릿수)int : fraction (이 숫자에 허용되는 최대 소수 자릿수) |
| @Email           | null도 valid로 간주된다.                                     | 올바른 형식의 이메일 주소여야한다.                           |                                                              |
| @Future          | 시간 클래스                                                  | Now 보다 미래의 날짜, 시간                                   |                                                              |
| @FutureOrPresent | 시간 클래스                                                  | Now의 시간이거나 미래의 날짜, 시간                           |                                                              |
| @Max             | 실수 제외 숫자 클래스.                                       | 지정된 최대 값보다 작거나 같은 숫자이다.                     | long : value (max 값을 지정한다)                             |
| @Min             | 실수 제외 숫자 클래스.                                       | 지정된 최소 값보다 크거나 같은 숫자이다.                     | long : value (min 값을 지정한다)                             |
| @Negative        | 숫자 클래스                                                  | 음수인 값이다.                                               |                                                              |
| @NegativeOrZero  | 숫자 클래스                                                  | 0이거나 음수인 값이다                                        |                                                              |
| @NotBlank        |                                                              | null 이 아닌 값이다.공백이 아닌 문자를 하나 이상 포함한다    |                                                              |
| @NotEmpty        | CharSequence,Collection, Map, Array                          | null이거나 empty(빈 문자열)가 아니어야 한다.                 |                                                              |
| @NotNull         | 어떤 타입이든 수용한다.                                      | null 이 아닌 값이다.                                         |                                                              |
| @Null            | 어떤 타입이든 수용한다.                                      | null 값이다.                                                 |                                                              |
| @Past            | 시간 클래스                                                  | Now보다 과거의 날짜, 시간                                    |                                                              |
| @PastOrPresent   | 시간클래스                                                   | Now의 시간이거나 과거의 날짜, 시간                           |                                                              |
| @Pattern         | 문자열                                                       | 지정한 정규식과 대응되는 문자열이어야한다. Java의 Pattern 패키지의 컨벤션을 따른다 | String : regexp (정규식 문자열을 지정한다)                   |
| @Positive        | 숫자 클래스                                                  | 양수인 값이다                                                |                                                              |
| @PositiveOrZero  | 숫자 클래스                                                  | 0이거나 양수인 값이다.                                       |                                                              |
| @Size            | CharSequence,Collection, Map, Array                          | 이 크기가 지정된 경계(포함) 사이에 있어야한다.               | int : max (element의 크기가 작거나 같다)int : min (element의 크기가 크거나 같다) |

|      | NotNull | NotEmpty | NotBlank |
| ---- | ------- | -------- | -------- |
| null | X       | X        | X        |
| “”   | O       | X        | X        |
| “ “  | O       | O        | X        |

cf) NotEmpty & NotBlank 는 String 타입에만 적용



```java
@Test
public void valid_check() {
    //given
    UserLoginRequestDto user = UserLoginRequestDto.builder()
                                        .name(null)
                                        .email("")
                                        .phone(" ")
                                        .build();
    //when
    Set<ConstraintViolation<UserLoginRequestDto>> violations = validator.validate(user);
		// validations 에 잘못된 값이라 판단되면 추가

    //then
    assertThat(violations.size()).isEqualTo(1);
		// NotNull의 경우 1
		// NotEmpty의 경우 2
		// NotBlank의 경우 3
}
```

- 의존성 추가

```java
# build.gradle

implementation group: 'org.springframework.boot', name: 'spring-boot-starter-validation', version: '2.5.6'
```

<aside> 💡 **@Valid, @Validated 차이**

@Valid는 Java 에서 지원해주는 어노테이션 @Validated는 Spring에서 지원해주는 어노테이션입니다.

@Validated는 @Valid의 기능을 포함하고, 유효성을 검토할 그룹을 지정할 수 있는 기능을 추가로 가지고 있습니다.

**springboot가 버전업을 하면서 web 의존성안에 있던 constraints packeage가 모듈로 빠짐.**

</aside>

## @Valid

> JSR-303 표준 스펙 제약조건이 부여된 객체에 대해 빈 검증기를 이용해 검증

- RestController 를 이용하여 @RequestBody 객체를 사용자로부터 가져올 때, 들어오는 값들을 검증

> 

- 사용 예시
  - entity 에서 어노테이션
  - controller 에서 `@Valid`  추가 → 유효성 검사 (`ArgumnetResolver`)

```java
public class Member {

    @NotNull(message = "id는 필수 값입니다.")
    @Size(min = 5, max = 10)
    private String id;

    @Max(value = 25, message = "25세 이하만 가능합니다.")
    @Min(value = 18, message = "18살 이상만 가능합니다.")
    private int age;

    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+")
    private String email;
    
}
@Controller
@RequestMapping(value = "/member")
public class TestController {
    
    @PostMapping
    @ResponseBody
    public ResponseEntity saveMember(@Valid Member member,Errors errors, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        
        /*
        	save Memeber
        */
        
        return ResponseEntity.ok(member);
    }
}
```

- ```
  Errors
  ```

   객체 : 검사 오류에 대한 정보를 저장하고 노출

  - errors.hasErrors() : 유효성 검사에 실패한 필드가 있는지 확인

## @Validated

> AOP 기반으로 메소드 요청을 인터셉터하여 처리 컨트롤러, 서비스, 레포지토리 등 계층에 무관하게 스프링 빈이라면 유효성 검증을 진행 기본적으로 @Valid와 기능이 같지만, **속성 제약조건에 대한 그룹을 만들어 적용**

- controller - api의 request객체 앞 `@validated` 어노테이션 추가

```java
@PostMapping
    public ResponseEntity<?> createUSer(@Validated 
					@RequestBody final UserCreateRequestDto userCreateRequestDto, 
					BindingResult bindingResult){
        .
				.
				.
    }

@RequestMapping(value = "/user/login", method = RequestMethod.POST)
public ResponseEntity login(@RequestBody @Validated(ValidationGroups.group1.class) User user, BindingResult bindingResult) {
    // do something
}
```

- 검증 그룹 인터페이스 생성

```java
public interface UserValidationGroup {} 
public interface AdminValidationGroup {}
```

- 제약조건에 적용 그룹 지정
# Java Lambda

- 자바 8에서 람다식(Lambda Expression) 지원 → 큰 변화  like Generics



## 람다식(Lambda Expression)

- 병렬처리, 이벤트 처리, 스트림 처리 등
- JDK1.8에서 지원
- 함수형 프로그래밍 방식
- 1회용 익명 메소드
  - 자바는 독립적인 메소드만을 생성 X → 익명 객체 이용
- 형태 : (매개변수, …) → {실행문}

```java
// 일반 함수

public int sum(int a, int b) {
    return a + b;
}

// 람다식
(a, b) -> a + b;

// 자바 컴파일러는 람다식을 아래와 같이 익명 클래스처럼 해석

new Object() {
    int sum(int a, int b) {
          return a + b;
    }
}
```

- 리턴 타입 X,  메소드 이름 X, 클래스 정의 X → 익명 함수 (Anonymous  Function)





## 람다식 문법 (Lambda Expression Syntax)

- ```
  (매개변수 목록) -> {람다식 바디}
  ```

  - 자바는 엄격한 타입 제한 BUT 람다식 파라미터 추론 가능 시 생략 가능

  - 매개변수 하나인 경우 괄호 생략 가능

    - ex)  a -> a * a

  - 람다식은 식이 하나 → 중괄호 생략 가능

    - 중괄호 생략 시 세미콜론 X

    - BUT return 문이 있는 경우, 중괄호 생략 X

      ```java
      (a, b) -> { return a > b ? a : b }
      
      // 중괄호를 쓰고 싶지 않으면
      
      (a, b) ->  a > b ? a : b
      ```

```java
// 매개변수 1개, 반환값 X
interface YourType {
		void talk(String msg);
}

// 1. 기본 형태 : (int num) -> {System.out.println()};

YourType you = (String name) -> { System.out.printf("내이름은 %s입니다.",name); }
you.talk("홍길동");

// 2. 단일 코드 -> 실행 블럭 제거 : (int num) -> System.out.println();

YourType you = (String name) -> System.out.println(name);
you.talk("홍길동");

// 3. 단일 매개변수 -> 타입 생략 -> 변수명만 기재 : (num) -> System.out.println();

YourType you = (name) -> System.out.println(name);
you.talk("홍길동");

// 4. 매개변수가 1개일때는 매개변수를 감싸는 괄호 생략가능 : num -> System.out.println();

YourType you = name -> System.out.println(name);
you.talk("홍길동");

/////////////////////////////////////////////////////////////////////////////////
// 매겨변수 X, 반환값 X
interface MyType{
		void hello();
}

// 5. 매개변수가 없는 람다식 -> 괄호를 무조건 기재 : () -> System.out.println();

MyType my = () -> System.out.println("괄호 필수");

/////////////////////////////////////////////////////////////////////////////////
// 매개변수 2개, 반환값 X 
interface MyType {
    void hello(String name, int age);
}

// 6. 매개변수가 여러개 일때 -> 괄호로 무조건 감싸야함 : (x,y) -> System.out.println();

MyType my = (name, age) -> System.out.printf("내 이름은 %s이고 %d살이야"); 
my.hello("홍길동",19);

/////////////////////////////////////////////////////////////////////////////////
// 매개변수 X, 반환값 O
interface HisType {
    String getFood();
}

// 7. 단일코드이기 때문에 괄호도 생략 가능 : () -> {return value;}

HisType his = () -> return "food";
System.out.println( his.getFood() );

// 8. return문만 있는 단일코드는 return과 괄호를 같이 생략 가능 : () -> value;

HisType his = () -> "food";
System.out.println( his.getFood() );

////////////////////////////////////////////////////////////////////////////////
interface HisTpye {
    String sayHello(String name, int age);
}

// 9. 매개변수 2개 이상, 반환타입 O, 리턴문 단일코드 : (x,y) -> x+y;

HisType his = ( name, age ) -> String.format("안녕하세요. 제이름은 %s이고 %d살 입니다.",name, age);
System.out.println(his.sayHello("홍길동",19));
```





## 예제

- 람다를 이용한 Runnable

```java
Thread thread = new Thread(new Runnable() {

    @Override
    public void run() {
          System.out.println("Start Thread");
          Thread.sleep(1000);
          System.out.println("End Thread");
   }
});

// 람다

Thread thread = new Thread(() -> {
          System.out.println("Start Thread");
          Thread.sleep(1000);
          System.out.println("End Thread");
});
```

- 람다를 이용한 컬렉션 순회
  - list<>인터페이스에 추가된 forEach() 메소드를 이용

```java
List<String> list = new ArrayList();
list.add("Element1");
list.add("Element2");
list.add("Element3");

list.forEach(x -> System.out.println(x))
// 위 코드는 list.forEach(System.out::println) 으로 축약할 수 있음
```







# 이중 콜론 연산자

- Java8에서 추가된 메소드 참조 연산자

- 람다식에서 파라미터를 중복하여 사용하고 싶지 않을 때 사용

- 사용방법

  - `[클래스명]::[메소드명(or new)]`
  - `[인스턴스]::[메소드명(or new)]`
  - ex) User::getId
  - ex) System.out::println

  ```java
  1. 람다 표현식 () -> {} 에서만 사용 가능
  2. static 메소드인 경우 인스턴스 대신 클래스 이름으로 사용
  ```





### 예시

- 리스트 순회하며 String을 대문자로 변경

```java
List<String> testList = Arrays.asList("one", "two", "three", "four");
testList = testList
        .stream()
        .map(a -> a.toUpperCase()) // 람다 표현식 
        .collect(Collectors.toList());

testList = testList
        .stream()
        .map(String::toUpperCase) // 이중 콜론 연산자 사용 
        .collect(Collectors.toList());
```

- 특정 Dto 리스트에서 아이디만 뽑을 때

```java
@Getter
@AllArgsConstructor(staticName = "of")
public class TestDto {
    private Long id;
    private String name;
}

List<TestDto> testDtoList = new ArrayList<>();
testDtoList.add(TestDto.of(1L, "첫번째"));
testDtoList.add(TestDto.of(2L, "두번째"));

// 람다 표현식
testDtoList.stream().map(a->a.getId()).collect(Collectors.toList());
// 이중 콜론 연산자 사용 
testDtoList.stream().map(TestDto::getId).collect(Collectors.toList());
```

- static 메소드 사용 시

```java
@Getter
@AllArgsConstructor(staticName = "of")
public class NameDto {
	private String name;
}
    
List<String> testList = Arrays.asList("one", "two", "three", "four");
// 람다 표현식 
testList.stream().map(a->NameDto.of(a)).collect(Collectors.toList())
// 이중 콜론 연산자 사용 
testList.stream().map(NameDto::of).collect(Collectors.toList())
```
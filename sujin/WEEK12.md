# 정규표현식 (Regular Expression)

특정한 규칙을 가진 문자열의 집합







## 작성 방법

- JAVA API (java.util.regrex 패키지)
  
    
    
    ### Pattrern 클래스
    
    ```java
    import java.util.regex.Pattern;
    
    public class RegexExample {
    	public static void main(String[] args)  {
        
                String pattern = "^[0-9]*$"; //숫자만
                String val = "123456789"; //대상문자열
            
                boolean regex = Pattern.matches(pattern, val);
                System.out.println(regex);
        }
    }
    ```
    
    - matches() 메서드 → 일치하면 true, 그렇지 않다면 false
        - 첫번째 매개값 : 정규 표현식
        - 두번째 매개값 :  검증대상 문자열
        
    
    
    
    ```
    💡 ** Pattern 클래스 주요 메서드 **
    
    
    `compile(String regex)` : 주어진 정규표현식으로부터 패턴 생성
    
    `matcher(CharSequence input)` : 대상 문자열이 패턴과 일치할 경우 true 반환
    
    `asPredicate()` : 문자열을 일치시키는 데 사용할 수 있는 술어 작성
    
    `pattern()` : 컴파일된 정규표현식을 String 형태로 반환
    
    `split(CharSequence input)` : 문자열을 주어진 인자값 CharSequence 패턴에 따라 분리
    
    
    
    
    💡 ** Parttern 플래그 값 사용(상수) **
    
    Pattern.CANON_EQ : None표준화된 매칭 모드를 활성화합니다.
    
    Pattern.CASE_INSENSITIVE : 대소문자를 구분하지 않습니다.
    
    Pattern.COMMENTS : 공백과 #으로 시작하는 주석이 무시됩니다. (라인의 끝까지).
    
    Pattern.MULTILINE : 수식 ‘^’ 는 라인의 시작과, ‘$’ 는 라인의 끝과 match 됩니다.
    
    Pattern.DOTALL : 수식 ‘.’과 모든 문자와 match 되고 ‘\n’ 도 match 에 포함됩니다.
    
    Pattern.UNICODE_CASE : 유니코드를 기준으로 대소문자 구분 없이 match 시킵니다.
    
    Pattert.UNIX_LINES : 수식 ‘.’ 과 ‘^’ 및 ‘$’의 match시에 한 라인의 끝을 의미하는 ‘\n’만 인식됩니다.
    ```
    
    
    
    
    
    
    
    
    
    ### Matcher 클래스
    
    ```java
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;
    
    public class RegexExample {
    	public static void main(String[] args)  {
                Pattern pattern = Pattern.compile("^[a-zA-Z]*$"); //영문자만
                String val = "abcdef"; //대상문자열
    	
                Matcher matcher = pattern.matcher(val);
                System.out.println(matcher.find());
    	}
    }
    ```
    
    - 대상 문자열의 패턴을 해석 & 주어진 패턴과 일치하는지 판별할 때 주로 사용
    - find() 메서드 : 대상 문자열이 영문자인지 검증 → 맞으면 true, 그렇지 않다면 false
    
    
    
    
    
    ```
    💡 ** Matcher 클래스 주요 메서드 **
    
    matches() : 대상 문자열과 패턴이 일치할 경우 true 반환합니다.
    
    find() : 대상 문자열과 패턴이 일치하는 경우 true를 반환하고, 그 위치로 이동합니다.
    
    find(int start) : start위치 이후부터 매칭검색을 수행합니다.
    
    start() : 매칭되는 문자열 시작위치 반환합니다.
    
    start(int group) : 지정된 그룹이 매칭되는 시작위치 반환합니다.
    
    end() : 매칭되는  문자열 끝 다음 문자위치 반환합니다.
    
    end(int group) : 지정되 그룹이 매칭되는 끝 다음 문자위치 반환합니다.
    
    group() : 매칭된 부분을 반환합니다.
    
    group(int group) : 매칭된 부분중 group번 그룹핑 매칭부분 반환합니다.
    
    groupCount() : 패턴내 그룹핑한(괄호지정) 전체 갯수를 반환합니다.
    
    ```
    
    







## 정규 표현식 문법

| 정규 표현식 | 설명 |
| --- | --- |
| ^ | 문자열 시작 |
| S | 문자열 종료 |
| . | 임의의 한 문자(단 \은 넣을 수 없음) |
| * | 앞 문자가 없을 수도 무한정 많을 수도 있음 |
| + | 앞 문자가 하나 이상 |
| ? | 앞 문자가 없거나 하나 있음 |
| [] | 문자의 집합이나 범위를 나타내며 두 문자 사이는 - 기호로 범위를 나타냅니다.
[] 내에서 ^ 가 선행하여 존재하면 not을 나타낸다. |
| {} | 횟수 또는 범위를 나타냅니다. |
| ( ) | 소괄호 안의 문자를 하나의 문자로 인식 |
| | | 패턴 안에서 or 연산을 수행할 때 사용 |
| \ | 정규 표현식 역슬래시(\)는 확장문자 (역슬래시 다음에 일반 문자가 오면 특수문자로 취급하고 역슬래시 다음에 특수문자가 오면 그 문자 자체를 의미) |
| \b | 단어의 경계 |
| \B | 단어가 아닌것에 대한 경계 |
| \A | 입력의 시작 부분 |
| \G | 이전 매치의 끝 |
| \Z | 입력의 끝이지만 종결자가 있는 경우 |
| \z | 입력의 끝 |
| \s | 공백 문자 |
| \S | 공백 문자가 아닌 나머지 문자 |
| \w | 알파벳이나 숫자 |
| \W | 알파벳이나 숫자를 제외한 문자 |
| \d | 숫자 [0-9]와 동일 |
| \D | 숫자를 제외한 모든 문자 |
| (?i) | 앞 부분에 (?!)라는 옵션을 넣어주게 되면 대소문자는 구분하지 않습니다. |





## 자주 사용하는 정규 표현식

| 정규 표현식 | 설명 |
| --- | --- |
| ^[0-9]*$ | 숫자 |
| ^[a-zA-Z]*$ | 영문자 |
| ^[가-힣]*$ | 한글 |
| \\w+@\\w+\\.\\w+(\\.\\w+)? | E-Mail |
| ^\d{2,3}-\d{3,4}-\d{4}$ | 전화번호 |
| ^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$ | 휴대 전화 번호 |
| \d{6} \- [1-4]\d{6} | 주민등록번호 |
| ^\d{3}-\d{2}$ | 우편번호 |





### Quantifier

- 요소들을 얼마나 반복시킬지 정의

| Regular Expression | Description |
| --- | --- |
| * | 0회 이상 반복 |
| + | 1회 이상 반복 |
| ? | 0 또는 1회만 |
| {X} | X회 이상 반복 |
| {X,Y} | X~Y 사이의 수만큼 반복 |
| *? | 가장 적게 일치하는 패턴을 찾음 |







### Regex 를 지원하는 String 메소드

| Method | Description |
| --- | --- |
| String.matches(regex) | String이 regex와 일치하면 true 리턴 |
| String.split(regex) | regex와 일치하는 것을 기준으로 String을 분리하여 배열로 리턴 |
| String.replaceFirst(regex, replacement) | regex와 가장 먼저 일치하는 것을 replacement로 변환 |
| String.replaceAll(regex, replacement) | regex와 일치하는 모든 것을 replacement로 변환 |

```java
@Test
public void ex9() {
    String pattern = "a*[0-9]*";
    assertTrue("aaa123".matches(pattern));

    pattern = "\\s";
    String arr[] = "Hello World Java Regex".split(pattern);
    System.out.println(Arrays.asList(arr));

    pattern = "Hello";
    System.out.println("Hello World Hello World ".replaceFirst(pattern, "Regex"));

    pattern = "Hello";
    System.out.println("Hello World Hello World ".replaceAll(pattern, "Regex"));
}
```

```java
// 결과

[Hello, World, Java, Regex]
Regex World Hello World
Regex World Regex World
```





### 주의

- +? 또는 *? 의 예제
    - ? 가 단독으로 사용되면 0 또는 1회
    - BUT +, * 등과 함께 사용되면 가장 적은 조건으로 매칭되는 문자열을 찾음
    
    - + 과 +? 비교 예시
    
    ```java
    @Test
    public void ex14() {
        String result;
        result = "The cat sat on the mat.".replaceAll("c.+t", "*");
        System.out.println(result);
    
        result = "The cat sat on the mat.".replaceAll("c.+?t", "*");
        System.out.println(result);
    }
    ```
    
    ```java
    // 결과
    
    The *.
    The * sat on the mat.
    ```







Ref

 https://coding-factory.tistory.com/529

https://coding-factory.tistory.com/819

https://codechacha.com/ko/java-regex/

https://hbase.tistory.com/160

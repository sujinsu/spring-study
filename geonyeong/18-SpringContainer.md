- [SpringContainer](#springcontainer)
  - [spring-contex 실습](#spring-contex-실습)
  - [Spring은 객체 컨테이너](#spring은-객체-컨테이너)
  - [Sigleton 객체](#sigleton-객체)
  - [Reference](#reference)

# SpringContainer

## spring-contex 실습

```gradle
// gradle java 플러그인 적용
plugins {
    id 'java'
}

group 'io.book'
version '1.0-SNAPSHOT'

// 의존 모듈을 메이븐 중앙 리포지토리에서 다운로드
repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
    implementation 'org.springframework:spring-context:5.0.2.RELEASE' // spring-context 모듈에 대한 의존 설정
}

test {
    useJUnitPlatform()
}
```

```java
/*
자기소개하는 User 클래스
*/
public class User {
    private String template;

    public String introduce(String name) {
        return String.format(template, name);
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
```

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContext {
    @Bean
    public User user() {
        User user = new User();
        user.setTemplate("안녕하세요. 제 이름은 %s입니다");
        return user;
    }
}
```

```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class); // 설정 정보를 이용해서 Bean 객체를 생성
        User user = ctx.getBean("user", User.class); // Bean 객체를 제공
        String message = user.introduce("허건영");
        System.out.println(message);
        ctx.close();
    }
}
```

실행결과
```
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.springframework.cglib.core.ReflectUtils$1 (file:/Users/heo/.gradle/caches/modules-2/files-2.1/org.springframework/spring-core/5.0.2.RELEASE/45b2958ab3fb022dd29f8b1c553ebf1c75a144aa/spring-core-5.0.2.RELEASE.jar) to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
WARNING: Please consider reporting this to the maintainers of org.springframework.cglib.core.ReflectUtils$1
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
안녕하세요. 제 이름은 허건영입니다
```

## Spring은 객체 컨테이너

스프링의 핵심 기능 == 객체를 생성하고 초기화, 이와 관련된 기능은 ApplicationContext라는 인터페이스에 정의

AnnotationConfigApplicationContext 클래스

- AnnotationConfigApplicationContext 클래스는 이 인터페이스를 알맞게 구현한 클래스 중 하나

- AnnotationConfigApplicationContext 클래스는 자바 클래스에서 정보를 읽어와 객체 생성과 초기화를 수행

- XML 파일이나 그루비 설정 코드를 이용해서 객체 생성/초기화를 수행하는 클래스도 존재

ApplicationContext는 객체를 생성, 초기화, 보관, 제거 등을 담당하고 있어서 Container라고 부르고, 이 책에서는 스프링 컨테이너라고 표현

## Sigleton 객체

```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main2 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
        User user1 = ctx.getBean("user", User.class);
        User user2 = ctx.getBean("user", User.class);

        System.out.println("user1 == user2 결과 " + (user1 == user2));
        ctx.close();
    }
}
```

실행결과
```
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.springframework.cglib.core.ReflectUtils$1 (file:/Users/heo/.gradle/caches/modules-2/files-2.1/org.springframework/spring-core/5.0.2.RELEASE/45b2958ab3fb022dd29f8b1c553ebf1c75a144aa/spring-core-5.0.2.RELEASE.jar) to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
WARNING: Please consider reporting this to the maintainers of org.springframework.cglib.core.ReflectUtils$1
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
user1 == user2 결과 true
```

- 별도로 설정하지 않는 경우 스프링은 한 개의 Bean 객체만을 생성 (여기서 Bean 객체는 singleton 범위를 갖는다라고 표현)

- Singleton : 단일 객체(Single Object)를 의미 (`@Bean` 애노테이션에 해당하는 한 개의 빈 객체를 생성)

아래에서 "user", "engUser" 각각에 해당하는 객체 한 개씩을 만들어 두 개의 빈 객체가 생성됨

```java
@Configuration
public class AppContext {
    @Bean
    public User user() {
        User user = new User();
        user.setTemplate("안녕하세요. 제 이름은 %s입니다");
        return user;
    }

    @Bean
    public User engUser() {
        User user = new User();
        user.setTemplate("Hi. Let me introducy my self. My name is %s");
        return user;
    }
}
```

```java
public class Main2 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
        User user = ctx.getBean("user", User.class);
        User engUser = ctx.getBean("engUser", User.class);

        System.out.println("user == engUser 결과 " + (user == engUser));
        ctx.close();
    }
}
```

실행결과
```
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.springframework.cglib.core.ReflectUtils$1 (file:/Users/heo/.gradle/caches/modules-2/files-2.1/org.springframework/spring-core/5.0.2.RELEASE/45b2958ab3fb022dd29f8b1c553ebf1c75a144aa/spring-core-5.0.2.RELEASE.jar) to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
WARNING: Please consider reporting this to the maintainers of org.springframework.cglib.core.ReflectUtils$1
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
user == engUser 결과 false
```

## Reference

스프링5 프로그래밍 입문 (최범균 저)
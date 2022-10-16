# Reflection

## DI (Dependency Injection)

- Service

```java
import com.di.reflection.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}
```

- Repository

```java
package com.di.reflection.repository;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
}
```

- Test

```java
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    @DisplayName("DI Test")
    void DI_테스트() {
        assertNotNull(userService);
    }
}
```

" 어떻게 Null이 나오지 않는걸까?

## 리플렉션 API

### 클래스 정보 조회

```java
package com.gyheo.java;

import java.util.Arrays;

public class ReflectionTest {
    public static void main(String[] args) throws ClassNotFoundException {
        // 클래스 인스턴스에 접근하는 방법
        Class<User> userClass = User.class; // type을 통해 가져오는 방법

        User user = new User();
        Class<? extends User> uClass = user.getClass();

        Class<?> aClass = Class.forName("com.gyheo.java.User");

        Arrays.stream(userClass.getFields()).forEach(System.out::println); // 접근 지시자 영향
        System.out.println("-----");
        Arrays.stream(userClass.getDeclaredFields()).forEach(System.out::println); // All

        System.out.println("-----");
        Arrays.stream(userClass.getDeclaredFields()).forEach(f -> {
            try {
                f.setAccessible(true);
                System.out.println("f = " + f.get(user));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        System.out.println("-----");
        Arrays.stream(MyUser.class.getInterfaces()).forEach(System.out::println);
    }
}
```

**결과**

```log
public java.lang.String com.gyheo.java.User.address
-----
private java.lang.String com.gyheo.java.User.name
private java.lang.Integer com.gyheo.java.User.age
private static final java.lang.String com.gyheo.java.User.STRING1
private static final java.lang.String com.gyheo.java.User.STRING2
public java.lang.String com.gyheo.java.User.address
protected java.lang.String com.gyheo.java.User.firstName
-----
f = null
f = 10
f = STR1
f = STR2
f = Seoul
f = GY
-----
interface com.gyheo.java.MyInterface

```

### 에노테이션과 리플렉션

- 애노테이션 만들기

```java
package com.gyheo.java;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 런타임일때도 유지하고 싶을 때 기본적으로 Byte Code를 로딩했을 때 남지 않는다
@Target({ElementType.TYPE, ElementType.FIELD}) // annotation 위치 제한
@Inherited // 상속이 되는 애노테이션
public @interface MyAnnotation {
    String name() default "geonyeong";

    int number() default 29;
}
```

테스트

```java
package com.gyheo.java;

import java.util.Arrays;

public class AnnotationTest {
    public static void main(String[] args) {
        Arrays.stream(MyUser.class.getAnnotations()).forEach(System.out::println);
    }
}
```

## 참조

[더 자바, 코드를 조작하는 다양한 방법](https://www.inflearn.com/course/the-java-code-manipulation/dashboard)
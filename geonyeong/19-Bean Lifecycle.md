- [Bean Lifecycle](#bean-lifecycle)
	- [1. 컨테이너 초기화와 종료](#1-컨테이너-초기화와-종료)
	- [2. 스프링 빈 객체의 라이프사이클](#2-스프링-빈-객체의-라이프사이클)
		- [2.1 빈 객체의 초기화와 소멸 : 스프링 인터페이스](#21-빈-객체의-초기화와-소멸--스프링-인터페이스)
		- [2.2 빈 객체의 초기화와 소멸 : 커스텀 메서드](#22-빈-객체의-초기화와-소멸--커스텀-메서드)
	- [Reference](#reference)

# Bean Lifecycle

## 1. 컨테이너 초기화와 종료

스프링 컨테이너는 초기화와 종료라는 라이프사이클을 갖는다

```java

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {
		// 1. 컨테이너 초기화
    AnnotationConfigApplicationContext ctx = 
        new AnnotationConfigApplicationContext(AppContext.class);

		// 2. 컨테이너에서 빈 객체를 구해서 사용
    Greeter g1 = ctx.getBean("greeter", Greeter.class);
    Greeter g2 = ctx.getBean("greeter", Greeter.class);
    System.out.println("(g1 == g2) = " + (g1 == g2));

		// 3. 컨테이너 종료
    ctx.close();
  }
}
```

## 2. 스프링 빈 객체의 라이프사이클

객체 생성 → 의존 설정 → 초기화 → 소멸

스프링 컨테이너를 초기화할 때 스프링 컨테이너는 가장 먼저 빈 객체를 생성하고 의존 관계를 설정
(의존 자동 주입을 통한 의존 설정이 이 시점에 수행)

모든 의존 설정이 완료되면 빈 객체의 쵝화를 수행
(빈 객체를 초기화하기 위해 스프링은 빈 객체의 지정된 메서드를 호출)

스프링 컨테이너를 종료하면 스프링 컨테이너는 빈 객체의 소멸을 처리, 이 때에도 지정된 메서드를 호출

### 2.1 빈 객체의 초기화와 소멸 : 스프링 인터페이스

- org.springframework.beans.factory.InitializingBean
- org.springframework.beans.factory.DisposableBean

```java
public interface InitializingBean {
	void afterPropertiesSet() throws Exception;
}

public interface DisposableBean {
	void destory() throws Exception;
}
```

InitializingBean 인터페이스를 구현하면 스프링 컨테이너는 초기화 과정에서 빈 객체의 afterPropertiesSet() 메서드를 실행
(빈 객체를 생성 후 초기화 과정이 필요하면 InitializingBean 인터페이스를 상속하고 afterPropertiesSet() 메서드를 알맞게 구현)

스프링 컨테이너는 빈 객체가 DisposableBean 인터페이스를 구현한 경우 소멸과정에서 빈 객체의 destory() 메서드를 실행
(빈 객체의 소멸 과정이 필요하면 DisposableBean 인터페이스를 상속하고 destory() 메서드를 알맞게 구현)

```java

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Client implements InitializingBean, DisposableBean {

	private String host;

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Client.afterPropertiesSet() 실행");
	}

	public void send() {
		System.out.println("Client.send() to " + host);
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("Client.destroy() 실행");
	}

}

```java
@Configuration
public class AppContext {
    @Bean
    public Client client() {
        Client client = new Client()
        client.setHost("host");
        return client;
    }
}
```

```java
import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import config.AppCtx;
import spring.Client;

public class Main {

	public static void main(String[] args) throws IOException {
		AbstractApplicationContext ctx = 
				new AnnotationConfigApplicationContext(AppCtx.class);

		Client client = ctx.getBean(Client.class);
		client.send();

		ctx.close();
	}

}
```

실행결과
```
Client.afterPropertiesSet() 실행
Client.send() to host
Client.destory() 실행
```

### 2.2 빈 객체의 초기화와 소멸 : 커스텀 메서드

```java

public class Client2 {

	private String host;

	public void setHost(String host) {
		this.host = host;
	}

	public void connect() {
		System.out.println("Client2.connect() 실행");
	}

	public void send() {
		System.out.println("Client2.send() to " + host);
	}

	public void close() {
		System.out.println("Client2.close() 실행");
	}

}
```

```java
@Configuration
public class AppContext {
    @Bean(initMethod = "connect", destoryMethod = "close")
    public Client2 client2() {
        Client2 client = new Client2()
        client.setHost("host");
        return client;
    }
}
```

실행결과

```
Client2.afterPropertiesSet() 실행
Client2.connect() 실행
Client2.send() to host
Client2.close() 실행
Client2.destory() 실행
```

## Reference

스프링5 프로그래밍 입문 (최범균 저)
# Spring DI

## 1. 의존이란?

DI (Dependency Injection) 우리말로 의존 주입

여기서의 의존은 `객체` 간의 의존

아래에서 MemberRegisterService클래스가 DB처리를 위해 MemberDao 클래스의 메서드를 사용

```java
package spring;

import java.time.LocalDateTime;

public class MemberRegisterService {
  // 의존 객체를 직접 생성
  private MemberDao memberDao = new MemberDao();

  public void regist(RegisterRequest req) {
    Member member = memberDao.selectByEmail(req.getEmail()); // 이메일로 Member 조회

    // 같은 회원 이메일인 경우 Exception 발생
    if (member != null) {
      throw new DuplicateMemberException("dup email " + req.getEmail());
    }

    // 같은 이메일을 가진 회원이 존재하지 않는 경우 DB에 삽입
    Member newMember = new Member(
        req.getEmail(), req.getPassword(), req.getName(), 
        LocalDateTime.now());
    memberDao.insert(newMember);
    }
}
```

**위와 같이 하나의 클래스가 다른 클래스의 메서드를 실행할 때 이를 `의존` 한다고 표현**

의존하는 대상이 있으면 그 대상을 구하는 방법이 필요

가장 쉬운 방법 - 의존 대상 객체를 직접 생성

- **클래스 내부에서 직접 의존 객체를 생성하는 것이 쉽긴 하지만 유지보수 관점에서 문제를 유발**

```java
public class MemberRegisterService {
  // 의존 객체를 직접 생성
  private MemberDao memberDao = new MemberDao();
```

## 2. DI를 통한 의존 처리

DI(Dependency Injection, 의존 주입)는 의존하는 객체를 직접 생성하는 대신 의존 객체를 전달받는 방식을 사용

위의 코드 변경

**DI를 사용하는 이유 - 변경의 유연함**

```java
package spring;

import java.time.LocalDateTime;

public class MemberRegisterService {
  private MemberDao memberDao;

  // 의존 객체를 생성했던 코드와 달리 의존 객체를 직접 생성하지 않고 전달받기 때문에 DI 패턴을 이용
  public MemberRegisterService(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  public Long regist(RegisterRequest req) {
    Member member = memberDao.selectByEmail(req.getEmail());
    if (member != null) {
      throw new DuplicateMemberException("dup email " + req.getEmail());
    }
    Member newMember = new Member(
        req.getEmail(), req.getPassword(), req.getName(), 
        LocalDateTime.now());
    memberDao.insert(newMember);
    return newMember.getId();
  }
}
```

## 3. DI와 의존 객체 변경의 유연함

```java
public class MemberRegisterService {
  private MemberDao memberDao = new MemberDao();
  ...
}
```

```java
public class ChangePasswordService {
  private MemberDao memberDao = new MemberDao();
  ...
}
```

MemberDao 클래스는 회원 데이터를 DB에 저장인데 요구사항 변경으로 Cache 데이터를 적용해야하는 상황이 발생

```java
public class CachedMemberDao extends MemberDao {
  ...
}
```

CachedMemberDao를 사용하려면 MemberRegisterService, ChangePasswordService 클래스에서 모두 변경해주어야 함

여기서는 2개의 클래스 뿐이었지만 만약 1,000개의 클래스라면..?

```java
public class MemberRegisterService {
  private MemberDao memberDao = new CachedMemberDao();
  ...
}
```

```java
public class ChangePasswordService {
  private MemberDao memberDao = new CachedMemberDao();
  ...
}
```

위와 같은 상황에서 DI를 사용하면 수정해야할 소스 코드가 줄어듦 => 유지보수 용이


```java
public class MemberRegisterService {
  private MemberDao memberDao;

  public MemberRegisterService(MemberDao memberDao) {
    this.memberDao = memberDao;
  }
  ...
}

public class ChangePasswordService {
  private MemberDao memberDao;

  public ChangePasswordService(MemberDao memberDao) {
    this.memberDao = memberDao;
  }
  ...
}
```

```java
// before
MemberDao memberDao = new MemberDao();
MemberRegisterService regSvc = new MemberRegisterService(memberDao);
ChangePasswordService pwdSvc = new ChangePasswordService(memberDao);

// after
MemberDao memberDao = new CachedMemberDao();
MemberRegisterService regSvc = new MemberRegisterService(memberDao);
ChangePasswordService pwdSvc = new ChangePasswordService(memberDao);
```

## Reference

스프링5 프로그래밍 입문 (최범균 저)

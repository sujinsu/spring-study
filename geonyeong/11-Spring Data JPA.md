- [Spring Data JPA](#spring-data-jpa)
	- [Spring Data JPA란?](#spring-data-jpa란)
	- [주의사항](#주의사항)
	- [build.gradle](#buildgradle)
	- [순수 JPA 이용](#순수-jpa-이용)
	- [Spring Data JPA 이용](#spring-data-jpa-이용)
	- [Reference](#reference)

# Spring Data JPA

## Spring Data JPA란?

- Spring Data JPA는 Spring Framework와 JPA 라는 기반 위에 JPA를 편리하게 사용하도록 도와주는 기술

- Repository에 구현 클래스 없이 interface만으로 개발 완료 가능

- CRUD(등록, 수정, 삭제, 조회) 기능을 모두 제공함으로써 단순한 반복이 줄어듦 → **핵심 비즈니스 로직에 집중 가능** → **개발 생산성 향상**

Spring Data JPA는 `선택`이 아니라 `필수`!

## 주의사항

- Spring Data JPA는 JPA를 편하게 사용할 수 있지만 JPA의 너무 많은 부분을 자동화, 추상화함으로써 기본 이해 없이 사용하면 깊은 내부 동작 이해가 어려움
- 장애나 문제가 발생했을 때 발견하기 어렵다

- 순수한 JPA 코드와 Spring Data JPA를 비교하면서 공부

- **JPA를 모르면 Spring Data JPA만으로는 한계가 온다.**

## build.gradle

```yaml
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
```

## 순수 JPA 이용

```java
package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {
    // Spring Boot가 스프링 컨테이너가 영속성 컨텍스트를 (EntityManager를) 가져다준다.
    @PersistenceContext
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    public void delete(Member member) {
        em.remove(member);
    }

    public List<Member> findAll() {
        // 객체를 대상으로 하는 쿼리
        List<Member> resultList= em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return resultList;
    }

    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public long count() {
        return em.createQuery("select count(m) from Member m", Long.class).getSingleResult();
    }
}
```

## Spring Data JPA 이용

```java
package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
```

> Q) "interface만 있는데 어떻게 실행이 되나요?  
> A) Spring Container에서 해당 interface에 대한 구현체를 만들어주고 의존 관계를 주입해줍니다.

## Reference
[실전! 스프링 데이터 JPA](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%8D%B0%EC%9D%B4%ED%84%B0-JPA-%EC%8B%A4%EC%A0%84/)
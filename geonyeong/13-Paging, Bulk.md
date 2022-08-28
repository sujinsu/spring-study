- [페이징, 벌크성 수정 쿼리](#페이징-벌크성-수정-쿼리)
	- [페이징](#페이징)
		- [JPA Paging](#jpa-paging)
		- [Spring Data JPA Paging](#spring-data-jpa-paging)
	- [벌크성 수정 쿼리](#벌크성-수정-쿼리)
		- [JPA 벌크성 수정 쿼리](#jpa-벌크성-수정-쿼리)
		- [Spring Data JPA 벌크성 수정 쿼리](#spring-data-jpa-벌크성-수정-쿼리)
	- [Reference](#reference)

# 페이징, 벌크성 수정 쿼리

## 페이징

### JPA Paging

```java
    public List<Member> findByPage(int age, int offset, int limit) {
        return em.createQuery("select m from Member m where m.age = :age order by m.username desc")
                .setParameter("age", age)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public long totalCount(int age) {
        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }
```

```java
    @Test
    public void paging() {
        // given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));

        int age = 10;
        int offset = 0; // 첫 번째 페이지
        int limit = 3; // 페이지당 보여줄 데이터는 3건

        // when
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        // 페이지 계산 공식 적용

        // then
        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(5);
    }
```

### Spring Data JPA Paging

- 전체 count 쿼리는 성능에 영향을 끼쳐서 실무에서 사용시 주의
예) 전체 10만건 되는 회원 정보

```java
public interface MemberRepository extends Repository<Member, Long> {
    @Query(value = "select m from Member m")
    Page<Member> findByAge(int age, Pageable pageable);
}
```

```java
    @Test
    public void paging() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        Page<Member> page = memberRepository.findByAge(age, pageRequest); // 이대로 절대 내보내지 말 것!
//        Page<Member> page = memberRepository.findTop3ByAge(age, pageRequest); // Top 기능 사용 가능
//        Slice<Member> page = memberRepository.findByAge(age, pageRequest); // 추가로 limit + 1 => 다음 페이지 여부 확인
//        List<Member> page = memberRepository.findByAge(age, pageRequest);

        // API로 반환 가능 (map을 이용하여 DTO로 변환하여 내보낼것)
        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));

        // then
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }
```

## 벌크성 수정 쿼리

### JPA 벌크성 수정 쿼리

```java
    public int bulkAgePlus(int age) {
        return em.createQuery("update Member m set m.age = m.age + 1 where m.age >= :age")
                .setParameter("age", age)
                .executeUpdate();
    }
```

```java
    @Test
    public void bulkUpdate() {
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 19));
        memberJpaRepository.save(new Member("member3", 20));
        memberJpaRepository.save(new Member("member4", 21));
        memberJpaRepository.save(new Member("member5", 40));

        // when
        int resultCount = memberJpaRepository.bulkAgePlus(20);

        // then
        assertThat(resultCount).isEqualTo(3);
    }
```

### Spring Data JPA 벌크성 수정 쿼리

**주의**

- 벌크성 수정 쿼리는 그냥 바로 DB에 반영

- 1차 캐시는 DB에 바뀌는 것을 알 수 없기 때문에 영속성 컨텍스트를 clear 해야함

```java
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);
```

```java
    @Test
    public void bulkUpdate() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        // when
        int resultCount = memberRepository.bulkAgePlus(20); // DB에 곧장 반영
//        entityManager.flush(); // DB에 반영
//        entityManager.clear(); // 영속성 컨텍스트 클리어

        List<Member> result = memberRepository.findByUsername("member5");
        Member member5 = result.get(0);
        System.out.println("member5 = " + member5); // Quiz

        // then
        assertThat(resultCount).isEqualTo(3);
    }
```

## Reference

[실전! 스프링 데이터 JPA](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%8D%B0%EC%9D%B4%ED%84%B0-JPA-%EC%8B%A4%EC%A0%84/)
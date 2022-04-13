[TOC]

아직 하는 중 ~ 

# QueryDSL

**💎 SQL 실제 실행 순서**

- FROM : 각 테이블 확인
- ON : 조인 조건 확인
- JOIN : 테이블 조인 (병합)
- WHERE : 데이터 추출 조건 확인
- GROUP BY : 특정 칼럼으로 데이터 그룹화
- HAVING : 그룹화 이후 데이터 추출 조건 확인
- SELECT : 데이터 추출





| JPQL                       | QueryDSL                  |
| -------------------------- | ------------------------- |
| 문자 실행 시점 오류 (문자) | 컴파일 시점 오류 (코드)   |
| 파라미터 바인딩 직접       | 파라미터 바인딩 자동 처리 |
|                            |                           |



### JPQL

> SQL이 데이터 베이스 테이블을 대상으로 쿼리 질의 한다면,
>
> JPQL은 엔티티 객체를 대상으로 쿼리 질의



- **유의사항**

  - from 절에 들어가는 것은 객체

  - 엔티티와 속성은 대소문자 구분

    ex) Member 엔티티, name 필드 

  - JPQL 키워드는 대소문주 구분 X

  - 테이블이름 X, 엔티티 이름 O

  - 별칭 필수

- **결과 조회 API**
  - query.getResultList()
    - 결과가 하나 이상인 경우, 리스트 반환
  - query.getSingleResult()
    - 결과가 하나, 단일 객체 반환 (아니면 예외 발생)

- **파라미터 바인딩**

  - 이름 (선호되는 방식)
  - 위치

- **fetch 조인**

  - fetch type LAZY로 세팅 > 한꺼번에 조회가 필요한 경우 페치 조인 사용 

    -  [**JPA N+1문제**](https://jojoldu.tistory.com/165)

  - 엔티티 객체 그래프 한꺼번에 조회하는 방법

  - 별칭 사용 X

    ex) 멤버 조회 시 팀까지 같이 조회

    - JPQL 

      ```sql
      select m from Member m join fetch m.team
      ```

    - SQL

      ```sql
      SELECT M.*, T.*
      FROM MEMBER Y
      INNER JOIN TEAM T ON M.TEAM_ID = T.ID
      ```

      cf) 최근 jpa2.1 은 페치조인 외에 엔티티 그래프라는 기능이 생김

- **Named 쿼리 - 정적쿼리**

  > em.createQuery() : 사용자 요청 전까지 에러 잡을 수 X
  >
  > NamedQuery :  애플리케이션 로딩 시점 쿼리 파싱 > 배포 전 문제 해결 가능

  - 미리 정의해서 이름을 부여해두고 사용하는 JPQL 

  - 어노테이션, XML에 정의

  - ex

    ```java
    @Entity
    @NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username")
    public vlass Member {
      ...
    }
    ```

    

[참고1](https://ict-nroo.tistory.com/116)
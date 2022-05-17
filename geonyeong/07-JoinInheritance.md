# 상속관계 매핑

## 상속관계 매핑

![inheritance](assets/Inheritance%20Relation.png)

- Relational DB는 상속 관계가 X

- 슈퍼타입 서브타입 관계라는 모델링 기법이 객체 상속과 유사

## 구현 방법

- 슈퍼타입 서브타입 → 물리 모델

	* 각각 테이블로 변환 → JOIN 전략
  
  * 통합 테이블로 변환 → Single Table 전략
  
  * 서브타입 테이블로 변환 → 구현 클래스마다 테이블 전략

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "DTYPE")
class Board {
	@Id
	@GeneratedValue
	private Long id;
	
	private String title;
}
```

## JOIN 전략 (basic)

![join](assets/join%20strategy.png)

### 장점

- 테이블 정규화
  
- 외래 키 참조 가능

- 저장 공간 효율화

### 단점

- 조회시 조인이 많아져서 성능 저하 (쿼리 복잡)

## Single Table 전략

![single](assets/single%20strategy.png)

### 장점

- 조인이 필요하지 않아 성능 향상 (쿼리 단순)

### 단점

- 자식 Entity가 매핑한 컬럼은 모두 `null` 허용

- 상황에 따라 조회 성능이 느려짐

## 구현 클래스마다 테이블 전략

![each class](assets/each%20class.png)

** DBA, BackEnd 개발자 모두 불호 **

### 장점

- 서브 타입 명확하게 구분

### 단점

- 여러 자식 테이블을 조회할 때 성능 저하

- 통합 어려움

## `@MappedSuperclass`

- 상속관계 매핑 X

- Entity X, 테이블과 매핑 X

- 부모 클래스를 상속 받는 **자식 클래스에 매핑 정보만 제공**

- 부모 클래스는 `abstract`로 권장

## Reference

[자바 ORM 표준 JPA 프로그래밍 - 기본편](https://www.inflearn.com/course/ORM-JPA-Basic/dashboard)

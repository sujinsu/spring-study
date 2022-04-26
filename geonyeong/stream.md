- [Java 8](#java-8)
	- [LTS (Long-Term-Support)](#lts-long-term-support)
	- [주요 기능](#주요-기능)
- [Stream](#stream)
	- [개념](#개념)
	- [특징](#특징)
	- [intermediate operations (중간 오퍼레이션)](#intermediate-operations-중간-오퍼레이션)
	- [terminal operations (종료 오퍼레이션)](#terminal-operations-종료-오퍼레이션)
- [Reference](#reference)

## Java 8

- 2014년 3월 출시
- 현재 72% 사용 중이고 Java 11 사용량이 늘어나는 추세

### LTS (Long-Term-Support)

- 지원기간 5년
- 실제 운영환경에서는 LTS 권장

### 주요 기능

- Lambda
- **Stream**
- Optional<T>
- etc

## Stream

### 개념

- **A sequence of elements** supporting sequential and parallel aggregate operations

![stream](assets/stream.png)

### 특징

- No storage 
“List와 같이 데이터를 담는 저장소가 X”
- Functional in nature
“소스를 변경하지 않는다.”
- 스트림으로 처리하는 데이터는 오직 한번만 처리
- Laziness-seeking 
“stream이 제공하는 여러가지 메서드가 존재”

Stream operations are divided into ***intermediate*** and ***terminal*** operations,

다수의 intermediate operation, 한 개의 종료 operation으로 구성

### intermediate operations (중간 오퍼레이션)

- **stream을 반환**
- terminal operation을 만나기 전까지 실행되지 않는다
- map, filter, limit, skip, sorted, ...

### terminal operations (종료 오퍼레이션)

- **stream을 반환 X**
- collect, allMatch, count, forEach, min, max

## Reference

- [JetBrain](https://www.jetbrains.com/lp/devecosystem-2021/java/)
- [더 자바, Java 8](https://www.inflearn.com/course/the-java-java8/dashboard)
- [Java Stream Documentaion](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)
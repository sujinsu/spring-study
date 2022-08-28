# Batch


자료: https://khj93.tistory.com/entry/Spring-Batch%EB%9E%80-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B3%A0-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0, https://godekdls.github.io/Spring%20Batch/introduction/, https://devfunny.tistory.com/470, https://ojt90902.tistory.com/760, https://pompitzz.github.io/blog/Spring/Scheduler.html#_2-taskscheduler%E1%84%8B%E1%85%AA-trigger, https://jeong-pro.tistory.com/186, https://data-make.tistory.com/699, http://jmlim.github.io/spring/2018/11/27/spring-boot-schedule/, http://www.cronmaker.com/;jsessionid=node012dmcxbzjzci413453pgq0px6j99880.node0?0



# Spring Batch

[Spring Batch](https://spring.io/projects/spring-batch#overview)

## 목적

- 로깅/ 추적
- 트랜잭션 관리
- 작업 처리 통계
- 작업 재시작
- 건너뛰기
- 리소스 관리 등 대용량 레코드 처리
- 최적화 및 파티셔닝 기술

ex) 다량의 정보를 사용자 인터랙션 없이 자동, 효율적으로 처리하는 복잡한 프로세싱(월말 정산 등)
      매우 큰 데이터 셋을 반복적, 주기적으로 처리하는 어플리케이션 (보험료 조정)

      내/외부 시스템 데이터 통합. 포맷팅, 유효성 검사, 트랜잭션 처리

## Spring Batch

- 포괄적인 경량 배치 프레임워크
- 엔터프라이즈 시스템 운영에 일상적으로 필요한 어플리케이션 개발을 위해 설계
- Spring Framework 특성 (생산성, POJO 기반 접근, 사용 편의성) → 개발자 편리, 고급기술 가능
- 스케줄링 프레임워크 대체X → 스케줄러 (Quartz, Tivoli, Control-M 등) 과 함께 동작하도록 설계

![Untitled](Batch%201bbf868e48054c48a966b19c3d550b8e/Untitled.png)

<aside>
💡 **Application :** 개발자가 만드는 모든 배치 Job과 커스텀 코드


**Batch Core :** job 을 실행하고 제어하는 데 필요한 핵심 런타임 클래스
         - JobLauncher, Job, Step 구현체 포함

**Batch Infrastructure :** Application, Core 모두 공통 infrastructure 위에서 빌드
        **** - ****공통 reader, writer, 서비스(RetryTemplate 등) 포함
         - 어플리케이션 개발자 사용 (reader, writer)
                  - 코어 프레임워크 자체 활용 (자체 라이브러리 retry )

</aside>

![Untitled](Batch%201bbf868e48054c48a966b19c3d550b8e/Untitled%201.png)

![Untitled](Batch%201bbf868e48054c48a966b19c3d550b8e/Untitled%202.png)

## 용어

- `Job`
    - 배치 처리 과정을 하나의 단위로 만들어 놓은 객체
    - step 인스턴스의 컨테이너 개념
- `JobInstance`
    - Job 실행의 단위
    - Job 실행 시 JobInstance 생성
        - 1월 1일 실행, 1월 2일 실행 
        → 1월 1일 실행 JobInstance 실패 시 재실행 하면 1월 1일만 처리
- `JobParameters`
    - JobInstance 구별 역할
    - JobInstance 전달되는 매개변수 역할
    - String, Double, Long, Date 4가지 형식 지원
- `JobExcution`
    - jobInstance 실행 시도에 대한 객체
    - 상태, 시작시간, 종료시간, 생성시간 등 정보
    - ex) 1월 1일에 실행한 JobInstacne가 실패 & 재실행
    → 동일한 JobInstance 실행 
    BUT 2번 실행에 대한 개별 JobExecution 생성
- `Step`
    - Job의 배치 처리 정의, 순차적인 단계 캡슐화 (Job의 실제 일괄 처리 제어하는 모든 정보)
    - Job은 최소 1개 이상의 Stop
- `StepExcution`
    - JobExcution 과 동일하게 Step 실행 시도에 대한 객체
    - 이전 단계 Step 실패 시 다음 단계 실행 X
    - read수, write 수, commit 수, skip 수 등 정보 저장
- `ExecutionContext`
    - Job 에서 데이터 공유할 수 있는 데이터 저장소
    - Step 간 Data 공유 가능 → Job 실패 시 마지막 실행 재구성 가능
    - 2가지 종류
        - JobExecutionContext : commit 시점 저장
        - StepExecutionContext  : 실행 사이 저장
- `JobRepository`
    - 모든 배치 처리 정보를 담고있는 매커니즘
    - Job 실행 → JobRepository 에 JobExcution, StepExcution 생성 및 조회 사용 가능
- `JobLauncher`
    - Job과 JobParameters를 사용하여 Job 실행하는 객체
- `ItemReader`
    - Step에서 Item 읽어오는 인터페이스
- `ItemWriter`
    - 처리된 Data를 Writer 할 때 사용
    - 기본적으로 Item 을 Chunk 로 묶어 처리
    - 처리 결과물에 따라 Insert, Update
        - ex) Queue 사용 시 Send
- `ItemProcessor`
    - Reader에서 읽어온 Item 데이터를 처리하는 역할
    - 배치 처리를 위한 필수 요소 X
    

cf) Reader, Writer, Processor 처리를 분리 → 각각의 역할 명확히 구분

<aside>
✏️ **Chunk**

Spring Batch에서의 Chunk란 처리 되는 커밋 row 수
chunk 단위로 Transaction을 수행하기 때문에 실패시 Chunk 단위 만큼 rollback

**Chunk 지향 처리 : 3가지 시나리오로 실행**
- 읽기 (Read) : Database 에서 배치 처리할 Data
- 처리 (Processing) : 읽어온 Data 를 가공, 처리 한다 ( 필수 사항 X)
- 쓰기 (Write) : 가공, 처리한 데이터를 Database에 저장

</aside>

![Untitled](Batch%201bbf868e48054c48a966b19c3d550b8e/Untitled%203.png)

![Untitled](Batch%201bbf868e48054c48a966b19c3d550b8e/Untitled%204.png)

```java
List items = new Arraylist();
for(int i = 0; i < commitInterval; i++){
    Object item = itemReader.read()
    Object processedItem = itemProcessor.process(item);
    items.add(processedItem);
}
itemWriter.write(items);
```

## Batch 가이드

- 단일 배치 어플리케이션은 가능한 단순화, 복잡한 로직 피하기
- 데이터 처리와 저장은 물리적으로 가까운 곳에서 수행 (데이터 처리되는 곳 == 데이터 저장)
- 시스템 리소스 사용, 특히 I/O 최소화, 내부 메모리에서 가능한 많은 연산을 실행
- 어플리케이션 I/O 점검하여 불필요한 물리적 I/O 줄이기
    - ✔ 주의할 결함
        - 한 번 읽고나서 캐시하거나 작업 스토리지에 저장해도 되는 데이터를 매 트랜잭션마다 읽는 경우
        - 같은 트랜잭션 내에서 이미 읽은 데이터를 다시 읽는 경우
        - 불필요한 테이블 스캔이나 인덱스 스캔을 유발하는 경우
        - SQL 구문에서 WHERE 절에 키를 지정하지 않는 경우
- 배치 실행 시 같은 작업을 두 번 하지 마라. (같은 데이터를 다시 처리 X)
- 실행 중에 재할당에 시간을 쏟지 않게 시작 시 충분한 메모리 할당
- 데이터 무결성을 위해 적절한 유효성 검증 로직 추가 (최악의 상황 고려)
- 실제 프로덕션 환경과 그에 맞는 데이터 볼륨을 가지고 가능한 빨리 부하테스트를 계획하고 실행

## Java Config

`

<aside>
💡 **두 가지 컴포넌트 지원**

`@EnableBatchProcessing`

- 스프링 배치가 제공하는 어노테이션, 배치 인프라스트럭처 부트스트랩 사용
- 배치 인프라스트럭처를 위한 대부분의 빈 정의 제공

****`JobBuilderFactory` , `StepBuilderFactory`

</aside>

```java
@Configuration
@EnableBatchProcessing
@Import(DataSourceConfiguration.class)
public class AppConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Job job(@Qualifier("step1") Step step1, @Qualifier("step2") Step step2) {
        return jobs.get("myJob").start(step1).next(step2).build();
    }

    @Bean
    protected Step step1(ItemReader<Person> reader,
                         ItemProcessor<Person, Person> processor,
                         ItemWriter<Person> writer) {
        return steps.get("step1")
            .<Person, Person> chunk(10)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    protected Step step2(Tasklet tasklet) {
        return steps.get("step2")
            .tasklet(tasklet)
            .build();
    }
}
```

## @EnableScheduling & @Scheduled

- spring 3.1 이상부터 지원
- ex) linux의 crontab

<aside>
💡 scheduler 를 사용할 Class에 `@Component`

Method에 `@Scheduled` 추가

- Method는 void 타입
- 매개변수 사용 불가
</aside>

**1. 스케줄링 활성화 및 실행**

<aside>
✏️ @EnableScheduling :  스케줄링 활성화
@Scheduled : 스케줄링할 작업 추가

</aside>

```java
@Slf4j
@Configuration
@EnableScheduling // 스케줄링을 위해 추가
public class SchedulerConfig {
    @Scheduled(cron = "0/1 * * * * *")
    public void test() {
        log.info("Hello World");
    }

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(SchedulerConfig.class);
    }
}
```

- 따로 ScheduledExecutorService나 SchedulerThreadPool 빈을 등록하지 않으면 
디폴트로 단일 스레드풀을 사용

### 

 

 **2. TaskScheduler 와 Trigger**

<aside>
✏️ TaskScheduler  :  실제 작업을 스케줄링
Trigger : 작업이 수행되어야 할 시간 계산을 담당

</aside>

```java
// 실제 작업을 스케줄링
public interface TaskScheduler {
	@Nullable
	ScheduledFuture<?> schedule(Runnable task, Trigger trigger);
}

// 작업이 수행되어야 할 시간 계산을 담당
public interface Trigger {
	@Nullable
	Date nextExecutionTime(TriggerContext triggerContext);
}
```

 

- TaskScheduler 구현체 : ConcurrentTaskScheduler, ThreadPoolTaskScheduler
    - 따로 등록하지 않으면 ConcurrentTaskScheduler 를 사용
- Trigger 구현체 : CronTrigger, PeriodicTrigger
    - 예시와 같이 Cron 표기법 사용시 CronTrigger 사용
    

**3. 스케줄 등록 및 실행 원리**

1) ****`ScheduledAnnotationBeanPostProcessor` 등록**

- @EnableScheduling → @SchedulingConfiguration → @ScheduledAnnotationBeanPostProcessor 을 빈으로 등록 : 스케줄링 필요 작업 수행

****2) `PostProcessor` 에서 @Scheduled 메서드 작업을 스케줄러에 등록**

- PostProcessor 에서@Scheduled 메서드 작업을 스케줄러에 등록
- PostProcessor.postProcessAfterInitialization에서 
bean에 @Scheduled가 선언된 메서드들을 찾아 postScheduled를 호출
- PostProcessor.postScheduled에서 @Scheduled에 설정된 fixedDelay, fixedRate, cron등에 맞는 Task를 생성하여 ScheduledTaskRegistrar에 등록
- ScheduledTaskRegistrar에서 taskScheduler에 작업을 스케줄링
- taskScheduler.schedule 메서드를 Runnable(@Scheduled 메서드 작업), Trigger(CronTrigger)와 함께 호출

****3) `TaskScheduler` 에서 작업을 스케줄링**

- TaskScheduler.schedule에서는 task, trigger, executor, errorhandler로 
  ReschedulingRunnable를 생성하고 schedule을 호출
    - `ReschedulingRunnable` :  Trigger 를 통해 특정 주기에 맞게 반복적인 수행 처리 담당

### Example

```java
@Scheduled(fixedDelay = 1000)
// @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}") // 문자열 milliseconds 사용 시
public void scheduleFixedDelayTask() throws InterruptedException {
    log.info("Fixed delay task - {}", System.currentTimeMillis() / 1000);
    Thread.sleep(5000);
}
```

- **fixedDelay**
    - 해당 메서드 끝나는 시간 기준, milliseconds 간격 실행
    - 하나의 인스턴스 항상 실행 시 유용
    - 이전 작업이 완료될 때까지 대기
- **fixedDelayString**
    - fixedDelay와 동일하고 설정시간을 문자로 입력하는 경우 사용
    

```java
@Async
@Scheduled(fixedRate = 1000)
// @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")  // 문자열 milliseconds 사용 시
public void scheduleFixedRateTask() throws InterruptedException {
    log.info("Fixed rate task - {}", System.currentTimeMillis() / 1000);
    Thread.sleep(5000);
}
```

- **fixedRate**
    - 해당 메서드 시작하는 시간 기준, milliseconds 간격 실행
    - 병렬로 Scheduler 사용 시,
        - Class 에 `@EnableAsync`
        - Method에 `@Async`
        - 모든 실행이 독립적인 경우 유용
- **fixedRateString**
    - fixedRate와 동일하고 설정시간을 문자로 입력하는 경우 사용
    

```java
Scheduled(fixedDelay = 1000, initialDelay = 5000)
public void scheduleFixedRateWithInitialDelayTask() {
    long now = System.currentTimeMillis() / 1000;
    log.info("Fixed rate task with one second initial delay - {}", now);
}
```

- **fixedDelay + fixedRate**
    - initialDelay  값 이후 처음 실행, fixedDelay  값에 따라 계속 실행
- **initialDelay**
    - 설정된initialDelay 시간 후부터fixedDelay 시간 간격으로 실행
- **initialDelayString**
    - initialDelay와 동일 하고 설정시간(milliseconds)을 문자로 입력

- **Cron**
    - 작업 예약으로 실행
    - cron = "* * * * * *"
    - 첫번째 부터 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7)
    
- **Zone**
    - zone = "Asia/Seoul
    - 미설정시 Local 시간대 사용
    - [https://docs.oracle.com/cd/B13866_04/webconf.904/b10877/timezone.htm](https://docs.oracle.com/cd/B13866_04/webconf.904/b10877/timezone.htm)
    

```java
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component // 추가
@EnableAsync // 추가
public class ScheduleTest {

    /**
     * 해당 메서드 로직이 끝나는 시간 기준, milliseconds 간격으로 실행
     * 이전 작업이 완료될 때까지 대기
     */
    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() throws InterruptedException {
        log.info("Fixed delay task - {}", System.currentTimeMillis() / 1000);
        Thread.sleep(5000);
    }

    /**
     * 해당 메서드 로직이 시작하는 시간 기준, milliseconds 간격으로 실행
     * 이전 작업이 완료될 때까지 다음 작업이 진행되지 않음
     */
    @Async // 병렬로 Scheduler 를 사용할 경우 @Async 추가
    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTask() throws InterruptedException {
        log.info("Fixed rate task - {}", System.currentTimeMillis() / 1000);
        Thread.sleep(5000);
    }

    /**
     * 설정된 initialDelay 시간(milliseconds) 후부터 fixedDelay 시간(milliseconds) 간격으로 실행
     */
    @Scheduled(fixedDelay = 1000, initialDelay = 5000)
    public void scheduleFixedRateWithInitialDelayTask() {
        long now = System.currentTimeMillis() / 1000;
        log.info("Fixed rate task with one second initial delay -{}", now);
    }

    /**
     * Cron 표현식을 사용한 작업 예약
     * 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7)
     */
    @Scheduled(cron = "0 15 10 15 * ?")
    public void scheduleTaskUsingCronExpression() {
        long now = System.currentTimeMillis() / 1000;
        log.info("schedule tasks using cron jobs - {}", now);
    }
}
```
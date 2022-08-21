- [Date, Time](#date-time)
	- [Date와 Time 문제점](#date와-time-문제점)
	- [Java8 Date-Time API](#java8-date-time-api)
		- [기계 시간](#기계-시간)
		- [사람 시간](#사람-시간)

# Date, Time

## Date와 Time 문제점

- java.util.Date 클래스는 mutable한 속성으로 thread가 동시에 접근시 데이터 값이 예상 결과와 다를 수 있다

```java
public class DateAndTime {
    public static void main(String[] args) throws InterruptedException {
        Date date = new Date();

        // machine time, 기계가 읽기 위한 시간
        long time = date.getTime(); // 의미 이상, 몇시 몇분이 아닌 1970년..

        System.out.println("date = " + date);
        System.out.println("time = " + time);

        Thread.sleep(1000 * 3);

        Date after3Seconds = new Date();
        System.out.println("after3Seconds = " + after3Seconds);
        after3Seconds.setTime(time);
        System.out.println("after3Seconds = " + after3Seconds);

        // month가 0부터 시작, Type Safety가 없다
        Calendar gyheoBirthDay = new GregorianCalendar(1993, 1, 14);
        System.out.println("gyheoBirthDay = " + gyheoBirthDay.getTime());
        // getTime인데 Date를 반환, 의미가 명확하지 않다
        gyheoBirthDay.add(Calendar.DATE, 1);
        System.out.println("gyheoBirthDay = " + gyheoBirthDay.getTime());
    }
}
```

```shell
date = Sun Aug 21 11:34:17 KST 2022
time = 1661049257704
after3Seconds = Sun Aug 21 11:34:20 KST 2022
after3Seconds = Sun Aug 21 11:34:17 KST 2022
gyheoBirthDay = Sun Feb 14 00:00:00 KST 1993
gyheoBirthDay = Mon Feb 15 00:00:00 KST 1993
```

- 클래스 이름이 명확하지 않고 메서드명과 리턴 타입이 일치하지 않아 유지보수가 불편

- 버그 발생 가능성이 높다

## Java8 Date-Time API

- 기계 (machine time) vs 사람 (human time)

- DateTimeFormatter를 통해 우리가 원하는 문자열 형식으로 표현 가능

### 기계 시간

- EPOCK (1970-01-01 00:00:00)부터 현재까지의 timestamp로 표현

- Instant, Duration

```java
        Instant instant = Instant.now(); // 지금 기계 시간을 사용자가 보기 좋게 출력
        System.out.println("instant = " + instant); // 기준시 UTC, GMT

        ZoneId zone = ZoneId.systemDefault();
        System.out.println("zone = " + zone);
        ZonedDateTime zonedDateTime = instant.atZone(zone);
        System.out.println("zonedDateTime = " + zonedDateTime);

        Instant instantNow = Instant.now();
        Instant plus = instantNow.plus(10, ChronoUnit.SECONDS); // plus라는 새로운 객체를 만들어낸다 (imnmutable)
        Duration between = Duration.between(instantNow, plus);
        System.out.println("between.getSeconds() = " + between.getSeconds());
```

```shell
instant = 2022-08-21T02:59:43.913542Z
zone = Asia/Seoul
zonedDateTime = 2022-08-21T11:59:43.913542+09:00[Asia/Seoul]
between.getSeconds() = 10
```

### 사람 시간

- 우리가 흔히 사용하는 년, 월, 일, 시, 분 초, 표현

- LocalDateTime, Period

```java
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now = " + now); // 서버의 시스템 정보를 참조해서 그 시간대로 사용
        LocalDateTime birthDay = LocalDateTime.of(1993, Month.JANUARY, 14, 0, 0, 0);
        ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        System.out.println("nowInKorea = " + nowInKorea);

        Instant nowInstant = Instant.now();
        ZonedDateTime zonedDateTimeFromInstant = nowInstant.atZone(ZoneId.of("Asia/Seoul"));
        System.out.println("zonedDateTimeFromInstant = " + zonedDateTimeFromInstant);

        LocalDate today = LocalDate.now();
        System.out.println("today = " + today);

        LocalDate thisYearBirthday = LocalDate.of(2023, Month.JANUARY, 14);
        Period period = Period.between(today, thisYearBirthday);
        System.out.println("period = " + period.getDays());

        Period until = today.until(thisYearBirthday);
        System.out.println("until.get(ChronoUnit.DAYS) = " + until.get(ChronoUnit.DAYS));
```

```shell
nowInKorea = 2022-08-21T12:10:34.030927+09:00[Asia/Seoul]
zonedDateTimeFromInstant = 2022-08-21T12:10:34.031156+09:00[Asia/Seoul]
today = 2022-08-21
period = 24
until.get(ChronoUnit.DAYS) = 24
```
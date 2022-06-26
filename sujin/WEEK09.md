# APM

> (Application Performance Mornitoring)
>
> **목적**
>
> \- 각 서비스 구간 별로 성능을 기록해 병목을 파악하고 대응
>
> \- 기록에 근거해 현재 서비스에서 수용 가능한 트래픽을 예상
>
> \- 알람 등 을 이용해 장애 상황에 빠르게 대응
>
> \- 자원 사용률, GC 상태 등의 정보를 이용해 잘못된 설계, 메모리 누수 등을 사전에 탐지



# pinpoint



https://github.com/pinpoint-apm/pinpoint#supported-modules

https://pinpoint-apm.gitbook.io/pinpoint/#want-a-quick-tour 

http://pinpoint-apm.github.io/pinpoint/1.7.3/main.html 

https://guide-fin.ncloud-docs.com/docs/pinpoint-pinpoint-1-2 



**결론**

:heavy_plus_sign:  아이템 대다수 관리 가능 (AI-OCR 등)

- PHP, PYTHON, JAVA 등의 언어 커버 가능 

:heavy_minus_sign:  DB 까지의 흐름만 확인 가능 ( 내부 활동 X )





![](C:\Users\USER\Desktop\STUDY\SPRING_STUDY\spring-study\sujin\images\WEEK09.assets\pinpoint.png)





- Agent(Springboot와 같이 올라감) → Collector(tomcat에 설치) → hbase(데이터 저장) → Web(데이터 조회)



https://github.com/pinpoint-apm/pinpoint/tree/master/plugins  

```
# 지원되는 모듈

JDK 8+
Tomcat 6/7/8/9, Jetty 8/9, JBoss EAP 6/7, Resin 4, Websphere 6/7/8, Vertx 3.3/3.4/3.5, 
Weblogic 10/11g/12c, Undertow
Spring, Spring Boot (Embedded Tomcat, Jetty, Undertow), Spring asynchronous communication
Apache HTTP Client 3.x/4.x, JDK HttpConnector, GoogleHttpClient, OkHttpClient, 
NingAsyncHttpClient, Akka-http, Apache CXF
Thrift Client, Thrift Service, DUBBO PROVIDER, DUBBO CONSUMER, GRPC
ActiveMQ, RabbitMQ, Kafka, RocketMQ
MySQL, Oracle, MSSQL(jtds), CUBRID, POSTGRESQL, MARIA
Arcus, Memcached, Redis(Jedis, Lettuce), CASSANDRA, MongoDB, Hbase, Elasticsearch
iBATIS, MyBatis
DBCP, DBCP2, HIKARICP, DRUID
gson, Jackson, Json Lib, Fastjson
log4j, Logback, log4j2
```

 ![](C:\Users\USER\Desktop\STUDY\SPRING_STUDY\spring-study\sujin\images\WEEK09.assets\mainDashboard.png)



Main DashBoard

 

![](C:\Users\USER\Desktop\STUDY\SPRING_STUDY\spring-study\sujin\images\WEEK09.assets\callStack.png)

CallStack

![](C:\Users\USER\Desktop\STUDY\SPRING_STUDY\spring-study\sujin\images\WEEK09.assets\Inspector.png)

Inspector







**개요**

**1. Main Dashboard**

- ServerMap - 구성요소가 상호 연결되는 방식을 시각화
  - 분산 시스템의 *토폴로지 이해
    - 컴퓨터 네트워크의 요소들(링크, 노드 등)을 물리적으로 연결해 놓은 것, 또는 그 연결 방식
  - 현재 상태 및 트랜잭션 수와 같은 구성 요소 세부 정보 표시
- 실시간 활성 스레드 차트 : 응용 프로그램 내부의 활성 스레드를 실시간으로 모니터링
- 요청/응답 분산형 차트 : 시간 경과에 따른 요청 수와 응답 패턴을 시각화하여 잠재적인 문제 식별



**2. CallStack**

: 분산 환경의 모든 트랜잭션에 대한 코드 수준 가시성 확보,단일 보기에서 병목 현상과 실패 지점 식별



**3. Inspector**

: CPU 사용량, 메모리/GC, TPS 및 JVM 인수와 같은 애플리케이션에 대한 추가 세부 정보





**특징**

- 자바 기반 어플리케이션 모니터링에 강함
  - 자바 에이전트에서 데이터 수집 → Collector로 전송
- Collector에서는 HBase 에 정보 저장
  - Hbase : agent 로부터 데이터 수집
- 화면에서는 HBase에 저장된 데이터 읽어 사용자에게 저장
- 애플리케이션 앞에 있는 프록시 서버 모니터링 O
  - http://pinpoint-apm.github.io/pinpoint/1.7.3/proxyhttpheader.html 





:heavy_plus_sign: 전반적인 시스템 연계 현황 한눈에

:heavy_plus_sign:  하나의 요청이 여러 서버에 걸쳐 처리될 경우, 
      하나의 서버에서 처리된 것처럼 프로파일링된 결과 확인

:heavy_plus_sign: 웹기반 UI , 어디서나 쉽게 모니터링

:heavy_plus_sign:  여러 비동기  이벤트 지원

:heavy_minus_sign:  실시간 모니터링 약함

:heavy_minus_sign:   커스터마이징 X

:heavy_minus_sign:    초당 처리량이 매우 많을 경우 성능 저하 및 문제 발생



**구성**

1. Agent
2. Collector
   : 코드 수준의 정보 추적 → 트래픽 많아지면 데이터 양 기하급수적 증가 → HBase
3. Web
   : HBase 에서 데이터 조회 → 시각화된 모니터링 서비스 제공 

- DB - HBase (하둡 분산 데이터베이스 기반)
- Collector - Web UI에 보여주는 내용을 쌓아둠, 대상이 되는 타겟 서버의 정보를 HBase에 저장
- Agent - 각각 대상 서버에 pippoint agent 가 collector에 데이터를 udp/tcp + thrift 통해 보냄 





------

 

https://peterica.tistory.com/32

https://tech.trenbe.com/2022/02/22/pinpoint.html 



### 적용 방법   

> Agent , Collector, Web 구조

- 주의  : jdk 설치
- **hbase 설치, 설정, 구동**

```
# hbase/conf/hbase-env.sh : JAVA_HOME 추가
export JAVA_HOME=/usr/lib/...
```



- **agent 설치 및 적용**
  : 각 사용자가 해당 서비스에 직접 설치하고 설정



1. pinpoint agent 바이너리를 다운로드 및 설치 
2. Tomcat Application Service에 Agent 설치

```
# $CATALINA_HOME/bin/catalina.sh 
$AGENT_PATH={PINPOINT_AGENT_INSTALLATION_DIRECTORY}   # agent가 설치된 디렉토리
CATALINA_OPTS="$CATALINA_OPTS -javaagent:$AGENT_PATH/pinpoint-bootstrap-$VERSION.jar"
CATALINA_OPTS="$CATALINA_OPTS -Dpinpoint.agentId=$AGENT_ID"
CATALINA_OPTS="$CATALINA_OPTS -Dpinpoint.applicationName=$APPLICATION_NAME"
```

`$AGENT_PATH`: Pinpoint Agent가 설치된 디렉터리를 지정

`pinpoint-bootstrap-$VERSION.jar`:  릴리즈 버전

`$AGENT_ID`: Pinpoint Web 대시보드에서 각 Application을 구분하기 위한 Unique ID 설정

`$APPLICATION_NAME`: Pinpoint Web 대시보드에서 각 Application Group을 구분하기 위한 ID 설정



3. ${AGENT_PATH}/pinpoint.config 파일에서
       profiler.collector.ip=xxx.xxx.xxx.xxx에 Pinpoint Collector 서버 주소를 입력

4. $CATALINA_HOME/bin/startup.sh을 실행하면, Pinpoint Agent에서 값을 수집하여 
       Pinpoint Collector(Port: 9994)로 전송





------

# DPM



> (Database Performance Management)
>
> \- 안정적인 서비스  제공
>
> \- 데이터 손실 방지
>
> \- 장애 예방



**활용**

- 느린 쿼리 모니터링
  - 비용이 많이 드는 쿼리 개선
  - 필요한 데이터만  반환하게끔 비효율적인 조인 개선
- 데이터베이스 로깅 
  - 사전 데이터 베이스 모니터링
  - ex) SQL injection 공격시 실패한 쿼리 수 증가 감지 
- 주요 지표 측정
  - CPU 및 메모리 사용량과 같은 메트릭을 측정 → 데이터 베이스 성능 향상 여지
  - ex) 타 서비스가 서버에서 실행 중인 경우 간단한 쿼리임에도 비용이 많이 드는 쿼리
    → CPI 사용량과 같은 메트릭 측정 중요성 





편리한 대시보드 상에서 DB의 운영 현황을 실시간으로 파악할 수 있도록 지원 (실시간 서비스에서 더욱 강조)



#  scouter (+ telegraf )  



Scouter

https://github.com/scouter-project/scouter#application-performance-monitoring-for-open-source-sws

Telegraf

https://github.com/influxdata/telegraf#input-plugins

 



**결론**

:heavy_plus_sign:  telegraf 는 input Plugin 을 통해 MYSQL, redis, Kafka 등 dpm 기능  

:heavy_minus_sign:  zipkin은 직접 코드 수정의 단계가 필요

- PYTHON 은 zipkin 을 이용해야 함 
  - zipkin 의 경우 코드를 직접 수정하여 사용 
    -> AI-OCR 등 python 을 커버하기 위해 코드 수정의 단계 추가 



Zipkin : Scouter 의 아파치 분산 저장소


에이전트는 데이터를 수집하여 scouter 서버로 데이터 전송

scouter 의 저장소는 파일 기반의 저장소

공용디스크를 연결하지 않는 이상 한 대의 서버에만 데이터를 저장할 수 있는 구조가 기본

\>> Scouter  2.5 버전 이후부터는 집킨(zipkin)이라는 아파치 분산 저장소를 활용하여 데이터를 저장

| Server(Collector) | Agent 가 전송하는 데이터 처리                                |
| ----------------- | ------------------------------------------------------------ |
| Host Agent        | CPU, Memory, Disk 성능 정보를 서버로 전송 Linux, Windows 및 OS의 성능 메트릭 수집 |
| Java Agent        | Heap Memory, Thread, Profiles 등 자바 프로그램의 실시간 성능 데이터를 수집기로 전송 JVM 및 웹 애플리케이션 서버(예: Tomcat)의 프로필 및 성능 메트릭 수집 |
| Client(Viewer)    | 수집된 성능 정보를 확인하는 클라이언트 프로그램 Eclipse 기반의 UI |

- agent : 모니터링 대상(WAS)에 설치 & 데이터는 UDP, TCP 전송







**Scouter**

**주요기능**

- 서비스 모니터링
  - 동시 사용자, 응답시간, 실시간 처리현황
- 리소스 모니터링
  - CPU, 메모리, DB 커넥션, 파일 IO
- XLog와 트랜잭션 프로파일링
  - 실시간 분포도, 트랜잭션 추적

**특징**

- web api
  - HTTP 프로토콜을 통해 카운터, XLog, 프로필 및 기타 성능 메트릭을 얻기 위해 웹 API를 정찰
  - 이를 활용하여 많은 플러그인
    EX) scouter paper (스카우터 클라이언트의 웹버전) https://scouter-contrib.github.io/scouter-paper/manual.html 
    - 성능 데이터를 웹을 통해 확인할 수 있도록 제공하는 대시보드 소프트웨어
    - paper2.0 부터 Telegraf 데이터 지원 & 전체 시스템 성능 상황 파악
- Scouter Plugin
  - 특정 데이터 선처리
  - 업무적으로 의미 있는 데이터 Xlog, 프로파일에 추가 등
    ex) scouter-plugin-server-null : 수집데이터 단순 출력 sample plugin
          scouter-plugin-server-email : 발생하는 alert 를 email로 전송
          scouter-plugin-server-influxdb : 성능 counter 데이터를 시계열 DB 로 연동
- 모니터링 대상
  - scouter agent (java/host)
  - Telegraf (Redis, nginX, Kafka, MySQL, ElasticSearch, …)
  - zipkin-scouter-storage (C#, Go, Python, Javascript, PHP,… )
- 서비스 연계 추적
  - http 요청하는  서비스 간 연결 추적이 가능
    ex) /agent.java/conf/scouter.conf 
          trace_interservice_enabled=true
  - 추적 방법
    - Xlog List 에서 transaction 확인 → txid 선택 → 하위의 연계 확인 가능 
- 서비스 Dump
  - 응답 지연과 같은 성능 장애 발생 시 Thread Dump 제공 (멀티쓰레드 환경에서 쓰레드 확인)
     



**Telegraf**

- Telegraf 에서 Scouter로 데이터 통합
- InfluxDB에서 제작한 시스템 모니터링 및 지표 수집 에이전트
- 플러그인 시스템을 기반으로 제작 
  -> 여러 소프트웨어 혹은 서비스를 위한 지원을 간단하게 추가
- 다양한 백엔드로 수집한 데이터를 전송 가능
  ex) influxDB, ElasticSearch 등



**✔ Input plugin : 다양한 제품의 성능 정보 모니터링**


https://github.com/influxdata/telegraf/tree/master/plugins/inputs/mysql : MYSQL 
https://github.com/influxdata/telegraf/tree/master/plugins/inputs/nginx : NGINX
https://github.com/influxdata/telegraf/tree/master/plugins/inputs/redis : Redis
https://github.com/influxdata/telegraf/tree/master/plugins/inputs/kafka_consumer : Kafka 

- mariaDB (확인 필요) 
  -  https://github.com/scouter-project/scouter/issues/860 
    https://github.com/scouter-project/scouter-mariadb-plugin 

**✔ HTTP output plugin : telegraf 에서 전송된 데이터 Scouter에 통합 사용**







------

### 적용방법

- 주의점
  - 사전에 jdk 설치 필요



- server (collector)

```
# scouter_home/server/conf/scouter.conf

db_dir=/scouter/server/data  # 수집된 data 저장 경로
log_dir=/scouter/server/logs/ # log directory
net_udp_listen_port=6100
het_tcp_listen_port=6100


# 서버 환경에 맞는 메모리 설정 (default -Xmx512m)
-Xms2048m -Xmx2048m 

# 서버 기동과 중지
$scouter_home/server/startup.sh
$scouter_home/server/stop.sh

# 프로세스 상태 확인
#ps ax | grep scouter-server | grep -v grep
```



- agent host 

```
# 구동
net_collector_ip=127.0.0.1    # collector IP
net_collector_udp_port=6100   # collector 서버 udp port
net_collector_tcp_port=6100   # collector 서버 tcp port

# cpu 관련 
cpu_warning_pct=80    # CPU 경고 임계치
cpu_fatal_pct=85      # CPU 에러 임계치
cpu_check_period_ms=60000 # CPU 감시 주기(ms)
cpu_fatal_history=3   # CPU 알람 누적 카운트
cpu_alert_interval_ms=30000 # CPU 알람 주기 (ms)
disk_warning_pct=88   # CPU 경고 임계치 (%)
disk_fatal_pct=92     # CPU 에렁 임계치 (%)

# 스카우터 서버의 기동과 중지
cd  scouter_home/server/agent.host
./host.sh
./stop.sh
```



- agent java

```
obj_name=tomcat_1   # 감시 대상 서버 별칭
net_collector_ip=127.0.0.1  # collector IP
net_collector_udp_port=6100 # collector udp Listen port
net_collector_tcp_port=6100 # collector tcp Listen port
# 독립 실행이 아닌 WAS 실행 시 attach 되어 실행
export SCOUTER_AGENT_DIR="/scouter_home/agent.java/"  # scouter.agent.jar 위치

JAVA_OPTS=" ${JAVA_OPTS} -javaagent:${SCOUTER_AGENT_DIR}/scouter.agent.jar"
JAVA_OPTS=" ${JAVA_OPTS} -Dscouter.config=${SCOUTER_AGENT_DIR}/conf/scouter.conf"
JAVA_OPTS=" ${JAVA_OPTS} -Dobj_name=WAS-01"
```



- Scouter 과 ElasticeSearch연동 설정
  - scouter Server Plugin으로 성능 counter 정보 와 XLOG 정보를 ElasticSearch 로 전송해 주는 plugin

https://github.com/scouter-contrib/scouter-plugin-server-elasticsearch 

```
# scouter/conf/scout er.conf

# 기본 설정
ext_plugin_es_enabled : 본 plugin 사용 여부 (default : true)
ext_plugin_es_counter_index : elasticsearch counter index 명 (default : scouter-counter)
ext_plugin_es_xlog_index : elasticsearch xlog index 명 (default : scouter-xlog)
ext_plugin_ex_duration_day : elasticsearch index 저장 기간 (default : 90)

# http 방식 연동 여부 설정
ext_plugin_es_https_enabled : https 사용 여부 (default : http 사용)
ext_plugin_es_cluster_address : http target(elasticsearch) address (default : 127.0.0.1:9200)

>> 엘라스틱서치를 쿨러스터로 운영 중이면 콤마로 구분 지어 붙인다. ex) 127.0.0.1:9200,127.0.0.1:9201

ext_plugin_es_id : (default : empty)
ext_plugin_es_password : (default : empty)
```



- **Xlog**

> \- x 축 : 트랜잭션 종료 시간 (왼/오 방향키로 10초 이동 가능)
> \- y 축 : 트랜잭션 응답 시간 
>
> 시스템의 현재 상태를 응답 시간의 관점에서 요약
>
> 각각의 트랜잭션을 분석하고 시스템의 전반적인 상황을 한눈에 볼 수 있는 산포도
>
> 하나의 점은 하나의 요청
>
> 마지막 바이트까지 응답하여 요청이 끝났을 때 점이 찍힘.



점을 드래그하여 확인 가능한 정보

- CPU : CPU를 점유한 시간, 단위 밀리초
- SQL Count : SQL 수행 개수, 단위 개수
- SQL Time : SQL 수행 시간, 단위 밀리초
- API Count: API 호출 개수, 단위 개수
- API Time : API 수행 시간, 단위 밀리초
- KBytes : 요청을 처리하는 데 사용한 메모리의 양, 단위 킬로바이트

상세 화면 내용 분석

- CPU : CPU를 점유한 시간, 단위 밀리초
- SQL Count : SQL 수행 개수, 단위 개수
- SQL Time : SQL 수행 시간, 단위 밀리초
- API Count: API 호출 개수, 단위 개수
- API Time : API 수행 시간, 단위 밀리초
- KBytes : 요청을 처리하는 데 사용한 메모리의 양, 단위 킬로바이트
- txid : 트랜잭션 ID
- objName : 호스트/인스턴스 이름
- thread : 수행한 스레드
- endtime : 요청 종료 시간
- elapsed : 수행 시간
- service : 요청 URL
- ipaddr : 호출한 서버/클라이언트의 IP 및 scouter 사용자 ID
- cpu : CPU 점유 시간 및 요청 처리 시 사용한 메모리 크기
- sqlCount : SQL 개수 및 시간
- userAgent : 호출한 클라이언트의 종류
- group : 요청 그룹
- profileSize : 프로파일 크기









### Telegraf 설정 방법

> \1. Scouter 의 telegraf Server 옵션 활성화
>
> \2. Telegraf 의 Http output을 통해 scouter 데이터 전송 설정
>
> \3. Scouter Collector에 counter mapping 설정
>
> : telegraf에서 전달되는 measurement 의 field를 scouter counter에 mapping
>
> \-  measurement 등록
>
> \-  Host Mapping 설정 (optional)
>
> \-  object type 설정
>
> \-  line protocol 의 각 field 를 scouter의 counter에 매핑
>
> cf)  family : 동일한 성능 모니터링 항목을 가지고 있는 집합을 지칭하는 이름
>      object type : scouter에서 한번에 모니터링하는 대상의 집합
>      object : 개별 모니터링 대상
> \4. counters.site.xml 확인     



```
# Telegraf 의 HTTP output 을 통해 scouter로 데이터 전송 설정
[[outputs.http]]
    url = "http://my-scouter-server:6180/telegraf/metric"
    timeout = "5s"
    method = "POST"
    data_format = "influx"

[agent]
  ...
  interval = "4s"
  ...
  flush_interval = "4s"
```



```
[global_tags]
  datacenter = "linode-tyo2"
  type = "vm"

[agent]
  interval = "10s"
  round_interval = true
  metric_batch_size = 1000
  metric_buffer_limit = 10000
  connection_jitter = "1s"
  flush_interval = "10s"
  flush_jitter = "1s"
    
[[outputs.influxdb]]
  urls = ["http://localhost:8086"]
  database = "telegraf"
  retention_policy = ""
  write_consistency = "any"
  timeout = "5s"


[[inputs.cpu]]
  percpu = true
  totalcpu = true
  collect_cpu_time = false

[[inputs.disk]]
  ignore_fs = ["tmpfs", "devtmpfs", "devfs"]

[[inputs.diskio]]

[[inputs.kernel]]

[[inputs.mem]]

[[inputs.processes]]

[[inputs.swap]]

[[inputs.system]]

[[inputs.postgresql]]
  address = "postgres://test:pass@localhost/db?sslmode=disable""
  databases = ["db"]
```

- [global_tags] :  모든 지표에 추가될 태그 정의
  - 예시에서는 datacenter, type : 해당 인스턴스 위치와 종류
  - telegraf tag 추가시 흔히  사용
    ex) scouter_obj_type_prefix=”ORDER_SYSTEM”
- [agent]  : 에이전트의 기본 동작 방식 정의
- [outputs] : 수집된 지표를 어디로 보낼지 정의
  ex) outputs.kafka 등 다양한 출력 정의 가능
- [inputs] : 어떤 지표를 수집할지 정의
  ex) 리소스 사용량, PostgreSQL 데이터 통계 수집 설정









- Scouter Paper

https://www.youtube.com/watch?v=NjJ0dGhdIbU 
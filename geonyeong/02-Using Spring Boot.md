- [Using Spring Boot](#using-spring-boot)
  - [1. Building Systems](#1-building-systems)
    - [1.1 Dependency Management](#11-dependency-management)
    - [1.2 Maven](#12-maven)
    - [1.3 Gradle](#13-gradle)
    - [1.4 Ant](#14-ant)
    - [1.5 Starters](#15-starters)
  - [2. Structuring Your Codes](#2-structuring-your-codes)
    - [2.1 Using the “default” Package](#21-using-the-default-package)
    - [2.2 Locating the Main Application Class](#22-locating-the-main-application-class)
  - [3. Configuration Classes](#3-configuration-classes)
    - [3.1 Importing Additional Configuration Classes](#31-importing-additional-configuration-classes)
    - [3.2 Importing XML Configuration](#32-importing-xml-configuration)
  - [4. Auto-configuration](#4-auto-configuration)
    - [4.1 Graduallly Replacing Auto-configuration](#41-graduallly-replacing-auto-configuration)
    - [4.2 Disabling Specific Auto-configuration Classes](#42-disabling-specific-auto-configuration-classes)
  - [5. Spring Beans and Dependency Injection](#5-spring-beans-and-dependency-injection)
  - [6. Using the @SpringBootApplication Annotation](#6-using-the-springbootapplication-annotation)
  - [7. Running Your Application](#7-running-your-application)
    - [7.1 Running from an IDE](#71-running-from-an-ide)
    - [7.2 Running as a Packaged Application](#72-running-as-a-packaged-application)
    - [7.3 Using the Maven Plugin](#73-using-the-maven-plugin)
    - [7.4 Using the Gradle Plugin](#74-using-the-gradle-plugin)
  - [8. Developer Tools](#8-developer-tools)
  - [9. Packaging Your Application for Production](#9-packaging-your-application-for-production)
  - [Reference](#reference)

# Using Spring Boot

## 1. Building Systems

We would recommend that you choose `**Maven**` or `**Gradle**`

### 1.1 Dependency Management

### 1.2 Maven

### 1.3 Gradle

### 1.4 Ant

### 1.5 Starters

A set of convenient dependency descriptors that you can include in your application

All Official starters follow a similar naming pattern; `**spring-boot-starter-***`

## 2. Structuring Your Codes

### 2.1 Using the “default” Package

We recommend that you follow Java’s recommended package naming conventions (`com.example.project`)

### 2.2 Locating the Main Application Class

`**application**` 을 클래스 패키지의 최상단 위에 설정해 둘 것

`@SpringBootApplication` annotated class is used to search for `@Entity`

`@SpringBootApplication` contains `@EnableAutoConfiguration`, `@ComponentScan`, `@ConfigurationPropertiesScan`

## 3. Configuration Classes

Spring Boot **favors Java-based configuration.**

We generally recommend that your primary source be a single `@Configuration`

### 3.1 Importing Additional Configuration Classes

Alternatively, you can use `@ComponentScan` to automatically pick up all Spring components, including `@Configuration` classes.

### 3.2 Importing XML Configuration

If you absolutely must use XML based configuration, we recommend that you still start with a `@Configuration` class

## 4. Auto-configuration

Spring Boot auto-configuration attempts to automatically configure your Spring application

### 4.1 Graduallly Replacing Auto-configuration

If you need to find out what auto-configuration is currently being applied, and why, start your application with the `--debug` switch. Doing so enables debug logs for a selection of core loggers and logs a conditions report to the console.

### 4.2 Disabling Specific Auto-configuration Classes

`@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})`

## 5. Spring Beans and Dependency Injection

All of your application components (`@Component`, `@Service`, `@Repository`, `@Controller` etc) are automatically registered as Spring Beans

If a bean has one constructor, you can omit the `@Autowired`

`final`, indicating that it cannot be subsequently changed

## 6. Using the @SpringBootApplication Annotation

- `@EnableAutoConfiguration` : enable Spring Boot’s auto-configuration mechanism
- `@ComponentScan` : enable `@Component` scan on the package where the application is located
- `@ConfigurationPropertiesScan` : enable `@ConfigurationProperties` scan on the package where the application is located
- `@Configuration` : allow to register extra beans in the context or import additional configuration classes

## 7. Running Your Application

One of the biggest advantages of packaging your applicaiton as a jar

### 7.1 Running from an IDE

### 7.2 Running as a Packaged Application

### 7.3 Using the Maven Plugin

### 7.4 Using the Gradle Plugin

## 8. Developer Tools

`spring-boot-devtools` module can be included in any project to provide additional development-time features.

## 9. Packaging Your Application for Production

## Reference
- Spring Boot Documentaion ([Using Spring Boot](https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/reference/html/using-spring-boot.html#using-boot))
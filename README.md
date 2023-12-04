# Spring-Blog-Project
## Springboot - Vue 연동 및 Docker 배포

**백엔드 : spring, springboot, restful api, jwt, jpa, mysql**

**프론트 : vue, vuex, toast ui editor**
강의 복습 2번 / 개인 PDF 복습 4번

## 프로젝트 소개




* [도메인 분석 및 설계](#도메인-분석-및-설계)
* [Spring Data Jpa 분석](#spring-data-jpa-적용)
* [Restful API 개발](#restful-api-개발)
* [Querydsl 적용](#querydsl-적용)
* [Stacks](#stacks)
* [화면구성](#화면구성)
* [배운점](#배운점)

---

### 도메인 분석 및 설계

|회원 엔티티 분석|회원 테이블 분석|
|---|---|


---

### Spring Data Jpa 적용

순수 JPA를 통해 CRUD를 구현하기 위해선 Repository 빈을 등록하는 클래스를 만들고 이 안에서 CRUD를 구현한 함수들을 작성해줘야 합니다.

예시로 save(), findOne(), findAll(), findByName(), delete() .. 등 하나부터 전부 구현해야 합니다. 

하지만, Spring Data JPA가 제공하는 공통 인터페이스(JpaRepository<T, ID>)를 상속받아 사용하면 주요 CRUD 메소드를 구현하지 않고 사용할 수 있습니다.

공통 인터페이스를 상속한 레포지토리는 컴포넌트 스캔을 Spring Data JPA가 자동으로 처리해주기 때문에 @Repository 애노테이션을 생략할 수 있습니다.

T 부분에는 엔티티 타입을 작성하고 ID에는 엔티티를 식별하는 속성의 타입을 작성합니다.

주요 메서드 (S: 엔티티와 그 자식 타입 | T: 엔티티 | ID: 엔티티의 식별자 타입)
* save(S)
* delete(T)
* findById(ID)
* findAll(...)

이 프로젝트에서는 Spring Data JPA로 쿼리 메소드를 다음과 같은 방식으로 생성하였습니다.

1. 메소드 이름으로 쿼리 생성
2. @Query로 레포지토리 메소드에 쿼리 정의

@Query는 실행할 메소드에 정적 쿼리를 직접 작성할 수 있고 애플리케이션 실행 시점에 문법 오류를 발견할 수 있었습니다.

지연 로딩으로 연결된 엔티티들의 정보를 Fetch Join으로 조회하기 위해서 @EntityGraph와 @Query 애노테이션을 사용하였습니다.

[Repository Code](https://github.com/kylo-dev/Springboot-Jpashop/tree/springdatajpa/src/main/java/jpabook/jpashop/repository)

---

### Restful API 개발

[API 명세서 with Postman](https://documenter.getpostman.com/view/28292619/2s9YJW5R13)

Restful API 개발을 하면서 꼭 알아야 할 사항들을 배웠습니다.
1. Request or Response 값은 엔티티로 받는 것이 아닌 별도의 DTO로 받아 처리한다.
    * 엔티티에 프레젠테이션 계층 분리
    * DTO를 통해 다양한 API 요청을 해결하면서 엔티티에 영향을 주지 않는다.
2. 모든 엔티티의 관계는 즉시 로딩이 아닌 지연로딩으로 설정한다.
   * 지연 로딩 조회시 최적화를 위해 컬렉션이 아닌 경우는 Fetch Join을 통해 거의 해결이 된다.
   * 페이징이 필요한 경우 컬렉션은 Fetch Join 하지 않고 batch_fetch_size or @BatchSize를 통해 최적화한다.
3. 엔티티를 DTO로 변환해 조회하는 방식으로 해결이 되지 않는 경우 -> DTO 직접 조회 방식으로 최적화한다.

---


### Stacks
#### Environment
<div>
    <img src="https://img.shields.io/badge/Intellij-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
    <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white">
    <img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=github&logoColor=white">
    <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white">
</div>

#### Development
<div>
    <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
    <img src="https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">
    <img src="https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
</div>

---

### 화면구성

|메인 페이지|회원등록 페이지|상품등록 페이지|
|---|---|---|

---

### 배운점

> 


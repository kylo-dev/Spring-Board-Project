# Spring-Blog-Project
## Springboot - Vue 연동 및 Docker 배포

**백엔드 : spring, springboot, restful api, jwt, jpa, mysql**

**프론트 : vue, vuex, html, css, toast ui editor**

[Github Vue Code](https://github.com/kylo-dev/myblog)

## 프로젝트 소개

Blog의 회원가입, 로그인, 로그아웃, 게시글과 댓글 CRUD 기능을 만들어 보며 Springboot를 통해 Restful API 개발 방법을 익혔습니다.

Springboot 프로젝트에서 템플릿 문법인 Thymeleaf를 이용해 한 서버 프로그램에서 프론트와 백엔드를 구현할 수 있지만, 
제가 작성한 Restful API가 실제로 프론트 측에게 **어떻게 전달되며**, **어떤 형식으로 전달하는 것이 좋은지** 
직접 확인해 보고자, 프론트와 백엔드를 분류하여 개발해보았습니다.

추후에, 분류한 **프로그램을 Docker에 배포**하여 도커 이미지 생성, Dockerfile, Docker-compose 파일을 작성해 보며 
Front Server, Backend Server, Database Server를 연동시켜 봤습니다.


* [시스템 구성도 및 도메인 설계](#시스템-구성도-및-도메인-설계)
* [Spring Code 분석](#springboot-code-분석)
* [Restful API 개발](#restful-api-개발)
* [Docker 배포](#docker-배포)
* [Stacks](#stacks)
* [화면구성](#화면구성)
* [배운점](#배운점)

---

### 시스템 구성도 및 도메인 설계

| 시스템 구성도 | 데이터베이스 |
|---------|--------|
|<img src="https://github.com/kylo-dev/Spring-Blog-Project/assets/103489352/423816d7-8156-4f94-8a13-0fc40aa35982" width="450px" height="300px">|<img src="https://github.com/kylo-dev/Spring-Blog-Project/assets/103489352/11955ba3-b749-4a9e-b2d1-05b121bfdb6c" width="450px" height="300px">|


---

### Springboot Code 분석

기본 페이지에서 게시글 상세보기를 누르게 되면 글의 세부 정보 내용이 백엔드에서 프론트로 데이터를 전달합니다.

이때 전달해야하는 데이터는 Board의 게시글 정보, 작성자인 경우 수정 및 삭제할 권한을 주기 위해 User의 정보, 게시글
의 작성된 Reply의 정보를 전달해야 합니다.

1. `BoardRepository.findById(boardId)`로 게시글의 정보만 가져온 다음에 User와 Reply의 정보를 가져올 수 있지만
ManyToOne 관계인 User를 `board.getUser()`하게 되면 Query가 한 번 더 발생하게 됩니다.
   * 이를 최적화 하기 위해 Board와 User를 Fetch Join 하여 조회하였습니다. 위와 같이 데이터를 조회할 때 
   두 테이블의 정보가 모두 필요한 경우(지연로딩으로 설정됨)는 Fetch Join으로 조회하여 쿼리를 최적화 할 수 있었습니다.


2. Board와 Reply는 OneToMany 관계로 단순히 Fetch Join으로 조회하게 되면 문제가 발생합니다.
   1:N에서 1을 기준으로 조회 하는 것이 목표인데 N을 기준으로 row, result가 생성됩니다. 또한, 페이징을
   적용할 수 없는 문제가 있습니다.
   * 이를 해결하기 위해 `hibernate.default_batch_fetch_size`을 설정하여 데이터를 설정한 개수 만큼 불러옵니다.
      `board.getReplies()`로 지연 로딩을 초기화 하면 이때, Reply에 데이터를
      `where ... in...` 으로 조회합니다.


3. Reply는 여러 개의 값을 가지고 있으므로 List<Reply> 로 컬렉션 형태로 DTO에 저장하게 됩니다. 하지만 List<Reply>로 데이터를 저장하게 되면
   데이터의 순환 참조가 발생하는 문제가 발생합니다.
   * 이를 해결하기 위해 `List<Reply>` -> `List<ReplyDto>` 로 변경하여 Reply도 엔티티가 아닌 DTO 형태로 변환해 주어여 합니다.


```JAVA
@GetMapping("/api/board/{id}/reply")
public BoardReplyDto findBoardWithReply(@PathVariable Long id){
  Board board = boardService.findBoardWithUser(id);  // Board - User Fetch Join
  return BoardReplyDto.builder()
          .board_id(board.getId())
          .title(board.getTitle())
          .content(board.getContent())
          .user_id(board.getUser().getId())  // User Query 발생 X
          .username(board.getUser().getUsername())
          .replies(board.getReplies().stream()  // Reply Query 발생
                  .map(BoardReplyDto::convertDto)  // List<ReplyDto>로 변환
                  .collect(Collectors.toList()))
          .build();
}
```

---

### Restful API 개발

[API 명세서 with Postman](https://documenter.getpostman.com/view/28292619/2s9YeLWoPn)

Restful API 개발을 하면서 배운점
1. Request or Response 값은 엔티티로 받는 것이 아닌 별도의 DTO로 받아 처리한다.
    * 엔티티에 프레젠테이션 계층 분리
    * DTO를 통해 다양한 API 요청을 해결하면서 엔티티에 영향을 주지 않는다.
    * 엔티티를 조회할 때 발생하는 순환 참조가 발생하지 않는다.


2. 모든 엔티티의 관계는 즉시 로딩이 아닌 지연로딩으로 설정한다.
   * 지연 로딩 조회시 최적화를 위해 컬렉션이 아닌 경우는 Fetch Join을 통해 거의 해결이 된다.
   * 페이징이 필요한 경우 컬렉션은 Fetch Join 하지 않고 `batch_fetch_size or @BatchSize`를 통해 최적화한다.


3. 엔티티를 DTO로 변환해 조회하는 방식으로 해결이 되지 않는 경우 -> DTO 직접 조회 방식으로 최적화한다.

---

### Docker 배포

**배운점**

1. Docker를 처음 이용해 보면서 프로젝트 파일을 **Docker image, Docker hub** 배포 하는 방법에 대해 알 수 있었습니다.


2. **docker-compose 파일 작성**하여 프론트, 백엔드, 데이터베이스 서버를 설정해 보면서 3개의 서버를 연동하는
   방법에 대해 알 수 있었습니다.

**트러블 슈팅**

1. Spring 컨테이너와 Mysql 컨테이너가 연결이 되지 않는 오류가 있었습니다. 
   백엔드 서버 실행 시 발생하는 에러의 로그를 확인해 보며 Mysql 연동에 문제가 있는 것을 파악하고
   기존의 application.yml에서 설정했던 부분을 docker-compose 파일에서 `SPRING_DATASOURCE_**` 를 통해 설정하면서 
   오류를 해결할 수 있었습니다.


2. Spring 컨테이너와 Vue 컨테이너가 서로 데이터 전달이 되지 않은 문제가 있었습니다.
   로컬 컴퓨터에서 실행하면 데이터 전달이 잘 되었지만, Docker에 올리고 하나의 Network에 연결하여 사용할 때 Vue 코드에서 
   Axios 부분의 url 경로를 변경하지 않아 발생하는 문제였습니다.

   `axios.get('/api/**')` -> `axios.get('http://' + window.location.hostname + ':8080' + '/api/**')` 으로 변경하여
   컨터이너에 정확하게 어느 URL과 데이터 통신이 이루어지는지 작성하여 오류를 해결할 수 있었습니다.


<img src="https://github.com/kylo-dev/Spring-Blog-Project/assets/103489352/5e60e28a-c361-4b76-a084-2a75154eb88e" width="600px">

---


### Stacks

#### Development
<div>
    <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
    <img src="https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/Vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white">
    <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
    <img src="https://img.shields.io/badge/Mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
    <img src="https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">

</div>

#### Environment
<div>
    <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white">
    <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white">
    <img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=github&logoColor=white">
    <img src="https://img.shields.io/badge/Intellij-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
</div>

---

### 화면구성

|메인 페이지|게시글 상세 보기|회원가입|
|---|---|---|
|<img src="https://github.com/kylo-dev/Spring-Blog-Project/assets/103489352/252d9190-0059-4248-9bff-d75b35d21933" width="450px" height="300px">|<img src="https://github.com/kylo-dev/Spring-Blog-Project/assets/103489352/8490780f-4c21-4055-9639-40c27cace5fe" width="450px" height="300px">|<img src="https://github.com/kylo-dev/Spring-Blog-Project/assets/103489352/94bcb350-1b23-4d6d-8463-94c8322904af" width="450px" height="300px">|

---

### 배운점

> 백엔드 서버와 프론트 서버에서 데이터를 주고 받기 위해 Axios 통신을 사용해보며
   비동기 통신 기술을 이해하였습니다.

> RequestBody로 엔티티가 아닌 DTO로 받고, 응답 값 또한 DTO로 응답해 보면서 프론트엔드
   가 전달하는 값은 무엇인지(요청 값에 엔티티의 PK값이 들어가는지), 
   응답 값으로 어떻게 전달해야하는 지(Sucess or Fail)를 알 수 있었습니다.

> 일대다, 다대일 관계에 있어 조회시 `N+1` 문제와 `데이터 뻥튀기` 문제를 경험하고 이를
   해결해보면서 JPA의 기능인 Fetch Join과 Batch Size를 이해할 수 있었습니다.
 
> 마지막으로, 완성된 두 프로젝트를 Docker에 배포하면서 Jar 파일 생성 및 dockerfile를 작성해봤습니다.
   docker 명령어를 통해 Docker image를 생성하고 Docker Hub에 올려보며 여러 명령어를 사용하였습니다.
   docker-compose.yml 파일을 통해 3개의 서버를 관리 및 설정해보면서 연동 방법을 이해할 수 있었습니다.
   


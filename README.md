# AmrNotice

## Prerequisite
* java8
* Gradle
* Spring Boot 2.0.8
* git
* redis
* frontend : vuejs

## 개발환경
기본적으로 IDE는 IntelliJ 를 사용했으며 Frontend 파트는 Visual Source Code를 사용했습니다.

먼저 프로젝트 clone 후 작업디렉토리로 이동합니다.
```
$ git clone https://github.com/idek7/AmrNotice.git
$ cd AmrNotice/test
```

## Build & Run using IntelliJ
#### IntelliJ 설정
* File > New > Project from Existing sources 선택
* 소스 기본 디렉토리 (AmrNotice/test) 선택
* import poject from external model 체크 및 gradle 선택 후 Next 클릭
* Use auto-import 체크
* jdk 및 gradle home 확인 후 Finish 선택
* gradle sync 완료 확인
* File > Settings 선택 후 Build, Execution, Deployment > Compiler > Annotation Processors 에서 Enable annotatation processing 체크 (lombok annotation 인식을 위해서 필요)

#### Run on IntelliJ
* IntelliJ에서 src/main/java 폴더 아래에 me.amr.MainApplication 실행 (파일에서 마우스 우클릭 후 run)
* Run 콘솔 창에서 spring boot 실행과정 확인
* 크롬 브라우저에서 http://localhost:9090 으로 접속하여 로그인 페이지 확인

#### Run with Executable JAR
* IntelliJ에서 gradle 창의 tasks > build > jar 선택
* build/libs 디렉토리에 AmrNotice-0.0.1-SNAPSHOT.jar 확인
* executable jar 파일로 실행
```
java -jar AmrNotice-0.0.1-SNAPSHOT.jar
```
* 터미널에서 spring boot 실행과정 확인
* 크롬 브라우저에서 http://localhost:9090 으로 접속하여 로그인 페이지 확인

## Build & Run using CLI
* git clone 이후 작업 디렉토리로 이동
```
$ cd AmrNotice/test
```
* 다음 명령을 통해 build 및 실행
```
$ ./gradlew build -x test
$ ./gradlew bootRun
```

## FrontEnd
기본적으로 build된 frontend 파일들이 spring boot의 resources/static 하위 폴더에 적용된 상태입니다.
만약 별도로 테스트 하거나 실행해야 할 경우 다음 build 및 run 관련 절차를 참고하면 됩니다.

### Build
먼저 node 및 npm 은 설치되어 있다고 간주하고 이곳에서는 해당 내용은 생략합니다.
* frontend 폴더로 이동 후 필요한 module 들 설치
```
$ cd frontend
$ npm install
```
### Run
* 다음 명령을 수행하여 webpack server 를 통해 vuejs 가 동작
```
npm start
```
* 이후 크롬 브라우저에서 http://localhost:8080 접속 후 로그인 페이지 확인
* 개발 및 테스트 용도로 frontend를 별도로 실행할 경우에는 Spring Boot 가 별도로 실행 중이라고 간주하고 동작하며 dev 환경설정상 Spring Boot REST API 통신을 위해 proxy 설정으로 동작
### Deploy
* 다음 명령을 수행하여 Spring Boot의 static web page로 동작
```
npm run build
```
* Spring Boot의 resources 폴더의 하위에 static 폴더가 생성되었고 index.html 및 js, css 관련 파일들이 복사된 것을 확인

### 화면 확인
#### 로그인
* 로그인 화면에서 다음 계정 정보로 로그인
```
id : admin
password : 1234
```
* 로그인 후 화면 상단의 오른쪽에 로그아웃 버튼을 클릭하여 로그아웃 가능
* 기본적으로 admin 유저만 존재하며 별도 유저 생성 frontend는 존재하지 않지만 swagger(http://localhost:9090/swagger-ui.html)를 통해 별도 유저를 생성할 수 있음
* 로그인 시 JWT Token이 발급되며 token이 없거나 비정상적인 token 으로 공지사항 관련 API를 호출할 경우 Unauthorized error 발생

#### 공지사항

##### 리스트 확인
* 공지 작성 시 리스트에서 제목, 작성자, 작성날짜 등의 기본 정보 확인 가능

##### 내용 확인
* 공지 리스트에서 특정 공지의 제목 클릭 시 dropdown 형태로 내용 및 최초 생성날짜 정보 확인 가능

##### 페이징 처리
* CrudRespository 를 통해 paging 처리하지 않고 전체 리스트를 받아서 frontend 단에서 paging 처리하는 형태로 구현함
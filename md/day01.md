## 1일차
## SpringBoot 개요

### Spring Boot 환경설정
1. MacOs -> Java JDK 17.0 버전 이상
2. Extension pack for java 설치 후 세팅에서 setting.json 파일 오픈
3. 경로에 맞게 jdk 설정
  ```
      {
      "java.home": "/usr/lib/jvm/jdk-21",
      "java.configuration.runtimes": [
          {
              "name": "JavaSE-21",
              "path": "/usr/lib/jvm/jdk-21",
              "default": true
               }
          ]
       }
  ```
4. Spring 설치 -> `Spring Extesion Pack`, `Spring Initializr Java Support` 설치
5. Gradle 설치 -> `Gradle for Java` 설치
6. Springboot 프로젝트 생성
7. 명령 팔레트(shift + cmd + p) -> Spring Gradle -> 3.2.6 V
8. language -> Java
9. Group Id -> 개인 아이디임으로 변경가능!(com.come1997)
10. Artifact Id -> spring01 **(대문자 불가!)**
11. Packing type -> Jar.17 V
12. dependencies -> Selected 0 dependencies
13. Dialog folder -> 본인이 원하는 폴더 선택 후 Generate.. 버튼 선택
14. 우측 하단 Open 버튼 클릭
15. Lanuage Supportfor Java(JM) 설정 항상 선택
16. 프로젝트 생성 후 `build.gradle` 확인
17. src/main/resources/application.properties 확인
18. src/main/group/arifactid/Java 소스파일 위치, 작업
19. src/main/resources 프로젝트 설정 파일, 웹 리소스 파일 (css, js, html...)
20. Spring01Application.java, Run | Debug 메뉴
21. Gradle 빌드
    - SpringBoot Dashboard
    - Apps -> Spring01 Run | Debug 중에서 하나 아이콘 클릭해 서버 실행
    - 디버그로 실행해야 `Hot code replace`가 등장!!
<img src="https://github.com/king-dong-gun/basic_springboot/blob/main/images/sp01.png" width="350">

22. 터미널에서 `.\gradle.bat` 실행
23. `Gradle for java` 코끼리 아이콘 -> Task -> Build -> Build play icon(Run task) 실행
24. Spring Boot Dashboard -> Apps -> Spring01 Run | Debug 중 하나 아이콘 클릭 
25. 브라우저 변경설정 -> browser -> Spring>Dashboard -> 크롬 설정
   

    > **Trouble Shooting**
    >
    > 프로젝트 생성 중 Gradle Connection 에러가 뜨면?
    >
    > Extenstions -> Gradle for Java 제거 -> VS CODE 재실행 후 프로젝트 재생성
    > 
    > Gradle 빌드시 버전 에러로 빌드가 실패하면 `Gradle build tool`사이트에서 에러에 표시된 버전의 `Gradle bt` 다운로드 후 설치 

    > `:complieJava execution failed...` JDK 에러 메세지, 잘못된 설치다! `build.gradle Spring boot 버전 다운 3.3.0 -> 3.1.5`


### Spring 기초

Spring MVC
<img src="https://github.com/king-dong-gun/basic_springboot/blob/main/images/sp02.png" width = "710">

- 개발환경, 개발 난이도를 낮추는 작업 
- Servlet -> EJB -> JSP -> Spring -> Spring Boot으로 발전

- 장점
1. spring의 기술을 그대로 사용 가능하다. (마이그레이션이 간단함) 
2. JPA를 사용하면 ERD나 DB 설계를 하지 않고도 손쉽게 DB 생성이 가능하다.
3. spring에서 되지 않는 주변 서포트 기능이 다수 존재한다. (개발하기 쉽게 도와줌)
4. TomCat WebServer가 내장되어 있다. (따로 설치할 필요 X)
5. JUnit 테스트, Log432 로그도 모두 포함한다.
6. JSP, **Thymeleaf**, Mustache 등등 편하게 사용할 수 있다.
7. DB 연동이 매우 쉽다.

### DB 설정
- `H2 DB`: Spring Boot에서 손쉽게 사용한 Inmemory DB, Oracle, MySQL, SQLServer와 쉽게 호환된다.
- `Oracle`: 운영시 사용할 DB
- `Oracle PKNUSB / pknu_P@ss`로 생성
  ```shell
  > sqlplus system/password
  SQL > 
  ``` 
  
    

    

    
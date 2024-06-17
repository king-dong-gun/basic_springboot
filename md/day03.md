## 3일차

### 목차
1. 개발시작
   - MyBatis 적용
   - 개발 순서


### MyBatis 적용
1. MyBatis 적용
   1. `Spring`, `resource/WEB-INF` 위치에 `root-context.xmldp DB` -> `MyBatis`로 설정
   2. `Spring Boot`, `application.properties` + `config.java`로 변경


2. 개발 순서
   1. `DataBase` 생성
   2. `MyBatis`설정 -> `/config/MyBatisConfig.java` 
   3. 테이블과 일치하는 클래스 (`domain`, `entity`, `dto`, `vo(readonly)`, 등등...)
   > 테이블 커럼에서 `_(언더 바)`는 `Java` 클래스에서 사용을 안한다! 
   4. DB에 데이터를 주고받을 수 있는 클래스 (`dao`, **`mapper`**, `repository`)
   5. 분리했을 경우 `/resource/mapper/class.xml` 생성 및 쿼리 입력
   6. `Service` 인터페이스, `Service` 구현 클래스 생성
   7. 사용자가 접근하는 컨트롤러 `@RestController` 클래스 생성
   > 경우에 따라 `@SpringBootApplication`클래스에 `sqlsessionFactory` 빈을 생성 메서드로 작성!!

    8. Veiw -> `/resource/templates/Thymeleaf html` 생성 및 작성
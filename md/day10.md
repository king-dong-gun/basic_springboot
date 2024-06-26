## 10일차

### 목차
#### Spring Boot JPA 프로젝트 개발 
1. 검색 기능: JPA Query
  - `@Query` 어노테이션으로 직접 작성
  - DB 표준쿼리와 차이가 있다(Java Entity와 일치)
2. 마크다운 적용
3. 카테고리 설정

### **1. 검색기능**
#### `BoardRepository`에 `findAllByKeyword()` 메서드 작성
- 단순 쿼리가 아니라 `JpaRepository`가 자동으로 만들어 줄 수 없을 때 사용
```java
// 제목 또는 내용으로 검색하는 키워드를 찾기위한 메서드 추가

// 중복된 게시글 제거
@Query("SELECT DISTINCT board " +
        "FROM Board board " +
        "LEFT JOIN Reply reply ON reply.board = board " +
        "WHERE board.title LIKE %:kw% " +
        "OR board.content LIKE %:kw% " +
        "OR reply.content LIKE %:kw%")
Page<Board> findAllByKeyword(@Param("kw") String keyword, Pageable pageable);
```


### **2. 마크다운 적용**
1. `Wysiwyg` 에디터 -> `CKEditor`, `TinyMCE` 사용
2. - [simplemde()](https://simplemde.com/)
- 깃헙에 CDN 링크복사 `layout.html`에 링크추가
- `create.html`에 `textarea` **id**값을 `content`를 `simplemde`로 변환하는 js추가
- `detail.html` `textarea` `content`
- `build.gradle` 마크다운 디팬던시 추가
- `Common/commonUtil.java`추가
- `detail.html` 마크더운 뷰어 적용


### **3. 카테고리 설정**





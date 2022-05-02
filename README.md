# twicePractice
스프링 부트로 간단한 게시판 만들기 복습

#### 

- 스프링 부트는 기본적으로 HIKARI CP 라이브러리를 지원

- ORM은 객체지향 패러다임을 관계형 데이터베이스에 보존하는 기술

- @Column으로 기본값을 지정하기 위해서 columnDefinition을 이용하기도 한다

  ```
  @Column(columnDefinition = "varchar(255) default 'Yes'")
  ```

- findById()  vs   getOne()

>  findById() 는 Optional 타입으로 반환되기에 한번 더 결과를 존재하는지 체크				

> getOne() 은 실제 객체가 필요한 순간까지 SQL을 실행하지 않다가, 실제 객체를 사용하는 순간에 SQL을 생성한다.

- 페이징 처리,

```
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
   
   @Test
    public void testPageDefault(){

        Pageable pageable = PageRequest.of(0,10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println("총 몇 페이지? : " + result.getTotalPages());
        System.out.println("전체 갯수? : " + result.getTotalElements());
        System.out.println("현재 페이지 번호 0부터 시작? : " + result.getNumber());
        System.out.println("페이지 당 데이터 개수? : " + result.getSize());
        System.out.println("다음 페이지 존재 여부? : " + result.hasNext());
        System.out.println("시작 페이지(0) 여부? : " + result.isFirst());

    }
```

- 실제 페이지의 데이터 처리는 getContent()를 이용하여 List<엔티티 타입> 또는 Stream<엔티티 타입>을 반환하는  get()을 이용

```
 @Test
    public void testPageDefault(){

        Pageable pageable = PageRequest.of(0,10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println("총 몇 페이지? : " + result.getTotalPages());
        System.out.println("전체 갯수? : " + result.getTotalElements());
        System.out.println("현재 페이지 번호 0부터 시작? : " + result.getNumber());
        System.out.println("페이지 당 데이터 개수? : " + result.getSize());
        System.out.println("다음 페이지 존재 여부? : " + result.hasNext());
        System.out.println("시작 페이지(0) 여부? : " + result.isFirst());

        System.out.println(" ======================================= " );
        for (Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }
```

- 여러 정렬조건

```
@Entity
@Table(name="tbl_memo")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;
}
   
   
   
   
   @Test
    public void testSort(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        Pageable pageable = PageRequest.of(0,10,sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.forEach(memo -> {
            System.out.println(memo);
        });
    }
```

- 다양한 검색조건은 쿼리메서드 와 @Query 이용하자

> 쿼리메서드: 메서드 이름 자체가 쿼리의 구문으로 처리되는 기능

```
public interface MemoRepository extends JpaRepository<Memo,Long> {

    /* 원하는 mno 값에 포함되는 객체 구하기 */
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long fron, Long to);

    /* 쿼리메서드와 Pageable 파라미터 결합 사용 */
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);
}


    @Test
    public void testQueryMethodWithPageable(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L,50L,pageable);

        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }
```

> 쿼리 메소드는 검색과 같은 편리한 기능을 제공하나 조인과 같은 복잡한 조건 처리시 불편하다.
>
> 때문에 간단한 처리만 쿼리메서드를 이용하나, 대부분 @Query를 많이 사용한다.

- @Query와 페이징처리



- 타임리프 날짜 표현 포멧팅(유용)

```
implementation group: 'org.thymeleaf.extras', name: 'thymeleaf-extras-java8time', version: '3.0.4.RELEASE'
```

```
  <ul>
        <li th:each="dto : ${list}">
            [[${dto.sno}]] -- [[${#temporals.format(dto.regTime, 'yyyy/MM/dd')}]]
        </li>
    </ul>
```

![image-20220427235321843](C:\Users\pulpu\AppData\Roaming\Typora\typora-user-images\image-20220427235321843.png)

- 생성 수정 관리해주는 BaseEntity 설정

```
@MappedSuperclass // 테이블로 생성되지 않음
@EntityListeners(value={AuditingEntityListener.class}) //JPA 내부에서 엔티티 객체 생성/변경 감지하는 리스너
@Getter
abstract class BaseEntity {

    @CreatedDate // JPA에서 엔티티 생성 시간 처리
    @Column(name="regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate //최종 수정 시간 자동처리
    @Column(name="moddate")
    private LocalDateTime modDate;
}
```

```
@SpringBootApplication
@EnableJpaAuditing //AuditingEntityListener 활성화
public class TwicePracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwicePracticeApplication.class, args);
	}

}
```

- querydsl 설정

```
buildscript {
	ext {
		queryDslVersion = "5.0.0"
	}
}

plugins {
	id 'org.springframework.boot' version '2.6.7'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id 'java'

	id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
}

group = 'com.yoon'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	/* 타임리프 템플릿 엔인 추가 */
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    /* mysql 추가 */
	implementation group: 'mysql', name: 'mysql-connector-java', version: '8.0.28'
	/* 타임리프 날짜 포멧팅 추가 */
	implementation group: 'org.thymeleaf.extras', name: 'thymeleaf-extras-java8time', version: '3.0.4.RELEASE'
   /* querydsl 추가 */
	implementation "com.querydsl:querydsl-jpa:${queryDslVersion}"
	implementation "com.querydsl:querydsl-apt:${queryDslVersion}"

}

tasks.named('test') {
	useJUnitPlatform()
}

/* querydsl 추가 */
def querydslDir = "$buildDir/generated/querydsl" // JPA 사용 여부와 사용할 경로를 설정

 querydsl { jpa = true
	 querydslSourcesDir = querydslDir }
// build 시 사용할 sourceSet 추가
 sourceSets { main.java.srcDir querydslDir }
// querydsl 컴파일시 사용할 옵션 설정
compileQuerydsl{ options.annotationProcessorPath = configurations.querydsl }
// querydsl 이 compileClassPath 를 상속하도록 설정
configurations {
	compileOnly { extendsFrom annotationProcessor }
	querydsl.extendsFrom compileClasspath }


```

- DTO를 Entity로 변환하는 방법 2가지

> ModelMapper라이브러리 사용 또는 MapStruct 사용

- JPQL에서의 UPDATE? DELETE?

```
    @Modifying //JPQL을 통해서 UPDATE, DELETE를 실행하기 위해선 해당 어노테이션 같이 사용해야함
    @Query("delete from Reply r where r.board.bno= :bno")
    void deleteByBno(Long bno);
```

-  Modify에서 findById 가 아니라 getOne()을 사용한다??

```
    /*
    * modify()는 findById()를 이용하는 대신에 필요한 순간까지 로딩을 지연하는 방식인 getOnE()을 이용해서 		처리한다
    * */
    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = boardRepository.getOne(boardDTO.getBno());
        board.changeContent(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        boardRepository.save(board);
    }
```

- @ModelAttribute 쓰임새

```
    @GetMapping({"/read","/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model){

        BoardDTO boardDTO = boardService.get(bno);

        model.addAttribute("dto", boardDTO);
    }
```

> @ModelAttribute를 사용하면 bean 값으로 model.addAttribute("dto", boardDTO); 처럼
>
> pageRequestDTO로 사용할 수 있다. 
>
> 처음에 PageRequestDTO 값으로 만들어지며 http를 통해서 바인딩 된값으로 전달된다.

 다양한 @ModelAttribute 쓰임새들

```
    @GetMapping({"/read","/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, 	Model model){

        BoardDTO boardDTO = boardService.get(bno);

        model.addAttribute("dto", boardDTO);
    }
    
    
    @PostMapping("/modify")
    public String modify(BoardDTO dto, @ModelAttribute("requestDTO")PageRequestDTO 			      requestDTO, RedirectAttributes redirectAttributes){

        boardService.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        redirectAttributes.addAttribute("bno", dto.getBno());

        return "redirect:/board/read";
    }
```



# TestCode 작성
- Test class 생성 : 단축키 `cmd+N`

## 단위 테스트(Unit test)
- 작은 코드 단위(클래스, 메서드)를 독립적으로 검증하는 테스트
- 검증 속도가 빠르고 안정적

### JUnit 5
- 단위 테스트를 위한 테스트 프레임워크
  - XUnit : SUnit(Smalltalk), JUnit(Java), NUnit(.NET), ...

### AssertJ
- 테스트 코드 작성을 원활하게 돕는 테스트 라이브러리
- 풍부한 API, 메서드 체이닝 지원

### spring boot에서 사용하기
- `org.springframework.boot:spring-boot-starter-test`에 `JUnit5`, `AssertJ` 포함되어있음
  - 테스트 생성 시 테스트 라이브러리 선택 가능
- `@Test` 사용
- `assertEquals(예상값, 실제값)` :  두 값이 같은지 비교
  - 간단하고 직관적
- `assertThat(검증대상)` : 테스트의 가독성을 높이고 더 유연한 조건 검증을 가능한 메서드
  - 메소드 체이닝 패턴 형식으로 사용 가독성을 향상
    - ex) `.isEqualTo(예상값)`, `.isEmpty()`, `.hasSize(예상크기)`, ...

### 요구사항 세분화
- 테스트 케이스 세분화
  - 해피 케이스
  - 예외 케이스
    - `assertThatThrownBy(()-> 예외 일어나는 상황)` : 메소드 체이닝 패턴 사용해 의도한 예외가 잘 일어나는 지 확인
      - ex) `.isInstanceOf(예외클래스.class)`, `.hasMessage(예외 발생 시 출력 메세지)`
- 경계값(범위, 구간, 날짜 등) 테스트
- 테스트하기 어려운 영역 구분하고 분리하기
  - ex) 현재 시간에 따라 로직처리 -> 시간을 외부에서 전달해주는 형태로 변형해 테스트
    - 그렇게 변경해도 되나? -> 테스트에서 중요한 것은 시각이 아님, 시각이 주어졌을 때 로직이 잘 실행되는지 확인하는 것이 중요
  - 외부로 분리할수록 테스트 가능한 코드는 많아짐
  - 테스트 어려운 영역
    - 관측할 때마다 다른 값(현재 날짜/시간, 랜덤값, 전역 변수/함수, 사용자 입력 등)에 의존하는 코드 : 외부에서 들어오는 제어 불가능한 값
    - 외부 세계에 영향(표준 출력, 메세지 발송, DB에 기록하기 등) 주는 코드 : 외부로 보내는 값, 검증 어려움
  - 테스트하기 쉬운 코드 : 순수함수
    - 같은 입력에는 항상 같은 결과
    - 외부 세상과 단절된 형태


## TDD(Test Driven Development)
- 프로덕션 코드보다 테스트 코드를 먼저 작성하여 테스트가 구현 과정을 주도하도록 하는 방법론
- RED -> GREEN -> REFACTOR 순으로 반복
  - RED : 실패하는 테스트 작성
  - GREEN : 테스트 통과하는 최소한의 코딩
  - REFACTOR : 테스트 통과 유지하며 구현 코드 개선
- 시점에 따른 비교
  - 선 기능 구현 후 테스트 작성 시 
    - 테스트 자체의 누락 가능성
    - 특정 테스트 케이스(해피 케이스)만 검증할 가능성
    - 잘못된 구현을 다소 늦게 발견할 가능성
  - 선 테스트 작성 후 기능 구현 시
    - 복잡도가 낮은 테스트 가능한 코드로 구현할 수 있게 함
    - 쉽게 발견하기 어려운 엣지 케이스를 놓치지 않게 함
    - 구현에 대한 빠른 피드백을 받을 수 있음
    - 과감한 리팩토링 가능
- 관점의 변화
  - 테스트는 구현부 검증을 위한 보조 수단 -> 테스트와 상호 작용하며 발전하는 구현부


## 테스트는 "문서"다
- 왜 문서인가?
  - 프로덕션 기능을 설명하는 테스트 코드 문서
  - 다양한 테스트 케이스 통해 프로덕션 코드 이해하는 시각과 관점 보완
  - 고민의 결과물을 모두의 자산으로 공유 가능
- `DisplayName`
  - 문서로 작동할 수 있는 첫번째 단계
  - JUnit5부터 지원
  - 작성법
    - 명사의 나열보다 문장형태로 작성하기("~테스트" 지양)
    - 테스트 행위에 대한 결과까지 기술하기
    - 도메인 용어를 사용하여 한층 추상화된 내용 담기: 메서드 자체 관점보다 도메인 정책 관점
      - ex) 특정 시간 이전 -> 영업 시작 시간 이전
    - 테스트 현상을 중점으로 기술하지 말 것(실패/성공한다 X)
      - ex) 주문 생성에 실패한다 -> 주문을 생성할 수 없다
- BDD(Behavior Driven Development)
  - TDD에서 파생된 개발방법
  - 함수 단위의 테스트에 집중하기보다, 시나리오에 기반한 테스트케이스(TC) 자체에 집중하여 테스트
  - 개발자가 아닌 사람이 봐도 이해가능하도록 추상화
  - Given / When / Then: 어떤 환경에서 어떤 행동을 진행했을 때, 어떤 상태 변화가 일어난다 -> DisplayName에 대해 명확
    - Given: 시나리오 진행에 필요한 모든 준비 과정(객체, 값, 상태 등)
    - When: 시나리오 행동 진행, 보통 코드 한줄(실행하는 메서드)
    - Then: 시나리오 진행에 대한 결과 명시, 검증

## Spring & JPA 기반 테스트
### 레이어드 아키텍처(Layered Architecture)
- 관심사 분리 
- 3계층
  - Presentation Layer
  - Business Layer
  - Persistence Layer
  - `Presentation layer(UI) <-> Business Layer <-> Persistence layer <-> DB`
- 통합테스트
  - 여러 모듈이 협력하는 기능을 통합적으로 검증하는 테스트
  - 일반적으로 작은 범위의 단위 테스트만으로는 기능 전체의 신뢰성 보장 불가
  - 풍부한 단위 테스트 & 큰 기능 단위 검증하는 통합테스트 
### Spring
- 주요개념
  - IoC(Inversion of Control)
  - DI(Dependency Injection)
  - AOP(Aspect Oriented Programming): 스프링에서 프록시 사용해서 구현
### JPA(Java Persistence API)
- ORM(Object-Relational Mapping)
  - 패러다임간의 불일치
    - 객체는 속성(field), 기능(method) 가짐. 객체지향은 추상화, 상속, 다형성같은 개념이 있지만 DB에는 없기 때문에 서로가 지향하는 목적, 기능, 표현 방법이 다름
      - 객체지향 패러다임 != 관계형 DB
    - 기존 개발자가 직접 mapping하며 비지니스로직에 집중하기 힘듦 -> ORM은 자동으로 mapping 해주어 패러다임의 불일치 문제를 개발자 대신 해결
- JPA
  - Java 진영의 ORM 기술 표준
  - 인터페이스로, 보통 Hibernate 구현체 많이 사용
  - 반복적 CRUD SQL 생성 및 실행, 여러 부가기능 제공
  - 편리하지만 직접 쿼리 작성 안하기때문에 어떤식으로 쿼리 만들어지고 실행되는지 명확하게 이해해야함
  - Spring 진영에서는 JPA를 한번 더 추상화한 Spring Data JPA 제공
    - QueryDSL과 조합하여 많이 사용(타입체크, 동적쿼리)
      - QueryDSL
        - JPA만 사용할 때의 복잡한 쿼리, 동적 쿼리 구현 한계 해결
        - 장점
          - 문자가 아닌 코드로 쿼리 작성하여 컴파일 시점에 문법 오류 확인 가능
          - 제약 조건 등을 메서드 추출을 통해 재사용 가능
        - 사용 예시
          ```java
          String username = "test";
          
          List<member> result = queryFactory
                  .select(member)
                  .from(member)
                  .where(usernameEq(username))
                  .fetch();
          ```
- 주로 사용되는 어노테이션
  - 테이블과 객체 매핑: @Entity, @Id, @Column
  - 두 테이블(객체)간 연관관계: @ManyToOne, @OneToMany, @OneToOne, @ManyToMany
### Layered Architecture test
#### 기본 세팅
- 엔티티 별 생성, 수정일 설정
  - `@EnableJpaAuditing`: 엔티티 객체 생성되거나 수정되었을 때 자동으로 값 등록 가능, Application 클래스에 추가
  - BaseEntity 추상 클래스 생성 : 생성일, 수정일 관리
  ```java
  @Getter
  @MappedSuperclass
  @EntityListeners(AuditingEntityListener.class)
  public abstract class BaseEntity {
  
      @CreatedDate
      private LocalDateTime createdDateTime;
  
      @LastModifiedDate
      private LocalDateTime modifiedDateTime;
  
  }
  ```
    - `@MappedSuperclass`: 공통 맵핑 정보가 필요할 때, 부모 클래스에서 선언하고 속성 상속 받아서 사용
    - `@EntityListeners(AuditingEntityListener::class)`: 엔티티에 이벤트 발생 시 관련 코드 실행
- `application.yml`
  - ddl-auto 여부, data.sql 실행 여부 등 profile 별 설정
  - `@ActiveProfiles(profile명)`으로 원하는 profile 환경에서 진행 가능
#### Persistence Layer
- Data Access의 역할
- 비지니스 가공 로직이 포함되면 안됨(data에 대한 CRUD에만 집중)
- `@DataJpaTest` vs `@SpringBootTest`
  - `@DataJpaTest`
    - JPA 컴포넌트들만을 테스트하기 위한 어노테이션
    - 자동으로 롤백
    - `replace=AutoConfigureTestDatabase.Replace`가 디폴트 -> 설정한 DB가 아닌 n-memory DB를 활용
      - `replace=AutoConfigureTestDatabase.NONE`로 설정해둔 DB 사용해 테스트 가능
  - `@SpringBootTest`
    - full application config을 로드해 통합 테스트 진행 위한 어노테이션
    - 설정한 config, context, components 모두 로드
    - DataSource bean을 그대로 사용
    - DB 롤백되지 않아 `@Transactional` 필요
#### Business Layer
- 비지니스 로직 구현
- Persistence Layer와의 상호작용 통해 비지니스 로직 전개
- 트랜잭션 보장해야 함(작업단위 원자성 보장)
- 서비스 테스트에서 `@Transactional` 사용 시 롤백해주어 클린업 작업 필요없지만, 실제 서비스 단계에서 `@Transactional` 적용안되어도 테스트 통과되기때문에 사용에 유의
- `@Transactional(readOnly = true)`: 읽기 전용 모드
  - 장점
    - 성능 최적화: 쿼리 및 캐싱 최적화 가능, 읽기 전용 설정 시 데이터 변경 일어나지 않아 변경 감지를 위한 스냅샷 저장 동작 안함
    - 데이터 일관성: 실수로 데이터 수정해 일관성 위반할 가능성 낮아짐
    - 가독성 향상: 설정된 메서드가 DB에서 데이터 읽기만 한다는 것 명확하게 확인 가능
  - 주의할점
    - `Optimistic Lock` 동작에 영향 미칠 수 있음: `@Transactional(readOnly = true)`로 설정한 메서드에 엔티티 수정 로직 있을 경우, 트랜잭션이 버전 번호 확인하지 못해 충돌 감지하지 못하고 동시에 발생한 트랜잭션 변경 사항을 덮어쓰게 되어 데이터 불일치 문제 발생 가능
#### Presentation Layer
- 외부 세계의 요청을 가장 먼저 받는 계층
- 파라미터에 대한 최소한의 검증 수행
- `@WebMvcTest` vs `@SpringBootTest`
  - `@WebMvcTest`
    - 컨트롤러 로직 테스트에 사용
      - 웹 계층에 필요한 구성만 로드
      - 특정 컨트롤러 대상으로 테스트하려면 해당 컨트롤러 클래스를 어노테이션 값으로 전달
        ```java
        @WebMvcTest(SomeController.class)
        class SomeControllerTest() {
        }
        ```
    - 내장된 `MockMvc` 인스턴스 사용해 HTTP 요청과 응답 쉽게 테스트 가능
      - `MockMvc`: Mock 객체 사용해 스프링 MVC 동작 재현할 수 있는 스프링에서 제공하는 테스트 프레임워크
        - Http 요청을 디스패처서블릿에 전송하고 결과 받아 테스트하는데 사용됨
        ```java
        @Autowired
        private MockMvc mockMvc;
        
        @Test
        void SomeTest() throws Exception {
            mockMvc.perform(get("/endpoint"))
                .andExpect(status().isOk());
        }
        ```
    - `@MockitoBean` 사용해 다른 빈들 모의 객체로 생성 및 주입 가능 
      ```java
        @MockitoBean
        private SomeService someService;
        ```
    - Mocking 메서드의 변경이 일어나면 수정 필요
  - `@SpringBootTest`
    - 프로젝트 내부 스프링 빈을 모두 등록 -> 테스트 속도 느림
    - 실제 운영 환경에서 사용되는 클래스들 통합해 테스트
      - 환경과 가장 유사하게 테스트 가능
    - 변경에 자유로움: service 메서드 스펙 변경시에도 Mockint 값 수정 필요 없음
    - 테스트 단위가 커 디버깅 까다로움
- validation
  - 분류
    - `@NotBlank`: 공백, 빈문자열 불가
    - `@NotNull`: 공백("  ") 가능, 빈문자열("") 가능
    - `@NotEmpty`: 공백("  ") 가능
    - `@Positive`: 양수 가능

### Mock
- stub vs mock
  - stub: 상태 검증(state verification)
  - mock: 행위 검증(behavior verification)
- Classicist vs Mockist
  - 단위 테스트 스타일에 대한 두 가지 대조적인 접근 방식
  - Classicist
    - 시스템 전체가 통합되었을 때 제대로 동작하는가
    - Mock을 거의 사용하지 않음, 대신 실제 객체 사용
    - 장점
      - 예상치 못한 버그 발견 가능
    - 단점
      - 의존성이 많을때 단위 테스트가 느려질 수 있음
      - 여러 모듈을 함께 테스트할 경우 문제가 발생했을 때 원인을 찾기 어려움
  - Mockist
    - 각 객체가 올바르게 협력하는가 중시
    - 의존성 있는 객체를 Mock으로 대체하여 단위 테스트 독립성 유지
    - 장점
      - 테스트 속도가 빠름
      - 문제 발생 시 버그 원인을 더 쉽게 찾을 수 있음
      - 개별 컴포넌트를 독립적으로 테스트 가능
    - 단점
      - 실제 객체와 다르게 동작할 수 있어 Mock이 현실성을 잃을 수 있음
      - Mock 설정이 많아질 경우 테스트 코드 유지보수가 어려워질 수 있음


## 팁
- request
  - 하위 레이어는 알아야하지만 상위 레이어 알 필요 x. 상위에서 request 그대로 넘겨주면 종속성 강해짐
    - 해당 레이어의 DTO를 따로 만들어 포맷을 맞춰 넘기는 게 좋음
      - 레이어 간 결합도를 낮추고 독립성을 유지
      - API 변경에 유연하게 대응 가능
      - 서비스 로직을 명확하게 분리
      - ex) controller: SomeRequest -> service: SomeServiceRequest 
- validataion 책임: 컨트롤러에서 모든 조건 검증하는게 맞는가
  - 서비스 특성에 따라 달라지는 조건 -> 서비스 레이어나 생성자 등의 안쪽 레이어에서 확인하는 게 좋음
    - ex)`@Max(20)`

  


## 추가 학습 내용
### Lombok
- 특징
  - 컴파일 시점에 코드 추가
  - 별도의 라이브러리 추가 설치 필요
- 장점
  - 어노테이션을 통한 코드 자동 생성으로 생산성, 편의성 증가
  - Code의 길이가 줄어들어 가독성, 유지보수성 향상
  - Builder 패턴 적용, Log 생성 등의 편의성
- `@Data`, `@Setter`, `@AllArgsConstructor` 지양
  - `@Data` : `@ToString`, `@Getter`, `@Setter`, `@EqualsAndHashCode`, `@RequiredArgsConstructor`을 모두 포함하는 강력한 어노테이션
    - setter 남용으로 안정성 보장 X : 변경을 허용치 않는 필드도 setter 생성됨
    - 양방향 연관관계에서 `@ToString` 사용 시 순환 참조 문제 : StackOverFlow 발생
      - `@ToString(of = {필드명, 필드명})`와 같이 직접 필드 등록 또는 문제 필드에 `@ToString.Exclude` 작성해 제외 
    - Mutable 객체에 `@EqualsAndHashCode` 파라미터 없이 사용 시 문제 : 필드값 변경 후 객체 비교 시 다른 객체로 인식 -> Immutable 객체 외엔 사용 지 or `@EqualsAndHashCode(of ={"필드명"})` 사용
    - `@RequiredArgsConstructor`으로 인한 문제 : 선언된 인스턴스 멤버 순서 변경 시 lombok이 생성자의 파라미터 순서를 필드 선언 순서에 따라 변형 -> 리팩토링 동작 없이 실제 입력된 값 바뀌어 들어가는 문제 발생
  - `@AllArgsConstructor` : 클래스에 존재하는 모든 필드 포함하는 생성자 자동 생성
    - 자동으로 생성되어 제어 불가
    - 필드 순서 변경되어도 타입 일치하면 컴파일 시점에 에러 발생하지 않음

### 애자일 방법론
#### 폭포수 방법론
- 요구사항 정의(설계) → 디자인 → 개발 → 테스트 → 배포의 과정이 순차적으로 진행
- 장점
  - 단계별로 업무를 분담하기 때문에 맡은 바 명확
  - 소요되는 시간이나 현재 상황을 추적하고 병목을 파악하기 쉬움
- 단점
  - 속도가 느리고 유연하지 못함
  - 개발 완료 시점에 해당 기능이 쓸모없어질 수 있음(빠르게 변하는 고객의 요구에 대처 불가) -> 애자일 방법론 등장
#### 애자일 방법론
- ‘기민한, 민첩한’이라는 뜻으로, 일정한 주기를 가지고 빠르게 제품을 출시하여 고객의 요구사항, 변화된 환경에 맞게 수정해나가는 탄력적인 방법론
- 애자일 소프트웨어 개발 선언
  ```
  우리는 소프트웨어를 개발하고, 또 다른 사람의 개발을 도와주면서 소프트웨어 개발의 더 나은 방법들을 찾아가고 있다. 
  이 작업을 통해 우리는 다음을 가치 있게 여기게 되었다.
  
  공정과 도구보다 개인과 상호작용을
  포괄적인 문서보다 작동하는 소프트웨어를
  계약 협상보다 고객과의 협력을
  계획을 따르기보다 변화에 대응하기를 
  가치 있게 여긴다.
  
  이 말은, 왼쪽에 있는 것들도 가치가 있지만,
  우리는 오른쪽에 있는 것들에 더 높은 가치를 둔다는 것이다.
  ```
- 장점 : 빠른 속도와 유연함
  - 첫 단계에서 모든 요구사항을 계획하고 분석하지 않기 때문에 디자인, 개발, 배포까지 신속하게 완수
  - 계획에 의존하지 않고 일정한 주기마다 그때그때 요구사항을 반영하고 수정하여 시장과 고객의 변화에 대응 쉬움
- 스크럼과 같은 프로세스 주로 활용
  - 스크럼 : 짧은 사이클로 제품을 개발하고 테스트하고 피드백을 받아 보완하는 방식, 1~4주의 작은 스프린트 단위로 `디자인 → 개발 → 테스트` 진행
#### 애자일 방법론 종류 
- 익스트림 프로그래밍(XP, eXtreme Programming)
  - 높은 품질의 소프트웨어를 신속하게 제공
  - 특징
    - 반복적 개발
    - 테스트 주도 개발(TDD)
    - 짝 프로그래밍(Pair Programming)
    - 지속적 통합(Continuous Integration)
    - 고객 참여
    - 단순한 설계(Simple Design)
  - 단계
    1. 탐색(Exploration): 프로젝트 초기에 비즈니스 요구사항을 이해하고 구체화
    2. 몰입(Commitment): 우선순위를 정하고, 다음 스프린트에 포함될 작업을 선정
    3. 조정(Steering): 개발 프로세스가 계획대로 진행되고 있는지 확인하고, 필요에 따라 계획 조정
- 크리스탈(Crystal) 방법론
  - 프로젝트의 특성과 팀의 요구에 맞추어 다양한 접근 방식 적용
  - 종류(프로젝트의 복잡도와 중요도에 따라 구분)
    - 크리스탈 클리어 (Crystal Clear): 낮은 복잡도와 중요도의 프로젝트에 사용, 문서화와 프로세스를 최소화, 팀원 간의 상호작용과 커뮤니케이션 중시
    - 크리스탈 옐로우 (Crystal Yellow)
    - 크리스탈 오렌지 (Crystal Orange)
    - 크리스탈 레드 (Crystal Red): 매우 높은 복잡도와 중요도의 프로젝트에 사용, 엄격한 프로세스와 규칙
- 적응형 소프트웨어 개발(ASD, Adaptive Software Development)
  - 복잡하고 불확실한 환경에서 최적의 소프트웨어를 개발하기 위해 팀의 협력과 지속적 학습을 강조
  - 설계(Design), 협력(Collaborate), 학습(Learn)의 세 단계로 나누어 진행
- 기능 중심 개발(FDD, Feature-Driven Development)
  - 소프트웨어를 기능 단위로 나누어 개발
- 익스트림 모델링(XM, extreme modeling)
  - 모델링과 설계를 극단적으로 단순화하고, 반복적이며 점진적인 방식으로 진행
- 칸반 모델링(kanban modeling)
  - 작업의 흐름을 시각화하고 관리하여 작업의 진행 상황을 한눈에 볼 수 있도록 함
  - 칸반 보드: 작업의 시각화를 위한 도구로, 일반적으로 열(Column)과 카드(Card)로 구성됨. 기본적으로 각 열은 "할 일(To Do)", "진행 중(In Progress)", "완료(Done)"으로 구성

### JUnit vs Spock
| **비교 항목**      | **JUnit** | **Spock** |
|------------------|----------|----------|
| **언어**         | Java     | Groovy   |
| **테스트 스타일** | 명령형 (Imperative) | 선언형 (Declarative) |
| **BDD 지원**     | 기본적으로 미지원 (Mockito, Cucumber와 함께 사용) | 기본적으로 강력한 BDD 지원 (`given-when-then`) |
| **Mocking**      | Mockito와 함께 사용해야 함 | 내장된 Mocking 기능 제공 |
| **데이터 주도 테스트** | `@ParameterizedTest` 사용 | `where:` 블록을 사용하여 간결하게 표현 가능 |
| **표현력 및 가독성** | Java 문법을 따름, 비교적 장황함 | Groovy 문법을 활용하여 간결하고 직관적 |
| **기존 Java 프로젝트와의 호환성** | 대부분의 Java 프로젝트에서 사용 가능 | Groovy를 추가해야 하지만, Java 코드도 테스트 가능 |
| **어노테이션 기반 설정** | `@Test`, `@BeforeEach`, `@AfterEach` 등 사용 | 블록(`setup`, `cleanup`, `expect` 등) 기반 |
| **주 사용 분야** | 일반적인 단위 테스트 | 단위 테스트 + BDD 스타일 테스트 |

- 상황에 따른 테스트 프레임워크 선택
  - JUnit
    - 기존 Java 프로젝트에서 간편하게 테스트를 추가
    - Groovy를 사용하지 않고, Java만으로 테스트를 작성
    - 외부 라이브러리(Mockito, Cucumber 등)를 활용하여 필요한 기능을 확장하는 것이 괜찮을 때
  - Spock
    - BDD 스타일의 테스트를 원하는 경우
    - 간결하고 가독성 높은 테스트 코드를 작성
    - 내장된 Mocking 기능을 사용

### Architecture
- Layered Architecture(계층형 아키텍처)
  - 전통적 아키텍처
  - 같은 목적의 코드들을 같은 계층으로 그룹화
  - 역할과 관심사를 계층으로 분리하여 코드의 모듈화와 재사용성을 높임
  - Layer:
    - Presentation Layer (UI 계층): 사용자 인터페이스 처리
    - Application Layer (서비스 계층): 비즈니스 로직 수행
    - Domain Layer (도메인 계층): 핵심 비즈니스 규칙 및 도메인 모델
    - Infrastructure Layer (영속성 계층): 데이터베이스, 외부 API와 통신
- Clean Architecture(클린 아키텍처)
  - 애플리케이션의 핵심 도메인이 외부 요소(DB, UI, 프레임워크 등)에 의존하지 않도록 하는 아키텍처
  - 모든 의존성은 바깥쪽에서 안쪽으로 향해야 함
  - 하나의 추상적 이론이므로 현업에 적용하기 어려움, 개발 복잡성이 증가할 수 있음
- Hexagonal Architecture(헥사고날 아키텍처, Ports and Adapters 패턴)
  - 클린 아키텍처를 실제로 구현하는 대표적인 모델
  - 애플리케이션 코어(비즈니스 로직)가 외부 기술(데이터베이스, UI, 메시징 등)에 직접 의존하지 않도록 함
  - `포트(Port)`와 `어댑터(Adapter)`를 사용하여 의존성을 분리
    - 포트(Port): 애플리케이션 내부에서 제공하는 인터페이스 (ex: UserRepository, PaymentGateway)
    - 어댑터(Adapter): 포트를 구현하여 실제 외부 기술과 연결 (ex: JpaUserRepository, StripePaymentGateway)
  - Layer
    - Domain Layer (도메인 계층, 가장 안쪽)
      - 순수한 비즈니스 로직과 엔티티
      - 외부 프레임워크나 데이터베이스를 전혀 몰라야 함
    - Application Layer (애플리케이션 계층, 중간 계층)
      - 유스케이스(Use Case)를 정의 (ex: UserService, OrderService)
      - 도메인 계층을 호출하여 애플리케이션의 흐름을 제어
      - 포트(Port) 인터페이스를 제공하여 외부와의 상호작용을 정의
    - Adapter Layer (어댑터 계층, 가장 바깥쪽)
      - 데이터베이스, 웹 API, UI 등 외부 시스템과 통신
      - 도메인과 애플리케이션 계층이 특정 기술에 종속되지 않도록 함
    - 장점
     - 유연성: 기술 스택이 바뀌어도 코어 로직을 변경하지 않고 새로운 어댑터만 추가하면 됨
     - 유지보수성: 코드가 명확하게 분리되어 있어 변경이 용이함
     - 테스트 용이성: 각 컴포넌트 독립적 테스트 가능, 외부 의존성 없이 테스트 가능
    
### Lock
- DB에 접근하여 데이터를 수정할 때, 여러 트랜잭션이 동시에 같은 데이터를 수정하면 충돌이 발생할 수 있음. 이를 방지하기 위해 Lock 사용
- Optimistic Lock
  - 기본적으로 데이터 갱신시 충돌이 발생하지 않을 것이라고 낙관적으로 보는 락 -> 우선적으로 락 걸지 X
  - DB가 제공하는 락 기능 사용하지 않고 application level(JPA 등)에서 잡아줌
  - 버전 번호(version) 필드 사용하여 충돌 감지
  - 트랜잭션이 필요하지 않음 → 성능이 좋음
    - 충돌이 빈번한 경우 오히려 성능이 낮아질 수 있음
  - 충돌 발생 시 롤백을 개발자가 직접 처리해야 하므로 비관적 락보다 불리
  - JPA에서의 동작
    1. 엔티티에 @Version 필드 추가 (version 컬럼 활용)
    2. 트랜잭션이 커밋될 때, UPDATE 실행 시 현재 버전과 DB 버전을 비교
       - 버전이 같으면: 업데이트 성공 후, 버전 번호 +1 증가
       - 버전이 다르면: 업데이트할 대상이 없으므로 JPA가 `OptimisticLockException` 발생
    3. 충돌 발생 시 롤백을 개발자가 직접 처리해야 함
- Pessimistic Lock
  - 기본적으로 데이터 갱신시 충돌이 발생할 것이라고 비관적으로 보고 미리 잠금을 거는 락 -> 우선적으로 락 걸음
  - DB가 제공하는 락 기능을 사용
  - 트랜잭션이 시작될 때 Shared Lock 또는 Exclusive Lock을 걸고 시작
  - 충돌이 발생하면 트랜잭션이 실패하며 자동으로 롤백됨
  - 장점
    - 트랜잭션 충돌을 미리 방지할 수 있음 (Optimistic Lock처럼 예외 발생 후 롤백할 필요 없음)
    - 데이터 정합성이 보장됨
  - 단점
    - 성능 저하 가능성 (락을 걸면 다른 트랜잭션이 해당 데이터를 사용할 수 없음 → 동시성이 낮아짐)
    - 데드락 발생 가능성 (여러 트랜잭션이 서로를 기다리면서 무한 대기 상태)
  - JPA에서의 동작
    - `@Lock(LockModeType.PESSIMISTIC_WRITE)`을 사용하여 락을 설정

### CQRS (Command and Query Responsibility Segregation)
- command와 query를 분리하는 패턴
  - Command: 데이터 저장소에 데이터 쓰기
  - Query: 데이터 저장소로부터 데이터 읽기
- 방식 비교
  - CQRS 따르지 x API
    ```Markdown
    # POST /posts
    
    1. post를 DB에 업로드
    2. 방금 업로드한 post를 DB에서 조회
    3. 조회된 post 정보 반환
    ```
  - CQRS 따르는 API
    ```Markdown
    # POST /posts
    
    1. post를 DB에 업로드
    2. 방금 업로드한 post의 ID 반환
    
    # GET /post/{post_id}
    1. id를 통해 post 조회
    2. 조회된 post 반환
    ```
  - 사용자가 별도로 조회해야하는 번거로움 -> Post-Redirect-Get(PRG) 패턴 사용
    - PRG : web 개발 분야에서 권장되는 디자인 패턴
      - POST 요청 후 응답 오면 곧바로 GET 요청
- 장점
  - 성능: 읽기 작업과 쓰기 작업 각각 최적화 가능
  - 확장성: 작업 빈도 놏은 읽기 전용 DB나 서버 두는 것 가능
- 단점
  - 일관성 문제: 읽기 전용 테이블이 항상 최신 정보임 보장 X

### 예외처리
- `@ControllerAdvice`, `@RestControllerAdvice`
  - Spring에서 애플리케이션 전체에 대해 전역적으로 예외를 처리할 수 있는 어노테이션
  - 종류
    - `@ControllerAdvice`: `@Controller`와 `@RestController`에서 발생하는 예외를 처리하는 데 사용
    - `@RestControllerAdvice`
      - 모든 `@RestController`에서 발생한 예외를 한 곳에서 처리할 수 있도록 도와주는 어노테이션
      - RESTful API 응답에 적합한 방식으로 예외를 처리, `@ResponseBody`가 포함되어 있어, 반환되는 값이 JSON 형태로 응답
  - 특징
    - 여러 컨트롤러에서 발생하는 예외를 한 곳에서 처리하기 때문에 코드 중복을 줄이고, 관리가 용이
- `@ExceptionHandler`
  - 특정 예외를 처리하는 메서드에 붙이는 어노테이션
  - 개별 컨트롤러 내에서 예외를 처리하는 데 사용
    - `@Controller` 또는 `@RestController 내에서 사용

### ObjectMapper
- Java에서 객체와 JSON 사이의 변환을 도와주는 클래스
  - Spring boot에서는 Jackson 라이브러리가 기본적으로 추가되어 별도의 의존성 추가 없이 사용 가능
- 직렬화(Serialization): Java 객체 -> JSON 변환 
  - `writeValueAsString()` 사용
  - 주의사항
    getter 메소드 규칙: Jackson은 기본적으로 JavaBean 규약에 따라 getter 메서드를 찾아서 JSON 필드를 바인딩. 만약 잘못된 getter 메서드 생성시 예상치 못한 JSON 응답이 발생 가능
- 역직렬화(Deserialization):JSON -> Java 객체 변환 
  - 과정
    1. 기본 생성자로 객체 생성
    2. 필드값 찾아 값 바인딩
  - 기본 생성자 사용없이 객체 만들기
    - ObjectMapper에 ParameterNames 모듈 추가
    - Java 컴파일의 `-parameters` 옵션 추가

### Test Double
- 테스트 대상 객체와 협력하는 객체를 대체할 수 있도록 만들어진 객체
- 테스트 환경에서 원래 객체를 사용하기 어려운 경우(예: 데이터베이스 연동, 네트워크 요청 등) 이를 대신해서 사용
- 종류
  - Dummy
    - 동작하지 않아도 테스트에는 영향 미치지 않는 객체 
    - 인스턴스화 된 객체 필요하지만 기능 필요하지 않은 경우에 사용
    - 메서드를 호출해도 아무 일도 하지 않거나, null, 기본값 등을 반환
    ```java
    public class DummyEmailSender implements EmailSender {
        @Override
        public void sendEmail(String to, String message) {
            // 아무 동작도 하지 않음 (단순히 인스턴스가 필요할 뿐)
        }
    }
    ```
  - Fake
    - 일부 실제 동작을 하지만, 정교하지 않은 객체
    - 복잡한 로직이나 객체 내부에서 필요로 하는 다른 외부 객체들의 동작 단순화하여 구현
      보통 인메모리 DB, 간단한 로직을 포함하는 테스트용 객체로 사용
    ```java
    public class FakeUserRepository implements UserRepository {
        private Map<Long, User> database = new HashMap<>();

        @Override
        public void save(User user) {
            database.put(user.getId(), user);
        }

        @Override
        public User findById(Long id) {
            return database.get(id); // 실제 DB 대신 메모리에서 데이터 반환
        }
    }
    ```
  - Stub
    - 미리 정해진 값을 반환하도록 구현된 객체
    - 인터페이스 또는 기본 클래스가 최소한으로 구현된 상태
    - 특정 요청이 들어오면, 미리 준비된 응답을 반환하도록 동작
    ```java
    public class StubWeatherService implements WeatherService {
        @Override
        public String getWeather(String location) {
            return "Sunny"; // 항상 "Sunny" 반환 (테스트를 위해 하드코딩된 응답)
        }
    }
    ```
    - `Mockito` 프레임워크의 `when()` 메서드가 수행하는 역할
       ```java
       WeatherService mockService = mock(WeatherService.class);
       when(mockService.getWeather("Seoul")).thenReturn("Sunny");
       ```
  - Spy
    - Stub과 비슷하지만, 추가적으로 호출된 기록을 저장할 수 있는 객체
    - 실제 객체처럼 동작시킬 수 있고, 필요한 부분에 대해서는 stub으로 만들어 동작 지정 가능
    - 호출 횟수나 특정 메서드 호출 여부를 검증할 때 유용
     ```java
     public class SpyPaymentService implements PaymentService {
        private boolean called = false;

        @Override
        public void processPayment(double amount) {
            called = true; // 호출 기록 남김
        }
    
        public boolean wasCalled() {
            return called;
        }
    }
    ```
    - `Mockito` 프레임워크의 `verify()` 메서드가 수행하는 역할
       ```java
      PaymentService spyService = spy(new RealPaymentService());
      spyService.processPayment(100.0);
  
      verify(spyService).processPayment(100.0); // 호출 여부 검증
       ```
  - Mock
    - 행동을 미리 정의하고, 특정 호출이 예상대로 이루어졌는지 검증하는 객체
    - Stub과 달리, 기대하는 동작을 명시적으로 설정 가능
    - 보통 Mock 프레임워크(Mockito, EasyMock)를 사용하여 생성
     ```java
    UserRepository mockRepo = mock(UserRepository.class);
    when(mockRepo.findById(1L)).thenReturn(new User(1L, "Alice"));

    User user = mockRepo.findById(1L); // 미리 정의된 결과 반환
    verify(mockRepo).findById(1L); // findById(1L) 호출 여부 검증
    ```
#### Mockito
- Java에서 Mock 객체를 쉽게 생성하고 관리할 수 있도록 도와주는 프레임워크
- JUnit과 함께 자주 사용되며, 가짜 객체(Mock)를 생성하고 동작을 설정하는 데 유용
- Mock
  - Mock 객체 생성법
    - `mock()` 사용
      - `UserRepository mockRepo = mock(UserRepository.class);`
    - `@Mock` 사용
      - 클래스 상단 `@ExtendWith(MockitoExtension.class)` 명시
      ```java
      @Mock
      private UserRepository userRepository;
      ```
  - `@InjectMocks` : DI와 같은 역할
    - 테스트 대상 클래스에 대해 생성자 주입, 세터 주입, 필드 주입 등을 자동으로 처리
    - @Mock으로 만들어진 객체를 해당 클래스의 생성자나 필드에 주입하는 방식으로 작동
- MockitoBean
  - Spring Context에서 기존 빈(Bean)을 대체하여 Mock 객체를 생성
  - @Autowired로 주입받을 수 있으며, 스프링의 DI(의존성 주입)를 활용한 테스트 가능
  - Spring Context에 등록된 원래의 빈을 대체하기 때문에, 실제 애플리케이션의 동작을 테스트하는 데 유용
- 자주 사용하는 메서드

  | 메서드 | 설명 |
  |--------|------|
  | `mock(Class<T> classToMock)` | 지정된 클래스의 Mock 객체 생성 |
  | `when(mock.method()).thenReturn(value)` | 특정 메서드 호출 시 지정한 값 반환 |
  | `doReturn(value).when(mock).method()` | `when()`과 유사하지만 Void 메서드에도 사용 가능 |
  | `verify(mock).method()` | 특정 메서드가 호출되었는지 검증 |
  | `verify(mock, times(n)).method()` | 메서드 호출 횟수 검증 |
  | `spy(realObject)` | 실제 객체의 일부 동작만 변경 |
  | `doNothing().when(mock).method()` | 메서드 호출 시 아무 동작도 하지 않도록 설정 |

#### BDDMockito
- BDD(Behavior-Driven Development, 행동 주도 개발) 스타일의 테스트 작성을 지원하는 Mockito 확장 기능
- 기존의 Mockito의 `when()` 스타일 대신 더 읽기 쉬운 Given-When-Then 형식으로 테스트를 작성할 수 있도록 도움
  - `when().thenReturn()` 대신 `given().willReturn()`을 사용
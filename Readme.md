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
  - 객체지향 패러다임 != 관계형 DB
    - 패러다임간의 불일치 -> 기존 개발자가 직접 mapping하며 비지니스로직에 집중하기 힘듦 -> 자동으로 mapping해주도록
- JPA
  - Java 진영의 ORM 기술 표준
  - 인터페이스로, 보통 Hibernate 구현체 많이 사용
  - 반복적 CRUD SQL 생성 및 실행, 여러 부가기능 제공
  - 편리하지만 직접 쿼리 작성 안하기때문에 어떤식으로 쿼리 만들어지고 실행되는지 명확하게 이해해야함
  - Spring 진영에서는 JPA를 한번 더 추상화한 Spring Data JPA 제공
    - QueryDSL과 조합하여 많이 사용(타입체크, 동적쿼리)
- 주로 사용되는 어노테이션
  - 테이블과 객체 매핑: @Entity, @Id, @Column
  - 두 테이블(객체)간 연관관계: @ManyToOne, @OneToMany, @OneToOne, @ManyToMany
### test 진행하기
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

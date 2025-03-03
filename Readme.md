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
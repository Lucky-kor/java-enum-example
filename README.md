# 자바 enum 학습 가이드

이 프로젝트는 자바의 `enum`(열거형)을 기초 문법부터 실제 리팩터링까지 순서대로 학습하기 위한 예제입니다.
단순히 열거 상수의 문법만 외우지 않고, **enum을 사용하지 않은 코드에 어떤 문제가 생기는지**, **enum이 데이터와 동작을 어떻게 한곳에 모으는지**, **긴 if-else가 어떻게 읽기 좋은 코드로 바뀌는지**를 직접 비교합니다.

각 코드를 실행하기 전에 주석 처리된 코드가 왜 컴파일되지 않는지, 실행 결과가 어떻게 나올지를 먼저 설명해 보세요.

## 학습 목표

이 프로젝트를 모두 학습하면 다음 내용을 설명할 수 있어야 합니다.

- 선택 가능한 값이 정해진 경우 문자열보다 enum이 적합한 이유를 설명할 수 있다.
- 열거 상수를 선언하고 `==`, `values()`, `valueOf()`를 올바르게 사용할 수 있다.
- `ordinal()`을 데이터베이스 값이나 업무용 번호로 사용하면 안 되는 이유를 설명할 수 있다.
- enum에 필드, 생성자, 일반 메서드를 선언할 수 있다.
- enum 필드를 주로 `private final`로 선언하는 이유를 설명할 수 있다.
- 문자열 상수와 enum의 타입 안전성 차이를 코드로 설명할 수 있다.
- enum에 추상 메서드를 선언하고 각 상수가 서로 다른 동작을 구현하게 만들 수 있다.
- 공통 동작은 일반 메서드로, 상수마다 달라지는 동작은 추상 메서드로 구분할 수 있다.
- 문자열과 if-else로 작성된 분기 코드를 enum으로 리팩터링할 수 있다.
- enum을 사용해도 외부 입력 검증과 `null` 처리는 별도로 필요함을 설명할 수 있다.

## 권장 학습 순서

| 순서 | 실행 클래스 | 핵심 주제 |
| --- | --- | --- |
| 1 | `enumExampleV1.Main` | 가장 단순한 enum, 비교, `values`, `valueOf`, `ordinal` 주의점 |
| 2 | `enumExampleV2.Main` | enum의 필드, 생성자, 일반 메서드 |
| 3 | `enumExampleV3.Main` | enum을 사용하지 않은 코드와 사용한 코드 비교 |
| 4 | `enumExampleV4.Main` | enum의 추상 메서드와 상수별 동작 |
| 5 | `enumExampleV5.Main` | 문자열 기반 if-else를 enum으로 리팩터링 |

`V1`에서 기본 문법을 익힌 뒤 `V2`에서 enum도 클래스처럼 데이터와 동작을 가질 수 있음을 확인합니다. `V3`에서는 enum 전후의 차이를 직접 비교하고, `V4`에서는 상수마다 동작이 달라지는 경우를 학습합니다. 마지막 `V5`에서는 실제로 긴 조건문을 제거하여 호출 코드의 가독성을 개선합니다.

## 예제 실행 방법

### IntelliJ IDEA에서 실행

1. `src` 폴더에서 학습할 패키지의 `Main.java`를 엽니다.
2. `main` 메서드 왼쪽의 실행 아이콘을 누릅니다.
3. 실행 결과를 아래쪽 **Run** 창에서 확인합니다.

### 터미널에서 실행

프로젝트의 최상위 폴더에서 다음과 같이 모든 예제를 컴파일할 수 있습니다.

```bash
mkdir -p out
javac -d out src/enumExampleV1/*.java \
  src/enumExampleV2/*.java \
  src/enumExampleV3/*.java \
  src/enumExampleV4/*.java \
  src/enumExampleV5/*.java
```

각 예제는 다음 명령으로 실행합니다.

```bash
java -cp out enumExampleV1.Main
java -cp out enumExampleV2.Main
java -cp out enumExampleV3.Main
java -cp out enumExampleV4.Main
java -cp out enumExampleV5.Main
```

학습을 마친 뒤 생성한 `out` 폴더는 삭제해도 됩니다.

## enum을 왜 사용하는가?

주문 상태가 네 가지라고 가정해 봅니다.

```java
String status = "READY";
```

`String`에는 어떤 문자열이든 들어갈 수 있으므로 다음 코드도 정상적으로 컴파일됩니다.

```java
status = "RADY";     // 오타
status = "졸림";     // 주문 상태와 관계없는 값
status = "";         // 빈 문자열
```

반면 enum 타입은 허용할 값을 코드에 명시합니다.

```java
OrderStatus status = OrderStatus.READY;
```

이 변수에는 `OrderStatus`에 선언한 열거 상수만 대입할 수 있습니다.

```java
// status = "RADY"; // String을 OrderStatus에 넣을 수 없어 컴파일 오류
```

가장 중요한 차이는 **잘못된 값을 언제 발견하는가**입니다.

| 비교 항목 | 문자열 사용 | enum 사용 |
| --- | --- | --- |
| 허용되는 값 | 모든 문자열 | 선언한 열거 상수 |
| 오타 발견 시점 | 실행 중 또는 데이터 확인 시 | 대부분 컴파일 시 |
| IDE 자동 완성 | 제한적 | 가능한 상수 목록 제공 |
| 메서드 매개변수의 의미 | 주석이나 문서를 읽어야 함 | 타입 이름으로 드러남 |
| 관련 데이터와 동작 | 여러 클래스와 조건문에 흩어지기 쉬움 | enum 내부에 모을 수 있음 |
| 값 비교 | `equals` 사용 | `==` 사용 가능 |

enum은 가능한 값이 **한정되어 있고 비교적 안정적인 개념**에 적합합니다. 요일, 주문 상태, 회원 등급, 배송 방법처럼 선택지가 명확한 경우가 대표적입니다. 사용자가 자유롭게 추가·삭제해야 하는 카테고리처럼 값이 계속 바뀌는 데이터는 enum보다 데이터베이스나 일반 객체가 더 적합할 수 있습니다.

## 1. enum 기본 문법

학습 파일:

- [`src/enumExampleV1/Day.java`](src/enumExampleV1/Day.java)
- [`src/enumExampleV1/Main.java`](src/enumExampleV1/Main.java)

### 열거 상수 선언

```java
public enum Day {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}
```

`MONDAY`부터 `SUNDAY`까지를 **열거 상수**라고 부릅니다. 열거 상수 이름은 일반 상수와 마찬가지로 대문자와 밑줄을 사용하는 `UPPER_SNAKE_CASE`로 작성합니다.

각 열거 상수는 해당 enum 타입으로 미리 만들어진 객체입니다. 외부에서 `new Day()`처럼 새 객체를 만들 수 없습니다. 같은 상수는 하나뿐이므로 다음 비교가 가능합니다.

```java
Day first = Day.TUESDAY;
Day second = Day.TUESDAY;

System.out.println(first == second); // true
```

### 자주 사용하는 메서드

- `name()`: 소스 코드에 선언한 정확한 상수 이름을 문자열로 반환합니다.
- `values()`: 선언된 모든 상수를 배열로 반환합니다.
- `valueOf("이름")`: 이름이 정확히 일치하는 상수를 반환합니다.
- `ordinal()`: 선언 순서를 0부터 반환합니다.

`valueOf`는 대소문자를 구분합니다. 존재하지 않는 이름을 전달하면 `IllegalArgumentException`이 발생하므로 검증되지 않은 사용자 입력에 바로 사용해서는 안 됩니다.

### `ordinal()`을 저장하지 말아야 하는 이유

`ordinal()`은 선언 순서일 뿐 고정된 업무 번호가 아닙니다. 중간에 새 상수를 넣거나 순서를 바꾸면 기존 상수의 숫자도 달라집니다.

고정된 코드가 필요하다면 `V2`처럼 직접 필드를 선언하세요.

```java
READY("RD"),
SHIPPING("SH");

private final String code;
```

### 직접 해보기

- `Day.WEDNESDAY` 두 개를 `==`와 `equals`로 각각 비교합니다.
- `Day.values()`를 반복하면서 `name()`과 `ordinal()`을 함께 출력합니다.
- `Day.valueOf("MONDAY")`와 `Day.valueOf("Monday")`의 결과를 예상합니다.
- `WEEKEND` 상수를 추가하고 `values()` 결과가 어떻게 바뀌는지 확인한 뒤 원래대로 되돌립니다.

## 2. enum의 필드와 일반 메서드

학습 파일:

- [`src/enumExampleV2/OrderStatus.java`](src/enumExampleV2/OrderStatus.java)
- [`src/enumExampleV2/Main.java`](src/enumExampleV2/Main.java)

### 필드 선언 방식

`OrderStatus`는 각 상태에 화면 표시 이름과 단계 번호를 함께 저장합니다.

```java
READY("주문 접수", 1),
PREPARING("상품 준비 중", 2),
SHIPPING("배송 중", 3),
DELIVERED("배송 완료", 4);

private final String displayName;
private final int step;
```

상수 목록 뒤에 필드나 메서드를 선언하려면 마지막 열거 상수 뒤에 세미콜론(`;`)이 필요합니다. 각 상수의 괄호 안 값은 생성자로 전달됩니다.

```java
OrderStatus(String displayName, int step) {
    this.displayName = displayName;
    this.step = step;
}
```

enum 생성자는 열거 상수를 만들 때만 내부적으로 호출됩니다. 그래서 외부 공개 생성자인 `public`이나 상속을 위한 `protected`를 사용할 수 없습니다. 생성자의 `private`은 생략할 수 있습니다.

필드는 보통 `private final`로 선언합니다. 열거 상수 객체는 하나만 만들어져 여러 코드가 공유하므로, 필드가 중간에 변경되면 모든 사용처에 영향을 주기 때문입니다.

### 일반 메서드 선언 방식

모든 상수가 같은 코드로 실행할 동작은 일반 메서드로 선언합니다.

```java
public boolean isDeliveryCompleted() {
    return this == DELIVERED;
}
```

`this`는 현재 메서드를 호출한 열거 상수를 가리킵니다.

```java
OrderStatus.DELIVERED.isDeliveryCompleted(); // true
OrderStatus.READY.isDeliveryCompleted();     // false
```

`next()`도 일반 메서드입니다. 상태 전이 규칙을 enum에 모았으므로 호출하는 코드는 다음처럼 현재 상태에 다음 상태를 물어볼 수 있습니다.

```java
status = status.next();
```

### 직접 해보기

- 각 상태의 `displayName`과 `step`을 `values()`로 모두 출력합니다.
- `CANCELED("주문 취소", 0)` 상태를 추가합니다.
- `CANCELED.next()`를 호출할 때 어떤 결과가 적절할지 결정하고 구현합니다.
- `displayName`의 `final`을 제거하고 값을 변경할 수 있게 만들었을 때 어떤 문제가 생길지 설명합니다.

## 3. enum 사용 전과 사용 후 비교

학습 파일:

- [`src/enumExampleV3/OrderStatusConstants.java`](src/enumExampleV3/OrderStatusConstants.java)
- [`src/enumExampleV3/OrderWithoutEnum.java`](src/enumExampleV3/OrderWithoutEnum.java)
- [`src/enumExampleV3/OrderStatus.java`](src/enumExampleV3/OrderStatus.java)
- [`src/enumExampleV3/OrderWithEnum.java`](src/enumExampleV3/OrderWithEnum.java)
- [`src/enumExampleV3/Main.java`](src/enumExampleV3/Main.java)

### 문자열 상수만 사용한 코드

문자열을 직접 반복하는 것보다 상수로 모으는 편이 낫습니다.

```java
public static final String READY = "READY";
public static final String SHIPPING = "SHIPPING";
```

그러나 다음 메서드의 타입은 여전히 `String`입니다.

```java
public void changeStatus(String status) {
    this.status = status;
}
```

따라서 상수 사용을 강제할 수 없습니다.

```java
order.changeStatus(OrderStatusConstants.SHIPPING); // 정상
order.changeStatus("SHIPING");                     // 오타지만 컴파일됨
order.changeStatus("아무 문자열");                 // 이것도 컴파일됨
```

### enum을 사용한 코드

매개변수 타입을 enum으로 바꾸면 메서드 선언 자체가 허용 범위를 표현합니다.

```java
public void changeStatus(OrderStatus status) {
    this.status = status;
}
```

```java
order.changeStatus(OrderStatus.SHIPPING); // 정상
// order.changeStatus("SHIPING");         // 컴파일 오류
```

enum을 사용하면 다음 효과를 얻습니다.

1. 잘못된 타입과 문자열 오타를 컴파일 단계에서 발견합니다.
2. IDE가 가능한 값을 자동 완성으로 보여 줍니다.
3. 메서드 이름과 타입만 보고도 받는 값의 의미와 범위를 알 수 있습니다.
4. 상태와 관련된 데이터 및 동작을 enum 안에 함께 둘 수 있습니다.
5. 문자열 비교에서 생기는 `equals`, 대소문자, 오타 문제를 줄입니다.

> enum 타입도 참조 타입이므로 `null`이 들어갈 가능성까지 자동으로 없애 주지는 않습니다. `null`을 허용하지 않아야 한다면 생성자나 메서드에서 `Objects.requireNonNull` 같은 별도의 검증을 사용할 수 있습니다.

### 직접 해보기

- 주석 처리된 `orderWithEnum.changeStatus("SHIPING")`을 해제하고 컴파일 오류를 읽습니다.
- `OrderWithoutEnum`에 상태 검증용 if 문을 추가해 보고, enum 방식과 코드 양을 비교합니다.
- `RETURNED` 상태를 문자열 방식과 enum 방식에 각각 추가할 때 수정해야 하는 위치를 찾습니다.
- 두 `changeStatus` 메서드 선언 중 어느 쪽이 더 많은 정보를 주는지 설명합니다.

## 4. enum의 추상 메서드

학습 파일:

- [`src/enumExampleV4/DiscountPolicy.java`](src/enumExampleV4/DiscountPolicy.java)
- [`src/enumExampleV4/Main.java`](src/enumExampleV4/Main.java)

### 언제 추상 메서드를 사용하는가?

일반 메서드는 모든 상수가 같은 구현을 공유할 때 사용합니다. 반면 **메서드 이름과 역할은 같지만 계산 방식이 상수마다 다를 때** 추상 메서드를 사용할 수 있습니다.

`DiscountPolicy`의 모든 상수는 “할인액을 계산한다”는 같은 역할을 가집니다. 그러나 할인 없음, 정액 할인, 정률 할인은 계산식이 서로 다릅니다.

```java
protected abstract int calculateDiscount(int price);
```

이 추상 메서드를 선언하면 각 열거 상수는 상수 본문에서 반드시 구현해야 합니다.

```java
NONE("할인 없음") {
    @Override
    protected int calculateDiscount(int price) {
        return 0;
    }
},
RATE_10_PERCENT("10% 할인") {
    @Override
    protected int calculateDiscount(int price) {
        return price * 10 / 100;
    }
};
```

상수를 하나 추가하면서 추상 메서드 구현을 빼먹으면 컴파일 오류가 발생합니다. 덕분에 새로운 정책을 추가할 때 구현 누락을 실행 전에 발견할 수 있습니다.

### 일반 메서드와 추상 메서드 나누기

| 구분 | 예제의 메서드 | 배치 이유 |
| --- | --- | --- |
| 일반 메서드 | `calculatePaymentAmount` | 가격 검증과 최종 금액 계산 흐름이 모든 정책에서 같음 |
| 추상 메서드 | `calculateDiscount` | 실제 할인액 계산식이 정책마다 다름 |

호출하는 쪽은 구체적인 계산 공식을 구분하지 않습니다.

```java
int paymentAmount = policy.calculatePaymentAmount(productPrice);
```

어떤 상수인지에 따라 그 상수가 구현한 `calculateDiscount`가 선택됩니다. 이것은 다형성의 한 예이며, 바깥쪽에 할인 정책별 if-else를 만들 필요가 없습니다.

### 직접 해보기

- `RATE_20_PERCENT` 정책을 추가합니다.
- 추상 메서드를 구현하지 않고 어떤 컴파일 오류가 발생하는지 확인합니다.
- 500원 상품에 `FIXED_1000`을 적용한 결과가 음수가 되지 않는 이유를 찾습니다.
- 공통 가격 검증을 각 상수의 메서드로 옮겼을 때 생기는 중복을 확인한 뒤 되돌립니다.

## 5. if-else를 enum으로 리팩터링

학습 파일:

- [`src/enumExampleV5/ShippingFeeCalculatorBefore.java`](src/enumExampleV5/ShippingFeeCalculatorBefore.java)
- [`src/enumExampleV5/DeliveryMethod.java`](src/enumExampleV5/DeliveryMethod.java)
- [`src/enumExampleV5/ShippingFeeCalculatorAfter.java`](src/enumExampleV5/ShippingFeeCalculatorAfter.java)
- [`src/enumExampleV5/Main.java`](src/enumExampleV5/Main.java)

### 리팩터링 전: 값을 꺼내 조건문으로 판단

```java
if ("STANDARD".equals(deliveryMethod)) {
    return orderAmount >= 50_000 ? 0 : 3_000;
} else if ("EXPRESS".equals(deliveryMethod)) {
    return orderAmount >= 100_000 ? 0 : 6_000;
} else if ("PICKUP".equals(deliveryMethod)) {
    return 0;
} else {
    throw new IllegalArgumentException(...);
}
```

이 코드에서는 배송 방법, 기본 배송비, 무료 배송 기준이 조건문의 각 분기에 흩어져 있습니다. `String`을 받으므로 오타를 막기 위한 마지막 `else`도 필요합니다. 다른 클래스에서도 배송 방법을 판단한다면 비슷한 조건문이 반복될 수 있습니다.

### 리팩터링 후: enum에게 동작을 요청

배송 방법에 속하는 데이터를 enum 상수에 모읍니다.

```java
STANDARD("일반 배송", 3_000, 50_000),
EXPRESS("빠른 배송", 6_000, 100_000),
PICKUP("매장 수령", 0, 0);
```

그리고 enum이 자신의 데이터로 요금을 계산하게 합니다.

```java
public int calculateFee(int orderAmount) {
    return orderAmount >= freeShippingThreshold ? 0 : baseFee;
}
```

호출 코드는 한 줄로 줄어듭니다.

```java
return deliveryMethod.calculateFee(orderAmount);
```

중요한 점은 단순히 줄 수가 줄었다는 사실이 아닙니다. 리팩터링 후 코드는 **“선택한 배송 방법이 배송비를 계산한다”**고 그대로 읽힙니다. 호출하는 코드는 배송 방법별 숫자와 분기 규칙을 알 필요가 없습니다.

### 무엇이 좋아졌는가?

- 매개변수 타입만으로 가능한 배송 방법이 명확해집니다.
- 배송 방법을 구분하던 if-else가 호출 코드에서 사라집니다.
- 배송 방법의 이름, 요금, 무료 기준이 한 상수에 모입니다.
- 문자열 오타를 컴파일 단계에서 발견합니다.
- 새 배송 방법을 추가할 위치가 `DeliveryMethod`로 모입니다.
- 상수별 계산 방식까지 달라진다면 `V4`의 추상 메서드 방식으로 자연스럽게 확장할 수 있습니다.

조건문 자체가 나쁜 것은 아닙니다. 단순한 범위 검사나 한 번만 쓰이는 작은 분기는 if 문이 더 명확할 수 있습니다. enum 리팩터링은 **같은 종류를 구분하는 조건이 반복되고, 그 종류마다 데이터나 행동이 함께 달라질 때** 특히 효과적입니다.

### 직접 해보기

- 주문 금액을 `40_000`, `50_000`, `100_000`으로 바꾸어 배송비를 비교합니다.
- `SAME_DAY("당일 배송", 10_000, 150_000)`을 추가합니다.
- 리팩터링 전 코드에도 `SAME_DAY`를 추가하고 수정량을 비교합니다.
- 배송 방법마다 계산식이 완전히 달라졌다고 가정하고 `V4`처럼 추상 메서드 방식으로 변경합니다.

## 전체 개념 정리

```text
단순한 값 목록
    ↓ V1: 열거 상수
값마다 데이터가 필요함
    ↓ V2: 필드 + 생성자
모든 값이 같은 동작을 수행함
    ↓ V2: 일반 메서드
값마다 동작의 구현이 달라짐
    ↓ V4: 추상 메서드 + 상수별 재정의
바깥에 같은 종류를 구분하는 if-else가 반복됨
    ↓ V5: 데이터와 동작을 enum으로 이동
타입 안전하고 의도가 잘 드러나는 코드
```

## 마무리 확인 문제

1. `public static final String` 상수를 사용해도 enum과 같은 타입 안전성을 얻지 못하는 이유는 무엇인가요?
2. enum 상수 목록 뒤에 필드나 메서드를 선언할 때 세미콜론이 필요한 위치는 어디인가요?
3. enum 생성자를 외부에서 호출할 수 없는 이유는 무엇인가요?
4. `ordinal()`을 데이터베이스에 저장하면 어떤 변경에 취약한가요?
5. 일반 메서드와 추상 메서드는 각각 어떤 상황에서 사용해야 하나요?
6. 추상 메서드를 가진 enum에 새 상수를 추가하면 컴파일러가 무엇을 확인해 주나요?
7. `ShippingFeeCalculatorAfter`가 리팩터링 전보다 읽기 쉬운 이유를 “코드 길이” 이외의 관점에서 설명해 보세요.
8. enum을 사용하면 `null`과 외부 문자열 입력 문제도 모두 자동으로 해결되나요?

위 질문에 코드를 보지 않고 답할 수 있고, 문자열과 if-else로 작성된 간단한 코드를 직접 enum으로 리팩터링할 수 있다면 이 프로젝트의 핵심 학습 목표를 달성한 것입니다.

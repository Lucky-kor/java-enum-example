package enumExampleV4;

/**
 * enum의 추상 메서드를 이용해 상수마다 다른 동작을 실행하는 예제입니다.
 */
public class Main {

    public static void main(String[] args) {
        int productPrice = 20_000;

        System.out.println("=== 할인 정책별 결제 금액 ===");

        /*
         * 호출하는 코드는 정책마다 어떤 계산식을 사용하는지 알 필요가 없습니다.
         * 모든 정책을 같은 메서드로 호출하면 실제 상수가 구현한 계산 방식이 실행됩니다.
         */
        for (DiscountPolicy policy : DiscountPolicy.values()) {
            int paymentAmount = policy.calculatePaymentAmount(productPrice);
            int discountAmount = productPrice - paymentAmount;

            System.out.println(
                    policy + " (" + policy.getDescription() + ")"
                            + " - 할인액: " + discountAmount
                            + "원, 결제액: " + paymentAmount + "원"
            );
        }
    }
}


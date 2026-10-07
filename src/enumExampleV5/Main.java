package enumExampleV5;

/**
 * 문자열을 검사하는 if-else 코드를 enum의 데이터와 메서드로 리팩터링한 결과를 비교합니다.
 */
public class Main {

    public static void main(String[] args) {
        int orderAmount = 40_000;

        ShippingFeeCalculatorBefore before = new ShippingFeeCalculatorBefore();
        ShippingFeeCalculatorAfter after = new ShippingFeeCalculatorAfter();

        System.out.println("=== if-else 리팩터링 전 ===");
        int feeBefore = before.calculate("STANDARD", orderAmount);
        System.out.println("배송 방법: STANDARD, 배송비: " + feeBefore + "원");

        System.out.println();
        System.out.println("=== enum으로 리팩터링 후 ===");
        DeliveryMethod deliveryMethod = DeliveryMethod.STANDARD;
        int feeAfter = after.calculate(deliveryMethod, orderAmount);
        System.out.println(
                "배송 방법: " + deliveryMethod.getDisplayName()
                        + ", 배송비: " + feeAfter + "원"
        );

        /*
         * 문자열 방식은 오타가 있어도 컴파일되며 아래처럼 실행 중에야 실패합니다.
         */
        System.out.println();
        try {
            before.calculate("STANDRAD", orderAmount);
        } catch (IllegalArgumentException exception) {
            System.out.println("문자열 오타는 실행 중 발견: " + exception.getMessage());
        }

        /*
         * enum 방식에서는 아래와 같은 존재하지 않는 상수를 컴파일러가 바로 알려 줍니다.
         * IDE 자동 완성으로 STANDARD, EXPRESS, PICKUP만 선택할 수도 있습니다.
         */
        // after.calculate(DeliveryMethod.STANDRAD, orderAmount);
    }
}


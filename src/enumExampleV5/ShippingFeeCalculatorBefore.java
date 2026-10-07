package enumExampleV5;

/**
 * 배송 방법을 문자열과 if-else로 구분하는 리팩터링 전 코드입니다.
 */
public class ShippingFeeCalculatorBefore {

    public int calculate(String deliveryMethod, int orderAmount) {
        validateOrderAmount(orderAmount);

        /*
         * 배송 방법이 늘어날수록 조건문이 길어지고,
         * 요금과 무료 배송 기준이 여러 분기에 흩어집니다.
         */
        if ("STANDARD".equals(deliveryMethod)) {
            return orderAmount >= 50_000 ? 0 : 3_000;
        } else if ("EXPRESS".equals(deliveryMethod)) {
            return orderAmount >= 100_000 ? 0 : 6_000;
        } else if ("PICKUP".equals(deliveryMethod)) {
            return 0;
        } else {
            /*
             * String을 받기 때문에 오타도 일단 컴파일됩니다.
             * 개발자가 모든 잘못된 값을 실행 중에 직접 검사해야 합니다.
             */
            throw new IllegalArgumentException("지원하지 않는 배송 방법: " + deliveryMethod);
        }
    }

    private void validateOrderAmount(int orderAmount) {
        if (orderAmount < 0) {
            throw new IllegalArgumentException("주문 금액은 0원 이상이어야 합니다.");
        }
    }
}


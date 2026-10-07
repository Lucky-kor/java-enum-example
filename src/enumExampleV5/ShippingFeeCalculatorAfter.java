package enumExampleV5;

/**
 * 배송 방법별 판단 책임을 DeliveryMethod enum에 맡긴 리팩터링 후 코드입니다.
 */
public class ShippingFeeCalculatorAfter {

    public int calculate(DeliveryMethod deliveryMethod, int orderAmount) {
        /*
         * 어떤 배송 방법인지 확인하는 if-else가 사라졌습니다.
         * 읽는 사람은 '선택한 배송 방법이 요금을 계산한다'는 핵심 흐름에 집중할 수 있습니다.
         */
        return deliveryMethod.calculateFee(orderAmount);
    }
}


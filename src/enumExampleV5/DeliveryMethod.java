package enumExampleV5;

/**
 * 배송 방법과 그 방법에 필요한 데이터를 함께 보관하는 enum입니다.
 */
public enum DeliveryMethod {
    STANDARD("일반 배송", 3_000, 50_000),
    EXPRESS("빠른 배송", 6_000, 100_000),
    PICKUP("매장 수령", 0, 0);

    private final String displayName;
    private final int baseFee;
    private final int freeShippingThreshold;

    DeliveryMethod(String displayName, int baseFee, int freeShippingThreshold) {
        this.displayName = displayName;
        this.baseFee = baseFee;
        this.freeShippingThreshold = freeShippingThreshold;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * 배송 방법을 구분하는 if 문 없이 현재 상수(this)의 데이터로 요금을 계산합니다.
     * PICKUP은 무료 기준이 0원이므로 모든 정상 주문 금액에서 자동으로 0원을 반환합니다.
     */
    public int calculateFee(int orderAmount) {
        if (orderAmount < 0) {
            throw new IllegalArgumentException("주문 금액은 0원 이상이어야 합니다.");
        }

        return orderAmount >= freeShippingThreshold ? 0 : baseFee;
    }
}


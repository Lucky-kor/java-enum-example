package enumExampleV4;

/**
 * 상수마다 서로 다른 계산 방법을 구현하는 enum입니다.
 *
 * 공통 데이터는 필드에, 공통 흐름은 일반 메서드에 두고,
 * 상수마다 달라야 하는 부분만 추상 메서드로 선언합니다.
 */
public enum DiscountPolicy {
    NONE("할인 없음") {
        @Override
        protected int calculateDiscount(int price) {
            return 0;
        }
    },
    FIXED_1000("1,000원 할인") {
        @Override
        protected int calculateDiscount(int price) {
            // 상품 가격보다 할인 금액이 커져 결제 금액이 음수가 되지 않도록 제한합니다.
            return Math.min(price, 1_000);
        }
    },
    RATE_10_PERCENT("10% 할인") {
        @Override
        protected int calculateDiscount(int price) {
            return price * 10 / 100;
        }
    };

    private final String description;

    DiscountPolicy(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 가격 검증과 최종 금액 계산은 모든 할인 정책에 공통이므로 일반 메서드로 선언합니다.
     */
    public int calculatePaymentAmount(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("상품 가격은 0원 이상이어야 합니다.");
        }

        return price - calculateDiscount(price);
    }

    /**
     * 할인액 계산 방식은 상수마다 다르므로 추상 메서드로 선언합니다.
     *
     * 추상 메서드가 있으면 NONE, FIXED_1000, RATE_10_PERCENT가
     * 각자의 상수 본문에서 이 메서드를 반드시 재정의해야 합니다.
     */
    protected abstract int calculateDiscount(int price);
}


package enumExampleV2;

/**
 * 필드, 생성자, 일반 메서드를 가진 enum입니다.
 *
 * enum은 단순한 상수 목록에 그치지 않습니다. 각 상수와 관련된 데이터는 필드에,
 * 그 데이터를 사용하는 동작은 메서드에 모을 수 있습니다.
 */
public enum OrderStatus {
    /*
     * 각 상수 뒤의 값은 아래에 선언된 OrderStatus 생성자로 전달됩니다.
     * 모든 상수는 같은 필드 구조와 같은 일반 메서드를 공유합니다.
     */
    READY("주문 접수", 1),
    PREPARING("상품 준비 중", 2),
    SHIPPING("배송 중", 3),
    DELIVERED("배송 완료", 4);

    /*
     * enum의 필드는 보통 외부에서 임의로 바꿀 수 없도록 private final로 선언합니다.
     * 열거 상수는 한 번만 만들어져 여러 곳에서 공유되므로 변경 가능한 필드는 피하는 편이 안전합니다.
     */
    private final String displayName;
    private final int step;

    /*
     * enum 생성자는 열거 상수를 만들 때 내부적으로만 호출됩니다.
     * public 또는 protected로 선언할 수 없으며, private은 생략할 수 있습니다.
     */
    OrderStatus(String displayName, int step) {
        this.displayName = displayName;
        this.step = step;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getStep() {
        return step;
    }

    /**
     * 모든 상수가 같은 방식으로 사용하는 일반 인스턴스 메서드입니다.
     */
    public boolean isDeliveryCompleted() {
        return this == DELIVERED;
    }

    /**
     * 현재 상태(this)를 기준으로 다음 상태를 반환합니다.
     * 호출하는 쪽에서 상태 전이 규칙을 반복하지 않고 enum 안에서 한곳에 관리합니다.
     */
    public OrderStatus next() {
        switch (this) {
            case READY:
                return PREPARING;
            case PREPARING:
                return SHIPPING;
            case SHIPPING:
                return DELIVERED;
            case DELIVERED:
                throw new IllegalStateException("배송 완료 이후의 상태는 없습니다.");
            default:
                throw new IllegalStateException("알 수 없는 주문 상태입니다: " + this);
        }
    }
}


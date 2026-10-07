package enumExampleV3;

/**
 * 주문 상태를 OrderStatus enum으로 저장하는 예제입니다.
 */
public class OrderWithEnum {
    private final long orderId;
    private OrderStatus status;

    public OrderWithEnum(long orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }

    /*
     * OrderStatus 매개변수에는 OrderStatus에 선언된 상수만 전달할 수 있습니다.
     * 허용되는 값의 범위를 메서드 선언 자체가 설명하고, 컴파일러도 그 규칙을 검사합니다.
     */
    public void changeStatus(OrderStatus status) {
        this.status = status;
    }

    public String getOrderInformation() {
        return "주문 번호: " + orderId + ", 상태: " + status;
    }
}


package enumExampleV3;

/**
 * 주문 상태를 String으로 저장하는, enum을 사용하지 않은 예제입니다.
 */
public class OrderWithoutEnum {
    private final long orderId;
    private String status;

    public OrderWithoutEnum(long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    /*
     * String 매개변수에는 어떤 문자열이든 전달할 수 있습니다.
     * "SHIPING"처럼 오타가 있어도 자바 컴파일러는 잘못된 주문 상태인지 판단하지 못합니다.
     */
    public void changeStatus(String status) {
        this.status = status;
    }

    public String getOrderInformation() {
        return "주문 번호: " + orderId + ", 상태: " + status;
    }
}


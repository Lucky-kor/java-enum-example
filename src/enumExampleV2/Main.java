package enumExampleV2;

/**
 * enum 상수마다 저장된 필드를 읽고, enum에 선언한 일반 메서드를 호출하는 예제입니다.
 */
public class Main {

    public static void main(String[] args) {
        OrderStatus status = OrderStatus.READY;

        System.out.println("=== 주문 상태 진행 ===");

        /*
         * 배송이 끝날 때까지 현재 상태의 데이터와 동작을 사용합니다.
         * 상태 이름과 단계 번호를 별도의 배열이나 if 문으로 관리할 필요가 없습니다.
         */
        while (!status.isDeliveryCompleted()) {
            printStatus(status);
            status = status.next();
        }

        // while 조건이 완료 상태에서 false가 되므로 마지막 상태는 반복문 밖에서 출력합니다.
        printStatus(status);

        System.out.println();
        System.out.println("배송 완료 여부: " + status.isDeliveryCompleted());
    }

    private static void printStatus(OrderStatus status) {
        System.out.println(
                status.getStep() + "단계 - " + status.getDisplayName() + " (" + status + ")"
        );
    }
}


package enumExampleV3;

/**
 * enum을 사용하지 않았을 때와 사용했을 때의 타입 안전성을 직접 비교합니다.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== enum을 사용하지 않은 코드 ===");

        OrderWithoutEnum orderWithoutEnum =
                new OrderWithoutEnum(1001L, OrderStatusConstants.READY);

        // 상수를 사용해 처음에는 올바른 값을 전달했습니다.
        System.out.println(orderWithoutEnum.getOrderInformation());

        /*
         * 그러나 매개변수가 String이므로 상수에 없는 오타도 정상적으로 컴파일됩니다.
         * 오류는 실행 후 데이터나 화면을 확인할 때까지 발견되지 않을 수 있습니다.
         */
        orderWithoutEnum.changeStatus("SHIPING"); // SHIPPING에서 P가 하나 빠진 오타
        System.out.println(orderWithoutEnum.getOrderInformation());

        System.out.println();
        System.out.println("=== enum을 사용한 코드 ===");

        OrderWithEnum orderWithEnum =
                new OrderWithEnum(1002L, OrderStatus.READY);

        orderWithEnum.changeStatus(OrderStatus.SHIPPING);
        System.out.println(orderWithEnum.getOrderInformation());

        /*
         * 아래 코드는 타입이 맞지 않아 컴파일되지 않습니다.
         * 즉, 문자열 오타를 프로그램 실행 전인 컴파일 단계에서 발견할 수 있습니다.
         */
        // orderWithEnum.changeStatus("SHIPING");

        /*
         * IDE에서 OrderStatus.까지만 입력하면 가능한 상수 목록을 자동 완성으로 볼 수 있습니다.
         * 코드를 읽는 사람도 changeStatus가 어떤 종류의 값을 받는지 즉시 알 수 있습니다.
         */
    }
}


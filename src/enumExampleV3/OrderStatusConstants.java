package enumExampleV3;

/**
 * enum을 사용하기 전에 흔히 만드는 문자열 상수 모음입니다.
 *
 * 상수를 사용하면 같은 문자열을 반복해서 직접 입력하는 문제는 줄어듭니다.
 * 하지만 메서드의 매개변수 타입은 여전히 String이므로 다른 문자열도 전달할 수 있습니다.
 */
public final class OrderStatusConstants {
    public static final String READY = "READY";
    public static final String SHIPPING = "SHIPPING";
    public static final String DELIVERED = "DELIVERED";
    public static final String CANCELED = "CANCELED";

    // 객체를 만들 필요가 없는 상수 모음 클래스이므로 외부 생성을 막습니다.
    private OrderStatusConstants() {
    }
}


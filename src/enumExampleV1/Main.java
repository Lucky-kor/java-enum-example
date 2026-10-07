package enumExampleV1;

/**
 * enum의 선언, 비교, values, valueOf 같은 기본 사용법을 확인하는 예제입니다.
 */
public class Main {

    public static void main(String[] args) {
        /*
         * Day 변수에는 Day에 선언된 열거 상수만 저장할 수 있습니다.
         * "TUESDAY" 같은 임의의 문자열은 Day 변수에 저장할 수 없습니다.
         */
        Day today = Day.TUESDAY;
        Day anotherTuesday = Day.TUESDAY;
        // Day invalidDay = "TUESDAY"; // 타입이 다르므로 컴파일 오류

        System.out.println("=== enum의 기본 사용 ===");
        System.out.println("오늘: " + today);

        /*
         * 같은 열거 상수는 프로그램 안에 하나만 존재합니다.
         * 따라서 enum은 equals뿐 아니라 ==로도 안전하게 비교할 수 있습니다.
         */
        System.out.println("같은 상수인가? " + (today == anotherTuesday)); // true

        /*
         * name()은 소스 코드에 선언한 열거 상수 이름을 문자열로 반환합니다.
         * ordinal()은 선언 순서를 0부터 반환하지만, 저장용 번호로 사용하면 안 됩니다.
         * 상수를 중간에 추가하거나 순서를 바꾸면 ordinal 값도 함께 바뀌기 때문입니다.
         */
        System.out.println("상수 이름: " + today.name());
        System.out.println("선언 순서: " + today.ordinal() + " (저장용 번호로 사용하지 않기)");

        /* values()는 enum에 선언된 모든 상수를 선언 순서대로 반환합니다. */
        System.out.println();
        System.out.println("=== values()로 모든 요일 순회 ===");
        for (Day day : Day.values()) {
            System.out.println(day);
        }

        /*
         * valueOf는 이름이 정확히 일치하는 열거 상수를 찾습니다.
         * 외부 입력은 오타나 소문자를 포함할 수 있으므로 예외 처리가 필요합니다.
         */
        System.out.println();
        System.out.println("=== 문자열을 enum으로 변환 ===");
        Day convertedDay = Day.valueOf("FRIDAY");
        System.out.println("FRIDAY 변환 결과: " + convertedDay);

        try {
            Day.valueOf("friday");
        } catch (IllegalArgumentException exception) {
            System.out.println("friday는 선언된 상수 이름과 대소문자가 일치하지 않습니다.");
        }
    }
}


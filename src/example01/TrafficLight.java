package example01;

// 열거형을 사용하여 신호등의 상태를 정의합니다.
enum TrafficLight {
    RED(30), // 빨간색 신호등은 30초간 지속
    GREEN(45), // 초록색 신호등은 45초간 지속
    YELLOW(5); // 노란색 신호등은 5초간 지속

    private final int duration; // 신호등의 지속 시간

    // 열거형 생성자
    TrafficLight(int duration) {
        this.duration = duration;
    }

    // 지속 시간을 반환하는 메서드
    public int getDuration() {
        return duration;
    }

    // 다음 신호등 상태를 반환하는 메서드
    public TrafficLight next() {
        switch (this) {
            case RED:
                return GREEN;
            case GREEN:
                return YELLOW;
            case YELLOW:
                return RED;
            default:
                throw new AssertionError("Unknown state: " + this);
        }
    }
}

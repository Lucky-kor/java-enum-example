package example01;

public class TrafficSystem {
    public static void main(String[] args) throws InterruptedException {
        TrafficLight currentLight = TrafficLight.RED;

        // 신호등의 상태 변경을 시뮬레이션합니다.
        for (int i = 0; i < 10; i++) {
            System.out.println("The traffic light is " + currentLight +
                    " for " + currentLight.getDuration() + " seconds.");
                Thread.sleep(currentLight.getDuration() * 1000);
            // 다음 신호등 상태로 변경
            currentLight = currentLight.next();
        }
    }
}

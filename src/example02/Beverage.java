package example02;

class Beverage {
    private final String name;
    private BeverageSize size;
    private final int price;

    // 음료의 이름과 사이즈를 받아서 객체를 생성합니다.
    public Beverage(String name, int price) {
        this.name = name;
        this.price = price;
    }

    // 음료의 상세 정보를 출력하는 메서드
    public void orderBeverageDetails(BeverageSize size) {
        System.out.printf("%s (%s): $%.2f%n", name, size, price + size.getExtraPrice());
    }
}

package example02;

public class BeverageOrderSystem {
    public static void main(String[] args) {
        // 다양한 사이즈의 음료를 주문합니다.
        Beverage coffeeSmall = new Beverage("Coffee", 2000);
        Beverage latteLarge = new Beverage("Latte", 3000);
        Beverage teaMedium = new Beverage("Tea", 2500);

        // 주문한 음료의 상세 정보를 출력합니다.
        coffeeSmall.orderBeverageDetails(BeverageSize.SMALL);
        latteLarge.orderBeverageDetails(BeverageSize.MEDIUM);
        teaMedium.orderBeverageDetails(BeverageSize.LARGE);
    }
}

package example02;

// 음료 사이즈와 그에 해당하는 가격을 관리하는 열거형
enum BeverageSize {
    SMALL(0), MEDIUM(500), LARGE(1200);

    private final double extraPrice; // 각 사이즈의 가격

    // 열거형 생성자
    BeverageSize(double extraPrice) {
        this.extraPrice = extraPrice;
    }

    // 가격을 반환하는 메서드
    public double getExtraPrice() {
        return extraPrice;
    }
}

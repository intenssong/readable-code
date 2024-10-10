package cleancode.studycafe.tobe.model;

public class StudyCafePass extends Pass{
    private final double discountRate;

    private StudyCafePass(StudyCafePassType passType, int duration, int price, double discountRate) {
        super(passType, duration, price);
        this.discountRate = discountRate;
    }

    public static StudyCafePass of(StudyCafePassType passType, int duration, int price, double discountRate) {
        return new StudyCafePass(passType, duration, price, discountRate);
    }

    public int getDiscountPrice() {
        int discountPrice = (int) (getPrice() * discountRate);
        return discountPrice;
    }
}

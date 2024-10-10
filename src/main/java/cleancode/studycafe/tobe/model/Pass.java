package cleancode.studycafe.tobe.model;

public abstract class Pass {
    private  StudyCafePassType passType;
    private  int duration;
    private  int price;

    public Pass(StudyCafePassType passType, int duration, int price) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
    }

    public StudyCafePassType getPassType() {
        return passType;
    }

    public int getDuration() {
        return duration;
    }

    public int getPrice() {
        return price;
    }

}

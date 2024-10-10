package cleancode.studycafe.tobe.model;

import java.util.ArrayList;
import java.util.List;

public class Passes {
    private final List<Pass> studyCafePasses;


    private Passes(List<Pass> studyCafePasses) {
        this.studyCafePasses = studyCafePasses;
    }

    public static Passes of(List<Pass> studyCafePasses) {
        return new Passes(studyCafePasses);
    }

    public StudyCafeLockerPass findLockerPass(StudyCafePass selectedPass) {
        List<Pass> lockerPasses = new ArrayList<Pass>(studyCafePasses);
        return (StudyCafeLockerPass)lockerPasses.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                                && option.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);
    }

    public List<Pass> findPassesBy(StudyCafePassType studyCafePassType) {
        List<Pass> findPasses = new ArrayList<Pass>(studyCafePasses);
        return findPasses.stream()
                .filter(pass -> pass.getPassType() == studyCafePassType)
                .toList();
    }

}

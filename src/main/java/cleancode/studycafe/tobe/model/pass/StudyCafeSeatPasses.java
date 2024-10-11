package cleancode.studycafe.tobe.model.pass;

import java.util.ArrayList;
import java.util.List;

public class StudyCafeSeatPasses {

    private final List<StudyCafeSeatPass> passes;

    private StudyCafeSeatPasses(List<StudyCafeSeatPass> passes) {
        this.passes = passes;
    }

    public static StudyCafeSeatPasses of(List<StudyCafeSeatPass> passes) {
        return new StudyCafeSeatPasses(passes);
    }


    public List<StudyCafeSeatPass> findPassBy(StudyCafePassType studyCafePassType) {
        return new ArrayList<>(passes).stream()
                .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
                .toList();
    }
}

package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.model.Pass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;

public interface InputHandler {
    StudyCafePassType getPassTypeSelectingUserAction();

    Pass getSelectPass(List<Pass> passes);

    boolean getLockerSelection();
}


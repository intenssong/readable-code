package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.Pass;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;

public interface OutputHandler {
    void showWelcomeMessage();

    void showAnnouncement();

    void showPassTypeSelection();

    void showPassListForSelection(List<Pass> passes);

    void showLockerPass(StudyCafeLockerPass lockerPass);

    void showPassOrderSummary(StudyCafePass selectedPass, StudyCafeLockerPass lockerPass);

    void showSimpleMessage(String message);

    void display(Pass pass);
}

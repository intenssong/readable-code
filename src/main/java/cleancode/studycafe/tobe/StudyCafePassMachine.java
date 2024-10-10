package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.ConsoleInputHandler;
import cleancode.studycafe.tobe.io.ConsoleOutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.*;

import java.util.List;

public class StudyCafePassMachine {

    public static final StudyCafeFileHandler STUDY_CAFE_FILE_HANDLER = new StudyCafeFileHandler();
    public static final Passes LOCKER_PASSES = STUDY_CAFE_FILE_HANDLER.readLockerPasses();
    public static final Passes STUDY_CAFE_PASSES = STUDY_CAFE_FILE_HANDLER.readStudyCafePasses();
    private final ConsoleInputHandler inputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler outputHandler = new ConsoleOutputHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.showPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();


                List<Pass> findedPasses = STUDY_CAFE_PASSES.findPassesBy(studyCafePassType);
                outputHandler.showPassListForSelection(findedPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(findedPasses);

                if(isFixedType(studyCafePassType)){
                    StudyCafeLockerPass lockerPass = LOCKER_PASSES.findLockerPass(selectedPass);
                    boolean lockerSelection = false;
                    if (isNotNull(lockerPass)) {
                        outputHandler.showLockerPass(lockerPass);
                        lockerSelection = inputHandler.getLockerSelection();
                    }

                    if (lockerSelection) {
                        outputHandler.showPassOrderSummary(selectedPass, lockerPass);
                    }
                }


                outputHandler.showPassOrderSummary(selectedPass, null);

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private static boolean isNotNull(StudyCafeLockerPass lockerPass) {
        return lockerPass != null;
    }

    private static boolean isFixedType(StudyCafePassType studyCafePassType) {
        return studyCafePassType == StudyCafePassType.FIXED;
    }


}

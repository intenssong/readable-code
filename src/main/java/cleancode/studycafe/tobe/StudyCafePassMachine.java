package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    public static final StudyCafeFileHandler STUDY_CAFE_FILE_HANDLER = new StudyCafeFileHandler();
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePass selectedPass = getSelectedPass();
            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);

            optionalLockerPass.ifPresentOrElse(
                    lockerpass -> outputHandler.showPassOrderSummary(selectedPass, optionalLockerPass.get()),
                    () ->outputHandler.showPassOrderSummary(selectedPass)
            );



        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass getSelectedPass() {
        outputHandler.askPassTypeSelection();
        StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

        List<StudyCafePass> passCandidate = findPassCandidatesBy(studyCafePassType);

        outputHandler.showPassListForSelection(passCandidate);
        StudyCafePass selectedPass = inputHandler.getSelectPass(passCandidate);
        return selectedPass;
    }

    private static List<StudyCafePass> findPassCandidatesBy(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> allPasses = STUDY_CAFE_FILE_HANDLER.readStudyCafePasses();
        List<StudyCafePass> passCandidate = allPasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                .toList();
        return passCandidate;
    }

    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafePass selectedPass) {
        if(selectedPass.getPassType() != StudyCafePassType.FIXED){
            return Optional.empty();
        }

        StudyCafeLockerPass lockerPassCandidate = findLockerPassCandidateBy(selectedPass);


        if (lockerPassCandidate != null) {
            outputHandler.askLockerPass(lockerPassCandidate);
            boolean isLockerSelected = inputHandler.getLockerSelection();

            if(isLockerSelected) {
                return Optional.of(lockerPassCandidate);
            }
        }
        return Optional.empty();
    }

    private static StudyCafeLockerPass findLockerPassCandidateBy(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> allLockerPasses = STUDY_CAFE_FILE_HANDLER.readLockerPasses();
        StudyCafeLockerPass lockerPassCandidate = allLockerPasses.stream()
            .filter(lockerPass ->
                lockerPass.getPassType() == selectedPass.getPassType()
                    && lockerPass.getDuration() == selectedPass.getDuration()
            )
            .findFirst()
            .orElse(null);
        return lockerPassCandidate;
    }

}

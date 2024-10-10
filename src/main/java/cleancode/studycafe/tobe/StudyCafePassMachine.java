package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.io.StudyCafeIOHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    public static final StudyCafeFileHandler STUDY_CAFE_FILE_HANDLER = new StudyCafeFileHandler();
    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafePass selectedPass = selectPass();
            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);

            optionalLockerPass.ifPresentOrElse(
                    lockerpass -> ioHandler.showPassOrderSummary(selectedPass, optionalLockerPass.get()),
                    () ->ioHandler.showPassOrderSummary(selectedPass)
            );



        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass selectPass() {
        StudyCafePassType passType = ioHandler.askPassTypeSelecting();

        List<StudyCafePass> passCandidate = findPassCandidatesBy(passType);

        return ioHandler.askPassSelecting(passCandidate);
    }

    private static List<StudyCafePass> findPassCandidatesBy(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> allPasses = STUDY_CAFE_FILE_HANDLER.readStudyCafePasses();
        List<StudyCafePass> passCandidate = allPasses.stream()
                .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
                .toList();
        return passCandidate;
    }

    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafePass selectedPass) {
        // 고정 좌석 타입이 아닌가?
        // 사물함 옵션을 사용할 수 있는 타입이 아닌가?
        if(selectedPass.cannotUseLocker()){
            return Optional.empty();
        }

        StudyCafeLockerPass lockerPassCandidate = findLockerPassCandidateBy(selectedPass);


        if (lockerPassCandidate != null) {

            boolean isLockerSelected = ioHandler.askLockerPassSelecting(lockerPassCandidate);

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
                 selectedPass.isSameDurationType(lockerPass)

            )
            .findFirst()
            .orElse(null);
        return lockerPassCandidate;
    }

}

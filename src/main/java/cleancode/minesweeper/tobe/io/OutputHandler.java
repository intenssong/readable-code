package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.GameException;


public interface OutputHandler {

    void showGameStartComments();

    void showBoard(GameBoard board);

    void showGameWinningComment();

    void showGameLosingComment();

    void showCustomExceptionMessage(GameException e);

    void showCommentForUserActionInput();

    void showCommentForCellInput();

    void showSimpleMessage(String message);
}

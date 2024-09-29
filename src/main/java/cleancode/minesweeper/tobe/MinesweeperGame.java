package cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    public static final Scanner SCANNER = new Scanner(System.in);
    public static final int BOARD_ROW_SIZR = 8;
    public static final int BOARD_COL_SIZE = 10;
    public static final String FLAG_SIGN = "⚑";
    public static final String CLOSED_CELL_SIGN = "□";
    public static final String LAND_MINE = "☼";
    public static final String OPENED_CELL = "■";
    public static final int LAND_MINE_COUNT = 10;
    private static String[][] BOARD = new String[BOARD_ROW_SIZR][BOARD_COL_SIZE];
    private static Integer[][] NEARBY_LAND_MINE_COUNTS = new Integer[BOARD_ROW_SIZR][BOARD_COL_SIZE];
    private static boolean[][] LAND_MINES = new boolean[BOARD_ROW_SIZR][BOARD_COL_SIZE];
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static void main(String[] args) {
        showGameStartMessage();
        boardInitialize();

        while (true) {
            try {
                System.out.println("   a b userInputCol d e f g h i j");

                for (int row = 0; row < BOARD_ROW_SIZR; row++) {
                    System.out.printf("%d  ", row + 1);
                    for (int col = 0; col < BOARD_COL_SIZE; col++) {
                        System.out.print(BOARD[row][col] + " ");
                    }
                    System.out.println();
                }

                if (doesUserWinTheGame()) {
                    System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                    break;
                }

                if (doesUserLoseTheGame()) {
                    System.out.println("지뢰를 밟았습니다. GAME OVER!");
                    break;
                }
                String userCellInput = getCellInputFromUser();
                String userActionInput = getActionInputFromUser();
                actOnCell(userCellInput, userActionInput);
            }catch (AppException e) {
                System.out.println(e.getMessage());
            }catch (Exception e) {
                System.out.println("프로그램에 문제가 생겼습니다.");
                //e.printStackTrace(); 안티패턴. 실무에선 로그남기기
            }

        }
    }

    private static void actOnCell(String userCellInput, String userActionInput) throws AppException {
        int selectedRowIndex = getSelectedRowIndex(userCellInput);
        int selectedColIndex = getSelectedColIndex(userCellInput);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            BOARD[selectedRowIndex][selectedColIndex] = FLAG_SIGN;
            checkIfGameOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (isLandMineCell(selectedRowIndex, selectedColIndex)) {
                BOARD[selectedRowIndex][selectedColIndex] = LAND_MINE;
                changeGameStatusToLose();
                return ;
            }

            open(selectedRowIndex, selectedColIndex);
            checkIfGameOver();
            return;
        }
            System.out.println("잘못된 번호를 선택하셨습니다.");
    }

    private static void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private static boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return LAND_MINES[selectedRowIndex][selectedColIndex];
    }

    private static boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private static boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private static int getSelectedRowIndex(String userCellInput) {
        char cellInputRow = userCellInput.charAt(1);
        int selectedRowIndex = convertRowFrom(cellInputRow);
        return selectedRowIndex;
    }

    private static int getSelectedColIndex(String userCellInput) throws AppException {
        char cellInputCol = userCellInput.charAt(0);
        int selectedColIndex = convertColFrom(cellInputCol);
        return selectedColIndex;
    }

    private static void checkIfGameOver() {
        boolean isAllOpened = isAllCellOpened();
        if (isAllOpened) {
            changeGameStatusToWin();
        }
    }

    private static void changeGameStatusToWin() {
        gameStatus = 1;
    }

    private static boolean isAllCellOpened() {
        return Arrays.stream(BOARD)
                .flatMap(Arrays::stream)
                .noneMatch(CLOSED_CELL_SIGN::equals);
    }

    private static int convertRowFrom(char userInputRow) {
        int rowIndex = Character.getNumericValue(userInputRow) - 1;
        if (rowIndex > BOARD_ROW_SIZR) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private static int convertColFrom(char userInputCol) throws AppException {
        switch (userInputCol) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            default:
                throw new AppException("잘못된 입력입니다.");
        }
    }

    private static boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private static boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private static String getActionInputFromUser() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        String userInputAction = SCANNER.nextLine();
        return userInputAction;
    }

    private static String getCellInputFromUser() {
        System.out.println();
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        String userInputCell = SCANNER.nextLine();
        return userInputCell;
    }

    private static void boardInitialize() {
        for (int row = 0; row < BOARD_ROW_SIZR; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                BOARD[row][col] = CLOSED_CELL_SIGN;
            }
        }
        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(10);
            int row = new Random().nextInt(8);
            LAND_MINES[row][col] = true;
        }
        for (int row = 0; row < BOARD_ROW_SIZR; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {

                if (isLandMineCell(row, col)) {
                    NEARBY_LAND_MINE_COUNTS[row][col] = 0;
                    continue;
                }
                int count = countNearbyLandMines(row, col);
                NEARBY_LAND_MINE_COUNTS[row][col] = count;
            }
        }
    }

    private static int countNearbyLandMines(int row, int col) {
        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < BOARD_COL_SIZE && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < BOARD_COL_SIZE && isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZR && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZR && isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZR && col + 1 < BOARD_COL_SIZE && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

    private static void showGameStartMessage() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void open(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZR || col < 0 || col >= BOARD_COL_SIZE) {
            return;
        }
        if (!BOARD[row][col].equals(CLOSED_CELL_SIGN)) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }
        if (NEARBY_LAND_MINE_COUNTS[row][col] != 0) {
            BOARD[row][col] = String.valueOf(NEARBY_LAND_MINE_COUNTS[row][col]);
            return;
        } else {
            BOARD[row][col] = OPENED_CELL;
        }
        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

}

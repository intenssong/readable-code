package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.Advanced;
import cleancode.minesweeper.tobe.gamelevel.Beginner;

public class GameApplication {



    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper(new Advanced());
        minesweeper.run();
    }




}
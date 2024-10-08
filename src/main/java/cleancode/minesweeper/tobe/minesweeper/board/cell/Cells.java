package cleancode.minesweeper.tobe.minesweeper.board.cell;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Cells {
    private static List<Cell> cells;

    private Cells(List<Cell> cells) {
        this.cells = cells;
    }

    public static Cells of(List<Cell> cells) {
        return new Cells(cells);
    }

    public static Cells from(Cell[][] cells) {
        List<Cell> cellList = Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .toList();
        return of(cellList);

    }

    public boolean isAllChecked() {
        return cells.stream()
                .allMatch(Cell::isChecked);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cells cells1 = (Cells) o;
        return Objects.equals(cells, cells1.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cells);
    }
}

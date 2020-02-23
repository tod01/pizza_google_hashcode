import java.util.HashMap;

public class Pizza {
    int rows;
    int columns;
    int minIngredientEachPerSlice;
    int maxCellsPerSlice;
    HashMap<String, Cell> cells;
    int rowLength;
    int colLength;

    public Pizza() {
        cells = new HashMap<>();
    }

    public String getCellHashKey (int x, int y) {
        return String.format("%0" + rowLength + "d", x) +    String.format("%0" + colLength + "d", y);
    }

    public void addCell(String key, Cell cell) {
        this.cells.put(key, cell);
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setColLength(int colLength) {
        this.colLength = colLength;
    }

    public HashMap<String, Cell> getCells() {
        return cells;
    }

    public void setMaxCellsPerSlice(int maxCellsPerSlice) {
        this.maxCellsPerSlice = maxCellsPerSlice;
    }

    public void setRowLength(int rowLength) {
        this.rowLength = rowLength;
    }

    public void setMinIngredientEachPerSlice(int minIngredientEachPerSlice) {
        this.minIngredientEachPerSlice = minIngredientEachPerSlice;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getMaxCellsPerSlice() {
        return maxCellsPerSlice;
    }

    public int getMinIngredientEachPerSlice() {
        return minIngredientEachPerSlice;
    }
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PizzaCutter {

    Pizza pizza;
    List<Slice> cutSlices;

    public PizzaCutter(Pizza pizza) {
        this.pizza = pizza;
        this.cutSlices = new ArrayList<>();
    }

    public List<Slice> getCutSlices() {
        return cutSlices;
    }

    public void cutPizza() {

        int maxCellsPerSlice = pizza.getMaxCellsPerSlice() / 2;

        for(int line = 0; line < pizza.getRows(); ++line) {
            for(int column=0; column < pizza.getColumns(); ++column) {
                String cellHashKey = pizza.getCellHashKey(line, column);
                Cell cell = pizza.getCells().get(cellHashKey);

                // construct a mini slice
                List<Cell> miniSlicedCells = this.constructMiniSlice(cell.getX(), cell.getY(), maxCellsPerSlice);

                // we exceed the number of columns of the pizza
                if((column + 1) >= pizza.getColumns()) {
                    // mini sliced cells are ok, then store them
                    if(this.checkForSufficient(miniSlicedCells)) {
                        this.insertASlice(miniSlicedCells);
                    }

                    continue;
                }

                // next sliced cells
                List<Cell> nextMiniSlicedCells = this.constructMiniSlice(line, column+1, maxCellsPerSlice);

                // mini sliced cells are ok, then
                if(this.checkForSufficient(miniSlicedCells)) {
                    // the next sliced cells aren't ok, add them to the first ones, then store all
                    if (!this.checkForSufficient(nextMiniSlicedCells)) {
                        miniSlicedCells.addAll(nextMiniSlicedCells);
                        this.insertASlice(miniSlicedCells);
                        column++;
                    }else {
                        // all of them are good, check if they are good together
                        // or add the first ones
                        List<Cell> tmp = miniSlicedCells;
                        tmp.addAll(nextMiniSlicedCells);
                        if(this.checkForSufficient(tmp)) {
                            this.insertASlice(tmp);
                            column++;
                        }else {
                            this.insertASlice(miniSlicedCells);
                        }
                    }

                }else {
                    // the next sliced cells are ok, add the first ones on them, then store all
                    if(this.checkForSufficient(nextMiniSlicedCells)) {
                        miniSlicedCells.addAll(nextMiniSlicedCells);
                        this.insertASlice(miniSlicedCells);
                        column++;
                    }else {
                        // append the next sliced cells to the first ones, then, check together they are ok
                        miniSlicedCells.addAll(nextMiniSlicedCells);
                        if(this.checkForSufficient(miniSlicedCells)) {
                            this.insertASlice(miniSlicedCells);
                            column++;
                        }
                    }
                }
            }
            line += maxCellsPerSlice - 1;
        }
    }

    /**
     * Insert cells in the list of slice
     * @param slicedCells the sliced cell to add
     */
    public void insertASlice(List<Cell> slicedCells) {

        Cell startSliceCell = getLeastCell(slicedCells);
        Cell endSliceCell   = getMaxCell(slicedCells);

        markAllSliceCellsAsCut(slicedCells);

        Slice slice = new Slice(startSliceCell.getX(), startSliceCell.getY(), endSliceCell.getX(), endSliceCell.getY());

        this.cutSlices.add(slice);

    }

    /**
     * Get the smallest cell of the sliced cells
     * @param slicedCells the sliced cells
     * @return the smallest cell
     */
    private Cell getLeastCell(List<Cell> slicedCells) {
        int minValue = Integer.MAX_VALUE;
        Cell minCell = null;

        for(Cell cell : slicedCells) {
            String hashCell = pizza.getCellHashKey(cell.getX(), cell.getY());
            int cellKeyIntValue = Integer.parseInt(hashCell);
            if(Integer.parseInt(hashCell) < minValue) {
                minCell  = cell;
                minValue = cellKeyIntValue;
            }
        }
        return minCell;
    }

    /**
     * Get the biggest cell of the sliced cells
     * @param slicedCells the sliced cells
     * @return the biggest cell
     */
    private Cell getMaxCell(List<Cell> slicedCells) {
        int maxValue = Integer.MIN_VALUE;
        Cell maxCell = null;

        for(Cell cell : slicedCells) {
            String hashCell = pizza.getCellHashKey(cell.getX(), cell.getY());
            int cellKeyIntValue = Integer.parseInt(hashCell);
            if(Integer.parseInt(hashCell) > maxValue) {
                maxCell  = cell;
                maxValue = cellKeyIntValue;
            }
        }
        return maxCell;
    }

    /**
     * Mark all sliced cells as cut
     * @param slicedCells
     */
    private void markAllSliceCellsAsCut(List<Cell> slicedCells) {

        for(Cell cell : slicedCells) {
            String hashKeyCell = pizza.getCellHashKey(cell.getX(), cell.getY());
            cell.setCutOut(true);
            pizza.getCells().put(hashKeyCell, cell);
        }
    }

    /**
     * Verify that the sliced cells meet the criteria
     * @param slicedCells the sliced cells
     * @return
     */
    private boolean checkForSufficient(List<Cell> slicedCells) {

        // 1- there is the minimum of mushroom and tomato

        int mushroom = 0, tomato = 0;
        for(Cell cell : slicedCells) {
            if(cell.getIngredient() == 'T') {
                tomato++;
            }else {
                mushroom++;
            }

            // 2- cell isn't previously cut
            if(cell.isCutOut()) {
                return false;
            }
        }

        if(mushroom < this.pizza.getMinIngredientEachPerSlice() || tomato < this.pizza.getMinIngredientEachPerSlice()) {
            return  false;
        }

        // 3- the number of cells doesn't exceed the maximum criterion
        if((mushroom + tomato) > this.pizza.getMaxCellsPerSlice()) {
            return false;
        }

        return true;
    }

    private List<Cell> constructMiniSlice(int x, int y, int minCellsPerSlice) {

        HashMap<String, Cell> slicedCells = new HashMap<>();

        for(int i = 0; i < minCellsPerSlice; ++i) {

            // check that we haven't exceed the size of the list (of the pizza)
            if(x+i >= pizza.getRows()) {
                continue;
            }

            String hashCell = pizza.getCellHashKey(x+i, y);
            Cell cell = pizza.getCells().get(hashCell);

            slicedCells.put(hashCell, cell);
        }

        return new ArrayList<>(slicedCells.values());
    }
}

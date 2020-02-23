import java.io.*;

public class Simulator {
    private String fileName;
    private Pizza pizza;
    private PizzaCutter pizzaCutter;

    public Simulator(String fileName) {
        this.fileName = fileName;
        pizza = new Pizza();
    }

    public void simulate() {
        this.pizzaCutter = new PizzaCutter(pizza);
        this.pizzaCutter.cutPizza();
    }

    public void parseInput() {

        try {

            BufferedReader reader = new BufferedReader(new FileReader(fileName + ".in"));
            String line = reader.readLine();
            String[] firstLine = line.split(" ");

            pizza.setRows(Integer.parseInt(firstLine[0]));
            pizza.setColumns(Integer.parseInt(firstLine[1]));
            pizza.setMinIngredientEachPerSlice(Integer.parseInt(firstLine[2]));
            pizza.setMaxCellsPerSlice(Integer.parseInt(firstLine[3]));

            pizza.setRowLength(firstLine[0].length());
            pizza.setColumns(firstLine[1].length());

            for (int l=0; l < pizza.getRows(); ++l) {
                char[] characters = reader.readLine().toCharArray();
                for (int c=0; c < pizza.getColumns(); ++c) {
                    Cell cell = new Cell(l, c, characters[c]);
                    pizza.addCell(pizza.getCellHashKey(l, c), cell);
                }
            }

        }catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

    }

    /**
     * Print the output as stated in the problem
     */
    public void printOutput() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("../outputData/" + fileName + ".out"));
            writer.write(pizzaCutter.getCutSlices().size());
            for (Slice slice : pizzaCutter.getCutSlices()) {
                writer.write(slice.startX + " " + slice.startY + " " + slice.endX + " " + slice.endY);
            }
        }catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}

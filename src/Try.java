public class Try {

    public static void main(String[] args) {

        String[] inputFiles = {"example", "small", "medium", "big"};

        for(String file : inputFiles) {
            Simulator simulator = new Simulator("inputData/" + file);
            simulator.parseInput();
            simulator.simulate();
            simulator.printOutput();
        }

    }
}

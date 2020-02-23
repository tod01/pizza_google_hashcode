public class Cell {
    int x;
    int y;
    char ingredient;
    boolean cutOut = false;

    public Cell(int x, int y, char ingredient) {
        this.x = x;
        this.y = y;
        this.ingredient = ingredient;
    }

    public void setIngredient(char ingredient) {
        this.ingredient = ingredient;
    }

    public char getIngredient() {
        return ingredient;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isCutOut() {
        return cutOut;
    }

    public void setCutOut(boolean cutOut) {
        this.cutOut = cutOut;
    }
}

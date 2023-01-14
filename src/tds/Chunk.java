package tds;

import java.util.ArrayList;

public class Chunk {
    public ArrayList<Symbol> symbols;
    public Symbol parent;

    public Chunk(Symbol parent) {
        this.symbols = new ArrayList<>();
        this.parent = parent;
    }

    public String toString() {
        return "Chunk(symbol : " + symbols + ", parent : " + parent + ")";
    }
}

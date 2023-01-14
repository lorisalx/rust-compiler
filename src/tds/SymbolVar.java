package tds;

public class SymbolVar extends Symbol{
    public String idf;
    public String type;
    public boolean mutable;

    public SymbolVar(String idf, String type, boolean mutable) {
        super();
        this.idf = idf;
        this.type = type;
        this.mutable = mutable;
    }

    public String toString() {
        return "(Var) | name : " + idf + " | type : " + type + " | mutable : " + mutable;
    }
}

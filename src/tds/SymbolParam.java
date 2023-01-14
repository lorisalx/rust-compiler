package tds;

public class SymbolParam extends Symbol {
    public String idf;
    public String type;

    public SymbolParam(String idf, String type) {
        this.idf = idf;
        this.type = type;
    }

    public String toString() {
        return "(Param) | name : " + idf + " | type :  " + type;
    }
}

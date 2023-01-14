package tds;

public class Symbol {
    public String idf;


    public Symbol() {}

    public void setIdf (String idf) {
        this.idf = idf;
    }
    public String toString() {
        if (idf.equals("main")) {
            return "(Root)";
        }
        return "Symbol(" + idf + ")";
    }
}

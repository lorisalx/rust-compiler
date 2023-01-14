package tds;

public class SymbolBloc extends Symbol {
    public int region;
    public int imbrication;

    public SymbolBloc() {

    }

    public void setAttributes(int region, int imbrication) {
        this.region = region;
        this.imbrication = imbrication;
    }

    public String toString() {
        return "(Bloc) | Region : " + region + " | Imbr : " + imbrication;
    }
}

package tds;

import java.util.ArrayList;

public class SymbolFunction extends Symbol {
    public String idf;
    public String returnType;

    public String typeParams;

    public SymbolFunction() {
    }

    public void setAttributes(String idf, String returnType, String typeParams) {
        this.idf = idf;
        this.returnType = returnType;
        this.typeParams = typeParams;
    }

    public String toString() {
        return "(Function) | name : " + idf + " | returnType :  " + returnType + " | typeParams : " + typeParams;
    }
}

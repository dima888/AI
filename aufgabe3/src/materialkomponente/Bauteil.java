package materialkomponente;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.*;

public class Bauteil {
	
    private Boolean istKomplex;
    private Integer inventarAnzahl;
    private List<BauteilNr> verbrauch;
    
    private Bauteil(Boolean istKomplex, Integer inventarAnzahl, List<BauteilNr> verbrauch) {
        this.istKomplex = istKomplex;
        this.inventarAnzahl = inventarAnzahl;
        this.verbrauch = verbrauch;
    }

    public static Bauteil bauteil(Boolean istKomplex, Integer inventarAnzahl, List<BauteilNr> verbrauch) {
        return new Bauteil(istKomplex, inventarAnzahl, verbrauch);
    }

    public Bauteil asAnzahl(int anzahl) {
        if(getIstKomplex()) {
            Integer neu = getInventarAnzahl() + anzahl;
            return bauteil(getIstKomplex(), neu, getVerbrauch());
        }
        return this;
    }

    public static Bauteil fromString(String s){
        List<BauteilNr> verb = new ArrayList<>();
        String[] ss = s.split(",");
        System.out.println("");
        Boolean c = Boolean.valueOf(ss[0]);
        Integer anz = valueOf(-1);
        if (c) {
            anz = valueOf(parseInt(ss[1]));
        }
        for (int i=2 ; i < ss.length; i++) {
            BauteilNr btn = BauteilNr.bauteilNr(valueOf(parseInt(ss[i])));
            verb.add(btn);
        }
        return new Bauteil(c, anz, verb);
    }

    public Boolean getIstKomplex() {
        return istKomplex;
    }
    public Integer getInventarAnzahl() {
        return inventarAnzahl;
    }

    public List<BauteilNr> getVerbrauch() {
        return verbrauch;
    }

    @Override
    public String toString() {
        return "Bauteil{" +
                "istKomplex=" + istKomplex +
                ", inventarAnzahl=" + inventarAnzahl +
                ", verbrauch=" + verbrauch +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bauteil bauteil = (Bauteil) o;

        if (inventarAnzahl != null ? !inventarAnzahl.equals(bauteil.inventarAnzahl) : bauteil.inventarAnzahl != null)
            return false;
        if (!istKomplex.equals(bauteil.istKomplex)) return false;
        if (!verbrauch.equals(bauteil.verbrauch)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = istKomplex.hashCode();
        result = 31 * result + (inventarAnzahl != null ? inventarAnzahl.hashCode() : 0);
        result = 31 * result + verbrauch.hashCode();
        return result;
    }

    public String toStringRep() {
        String s = "";
        for(BauteilNr bt:verbrauch) {
            s += "," + String.valueOf(bt.getNR());
        }
        return istKomplex.toString() + "," + inventarAnzahl.toString() + s;
    }
}

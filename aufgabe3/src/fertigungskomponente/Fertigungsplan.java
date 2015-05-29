package fertigungskomponente;

import materialkomponente.BauteilNr;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Fertigungsplan {
    Map<BauteilNr, Integer> bauteilAenderungen;
    private Fertigungsplan(Map<BauteilNr, Integer> bauteilAenderungen) {
        this.bauteilAenderungen = bauteilAenderungen;
    }
    public static Fertigungsplan fromString(String s){
        Map<BauteilNr, Integer> bauteilAenderungen = new HashMap<>();
        String[] ss = s.split(",");
        for (int i=0 ; i < ss.length; i+=2) {
            Integer diff = Integer.valueOf(Integer.parseInt(ss[i+1]));
            BauteilNr btNr = BauteilNr.bauteilNr(Integer.valueOf(Integer.parseInt(ss[i])));
            bauteilAenderungen.put(btNr, diff);
        }
        return new Fertigungsplan(bauteilAenderungen);
    }

    public static Fertigungsplan fertigungsPlan(Map<BauteilNr, Integer> bauteilAenderungen) {
        return new Fertigungsplan(bauteilAenderungen);
    }

    public Map<BauteilNr, Integer> getBauteilAenderungen() {
        return bauteilAenderungen;
    }

    public String toStringRep() {
        String s = "";
        boolean first = true;
        for(Entry<BauteilNr, Integer> e:bauteilAenderungen.entrySet()) {
            BauteilNr btn = e.getKey();
            Integer i = e.getValue();
            if(!first) s+=",";
            s+="" + btn + "," + i;
            first = false;
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fertigungsplan that = (Fertigungsplan) o;

        if (!bauteilAenderungen.equals(that.bauteilAenderungen)) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Fertigungsplan{" +
                "bauteilAenderungen=" + bauteilAenderungen +
                '}';
    }

    @Override
    public int hashCode() {
        return bauteilAenderungen.hashCode();
    }
}

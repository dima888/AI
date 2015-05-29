package fertigungskomponente;

public class FertigungsauftragNr {
	
    Integer n;
    
    private FertigungsauftragNr(Integer nr) {
        n = nr;
    }
    public static FertigungsauftragNr fertigungsauftragNr(Integer nr) {
        return new FertigungsauftragNr(nr);
    }
    public int getNR() {
        return n.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FertigungsauftragNr that = (FertigungsauftragNr) o;

        if (n != null ? !n.equals(that.n) : that.n != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return n.toString();
    }
    
    @Override
    public int hashCode() {
        return n != null ? n.hashCode() : 0;
    }
}

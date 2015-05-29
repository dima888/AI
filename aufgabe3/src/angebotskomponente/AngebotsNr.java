package angebotskomponente;

public class AngebotsNr {
    Integer n;
    private AngebotsNr(Integer nr) {
        n = nr;
    }
    
    public static AngebotsNr angebotsNr(Integer nr) {
        return new AngebotsNr(nr);
    }
    
    public int getNR() {
        return n.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AngebotsNr that = (AngebotsNr) o;

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

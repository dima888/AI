package auftragskomponente;

public class AuftragsNr {
    Integer n;
    
    private AuftragsNr(Integer nr) {
        n = nr;
    }
    
    public static AuftragsNr auftragsNr(Integer nr) {
        return new AuftragsNr(nr);
    }
    public int getNR() {
        return n.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuftragsNr that = (AuftragsNr) o;

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

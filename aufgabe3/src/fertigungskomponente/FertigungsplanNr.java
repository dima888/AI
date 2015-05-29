package fertigungskomponente;

public class FertigungsplanNr {
    Integer n;
    private FertigungsplanNr(Integer nr) {
        n = nr;
    }
    public static FertigungsplanNr fertigungsplanNr(Integer nr) {
        return new FertigungsplanNr(nr);
    }
    public int getNR() {
        return n.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FertigungsplanNr that = (FertigungsplanNr) o;

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

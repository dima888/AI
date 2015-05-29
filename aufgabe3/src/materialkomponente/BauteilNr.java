package materialkomponente;

public class BauteilNr {
	
    Integer n;
    
    private BauteilNr(Integer nr) {
        n = nr;
    }
    
    public static BauteilNr bauteilNr(Integer nr) {
        return new BauteilNr(nr);
    }
    
    public int getNR() {
        return n.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BauteilNr that = (BauteilNr) o;

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

package materialkomponente;

import utilities.KeineInventurAtomarerBauteileException;
import utilities.NotFoundException;
import utilities.TechnicalException;

import java.util.List;

public interface IMaterialServicesFuerFertigung {
    int zeigeInventar(BauteilNr btn) throws KeineInventurAtomarerBauteileException, NotFoundException, TechnicalException;
    boolean istKomplexesBauteil(BauteilNr btn) throws NotFoundException, TechnicalException;
    List<BauteilNr> bestandTeilevon(BauteilNr btn) throws NotFoundException, TechnicalException;
    void erzeuge(BauteilNr btn, int anzahl) throws NotFoundException, TechnicalException, KeineInventurAtomarerBauteileException;
    void verbrauche(BauteilNr btn, int anzahl) throws NotFoundException, TechnicalException;
}

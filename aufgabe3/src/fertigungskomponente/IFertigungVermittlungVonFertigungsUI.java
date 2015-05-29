package fertigungskomponente;

import utilities.KeineInventurAtomarerBauteileException;
import utilities.NotFoundException;
import utilities.TechnicalException;

public interface IFertigungVermittlungVonFertigungsUI {
    void stelleFertig(FertigungsauftragNr fNr) throws NotFoundException, TechnicalException, KeineInventurAtomarerBauteileException;
}

package fertigungskomponente;

import auftragskomponente.AuftragsNr;
import auftragskomponente.IAuftragsvermittlungVonFertigung;
import utilities.KeineInventurAtomarerBauteileException;
import utilities.NotFoundException;
import utilities.TechnicalException;

public interface IFertigungServicesFuerAuftrag {
    FertigungsauftragNr fertigeAn(AuftragsNr n) throws NotFoundException, TechnicalException, KeineInventurAtomarerBauteileException;

    void vermittleAufraegeAn(IAuftragsvermittlungVonFertigung afVerm);
}

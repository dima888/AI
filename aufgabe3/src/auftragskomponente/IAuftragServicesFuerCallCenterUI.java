package auftragskomponente;

import angebotskomponente.AngebotsNr;
import utilities.NotFoundException;
import utilities.TechnicalException;

public interface IAuftragServicesFuerCallCenterUI {
    AuftragsNr erstelleAuftrag(AngebotsNr nr) throws NotFoundException, TechnicalException;
}

package auftragskomponente;

import fertigungskomponente.FertigungsauftragNr;
import materialkomponente.BauteilNr;
import utilities.NotFoundException;
import utilities.TechnicalException;

public interface IAuftragsvermittlungVonFertigung {
    void stelleFertig(FertigungsauftragNr fnr);
    BauteilNr getAuftragBauteilNr(AuftragsNr n) throws NotFoundException, TechnicalException;
    Integer getAuftragBauteileAnzahl(AuftragsNr n) throws NotFoundException, TechnicalException;
}

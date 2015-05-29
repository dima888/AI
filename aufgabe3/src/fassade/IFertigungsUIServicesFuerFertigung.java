package fassade;

import fertigungskomponente.FertigungsauftragNr;
import fertigungskomponente.FertigungsplanNr;

public interface IFertigungsUIServicesFuerFertigung {
    void fertigeAn(FertigungsplanNr fpNr, FertigungsauftragNr fnr);
}

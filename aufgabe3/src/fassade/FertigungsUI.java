package fassade;

import fertigungskomponente.FertigungsauftragNr;
import fertigungskomponente.FertigungsplanNr;
import java.util.ArrayList;
import java.util.List;

public class FertigungsUI implements IFertigungsUIServicesFuerFertigung {
    public List<FertigungsplanNr> erhalteneAuftraege = new ArrayList<FertigungsplanNr>();
    public FertigungsUI() {
    }

    @Override
    public void fertigeAn(FertigungsplanNr fpNr, FertigungsauftragNr fnr) {
        System.out.println("Plan " + fpNr + " vom Fertigungsauftrag " + fnr + " wurde von der Abteilung gefertigt.");
        erhalteneAuftraege.add(fpNr);
    }
}

package fertigungskomponente;

import auftragskomponente.AuftragsNr;
import materialkomponente.BauteilNr;
import persistenz.IPersistenzService;
import utilities.NotFoundException;
import utilities.TechnicalException;
import java.util.HashMap;
import static fertigungskomponente.Fertigungsplan.*;
import static fertigungskomponente.FertigungsplanNr.*;
import static fertigungskomponente.FertigungsauftragNr.*;
import static persistenz.DatabaseConnection.*;

public class Fertigungverwalter {
	private IPersistenzService pServ;

	public Fertigungverwalter(IPersistenzService persServ) {
		this.pServ = persServ;
	}


    public FertigungsplanNr erzeugeFertigungsplan(Fertigungsplan p) throws TechnicalException {
        return fertigungsplanNr(pServ.create(FERTIGUNGSPLAN, p.toStringRep()));
    }

    public FertigungsauftragNr erzeugeFertigungsauftragAusAuftrag(AuftragsNr n, FertigungsplanNr fpNr) throws TechnicalException {
            return fertigungsauftragNr(pServ.create(FERTIGUNGSAUFTRAG, fpNr.toString()));
    }

    public FertigungsauftragNr erzeugeLeerenplanAuftrag() throws TechnicalException {
        String fpStr = fertigungsPlan(new HashMap<BauteilNr, Integer>()).toStringRep();
        FertigungsplanNr fpNr = fertigungsplanNr(pServ.create(FERTIGUNGSPLAN, fpStr));
        return fertigungsauftragNr(pServ.create(FERTIGUNGSAUFTRAG, fpNr.toString()));
    }

    public Fertigungsplan holeFertigungsplanzumFertigungsauftrag(FertigungsauftragNr fNr) throws NotFoundException, TechnicalException {
        String fPlanNrStr = pServ.read(FERTIGUNGSAUFTRAG, fNr.getNR());
        String fPlanStr = pServ.read(FERTIGUNGSPLAN, Integer.parseInt(fPlanNrStr));
        Fertigungsplan fPlan = Fertigungsplan.fromString(fPlanStr);
        return fPlan;
    }
}

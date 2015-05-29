package auftragskomponente;

import angebotskomponente.AngebotsNr;
import materialkomponente.BauteilNr;
import persistenz.IPersistenzService;
import utilities.NotFoundException;
import utilities.TechnicalException;

import static materialkomponente.BauteilNr.bauteilNr;
import static persistenz.DatabaseConnection.*;
import static java.lang.Integer.parseInt;

public class Auftragverwalter {
	private IPersistenzService pServ;

	public Auftragverwalter(IPersistenzService persServ) {
		this.pServ = persServ;
	}

    public AuftragsNr erzeugeAuftragAusAngebot(AngebotsNr n) throws NotFoundException, TechnicalException {
        String rep = pServ.read(ANGEBOT, n.getNR());
        pServ.delete(ANGEBOT, n.getNR());
        int afNr = pServ.create(AUFTRAG, rep);
        return AuftragsNr.auftragsNr(afNr);
    }

    public BauteilNr getAuftragBauteilNr(AuftragsNr n) throws NotFoundException, TechnicalException {
        String s = pServ.read(AUFTRAG, n.getNR());
        return bauteilNr(parseInt(s.split(",")[1]));
    }

    public Integer getAuftragBauteileAnzahl(AuftragsNr n) throws NotFoundException, TechnicalException {
        String s = pServ.read(AUFTRAG, n.getNR());
        return parseInt(s.split(",")[2]);
    }
}

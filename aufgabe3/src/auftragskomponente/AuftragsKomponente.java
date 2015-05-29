package auftragskomponente;

import angebotskomponente.AngebotsNr;
import fertigungskomponente.FertigungsauftragNr;
import fertigungskomponente.IFertigungServicesFuerAuftrag;
import materialkomponente.BauteilNr;
import persistenz.IPersistenzService;
import utilities.KeineInventurAtomarerBauteileException;
import utilities.NotFoundException;
import utilities.TechnicalException;

import java.util.ArrayList;
import java.util.List;

public class AuftragsKomponente implements IAuftragServicesFuerCallCenterUI, IAuftragsvermittlungVonFertigung{

	private Auftragverwalter afVerw = null;
    private IFertigungServicesFuerAuftrag fServ = null;
    public List<FertigungsauftragNr> fertiggestellteFertigungsauftraege = new ArrayList<>();
	public AuftragsKomponente(IPersistenzService pServ, IFertigungServicesFuerAuftrag fServ) {
        afVerw = new Auftragverwalter(pServ);
        this.fServ = fServ;
	}

    // Anwendungsf��lle
    @Override
    public AuftragsNr erstelleAuftrag(AngebotsNr nr) throws NotFoundException, TechnicalException {
        return afVerw.erzeugeAuftragAusAngebot(nr);
    }

    public FertigungsauftragNr fertigeAn(AuftragsNr anr) throws TechnicalException, KeineInventurAtomarerBauteileException, NotFoundException {
        return fServ.fertigeAn(anr);
    }

    @Override
    public void stelleFertig(FertigungsauftragNr fnr) {
        System.out.println("Fertigungsauftrag " + fnr + " fertiggestellt.");
        fertiggestellteFertigungsauftraege.add(fnr);

    }

    @Override
    public BauteilNr getAuftragBauteilNr(AuftragsNr n) throws NotFoundException, TechnicalException {
        return afVerw.getAuftragBauteilNr(n);
    }
    @Override
    public Integer getAuftragBauteileAnzahl(AuftragsNr n) throws NotFoundException, TechnicalException {
        return afVerw.getAuftragBauteileAnzahl(n);
    }
}

package fassade;

import auftragskomponente.AuftragsKomponente;
import auftragskomponente.IAuftragServicesFuerCallCenterUI;
import auftragskomponente.IAuftragsvermittlungVonFertigung;
import fertigungskomponente.FertigungsKomponente;
import fertigungskomponente.IFertigungServicesFuerAuftrag;
import materialkomponente.IMaterialServicesFuerFertigung;
import materialkomponente.MaterialKomponente;
import persistenz.DatabaseConnection;
import persistenz.IPersistenzService;
import utilities.TechnicalException;

public class UserInterface {
    IPersistenzService perServ;
    public IAuftragServicesFuerCallCenterUI afServ;
    public IFertigungServicesFuerAuftrag fServ;
    IMaterialServicesFuerFertigung mServ;
    IAuftragsvermittlungVonFertigung afVerm;
    public IFertigungsUIServicesFuerFertigung fuiServ;
    
	public UserInterface() throws TechnicalException {
		this.perServ = new DatabaseConnection();
        this.mServ = new MaterialKomponente(perServ);
        fuiServ = new FertigungsUI();
        this.fServ = new FertigungsKomponente(perServ, mServ, fuiServ);
		AuftragsKomponente afK = new AuftragsKomponente(perServ, fServ);
        this.afVerm = afK;
        fServ.vermittleAufraegeAn(afVerm);
        new CallCenterUI(afServ);
	}
}

package fassade;

import angebotskomponente.AngebotsNr;
import auftragskomponente.AuftragsNr;
import auftragskomponente.IAuftragServicesFuerCallCenterUI;
import utilities.NotFoundException;
import utilities.TechnicalException;

public class CallCenterUI implements IUserInterfaceServicesFuerCallCenterUI {
	
    IAuftragServicesFuerCallCenterUI afServ;
    
    public CallCenterUI(IAuftragServicesFuerCallCenterUI afServ) {
        this.afServ = afServ;
    }

    @Override
    public AuftragsNr erstelleAuftrag(AngebotsNr anr) throws NotFoundException, TechnicalException {
        return afServ.erstelleAuftrag(anr);
    }
}

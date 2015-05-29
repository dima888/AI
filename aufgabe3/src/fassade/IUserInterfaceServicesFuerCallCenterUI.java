package fassade;

import angebotskomponente.AngebotsNr;
import auftragskomponente.AuftragsNr;
import utilities.NotFoundException;
import utilities.TechnicalException;

public interface IUserInterfaceServicesFuerCallCenterUI {
	public AuftragsNr erstelleAuftrag(AngebotsNr anr) throws NotFoundException, TechnicalException;
}

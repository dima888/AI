import static angebotskomponente.AngebotsNr.angebotsNr;
import static persistenz.DatabaseConnection.ANGEBOT;
import static persistenz.DatabaseConnection.AUFTRAG;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import angebotskomponente.AngebotsNr;
import auftragskomponente.AuftragsNr;
import auftragskomponente.Auftragverwalter;
import fassade.IUserInterfaceServicesFuerCallCenterUI;
import fassade.UserInterface;
import persistenz.DatabaseConnection;
import persistenz.IPersistenzService;
import utilities.NotFoundException;
import utilities.TechnicalException;

public class Main {
    public static void main(String[] args) throws Exception {
        //UserInterface ui = new UserInterface();
        //ui.afServ.erstelleAuftrag(AngebotsNr.angebotsNr(15));
    	
    	IPersistenzService pServ;
        Auftragverwalter afVerwalt1;
        String angS;
        int angNr = 0;

            pServ = new DatabaseConnection();
            afVerwalt1 = new Auftragverwalter(pServ);

            // Angebot in Datenbank erzeugen
            // 15 ist die KundenNr
            // 3 ist die BauteilNr
            // 7 ist die Anzahl der angebotenen Bauteile
            // 14.2 sind die Kosten in Euro
            angS = "15,3,7,14.2";
            angNr = pServ.create(ANGEBOT, angS);

            // Einen Auftrag aus dem Angebot erzeugen
            AngebotsNr an = angebotsNr(angNr);
            AuftragsNr aufNr = afVerwalt1.erzeugeAuftragAusAngebot(an);

            // Aus der Datenbank lesen und auf gleichheit pr��fen
            String s = pServ.read(AUFTRAG, aufNr.getNR());
            Assert.assertEquals(s, angS);

            // das Angebot sollte nicht mehr existieren
            boolean thrown = false;
            try {
                pServ.read(ANGEBOT, angNr);
            }
            catch (NotFoundException e) {
                thrown = true;
            }  	
    }
}

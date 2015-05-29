package test;

import static angebotskomponente.AngebotsNr.angebotsNr;

import angebotskomponente.AngebotsNr;
import auftragskomponente.AuftragsNr;
import auftragskomponente.Auftragverwalter;
import persistenz.DatabaseConnection;
import persistenz.IPersistenzService;
import utilities.NotFoundException;
import utilities.TechnicalException;
import org.junit.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static persistenz.DatabaseConnection.ANGEBOT;
import static persistenz.DatabaseConnection.AUFTRAG;

public class AuftragsKomponenteTest extends TestCase {
    IPersistenzService pServ;
    Auftragverwalter afVerwalt1;
    String angS;
    int angNr = 0;
    @Before
    public void setUp() throws Exception {
        pServ = new DatabaseConnection();
        afVerwalt1 = new Auftragverwalter(pServ);

        // Angebot in Datenbank erzeugen
        // 15 ist die KundenNr
        // 3 ist die BauteilNr
        // 7 ist die Anzahl der angebotenen Bauteile
        // 14.2 sind die Kosten in Euro
        angS = "15,3,7,14.2";
        angNr = pServ.create(ANGEBOT, angS);
    }

    @After
    public void tearDown() {
        pServ = null;
        afVerwalt1 = null;
        angS = null;
        angNr = 0;
    }

    @Test
    public void testerzeugeAuftragAusAngebot() throws TechnicalException, NotFoundException {

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
        assertTrue(thrown);
    }
}
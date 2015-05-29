package test;

import angebotskomponente.AngebotsNr;
import auftragskomponente.AuftragsKomponente;
import auftragskomponente.AuftragsNr;
import static persistenz.DatabaseConnection.*;
import static angebotskomponente.AngebotsNr.angebotsNr;
import fassade.FertigungsUI;
import fertigungskomponente.*;
import materialkomponente.BauteilNr;
import materialkomponente.IMaterialServicesFuerFertigung;
import materialkomponente.MaterialKomponente;
import persistenz.DatabaseConnection;
import persistenz.IPersistenzService;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemTest extends TestCase {
    IFertigungServicesFuerAuftrag fServ;
    IPersistenzService pServ;
    IMaterialServicesFuerFertigung mServ;
    AuftragsKomponente aKomp;
    IFertigungVermittlungVonFertigungsUI fVermFui;
    FertigungsUI fUI;
    FertigungsKomponente fert;
    int b1 = 0, b2 = 0, bKomplex = 0;
    AuftragsNr afNr;
    AngebotsNr anNr;

    @Before
    public void setUp() throws Exception {
        pServ = new DatabaseConnection();
        mServ = new MaterialKomponente(pServ);
        fUI = new FertigungsUI();
        fert = new FertigungsKomponente(pServ, mServ, fUI);
        fServ = fert;
        fVermFui = fert;
        aKomp = new AuftragsKomponente(pServ, fServ);
        fServ.vermittleAufraegeAn(aKomp);

        // Bauteile
        b1 = pServ.create(BAUTEIL, "false,-1");
        b2 = pServ.create(BAUTEIL, "false,-1");
        String s = String.format( "true,5,%d,%d,%d",b1,b1,b2);
        bKomplex = pServ.create(BAUTEIL,s);

        // Auftr��ge
        anNr = angebotsNr(pServ.create(ANGEBOT, "15," + bKomplex + ",7,14.2"));
    }

    @After
    public void tearDown() throws Exception {
        fServ = null;
        mServ = null;
        fUI = null;
        pServ = null;
        afNr = null;
        anNr = null;
        b1 = b2 = bKomplex = 0;
    }

    @Test
    public void testAuftragserstellungUndFertigung() throws Exception {
        // Auftrag aus einem Angebot erstellen
        aKomp.erstelleAuftrag(anNr);

        // Wurde aus dem Angebot ein Auftrag?
        String s = pServ.read(AUFTRAG, 0);
        assertEquals(s, "15,2,7,14.2");

        // Auftrag erteilen
        fServ.fertigeAn(AuftragsNr.auftragsNr(0));

        // hat die FertiugnsUI einen Auftrag erhalten?
        FertigungsplanNr fpNr = fUI.erhalteneAuftraege.get(0);
        String fPlan_str = pServ.read(FERTIGUNGSPLAN, fpNr.getNR());
        assertEquals(fPlan_str, "0,-2,1,-1,2,2");
        // es sollen zwei von b1 und eins von b2 verbraucht werden.
        // daf��r entstehen zwei von bKomplex.

        // jetzt sagen wir der Fertigung es sei erledigt.
        fVermFui.stelleFertig(FertigungsauftragNr.fertigungsauftragNr(0));

        // jetzt sollte es 7 komplexe Bauteile geben.
        int anz = mServ.zeigeInventar(BauteilNr.bauteilNr(bKomplex));
        assertEquals(anz, 7);

        // die Fertigungs ist erledigt, wenn die Auftragskomponente
        // mit einer Fertigstellung benachrichtigt wurde
        FertigungsauftragNr erwartet = FertigungsauftragNr.fertigungsauftragNr(0);
        FertigungsauftragNr erhalten = aKomp.fertiggestellteFertigungsauftraege.get(0);
        assertEquals(erwartet, erhalten);
    }
}

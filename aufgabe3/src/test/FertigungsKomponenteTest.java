package test;

import angebotskomponente.AngebotsNr;
import auftragskomponente.AuftragsKomponente;
import auftragskomponente.AuftragsNr;
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

import static angebotskomponente.AngebotsNr.angebotsNr;
import static persistenz.DatabaseConnection.FERTIGUNGSPLAN;

public class FertigungsKomponenteTest extends TestCase {
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
        b1 = pServ.create(DatabaseConnection.BAUTEIL, "false,-1");
        b2 = pServ.create(DatabaseConnection.BAUTEIL, "false,-1");
        String s = String.format( "true,5,%d,%d,%d",b1,b1,b2);
        bKomplex = pServ.create(DatabaseConnection.BAUTEIL,s);

        // Auftr��ge
        anNr = angebotsNr(pServ.create(DatabaseConnection.ANGEBOT, "15," + bKomplex + ",7,14.2"));
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
    public void testFertigeAn() throws Exception {
        fServ.fertigeAn(AuftragsNr.auftragsNr(0));

        FertigungsplanNr fpNr = fUI.erhalteneAuftraege.get(0);
        String fPlan_str = pServ.read(FERTIGUNGSPLAN, fpNr.getNR());
        assertEquals(fPlan_str, "0,-2,1,-1,2,2");

        fVermFui.stelleFertig(FertigungsauftragNr.fertigungsauftragNr(0));

        int anz = mServ.zeigeInventar(BauteilNr.bauteilNr(bKomplex));
        assertEquals(anz, 7);

        FertigungsauftragNr erwartet = FertigungsauftragNr.fertigungsauftragNr(0);
        FertigungsauftragNr erhalten = aKomp.fertiggestellteFertigungsauftraege.get(0);
        assertEquals(erwartet, erhalten);
    }
}

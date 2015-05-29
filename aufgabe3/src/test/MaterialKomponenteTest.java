package test;

import static materialkomponente.BauteilNr.bauteilNr;

import materialkomponente.BauteilNr;
import materialkomponente.IMaterialServicesFuerFertigung;
import materialkomponente.MaterialKomponente;
import persistenz.DatabaseConnection;
import persistenz.IPersistenzService;
import utilities.KeineInventurAtomarerBauteileException;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MaterialKomponenteTest extends TestCase {
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    IPersistenzService pServ;
    IMaterialServicesFuerFertigung mServ;
    int b1 = 0;
    int b2 = 0;
    int bKomplex = 0;

    @Before
    public void setUp() throws Exception {
        pServ = new DatabaseConnection();
        mServ = new MaterialKomponente(pServ);

        b1 = pServ.create(DatabaseConnection.BAUTEIL, "false,-1");
        b2 = pServ.create(DatabaseConnection.BAUTEIL, "false,-1");
        // das komplexe Bauteil besteht aus zwei mal b1 und einmal b2
        String s = String.format( "true,5,%d,%d,%d",b1,b1,b2);
        // true,5,0,0,1
        bKomplex = pServ.create(DatabaseConnection.BAUTEIL,s);
    }

    @After
    public void tearDown() throws Exception {
        pServ = null;
        mServ = null;
        b1 = b2 = bKomplex = 0;
    }


    @Test
    public void testZeigeInventar() throws Exception {
        int anz = mServ.zeigeInventar(bauteilNr(bKomplex));
        assertEquals(anz, 5);
    }

    @Test
    public void testZeigeInventarAtomareBauteile() {
        try {
            mServ.zeigeInventar(bauteilNr(b1));
        }
        catch (KeineInventurAtomarerBauteileException e) {
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testIstKomplexesBauteil() throws Exception {
        assertFalse(mServ.istKomplexesBauteil(bauteilNr(b1)));
        assertFalse(mServ.istKomplexesBauteil(bauteilNr(b2)));
        assertTrue(mServ.istKomplexesBauteil(bauteilNr(bKomplex)));
    }

    @Test
    public void testBestandTeilevon() throws Exception {
        List<BauteilNr> bauteile = mServ.bestandTeilevon(bauteilNr(bKomplex));
        assertEquals(new HashSet<>(bauteile),
                new HashSet<BauteilNr>(Arrays.asList(bauteilNr(b1), bauteilNr(b1), bauteilNr(b2))));
    }

    @Test
    public void testErzeuge() throws Exception {
        int dazu = 6;
        BauteilNr btn = bauteilNr(bKomplex);
        int vorher = mServ.zeigeInventar(btn);
        mServ.erzeuge(btn, dazu);
        int nachher = mServ.zeigeInventar(btn);
        assertEquals(nachher, vorher + dazu);
    }

    @Test
    public void testErzeugeAtomareBauteile() throws Exception {
        try {
            BauteilNr btn = bauteilNr(b1);
            mServ.erzeuge(btn, 1);
        }
        catch (KeineInventurAtomarerBauteileException e) {

        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testVerbrauche() throws Exception {
        int genommen = 2;
        BauteilNr btn = bauteilNr(bKomplex);
        int vorher = mServ.zeigeInventar(btn);
        mServ.verbrauche(btn, genommen);
        int nachher = mServ.zeigeInventar(btn);
        assertEquals(nachher, vorher - genommen);
    }
}

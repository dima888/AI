package materialkomponente;

import persistenz.IPersistenzService;
import utilities.KeineInventurAtomarerBauteileException;
import utilities.NotFoundException;
import utilities.TechnicalException;

import java.util.List;

public class MaterialKomponente implements IMaterialServicesFuerFertigung {

	private Materialverwalter mVerw = null;

	public MaterialKomponente(IPersistenzService pServ) {
        mVerw = new Materialverwalter(pServ);
	}

    // Systemoperationen
    @Override
    public int zeigeInventar(BauteilNr btn) throws KeineInventurAtomarerBauteileException, NotFoundException, TechnicalException {
        if(this.istKomplexesBauteil(btn)) {
            return mVerw.zeigeInventar(btn);
        }
        KeineInventurAtomarerBauteileException.throwKeineInventurAtomarerBauteileException();
        return -1;
    }

    @Override
    public boolean istKomplexesBauteil(BauteilNr btn) throws NotFoundException, TechnicalException {
        return mVerw.istKomplexesBauteil(btn);
    }

    @Override
    public List<BauteilNr> bestandTeilevon(BauteilNr btn) throws NotFoundException, TechnicalException {
        return mVerw.bestandTeilevon(btn);
    }

    @Override
    public void erzeuge(BauteilNr btn, int anzahl) throws NotFoundException, TechnicalException, KeineInventurAtomarerBauteileException {
        // nur komplexe Bauteile k��nnen gebaut werden.
        // atomare Bauteile sind dgegen stets vorhanden
        if (mVerw.istKomplexesBauteil(btn)) {
            mVerw.updateBy(btn, anzahl);
        }
        else {
            KeineInventurAtomarerBauteileException.throwKeineInventurAtomarerBauteileException();
        }

    }

    @Override
    public void verbrauche(BauteilNr btn, int anzahl) throws NotFoundException, TechnicalException {
        mVerw.updateBy(btn, -anzahl);
    }
}

package materialkomponente;

import persistenz.IPersistenzService;
import utilities.NotFoundException;
import utilities.TechnicalException;

import static persistenz.DatabaseConnection.*;

import java.util.List;

public class Materialverwalter {
	private IPersistenzService pServ;

	public Materialverwalter(IPersistenzService persServ) {
		this.pServ = persServ;
	}

    public int zeigeInventar(BauteilNr btn) throws NotFoundException, TechnicalException {
        int i = readBauteil(btn).getInventarAnzahl();
        return Integer.valueOf(i);
    }

    public boolean istKomplexesBauteil(BauteilNr btn) throws NotFoundException, TechnicalException {
        return readBauteil(btn).getIstKomplex();
    }

    public List<BauteilNr> bestandTeilevon(BauteilNr btn) throws NotFoundException, TechnicalException {
        return readBauteil(btn).getVerbrauch();
    }

    public void updateBy(BauteilNr btn, int anzahl) throws NotFoundException, TechnicalException {
        Bauteil neu = readBauteil(btn).asAnzahl(anzahl);
        pServ.update(BAUTEIL, btn.getNR(), neu.toStringRep());
    }

    public Bauteil readBauteil(BauteilNr btn) throws NotFoundException, TechnicalException {
        return Bauteil.fromString(pServ.read(BAUTEIL, btn.getNR()));
    }
}

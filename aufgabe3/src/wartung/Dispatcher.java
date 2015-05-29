package wartung;

import angebotskomponente.AngebotsNr;
import auftragskomponente.AuftragsNr;
import auftragskomponente.IAuftragServicesFuerCallCenterUI;
import mps.MPSConnector;
import utilities.NotFoundException;
import utilities.TechnicalException;

import java.io.IOException;

/**
 * Created by Swaneet on 03.12.2014.
 */
public class Dispatcher implements IAuftragServicesFuerCallCenterUI {
    Monitor monitor;
    public Dispatcher() throws IOException {
        DashboardGUI gui = new DashboardGUI();
        System.out.println("Dispatcher: DashBoardGUI erzeugt.");
        this.monitor = new Monitor(gui);
        monitor.start();
        System.out.println("Dispatcher: Monitor erzeugt und gestartet.");
    }

    private MPSConnector getAvailibleMPS() throws TechnicalException{
        System.out.println("Dispaatcher: Searching for availible MPS...");
        int port = monitor.getFirstMPSport();
        System.out.println("Dispatcher: MPS ["+port+"]");
        return new MPSConnector(port);
    }

    // * Dispatcher, welcher jeweils an dem ersten freien Systmem die Anfragen abschickt.
    @Override
    public AuftragsNr erstelleAuftrag(AngebotsNr nr) throws NotFoundException, TechnicalException {
        MPSConnector mps = getAvailibleMPS();
        return mps.erstelleAuftrag(nr);
    }
}

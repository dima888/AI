package mps;

import auftragskomponente.AuftragsKomponente;
import auftragskomponente.IAuftragServicesFuerCallCenterUI;
import auftragskomponente.IAuftragsvermittlungVonFertigung;
import fassade.FertigungsUI;
import fassade.IFertigungsUIServicesFuerFertigung;
import fertigungskomponente.FertigungsKomponente;
import fertigungskomponente.IFertigungServicesFuerAuftrag;
import materialkomponente.IMaterialServicesFuerFertigung;
import materialkomponente.MaterialKomponente;
import persistenz.DatabaseConnection;
import persistenz.DatabaseConnector;
import persistenz.DatabaseServer;
import persistenz.IPersistenzService;

import static wartung.Monitor.MONITOR_LISTENER_PORT;

/**
 * Created by Swaneet on 04.12.2014.
 */

// this is the main method for creating an entire MPS system.
public class MPS {
    IAuftragServicesFuerCallCenterUI afServ;
    public static int MPS_BASE_PORT = 50000;//9300;
    static boolean INTEGRATED = true;   // set to true, to integrate it with other processes.
    public MPS(int num) throws Exception {
        // Konnektor zur Persistenz
        int port = DatabaseServer.DB_PORT;
        IPersistenzService pServ;
        if (INTEGRATED) {
            pServ = new DatabaseConnector(port); // korrekte Version
            System.out.println("MPS: Verbindung zur DB aufgebaut.");
        }
        else {
            pServ = new DatabaseConnection();// Version zum testen
            System.out.println("DEBUG: Using direct Database Connection.");
        }
        // MPS starten
        IMaterialServicesFuerFertigung mServ = new MaterialKomponente(pServ);
        IFertigungsUIServicesFuerFertigung fuiServ = new FertigungsUI();
        IFertigungServicesFuerAuftrag fServ = new FertigungsKomponente(pServ, mServ, fuiServ);
        AuftragsKomponente afK = new AuftragsKomponente(pServ, fServ);
        IAuftragsvermittlungVonFertigung afVerm = afK;
        afServ = afK;
        fServ.vermittleAufraegeAn(afVerm);

        // Beispielangebot erzeugen:
        int anr = pServ.create(DatabaseConnection.ANGEBOT,"15,2,7,14.2");
        System.out.println("beispielangebot erzeugt ["+anr+"]");

        MPSReporter mpsReporter = null;
        // I am Alive Thread starten
        if (INTEGRATED) {
            mpsReporter = new MPSReporter(num, MONITOR_LISTENER_PORT);
            mpsReporter.start();
        }
        // MPS Skeleton starten. auf dem angegebenen Port und unter der Verwendung der angegebenen Services.
        new MPSSkeleton(MPS_BASE_PORT+num, afServ, mpsReporter);

    }
}

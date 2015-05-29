package mps;

import angebotskomponente.AngebotsNr;
import auftragskomponente.AuftragsNr;
import auftragskomponente.IAuftragServicesFuerCallCenterUI;
import utilities.NotFoundException;
import utilities.TechnicalException;
import static utilities.NotFoundException.NFEX;
import static utilities.TechnicalException.TECHEX;
import static utilities.Constants.*;

/**
 * Created by Swaneet on 05.12.2014.
 */
import static java.lang.Integer.*;
import static angebotskomponente.AngebotsNr.*;
public class Processor {

    public static String ERSTELLE_AUFTRAG = "erstelleAuftrag";

    IAuftragServicesFuerCallCenterUI service;
    MPSReporter reporter;
    public Processor(IAuftragServicesFuerCallCenterUI afServ, MPSReporter mpsReporter) {
        this.service = afServ;
        this.reporter = mpsReporter;
    }

    public String processCommand(String req) {
        System.out.println("Procesor: Processing Command: " + req);

        // AuftragsNr erstelleAuftrag(AngebotsNr nr) throws NotFoundException, TechnicalException;
        // Input: "erstelleAuftrag;15"
        // wird umgewandelt in
        // Output: "23" oder "NotFoundException" oder "TechnicalException"

        String resp = "void";
        if (req.startsWith(ERSTELLE_AUFTRAG)) {
            reporter.countUsage(ERSTELLE_AUFTRAG);
            int n = parseInt(req.split(SEP)[1]);
            AngebotsNr nr = angebotsNr(n);
            try {
                AuftragsNr aNr = service.erstelleAuftrag(nr);
                resp = aNr.toString();
            } catch (NotFoundException e) {
                resp = NFEX + SEP + e.getMessage();
            } catch (TechnicalException e) {
                resp = TECHEX + SEP + e.getMessage();
            }
        }
        else {
            resp = "Processor: Command not found: " + resp;
        }
        return resp;
    }
}

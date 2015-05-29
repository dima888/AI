package mps;

import angebotskomponente.AngebotsNr;
import auftragskomponente.AuftragsNr;
import auftragskomponente.IAuftragServicesFuerCallCenterUI;
import persistenz.IPersistenzService;
import utilities.NotFoundException;
import utilities.TechnicalException;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

import static utilities.NotFoundException.*;
import static utilities.TechnicalException.*;
import static utilities.Constants.*;
import static mps.Processor.ERSTELLE_AUFTRAG;
import static java.lang.Integer.parseInt;
import static auftragskomponente.AuftragsNr.auftragsNr;
import static angebotskomponente.AngebotsNr.angebotsNr;
/**
 * Created by Swaneet on 04.12.2014.
 */
public class MPSConnector implements IAuftragServicesFuerCallCenterUI {
    int port;
    Socket mpssocket;
    InputStream inputStream;
    OutputStream outputStream;
    BufferedReader reader;
    DataOutputStream writer;
    // simulates the MPS system from inside the Dispatcher.
    // needs the port on which the MPS server is listening.

    public MPSConnector(int port) throws TechnicalException {
        try {
            this.port = port;
            this.mpssocket = new Socket("localhost", port);
            inputStream = mpssocket.getInputStream();
            outputStream = mpssocket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            writer = new DataOutputStream(outputStream);
        }catch (IOException e) {
            throwNewTechnicalException(e.getMessage());
        }
    }

    @Override
    public AuftragsNr erstelleAuftrag(AngebotsNr nr) throws NotFoundException, TechnicalException {
        String req = ERSTELLE_AUFTRAG + SEP + nr.toString();
        try {
            send(req);
            String resp = receive();

            if(resp.startsWith(TECHEX)){
                throwNewTechnicalException(resp);
            }
            if(resp.startsWith(NFEX)){
                throwNotFoundException();
            }
            AuftragsNr aNr = auftragsNr(parseInt(resp));
            return aNr;
        } catch (IOException e){
            e.printStackTrace();
            TechnicalException.throwNewTechnicalException(e.getMessage());
        }
        return null;
    }

    private void send(String request) throws IOException {
        writer.writeBytes(request + "\n");
    }
    private String receive() throws IOException {
        return reader.readLine();
    }
}

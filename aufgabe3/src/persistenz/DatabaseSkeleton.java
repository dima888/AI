package persistenz;

import utilities.NotFoundException;
import utilities.TechnicalException;

import java.net.Socket;
import java.io.*;
import static java.lang.Integer.parseInt;

import static utilities.Constants.SEP;
import static persistenz.DatabaseConnector.*;
import static utilities.TechnicalException.TECHEX;
import static utilities.NotFoundException.NFEX;

public class DatabaseSkeleton extends Thread {
    private Socket socket;
    private BufferedReader inStream;
    private DataOutputStream outStream;
    private IPersistenzService dbService;

    public DatabaseSkeleton(Socket socket, IPersistenzService dbService) {
        this.socket = socket;
        this.dbService = dbService;
        try {
            this.inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
        }
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                String call = inStream.readLine();
                System.out.println("Database: Erhielt: " + call);
                String resp = respondToCall(call);
                System.out.println("Database: Sendet: " + resp);
                outStream.writeBytes(resp + "\n");
            } catch (IOException ex) {
            }
        }
    }

    private String respondToCall(String call) {
        String resp = "";
        String[] args = call.split(SEP);
        if (call.startsWith(CREATE)) {
            resp = create(args);
        } else if (call.startsWith(READ)) {
            resp = read(args);
        } else if (call.startsWith(UPDATE)) {
            resp = update(args);
        } else if (call.startsWith(DELETE)) {
            resp = delete(args);
        } else {
            resp = "DatabaseSkeleton: Unknown Command: " + call;
        }
        return resp;
    }

    private String create(String[] args) {
        try {
            return "" + dbService.create(args[1], args[2]);
        } catch (TechnicalException e) {
            return TECHEX + SEP + e.getMessage();
        }
    }

    private String read(String[] args) {
        try {
            return dbService.read(args[1], parseInt(args[2]));
        } catch (TechnicalException e) {
            return TECHEX + SEP + e.getMessage();
        } catch (NotFoundException e) {
            return NFEX;
        }
    }

    private String update(String[] args) {
        try {
            dbService.update(args[1], parseInt(args[2]), args[3]);
            return "void";
        } catch (TechnicalException e) {
            return TECHEX + SEP + e.getMessage();
        } catch (NotFoundException e) {
            return NFEX;
        }
    }

    private String delete(String[] args) {
        try {
            dbService.delete(args[1], parseInt(args[2]));
            return "void";
        } catch (TechnicalException e) {
            return TECHEX + SEP + e.getMessage();
        }
    }

    public void shutdown() {
        interrupt();
        try {
            socket.close();
        } catch (IOException ex) {
        }
    }
}

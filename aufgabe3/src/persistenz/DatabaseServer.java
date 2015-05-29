package persistenz;

import utilities.TechnicalException;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseServer extends Thread {
    public final static int DB_PORT = 9500;
    private ServerSocket server;
    private List<DatabaseSkeleton> skeletons = new ArrayList<>();
    private IPersistenzService dbService;

    public DatabaseServer() {
        try {
            this.dbService = new DatabaseConnection();
        } catch (TechnicalException ex) {}
        try {
            this.server = new ServerSocket(DB_PORT);
            System.out.println("DatabaseServer ["+ DatabaseServer.DB_PORT+"]");
        } catch (IOException ex) {}
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                Socket connectionSocket = server.accept();
                DatabaseSkeleton skel = new DatabaseSkeleton(connectionSocket, dbService);
                skeletons.add(skel);
                skel.start();
            } catch (IOException ex) {
                Thread.currentThread().interrupt();
        }
        }
    }

    public void shutdown() {
        for (DatabaseSkeleton skel : skeletons) {
            skel.shutdown();
        }
        interrupt();
        try {
            server.close();
        } catch (IOException ex) {}
    }
}

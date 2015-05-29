package mps;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Swaneet on 05.12.2014.
 */
public class MPSclientAcceptor extends Thread {
    ServerSocket serverSocket;
    int port;
    Processor p;

    // For each MPS Instance the MPS server listens for the Dispatcher to connect and process its requests.

    public MPSclientAcceptor(int skeletonPort, Processor p) {
        this.port = skeletonPort;
        this.p = p;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("MPSServer[" + port+"]");
        } catch (IOException se) {
            System.err.println("MPSServer: Can not start listening on port " + port);
            se.printStackTrace();
            System.exit(-1);
        }
    }

    public void run() {
        while (true) {  // forver keep accepting new clients.
            try {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket, p).start();
                System.out.println("MPSServer: Got New Client.");
            } catch (IOException e) {
                System.out.println("MPSServer: Exception on Client");
                e.printStackTrace();
            }
        }
    }
}

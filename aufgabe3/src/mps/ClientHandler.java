package mps;

import java.io.*;
import java.net.Socket;

/**
 * Created by Swaneet on 05.12.2014.
 */
class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private DataOutputStream out;
    Processor processor;
    // A single Thread for each Client for this MPS. typically, there is only going to be one client. (the dispatcher)

    public ClientHandler(Socket socket, Processor p) {
        this.processor = p;
        this.socket = socket;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void run() {
        try {
            while (true) {
                System.out.println("ClientHandler: Waiting for request.");
                String req = in.readLine();
                System.out.println("ClientHandler: Got: " + req);
                String resp = processor.processCommand(req);
                out.writeBytes(resp+"\n");
            }
        } catch (IOException e) {   // abort on first uncatched Exception
            System.out.println(e);
        }
    }

}

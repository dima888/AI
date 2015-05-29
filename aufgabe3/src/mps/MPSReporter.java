package mps;

//import com.sun.javafx.webkit.KeyCodeMap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static wartung.Monitor.*;
import static utilities.Constants.SEP;
/**
 * Created by Swaneet on 04.12.2014.
 */
public class MPSReporter extends Thread{
    // reports the current status to the monitor.
    DataOutputStream writer;
    Map<String, Integer> usages = new HashMap();
    int wait = 300;
    int num;
    int port;
    public MPSReporter(int num, int port){
        this.num = num;
        this.port = port;
    }

    public void run() {
        try {
            Socket s = null;
            while (s == null) {
                try {
                    s = new Socket("localhost", port);
                } catch (IOException e) {
                    System.out.println("MPSReporter: Failed to connect to Monitor[" + port + "]. Is it running? Retrying in " + wait);
                }
                Thread.sleep(wait);
            }
            writer = new DataOutputStream(s.getOutputStream());
            while (true) {
                send(ALIVE + SEP + num + SEP + usagesToString());   // sends string of the form "ALIVE;0;Usages;erstelleAuftrag,15;bla,16"
                Thread.sleep(ALIVE_INTERVAL_MILLIS);
            }
        }
        catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
    private String usagesToString() {
        String s = "Usages";
        for (Map.Entry<String, Integer> e:usages.entrySet()) {
            s += SEP + e.getKey() + "," + e.getValue();
        }
        return s;
    }

    private void send(String s) throws IOException {
        writer.writeBytes(s + "\n");
    }

    synchronized public void countUsage(String s) {
        if (usages.containsKey(s)) {
            usages.put(s, usages.get(s) + 1);
        }
        else {
            usages.put(s, 1);
        }
    }
}

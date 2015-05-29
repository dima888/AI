package wartung;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static mps.MPS.MPS_BASE_PORT;
import static utilities.Constants.SEP;

public class Monitor extends Thread{
    // * Der Monitor hält sich mit dem Status der Prozesse im Laufenenden, benachrichtigt Dispatcher beim Ausfall
    //    * evtl. sind Monitor  und Dispatcher ein einziger Thread.
    public static int MONITOR_LISTENER_PORT = 9600;
    public static int ALIVE_INTERVAL_MILLIS = 100;
    public static int ALIVE_TIMEOUT_MILLIS= 500;
    public static String ALIVE = "alive";
    ServerSocket serverSocket;
    DashboardGUI gui;
    public static ConcurrentMap<Integer, String> mpssystems = new ConcurrentHashMap<>(); // stores the responses of the reporters
    private static ConcurrentMap<Integer, Integer> uptime = new ConcurrentHashMap<>();
    private static ConcurrentMap<Integer, Integer> downtime = new ConcurrentHashMap<>();
    public Monitor(DashboardGUI gui) {
        this.gui = gui;
        try {
            serverSocket = new ServerSocket(MONITOR_LISTENER_PORT);
            System.out.println("Monitor Listener [" + MONITOR_LISTENER_PORT+"]");
        } catch (IOException se) {
            System.err.println("Monitor Listener: Can not start listening on port " + MONITOR_LISTENER_PORT);
            se.printStackTrace();
            System.exit(-1);
        }
    }

    // we get the state of some MPS system. Upgrade the state.
    synchronized public void recordState(Integer mpsNum, String state) {
        mpssystems.putIfAbsent(mpsNum, state);
        String[] args = state.split(SEP);
        String usage = "";
        // "Alive;1;Usage;erstelleAngebot,15;bla,16"
        // i = 3
        // => "erstelleAngebot,15;bla,16"
        for (int i = 3; i < args.length; i++) {
            usage += SEP + args[i];
        }
        gui.statuses.put(mpsNum, usage);
        gui.refreshServerList(mpssystems.keySet());
        gui.isAlive.put(mpsNum, true);
    }

    synchronized public void deleteMPS(Integer mpsNum) {
        mpssystems.remove(mpsNum);
        gui.isAlive.put(mpsNum,false);
        gui.statuses.put(mpsNum, "NA");
    }

    public int getFirstMPSport() {
        while(noAvailibleSystems()) {
            Thread.yield();
        }
        for(Map.Entry<Integer, String> e:mpssystems.entrySet()) {
            return MPS_BASE_PORT  + e.getKey();
        }
        throw new RuntimeException("blubb");
    }

    synchronized private boolean noAvailibleSystems() {
        return mpssystems.isEmpty();
    }

    public void run() {
        while (true) {  // forver keep accepting new clients.
            try {
                Socket socket = serverSocket.accept();
                new ReporterHandler(socket, this).start();
                System.out.println("Monitor Listener: Got New MPS.");
            } catch (IOException e) {
                System.out.println("Monitor Listener: Exception on MPS");
                e.printStackTrace();
            }
        }
    }
}

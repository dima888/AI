package wartung;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.*;

import static wartung.Monitor.*;

/**
 * Created by Swaneet on 05.12.2014.
 */
class ReporterHandler extends Thread {

    private Monitor monitor;
    private Socket socket;
    private BufferedReader in;
    private DataOutputStream out;
    // A single Thread for each Client for this MPS. typically, there is only going to be one client. (the dispatcher)

    public ReporterHandler(Socket socket, Monitor monitor) {
        this.socket = socket;
        this.monitor = monitor;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void run() {
        // Either wait for the timeout or keep processing the inputs.
        // Using Futures: http://stackoverflow.com/questions/2275443/how-to-timeout-a-thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> ret = null;
        Integer mpsNum = -1;
        try {
            System.out.println("ReporterHandler: Waiting for reports.");
            // handle reports
            while (true) {
                ret = executor.submit(new ReportIteration(in, monitor));
                mpsNum = ret.get(ALIVE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            }
        } catch (TimeoutException e) {
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
        monitor.deleteMPS(mpsNum);
        System.out.println("ReporterHandler: Deleting: " + mpsNum );
    }

}

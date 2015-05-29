package wartung;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.Callable;

import static utilities.Constants.SEP;
import static java.lang.Integer.parseInt;

/**
 * Created by Swaneet on 06.12.2014.
 */
public class ReportIteration implements Callable<Integer>{
    private Monitor monitor;
    private BufferedReader in;

    ReportIteration(BufferedReader in, Monitor monitor) {
        this.monitor = monitor;
        this.in = in;
    }
    @Override
    public Integer call() throws IOException {
        String req = in.readLine();
        System.out.println("ReporterIteration: Got: " + req);
        String[] args = req.split(SEP);
        monitor.recordState(parseInt(args[1]), req);
        return parseInt(args[1]);
    }
}

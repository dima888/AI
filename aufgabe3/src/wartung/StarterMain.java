package wartung;

import java.io.IOException;
import java.net.UnknownHostException;

import wartung.DashboardGUI;
import wartung.Dispatcher;
import wartung.Monitor;
import wartung.ServerManager;

public class StarterMain {
	
	  private static Dispatcher dispatcher = null;
	  private static Monitor monitor = null;
	//private static CallCenterUI callcenterui = null;
	
	
	public static void main(String[] args) throws UnknownHostException, IOException {

		dispatcher = new Dispatcher();
		DashboardGUI dashboard = new DashboardGUI();

     }
}

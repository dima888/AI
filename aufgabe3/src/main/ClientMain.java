package main;

import wartung.Dispatcher;
//import jdk.nashorn.internal.runtime.ECMAException;

/**
 * Created by Swaneet on 06.12.2014.
 */
public class ClientMain {

    // Der letzendliche Client.
    // Startreihenfolge:
        // 1. DatabaseServerMain
        // 2. MPSServerMain
        // 3. ClientMain
    public static void main(String[] args) throws Exception {
        Dispatcher dp = new Dispatcher();
        // hier die weiteren aufrufe ...
        // dp.erstelleAuftrag(0);
    }
}

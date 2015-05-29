package main;

import persistenz.DatabaseServer;

/**
 * Created by Swaneet on 06.12.2014.
 */
public class DatabaseServerMain {
    // the main to start the Database Server
    public static void main(String[] args) throws Exception {
        DatabaseServer db = new DatabaseServer();
        db.start();
    }
}

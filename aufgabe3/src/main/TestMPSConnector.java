package main;

import auftragskomponente.AuftragsNr;
import mps.MPSConnector;
import utilities.NotFoundException;
import utilities.TechnicalException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static angebotskomponente.AngebotsNr.angebotsNr;
import static java.lang.Integer.parseInt;

/**
 * Created by Swaneet on 06.12.2014.
 */
public class TestMPSConnector {
    public static void main(String[] args) throws IOException, TechnicalException {
        MPSConnector mps = new MPSConnector(9302);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Write to test, f.e. \"0\". \"q\" to quit.");
        String s =in.readLine();
        while (! s.equals("q")) {
            try {
                AuftragsNr nr = mps.erstelleAuftrag(angebotsNr(parseInt(s)));
                System.out.println("Response: AuftragsNr: " + nr);
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (TechnicalException e) {
                e.printStackTrace();
            }
            System.out.println("Write to test, f.e. \"0\". \"q\" to quit.");
            s = in.readLine();
        }
    }
}

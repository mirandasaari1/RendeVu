package edu.csumb.anna.rendevu;

import java.util.ArrayList;

/**
 * Created by Anna on 3/25/17.
 */

public class Chaperones {
    private ArrayList<Chaperone> allChaperones;

    public Chaperones() {
        allChaperones = new ArrayList<Chaperone>();
        allChaperones.add(new Chaperone("Anna Pomelov", "(415)283-9158"));
        allChaperones.add(new Chaperone("Miranda Saari", "(303)777-2846"));
        allChaperones.add(new Chaperone("Sal Hernandez", "(409)247-1264"));
    }

    public ArrayList<Chaperone> getAllChaperones() {
        return allChaperones;
    }


    public void addItem(String name, String number) {
        allChaperones.add(new Chaperone(name, number));
    }
}

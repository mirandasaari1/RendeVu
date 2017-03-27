package edu.csumb.anna.rendevu.data;

import java.util.ArrayList;

import edu.csumb.anna.rendevu.data.Chaperone;

/**
 * Created by Anna on 3/25/17.
 */

public class PlannedDates {
    private ArrayList<PlannedDate> allPlannedDates;

    public PlannedDates() {
        allPlannedDates = new ArrayList<PlannedDate>();
        allPlannedDates.add(new PlannedDate("Orlando Bloom", "3/15", "7pm", "YogurtLand"));
        allPlannedDates.add(new PlannedDate("George Clooney", "2/13", "6pm", "Gusto's"));
        allPlannedDates.add(new PlannedDate("Ryan Gosling", "4/2", "7:15pm", "Blue Fin"));
        allPlannedDates.add(new PlannedDate("Tom Cruise", "4/1", "9pm", "Alcohol Bar"));
        allPlannedDates.add(new PlannedDate("Miranda Saari", "3/16", "7pm", "P.F. Changs"));

    }

    public ArrayList<PlannedDate> getAllPlannedDates() {
        return allPlannedDates;
    }


    public void addItem(String name, String date, String time, String location) {
        allPlannedDates.add(new PlannedDate(name, date, time, location));
    }
}

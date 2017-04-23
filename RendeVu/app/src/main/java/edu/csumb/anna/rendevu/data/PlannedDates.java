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
        allPlannedDates.add(new PlannedDate("Orlando Bloom", "3/15", "7pm", "9", "YogurtLand", "second date!!", null, null));
        allPlannedDates.add(new PlannedDate("George Clooney", "2/13", "6pm", "3", "Gusto's", "met on tinder, not sure", null, null));
        allPlannedDates.add(new PlannedDate("Ryan Gosling", "4/2", "7:15pm", "8", "Blue Fin", "n/a", null, null));
        allPlannedDates.add(new PlannedDate("Tom Cruise", "4/1", "9pm", "5", "Alcohol Bar", "seems nice", null, null));
        allPlannedDates.add(new PlannedDate("Miranda Saari", "3/16", "7pm", "10","P.F. Changs", "been wanting to go on this one for a long time!", null, null));

    }

    public ArrayList<PlannedDate> getAllPlannedDates() {
        return allPlannedDates;
    }


    public void addItem(String name, String date, String time, String comfort, String location, String information, String startTime, String endTime) {
        allPlannedDates.add(new PlannedDate(name, date, time, comfort, location, information, startTime, endTime));
    }
}

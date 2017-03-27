package edu.csumb.anna.rendevu.data;

/**
 * Created by Anna on 3/26/17.
 */

public class PlannedDate {
    private String dateName;
    private String dateDate;
    private String dateTime;
    private String dateLocation;

    public PlannedDate(String name, String date, String time, String location) {
        dateName = name;
        dateDate = date;
        dateTime = time;
        dateLocation = location;
    }
    public String getName() {
        return dateName;
    }

    public void setName(String name) {
        this.dateName = name;
    }

    public String getDateDate() {
        return dateDate;
    }

    public void setDateDate(String dateDate) {
        this.dateDate = dateDate;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateLocation() {
        return dateLocation;
    }

    public void setDateLocation(String dateLocation) {
        this.dateLocation = dateLocation;
    }
}

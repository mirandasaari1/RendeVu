package edu.csumb.anna.rendevu.data;

/**
 * Created by Anna on 3/26/17.
 */

public class PlannedDate {
    private String dateName;
    private String dateDate;
    private String dateTime;
    private String dateComfort;
    private String dateLocation;
    private String dateInformation;
    private String startTime;
    private String endTime;
    private int id;

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateName() {

        return dateName;
    }

    public int getId() {
        return id;
    }

    public PlannedDate(){

    }
    public PlannedDate(String name, String date, String time, String comfort, String location, String information, String start, String end) {
        dateName = name;
        dateDate = date;
        dateTime = time;
        dateComfort = comfort;
        dateLocation = location;
        dateInformation = information;
        startTime = start;
        endTime = end;
    }

    public PlannedDate(int id, String name, String info, String date, String dateTime){
        this.id = id;
        this.dateName = name;
        this.dateInformation = info;
        this.dateDate = date;
        this.dateTime = dateTime;
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

    public String getDateComfort() { return dateComfort; }

    public void setDateComfort(String dateComfort){this.dateComfort = dateComfort; }

    public String getDateLocation() {
        return dateLocation;
    }

    public void setDateLocation(String dateLocation) {
        this.dateLocation = dateLocation;
    }

    public String getDateInformation() {return dateInformation; }

    public void setDateInformation(String dateInformation) {
        this.dateInformation=dateInformation;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


}

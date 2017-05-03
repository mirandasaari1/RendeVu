package edu.csumb.anna.rendevu.data;

public class Chaperone extends Chaperones {

    private String chaperoneName;
    private String chaperoneNumber;
    private int chaperoneID;

    public Chaperone(){}

    public Chaperone(String n, String n2) {
        this.chaperoneName = n;
        this.chaperoneNumber = n2;
    }

    public Chaperone(String n, String n2, int id) {
        this.chaperoneName = n;
        this.chaperoneNumber = n2;
        this.chaperoneID = id;
    }
    public String getChaperoneName() {
        return this.chaperoneName;
    }

    public void setChaperoneName(String chaperoneName) {
        this.chaperoneName = chaperoneName;
    }

    public String getChaperoneNumber() {
        return this.chaperoneNumber;
    }

    public void setChaperoneNumber(String chaperoneNumber) {
        this.chaperoneNumber = chaperoneNumber;
    }
    public void setChaperoneID(int chaperoneID) {
        this.chaperoneID = chaperoneID;
    }

    public int getChaperoneID() {

        return chaperoneID;
    }
}
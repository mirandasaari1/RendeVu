package edu.csumb.anna.rendevu.data;

public class Chaperone extends Chaperones {

    private String chaperoneName;
    private String chaperoneNumber;

    public Chaperone(){

    }

    public Chaperone(String n, String n2) {
        chaperoneName = n;
        chaperoneNumber = n2;
    }
    public String getChaperoneName() {
        return chaperoneName;
    }

    public void setChaperoneName(String chaperoneName) {
        this.chaperoneName = chaperoneName;
    }

    public String getChaperoneNumber() {
        return chaperoneNumber;
    }

    public void setChaperoneNumber(String chaperoneNumber) {
        this.chaperoneNumber = chaperoneNumber;
    }
}
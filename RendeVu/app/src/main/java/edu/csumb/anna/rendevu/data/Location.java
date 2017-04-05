
package edu.csumb.anna.rendevu.data;
import com.google.gson.annotations.SerializedName;

import edu.csumb.anna.rendevu.StartDateActivity;

/**
 * Created by Anna on 3/28/17.
 */

public class Location {

    @SerializedName("lang")
    private String lang = "";

    @SerializedName("longd")
    private String longd = "";

    @SerializedName("id")
    private String id;

    public Location(){
        lang = "23432423";
        longd = "234234221";
        id = "38383838";
    }

    public String getlang() {
        return lang;
    }

    public void setlang(String lang) {
        this.lang = lang;
    }

    public String getlongd() {
        return longd;
    }

    public void setlongd(String longd) {
        this.longd = longd;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String toString() {
        return lang + " " + longd;
    }
}
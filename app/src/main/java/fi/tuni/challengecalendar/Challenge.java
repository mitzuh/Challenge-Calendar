package fi.tuni.challengecalendar;

import java.util.Date;

public class Challenge {
    String name;
    Date date;

    public Challenge(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        return this.name;
    }
}

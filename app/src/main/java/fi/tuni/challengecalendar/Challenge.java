package fi.tuni.challengecalendar;

import android.os.Parcel;
import android.os.Parcelable;

public class Challenge implements Parcelable, Comparable<Challenge> {
    int id;
    String name;
    String date;

    public Challenge(int id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public Challenge(Parcel in) {
        String[] data = new String[3];

        in.readStringArray(data);

        this.id = Integer.parseInt(data[0]);
        this.name = data[1];
        this.date = data[2];
    }

    public static final Creator<Challenge> CREATOR = new Creator<Challenge>() {
        @Override
        public Challenge createFromParcel(Parcel in) {
            return new Challenge(in);
        }

        @Override
        public Challenge[] newArray(int size) {
            return new Challenge[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {String.valueOf(this.id),
                this.name,
                this.date});
    }

    @Override
    public int compareTo(Challenge o) {
        return this.date.compareTo(o.date);
    }
}

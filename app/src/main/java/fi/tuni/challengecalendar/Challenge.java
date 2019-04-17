package fi.tuni.challengecalendar;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Challenge object -class, with name and deadline.
 */
public class Challenge implements Parcelable, Comparable<Challenge> {
    int id;
    String name;
    String date;

    /**
     * Constructor for Challenge.
     *
     * @param id Unique id, which can be used to get the Challenge.
     * @param name Description for the Challenge.
     * @param date Deadline the user must complete the Challenge before.
     */
    public Challenge(int id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    /**
     * Parceling constructor for the Challenge, which is used to read data
     * from the object.
     *
     * @param in Container for data object reference.
     */
    public Challenge(Parcel in) {
        String[] data = new String[3];

        in.readStringArray(data);

        this.id = Integer.parseInt(data[0]);
        this.name = data[1];
        this.date = data[2];
    }

    /**
     * Creates instance of Parcelable class from a Parcel.
     */
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

    /**
     * Gets the id of the Challenge.
     *
     * @return Challenge id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id for the Challenge.
     *
     * @param id Id to be assigned for the Challenge.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the description of the Challenge.
     *
     * @return Challenge description.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the description for the Challenge.
     *
     * @param name Description to be assigned for the Challenge.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the Challenge deadline.
     *
     * @return Deadline of the Challenge.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the Challenge deadline.
     *
     * @param date Deadline to be assigned for the Challenge.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Converts the Challenge class to its name in a String form, whenever
     * the Challenge is printed.
     *
     * @return Deascription of the Challenge.
     */
    public String toString() {
        return this.name;
    }

    /**
     * Creates bitmask return value.
     *
     * @return A bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flattens object to Parcel.
     *
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {String.valueOf(this.id),
                this.name,
                this.date});
    }

    /**
     * Compares passed Challenge deadline to this Challenge deadline.
     *
     * @param o Passed Challenge object.
     * @return 1 if this Challenge deadline is later than passed Challenges deadline,
     * -1 if the other way around.
     */
    @Override
    public int compareTo(Challenge o) {
        return this.date.compareTo(o.date);
    }
}

package fi.tuni.challengecalendar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Main Activity of the application.
 *
 * <p>
 *     This activity holds the calendar, where user can choose a date for new Challenge.
 *     When user adds a new Challenge, currently selected date is used for the deadline.
 *     User can also move from here to all Challenge view activities, which are: Upcoming,
 *     completed and failed.
 *     Current completion points are also displayed on the bottom of the screen in this activity.
 * </p>
 */
public class MainActivity extends ActionBarActivity {
    TextView textView;
    TextView pointsView;
    CalendarView calendarView;

    /**
     * Initialization of the Activity.
     *
     * @param savedInstanceState Previous state of the application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.dateText);
        pointsView = findViewById(R.id.pointsView);
        calendarView = findViewById(R.id.calendarView);

        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String selectedDate = sdf.format(new Date(calendarView.getDate()));
        textView.setText(selectedDate);

        pointsView.setText("Your total points: " + databaseHandler.getTotalPoints());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String d = String.format("%02d", dayOfMonth);
                String m = String.format("%02d", month+1);
                textView.setText(d + "." + m + "." + year);
            }
        });
    }

    /**
     * Checks if there are outdated challenges after the user returns
     * to the main view.
     */
    @Override
    public void onResume() {
        super.onResume();
        try {
            checkOutdated();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the ChallengeView activity.
     *
     * @param v Clicked Button, which starts the new activity.
     */
    public void showChallenges(View v) {
        Intent i = new Intent(this, ChallengeViewActivity.class);
        startActivity(i);
    }

    /**
     * Moves to AddChallenge activity.
     *
     * <p>
     *     Moves to activity where user can add new Challenge to previously
     *     selected date. Currently selected date on the calendar is passed to
     *     AddChallengeActivity in a bundle.
     * </p>
     *
     * @param v Clicked Button, which starts the new activity.
     */
    public void makeChallenge(View v) {
        Intent i = new Intent(this, AddChallengeActivity.class);
        Bundle b = new Bundle();

        b.putString("date", String.valueOf(textView.getText()));
        i.putExtras(b);

        startActivity(i);
    }

    /**
     * Moves to CompletedView activity.
     *
     * @param v Clicked Button, which starts the new activity.
     */
    public void showCompleted(View v) {
        Intent i = new Intent(this, CompletedViewActivity.class);
        startActivity(i);
    }

    /**
     * Moves to FailedView activity.
     *
     * @param v Clicked Button, which starts the new activity.
     */
    public void showFailed(View v) {
        Intent i = new Intent(this, FailedViewActivity.class);
        startActivity(i);
    }

    /**
     * Compares today Date and challenge deadline Date, and if the deadline
     * has passed, the Challenge is moved from upcoming challenges to failed challenges.
     *
     * @throws ParseException Exception for possible SimpleDateFormat parsing error.
     * Exception is thrown, if the date is in incorrect form.
     */
    public void checkOutdated() throws ParseException {
        List<Challenge> challenges = databaseHandler.getChallenges();

        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        for (int i=0; i<challenges.size(); i++) {
            Challenge c = challenges.get(i);
            Date date = sdf.parse(c.getDate());

            long diff = date.getTime()-new Date().getTime();
            long converted = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            if (converted < 0) {
                markAsFailed(c);
            }
        }
    }

    /**
     * Moves the Challenge from upcoming challenges to failed challenges.
     *
     * <p>
     *     Adds Challenge to failed table and deletes it from the upcoming challenges table.
     * </p>
     *
     * @param c Challenge, which user has failed to complete before its deadline.
     */
    public void markAsFailed(Challenge c) {
        List<Challenge> tempChallenges = databaseHandler.getChallenges();
        int index = c.getId();
        databaseHandler.addFailed(c);
        databaseHandler.deleteChallenge(index, tempChallenges.size());
    }

    /**
     * Returns to device home screen if the Android back-button is pressed.
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

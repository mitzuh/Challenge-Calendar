package fi.tuni.challengecalendar;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Main class for the application
 */
public class MainActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;

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

        databaseHandler = new DatabaseHandler(this);

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
     * Exception is thrown, if the date if in incorrect form.
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
     * @param c Challenge, which user has failed to complete in time.
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

    // MENU

    /**
     * Initializes the menu bar on top of the screen.
     * <p>
     *     Loads the created menu item from the .xml and enables it.
     * </p>
     *
     * @param menu Menu to be initialized.
     * @return True.
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem m = menu.findItem(R.id.settings);
        m.setEnabled(true);
        return true;
    }

    /**
     * Inflates the menu item to be displayed in the menu bar.
     *
     * @param menu Menu to be inflated.
     * @return True.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar, menu);
        return true;
    }

    /**
     * Does action, when menu item is clicked.
     *
     * @param item Clicked menu item.
     * @return False.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        return false;
    }
}

package fi.tuni.challengecalendar;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseHandler(this);

        // Temporal. Will be removed after challenges can be added manually.
        databaseHandler.addChallenge(new Challenge(1, "Test", "11.11.2019"));
        databaseHandler.addChallenge(new Challenge(2, "Test2", "30.03.2019"));

        final TextView textView = findViewById(R.id.dateText);
        final CalendarView calendarView = findViewById(R.id.calendarView);

        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String selectedDate = sdf.format(new Date(calendarView.getDate()));
        textView.setText(selectedDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String d = String.format("%02d", dayOfMonth);
                String m = String.format("%02d", month+1);
                textView.setText(d + "." + m + "." + year);
            }
        });
    }

    public void showChallenges(View v) {
        DatabaseHandler db = new DatabaseHandler(this);

        Intent i = new Intent(this, ChallengeViewActivity.class);
        Bundle b = new Bundle();

        List<Challenge> challenges = db.getChallenges();

        b.putParcelableArrayList("challenges", (ArrayList<? extends Parcelable>) challenges);
        i.putExtras(b);

        startActivity(i);
    }

    public void addChallenge(View v) {

    }
}

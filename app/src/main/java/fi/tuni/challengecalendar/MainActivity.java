package fi.tuni.challengecalendar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;

    TextView textView;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseHandler(this);

        textView = findViewById(R.id.dateText);
        calendarView = findViewById(R.id.calendarView);

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

    @Override
    public void onResume() {
        super.onResume();
        try {
            checkOutdated();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showChallenges(View v) {
        Intent i = new Intent(this, ChallengeViewActivity.class);
        startActivity(i);
    }

    public void makeChallenge(View v) {
        Intent i = new Intent(this, AddChallengeActivity.class);
        Bundle b = new Bundle();

        b.putString("date", String.valueOf(textView.getText()));
        i.putExtras(b);

        startActivity(i);
    }

    public void showCompleted(View v) {
        Intent i = new Intent(this, CompletedViewActivity.class);
        startActivity(i);
    }

    public void showFailed(View v) {
        Intent i = new Intent(this, FailedViewActivity.class);
        startActivity(i);
    }

    public void checkOutdated() throws Exception {
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

    public void markAsFailed(Challenge c) {
        List<Challenge> tempChallenges = databaseHandler.getChallenges();
        int index = c.getId();
        databaseHandler.addFailed(c);
        databaseHandler.deleteChallenge(index, tempChallenges.size());
    }
}

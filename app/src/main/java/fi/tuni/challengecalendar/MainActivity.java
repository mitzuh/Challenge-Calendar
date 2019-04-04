package fi.tuni.challengecalendar;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
}

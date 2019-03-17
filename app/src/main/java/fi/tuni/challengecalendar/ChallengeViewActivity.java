package fi.tuni.challengecalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.Date;

public class ChallengeViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_view);

        Challenge [] challenges = new Challenge[2];
        challenges[0] = new Challenge("Challenge 1", new Date());
        challenges[1] = new Challenge("Challenge 2", new Date());

        ArrayAdapter<Challenge> adapter = new ArrayAdapter<Challenge>(this,
                                            android.R.layout.simple_list_item_1, challenges);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}

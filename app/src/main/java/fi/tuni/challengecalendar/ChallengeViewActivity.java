package fi.tuni.challengecalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class ChallengeViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_view);

        Intent intent = getIntent();
        ArrayList<Challenge> list = (ArrayList<Challenge>) intent.getExtras().get("challenges");

        Challenge [] challenges = new Challenge[list.size()];

        for (int i=0; i< list.size(); i++) {
            challenges[i] = new Challenge(list.get(i).id, list.get(i).name, list.get(i).date);
        }

        ArrayAdapter<Challenge> adapter = new ArrayAdapter<Challenge>(this,
                                            android.R.layout.simple_list_item_1, challenges);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}

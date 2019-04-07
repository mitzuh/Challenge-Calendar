package fi.tuni.challengecalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChallengeViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_view);

        ListView listView = (ListView) findViewById(R.id.listView);

        DatabaseHandler db = new DatabaseHandler(this);
        List<Challenge> list = db.getChallenges();

        ArrayList<Challenge> challenges = new ArrayList<>();
        for (int i=0; i<list.size(); i++) {
            challenges.add(list.get(i));
        }

        ChallengeListAdapter adapter =
                new ChallengeListAdapter(this, R.layout.adapter_view_layout, challenges);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), EditChallengeActivity.class);
                Bundle b = new Bundle();

                b.putParcelable("challenge", challenges.get(position));
                i.putExtras(b);

                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}

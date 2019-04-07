package fi.tuni.challengecalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;

public class ChallengeViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_view);

        DatabaseHandler db = new DatabaseHandler(this);
        List<Challenge> list = db.getChallenges();

        final Challenge [] challenges = new Challenge[list.size()];

        for (int i=0; i< list.size(); i++) {
            challenges[i] = new Challenge(list.get(i).id, list.get(i).name, list.get(i).date);
        }

        ArrayAdapter<Challenge> adapter = new ArrayAdapter<Challenge>(this,
                                            android.R.layout.simple_list_item_1, challenges);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), EditChallengeActivity.class);
                Bundle b = new Bundle();

                b.putParcelable("challenge", challenges[position]);
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

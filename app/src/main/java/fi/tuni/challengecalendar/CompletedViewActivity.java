package fi.tuni.challengecalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for viewing all of the completed Challenges.
 *
 * <p>
 *     Challenge names with their completion dates are displayed in a list.
 * </p>
 */
public class CompletedViewActivity extends AppCompatActivity {

    /**
     * Initialization of the Activity.
     *
     * @param savedInstanceState Previous state of the application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_view);

        DatabaseHandler db = new DatabaseHandler(this);
        List<Challenge> list = db.getCompleted();

        ListView listView = (ListView) findViewById(R.id.completedView);

        ArrayList<Challenge> challenges = new ArrayList<>();
        for (int i=0; i<list.size(); i++) {
            challenges.add(list.get(i));
        }

        ChallengeListAdapter adapter =
                new ChallengeListAdapter(this, R.layout.adapter_view_layout, challenges);
        listView.setAdapter(adapter);
    }

    /**
     * Returns back to Main activity, when the Android back-button is pressed.
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}


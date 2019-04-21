package fi.tuni.challengecalendar;

import android.content.Intent;
import android.os.Bundle;
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
public class CompletedViewActivity extends ActionBarActivity {

    /**
     * Initialization of the Activity.
     *
     * <p>
     *     Gets all the completed Challenges from the database and puts them
     *     in an ArrayList. Then displays the Challenges in form
     *     of the custom array adapter.
     * </p>
     *
     * @param savedInstanceState Previous state of the application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_view);

        List<Challenge> list = databaseHandler.getCompleted();

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


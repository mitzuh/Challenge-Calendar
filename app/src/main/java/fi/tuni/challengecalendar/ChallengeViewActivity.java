package fi.tuni.challengecalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Activity class for viewing the upcoming Challenges.
 *
 * <p>
 *     Challenge descriptions with their deadlines is displayed in a list.
 * </p>
 */
public class ChallengeViewActivity extends ActionBarActivity {

    /**
     * Initialization of the Activity.
     *
     * <p>
     *     Gets all the upcoming Challenges from the database and puts them
     *     in an ArrayList. Then displays the Challenges in form
     *     of the custom array adapter.
     *     Challenges are sorted by their deadline, so the first object in the list is
     *     the nearest, and the last one furthest away from current date.
     * </p>
     *
     * @param savedInstanceState Previous state of the application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_view);

        ListView listView = (ListView) findViewById(R.id.listView);

        List<Challenge> list = databaseHandler.getChallenges();

        Collections.sort(list);

        ArrayList<Challenge> challenges = new ArrayList<>();
        for (int i=0; i<list.size(); i++) {
            challenges.add(list.get(i));
        }

        ChallengeListAdapter adapter =
                new ChallengeListAdapter(this, R.layout.adapter_view_layout, challenges);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Moves to EditChallenge activity with the clicked object data.
             *
             * @param parent AdapterView.
             * @param view Clicked item.
             * @param position Position of the item in the list.
             * @param id Id of the clicked item.
             */
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

    /**
     * Returns back to Main activity, when the Android back-button is pressed.
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}

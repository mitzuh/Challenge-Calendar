package fi.tuni.challengecalendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity class for Challenge completion and deletion.
 */
public class EditChallengeActivity extends ActionBarActivity {
    TextView textView;
    TextView textView2;

    Challenge c;

    /**
     * Initialization of the Activity.
     *
     * @param savedInstanceState Previous state of the application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_challenge);

        Bundle extras = getIntent().getExtras();

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);

        c = extras.getParcelable("challenge");

        textView.setText(c.getName());
        textView2.setText("Completion points: " + String.valueOf(c.getPoints()));
    }

    /**
     * Deletes a Challenge from the database.
     *
     * @param v Clicked Button, that is used to delete Challenge from the database.
     */
    public void deleteChallenge(View v) {
        List<Challenge> tempChallenges = databaseHandler.getChallenges();
        int index = c.getId();
        databaseHandler.deleteChallenge(index, tempChallenges.size());

        Intent intent = new Intent(this, ChallengeViewActivity.class);
        Bundle b = new Bundle();

        List<Challenge> challenges = databaseHandler.getChallenges();

        b.putParcelableArrayList("challenges", (ArrayList<? extends Parcelable>) challenges);
        intent.putExtras(b);

        Toast toast = Toast.makeText(getApplicationContext(),
                "Challenge deleted", Toast.LENGTH_SHORT);
        toast.show();

        startActivity(intent);
    }

    /**
     * Marks the Challenge as completed.
     *
     * <p>
     *      Deletes the Challenge from upcoming challenges and moves it
     *      into Completed Challenges in the database. Also adds points
     *      to the user based on the amount of completion points of the completed Challenge.
     * </p>
     *
     * @param v Clicked Button, that marks the Challenge as completed.
     */
    public void markAsComplete(View v) {
        List<Challenge> tempChallenges = databaseHandler.getChallenges();
        int index = c.getId();
        databaseHandler.addCompleted(c);
        databaseHandler.deleteChallenge(index, tempChallenges.size());

        // Add points to user
        databaseHandler.addPoints(c.getPoints());

        Intent intent = new Intent(this, ChallengeViewActivity.class);
        Bundle b = new Bundle();

        List<Challenge> challenges = databaseHandler.getChallenges();

        b.putParcelableArrayList("challenges", (ArrayList<? extends Parcelable>) challenges);
        intent.putExtras(b);

        Toast toast = Toast.makeText(getApplicationContext(),
                "You got " + c.getPoints() + " points for completing this challenge!", Toast.LENGTH_LONG);
        toast.show();

        startActivity(intent);
    }
}

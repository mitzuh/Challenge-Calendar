package fi.tuni.challengecalendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditChallengeActivity extends AppCompatActivity {
    DatabaseHandler databaseHandler;

    TextView textView;

    Challenge c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_challenge);

        databaseHandler = new DatabaseHandler(this);

        Bundle extras = getIntent().getExtras();

        textView = (TextView) findViewById(R.id.textView);

        c = extras.getParcelable("challenge");

        textView.setText(c.name);
    }

    public void deleteChallenge(View v) {
        List<Challenge> tempChallenges = databaseHandler.getChallenges();
        int index = c.getId();
        databaseHandler.deleteChallenge(index, tempChallenges.size());

        Intent intent = new Intent(this, ChallengeViewActivity.class);
        Bundle b = new Bundle();

        List<Challenge> challenges = databaseHandler.getChallenges();

        b.putParcelableArrayList("challenges", (ArrayList<? extends Parcelable>) challenges);
        intent.putExtras(b);

        startActivity(intent);
    }

    public void markAsComplete(View v) {
        List<Challenge> tempChallenges = databaseHandler.getChallenges();
        int index = c.getId();
        databaseHandler.addCompleted(c);
        databaseHandler.deleteChallenge(index, tempChallenges.size());

        Intent intent = new Intent(this, ChallengeViewActivity.class);
        Bundle b = new Bundle();

        List<Challenge> challenges = databaseHandler.getChallenges();

        b.putParcelableArrayList("challenges", (ArrayList<? extends Parcelable>) challenges);
        intent.putExtras(b);

        startActivity(intent);
    }
}

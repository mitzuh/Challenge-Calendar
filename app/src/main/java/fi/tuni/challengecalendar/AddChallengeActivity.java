package fi.tuni.challengecalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddChallengeActivity extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    EditText editText;

    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);

        databaseHandler = new DatabaseHandler(this);
        editText = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();
        date = intent.getExtras().getString("date");
    }

    public void addChallenge(View v) {
        databaseHandler.addChallenge(new Challenge(databaseHandler.getChallenges().size()+1,
                editText.getText().toString(),
                date));
    }
}

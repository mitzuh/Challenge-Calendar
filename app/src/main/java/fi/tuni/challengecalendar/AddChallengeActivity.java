package fi.tuni.challengecalendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        if (!editText.getText().toString().trim().isEmpty()) {
            databaseHandler.addChallenge(new Challenge(databaseHandler.getChallenges().size()+1,
                    editText.getText().toString(),
                    date));

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Challenge added", Toast.LENGTH_SHORT);
            toast.show();

            editText.setText(null);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Challenge must have a description!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

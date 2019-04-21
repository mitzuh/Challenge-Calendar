package fi.tuni.challengecalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity class for adding Challenges to the user.
 */
public class AddChallengeActivity extends ActionBarActivity {
    EditText editText;
    EditText editText2;

    String date;

    /**
     * Initialization of the Activity.
     *
     * @param savedInstanceState Previous state of the application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);

        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);

        Intent intent = getIntent();
        date = intent.getExtras().getString("date");
    }

    /**
     * Adds new Challenge to the database, when Add Challenge -Button is clicked.
     *
     * <p>
     *     Checks first if the description EditText field is empty. If it is, user
     *     is notified to fill info in there. If it has some text there, the Challenge
     *     is added to the database.
     * </p>
     *
     * @param v Clicked Button, which adds the Challenge to database.
     */
    public void addChallenge(View v) {
        if (!editText.getText().toString().trim().isEmpty()) {
            if (!editText2.getText().toString().trim().isEmpty() &&
                    Integer.parseInt(editText2.getText().toString()) > 0 &&
                    Integer.parseInt(editText2.getText().toString()) <= 5) {

                databaseHandler.addChallenge(new Challenge(databaseHandler.getChallenges().size()+1,
                        editText.getText().toString(),
                        date, Integer.parseInt(editText2.getText().toString())));

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Challenge added", Toast.LENGTH_SHORT);
                toast.show();

                editText.setText(null);
                editText2.setText(null);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Completion points must be between 1 and 5!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Challenge must have a description!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

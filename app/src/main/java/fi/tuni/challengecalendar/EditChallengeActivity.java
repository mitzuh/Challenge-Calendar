package fi.tuni.challengecalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class EditChallengeActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_challenge);

        Bundle extras = getIntent().getExtras();

        textView = (TextView) findViewById(R.id.textView);

        Challenge c = extras.getParcelable("challenge");

        textView.setText(c.name);
    }
}

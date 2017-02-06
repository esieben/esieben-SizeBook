package com.example.esieben_sizebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Activity that prompts user to input a name to create a new profile.
 * Created profile will be initialized with no date or comment, and all measurements set to zero
 */
public class profileCreationActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.esieben_sizebook.ACTIVITY2";

    /**
     * specifies associated view to be displayed with this activity
     * @param savedInstanceState system bundle object passed to method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);
    }

    /**
     * method called to return to MainActivity, passes input data.
     * @see MainActivity
     * @param view value associated with button used to call this method
     */
    public void submitProfile(View view){
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = (EditText) findViewById(R.id.nameField);
        String profileName = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, profileName);
        startActivity(intent);
    }
}

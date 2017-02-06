package com.example.esieben_sizebook;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Defines activity in which user may edit fields of selected profile object, passed from MainActivity
 * @see MainActivity
 * Various EditText fields are linked to allow input data to be collected.
 * EditText fields are initialized with any existing data, allowing the user to view previously
 * entered values. If fields are empty, hints are provided.
 * Limits on character counts and input types are enforced to prevent faulty data
 *
 */
public class profileEditActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.esieben_sizebook.ACTIVITY3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        Intent intent = getIntent();
        Profile p = (Profile) intent.getSerializableExtra("selectedProfile");

        EditText nameEdit = (EditText) findViewById(R.id.nameEdit);
        EditText yearEdit = (EditText) findViewById(R.id.yearEdit);
        EditText monthEdit = (EditText) findViewById(R.id.monthEdit);
        EditText dayEdit = (EditText) findViewById(R.id.dayEdit);
        EditText neckEdit = (EditText) findViewById(R.id.neckEdit);
        EditText bustEdit = (EditText) findViewById(R.id.bustEdit);
        EditText chestEdit = (EditText) findViewById(R.id.chestEdit);
        EditText waistEdit = (EditText) findViewById(R.id.waistEdit);
        EditText hipEdit = (EditText) findViewById(R.id.hipEdit);
        EditText inseamEdit = (EditText) findViewById(R.id.inseamEdit);
        EditText commentEdit = (EditText) findViewById(R.id.commentEdit);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);

        nameEdit.setText(p.getName());
        if (p.getDate() != null) {
            String[] yearMonthDay = p.getDate().split("-");
            yearEdit.setText(yearMonthDay[0]);
            monthEdit.setText(yearMonthDay[1]);
            dayEdit.setText(yearMonthDay[2]);
        }
        if (p.getNeck() != null) { neckEdit.setText(p.getNeck());}
        if(p.getBust() != null) { bustEdit.setText(p.getBust());}
        if(p.getChest() != null) { chestEdit.setText(p.getChest());}
        if(p.getWaist() != null) { waistEdit.setText(p.getWaist());}
        if(p.getHip() != null) { hipEdit.setText(p.getHip());}
        if(p.getInseam() != null) { inseamEdit.setText(p.getInseam());}
        if(p.getComment() != null) { commentEdit.setText(p.getComment());}

    }

    /**
     * passes a "DELETE" comment to MainActivity upon 'delete profile" button click
     * @param view associated view containing button
     */
    public void deleteProfile(View view){
        Intent intent = new Intent(this, MainActivity.class);
        EditText nameEdit = (EditText) findViewById(R.id.nameEdit);
        String profileName = nameEdit.getText().toString();
        String transfer = profileName + "0,,--0,,00,,00,,00,,00,,00,,00,,DELETE";
        intent.putExtra(EXTRA_MESSAGE, transfer);
        startActivity(intent);
    }

    /**
     * passes data input from EditText fields to MainActivity upon "save profile" button click
     * @param view associated view containing button
     */
    public void saveProfile(View view){
        Intent intent = new Intent(this, MainActivity.class);
        EditText nameEdit = (EditText) findViewById(R.id.nameEdit);
        EditText yearEdit = (EditText) findViewById(R.id.yearEdit);
        EditText monthEdit = (EditText) findViewById(R.id.monthEdit);
        EditText dayEdit = (EditText) findViewById(R.id.dayEdit);
        EditText neckEdit = (EditText) findViewById(R.id.neckEdit);
        EditText bustEdit = (EditText) findViewById(R.id.bustEdit);
        EditText chestEdit = (EditText) findViewById(R.id.chestEdit);
        EditText waistEdit = (EditText) findViewById(R.id.waistEdit);
        EditText hipEdit = (EditText) findViewById(R.id.hipEdit);
        EditText inseamEdit = (EditText) findViewById(R.id.inseamEdit);
        EditText commentEdit = (EditText) findViewById(R.id.commentEdit);
        String transfer;
        String profileName = nameEdit.getText().toString();
        String date = yearEdit.getText().toString() + "-" + monthEdit.getText().toString() + "-" + dayEdit.getText().toString();
        String profileNeck = neckEdit.getText().toString();
        String profileBust = bustEdit.getText().toString();
        String profileChest = chestEdit.getText().toString();
        String profileWaist = waistEdit.getText().toString();
        String profileHip = hipEdit.getText().toString();
        String profileInseam = inseamEdit.getText().toString();
        String profileComment = commentEdit.getText().toString();

        transfer = profileName + "0,," + date + "0,," + profileNeck + "0,," + profileBust + "0,," + profileChest + "0,," + profileWaist + "0,," + profileHip + "0,," + profileInseam + "0,," + profileComment;

        intent.putExtra(EXTRA_MESSAGE, transfer);
        startActivity(intent);

    }
}



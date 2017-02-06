package com.example.esieben_sizebook;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Class containing main-screen functionality
 * allows for user to add a profile, edit existing profiles, or clear all profiles
 */
public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ListView existingProfiles;
    private ArrayList<Profile> profileList;
    private ArrayAdapter<Profile> adapter;
    private TextView profileCount;

    /**
     * Method called when activity is started or resumed
     * Clear-button functionality is defined within this method, when pressed the method
     * <ol>
     *     <li>empties the Arraylist used to store active profile objects</li>
     *     <li>updates the display</li>
     *     <li>saves the updated data set to the file</li>
     *     <li>updates the profile count textview at the top of the screen</li>
     * </ol>
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        existingProfiles = (ListView) findViewById(R.id.existingProfiles);
        Button clearButton = (Button) findViewById(R.id.clearButton);
        profileCount = (TextView) findViewById(R.id.profileCount);

        profileList = new ArrayList<Profile>();
        Intent intent = getIntent();

        clearButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                profileList.clear();
                adapter.notifyDataSetChanged();
                saveInFile("");
                profileCount.setText(String.format("%d profiles found", profileList.size()));
            }
        });

        /**
         * method adapted from solution at
         * http://stackoverflow.com/questions/8352467/how-to-pass-data-from-a-selected-item-in-a-listview-to-another-activity
         * viewed on 04.02.2017
         *
         * method called when profile is selected for editting
         * @param position holds the index of the selected item, allowing the specific item selected to be passed to the next activity
         */
        existingProfiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, profileEditActivity.class);
                    Profile p = (Profile) existingProfiles.getItemAtPosition(position);
                    intent.putExtra("selectedProfile", p);
                    startActivity(intent);
            }
        });

    }

    /**
     * method called to shift user to the profile creation screen
     * called when "create new profile" button is clicked
     * @param view - empty variable
     * @see profileCreationActivity
     */
    public void createProfile(View view){
        Intent intent = new Intent(this, profileCreationActivity.class);
        startActivity(intent);
    }


    /**
     * adapted from lonelyTwitter application used in class
     *
     * loads all profiles from a file specified in FILENAME using JSON format
     * allows for recovery of data when app is closed
     *
     * @throws FileNotFoundException if profileList has not been initialized
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            //on 2017-01-24 18:19
            Type listType = new TypeToken<ArrayList<Profile>>() {}.getType();
            profileList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            profileList = new ArrayList<Profile>();
        }
    }

    /**
     * Saves profiles to a specified file in JSON format.
     *
     * adapted from LonelyTwitter application from class
     *
     * @throws FileNotFoundException if file folder doesn't exist
     */
    private void saveInFile(String text) {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(profileList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO: Handle the Exception properly later
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * adapted from lonelyTwitter application from class
     *
     * Collects intents and passed data from other activities
     * @see profileCreationActivity
     * adds newly created profiles to the display and updates profile counter
     *
     * @see profileEditActivity
     * updates data of newly editted profiles
     * excessive 'if' structure suggests that a data structure change is necessary to improve
     * code quality. Such development was postponed until full functionality was achieved, and
     * has been further postponed due to time constraints of submission.
     *
     * Suggested: storing measurements in the profile object as an array, such that it may be looped
     * through, rather than each item individually checked.
     *
     * profile is deleted by passing a comment of "DELETE" from the profileEditActivity
     *
     */
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        loadFromFile();
        profileCount.setText(String.format("%d profiles found", profileList.size()));
        adapter = new ArrayAdapter<Profile>(this,R.layout.list_item , profileList);
        existingProfiles.setAdapter(adapter);
        Intent intent = getIntent();
        if(intent.getStringExtra(profileCreationActivity.EXTRA_MESSAGE) != null) {
            String profileName = intent.getStringExtra(profileCreationActivity.EXTRA_MESSAGE);
            Profile newProfile = new Profile(profileName);
            profileList.add(newProfile);
            adapter.notifyDataSetChanged();
            saveInFile(profileName);
            profileCount.setText(String.format("%d profiles found", profileList.size()));


        if(intent.getStringExtra(profileEditActivity.EXTRA_MESSAGE) != null){
            String dataString = intent.getStringExtra(profileEditActivity.EXTRA_MESSAGE);
            String[] dataSplit = dataString.split("0,,");

            //search for profile to update
            for(int i = 0; i<profileList.size(); i++ ) {
                if (dataSplit[0].equals(profileList.get(i).getName())) {
                    if (!dataSplit[1].equals("--")) {
                        profileList.get(i).setDate(dataSplit[1]);
                    }
                    if (dataSplit[2] != null){
                        profileList.get(i).setNeck(Float.parseFloat(dataSplit[2]));
                    }
                    if (dataSplit[3] != null){
                        profileList.get(i).setBust(Float.parseFloat(dataSplit[3]));
                    }
                    if (dataSplit[4] != null){
                        profileList.get(i).setChest(Float.parseFloat(dataSplit[4]));
                    }
                    if (dataSplit[5] != null){
                        profileList.get(i).setWaist(Float.parseFloat(dataSplit[5]));
                    }
                    if (dataSplit[6] != null){
                        profileList.get(i).setHip(Float.parseFloat(dataSplit[6]));
                    }
                    if (dataSplit[7] != null){
                        profileList.get(i).setInseam(Float.parseFloat(dataSplit[7]));
                    }
                    if (dataSplit.length > 8){
                        if(dataSplit[8].equals("DELETE")){
                            profileList.remove(i);
                        }
                        else {
                            profileList.get(i).setComment(dataSplit[8]);
                        }
                    }

                }
            }

            adapter.notifyDataSetChanged();
            saveInFile(dataString);
            profileCount.setText(String.format("%d profiles found", profileList.size()));
        }
    }

}

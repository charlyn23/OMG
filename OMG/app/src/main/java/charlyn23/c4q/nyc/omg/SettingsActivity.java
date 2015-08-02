package charlyn23.c4q.nyc.omg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//todo: use shared preferences to save user info.
//todo: Possibly create a user info class to store all data and use with other activities.


public class SettingsActivity extends Activity {

    EditText nameEditTxt;
    EditText ageEditTxt;
    EditText genderEditTxt;
    EditText zipcodeEditTxt;

    SharedPreferences prefs ;
    SharedPreferences.Editor editor;

    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializeViews();

        prefs = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if(prefs != null){
            String savedUserName = prefs.getString("user name", "null");
            int savedUserAge = prefs.getInt("user age", 0);
            String savedUserGender = prefs.getString("user gender", "NA");
            int savedUserZipCode = prefs.getInt("user zipcode", 00000);

            if(savedUserName.equals("null") && savedUserAge == 0 && savedUserGender.equals("NA") && savedUserZipCode == 00000  ){
                nameEditTxt.setText("");
                ageEditTxt.setText("");
                genderEditTxt.setText("");
                zipcodeEditTxt.setText("");
            } else {
                nameEditTxt.setText(savedUserName);
                ageEditTxt.setText(savedUserAge + "");
                genderEditTxt.setText(savedUserGender);
                zipcodeEditTxt.setText(savedUserZipCode + "");
            }

        }

    }

    public void initializeViews () {

        nameEditTxt = (EditText) findViewById(R.id.name_et);
        ageEditTxt = (EditText) findViewById(R.id.age_et);
        genderEditTxt = (EditText) findViewById(R.id.gender_et);
        zipcodeEditTxt = (EditText) findViewById(R.id.zipcode_et);

    }

    public void saveOnClick (View view) {
        editor = prefs.edit();
        editor.putString("user name", nameEditTxt.getText().toString()).apply();
        editor.putInt("user age", Integer.parseInt(ageEditTxt.getText().toString())).apply();
        editor.putString("user gender", genderEditTxt.getText().toString()).apply();
        editor.putInt("user zipcode", Integer.parseInt(zipcodeEditTxt.getText().toString())).apply();

        nameEditTxt.setEnabled(false);
        ageEditTxt.setEnabled(false);
        genderEditTxt.setEnabled(false);
        zipcodeEditTxt.setEnabled(false);

        Toast.makeText(this, "User Info Saved!",Toast.LENGTH_SHORT).show();

    }

    public void editOnClick (View view) {
        nameEditTxt.setEnabled(true);
        ageEditTxt.setEnabled(true);
        genderEditTxt.setEnabled(true);
        zipcodeEditTxt.setEnabled(true);
    }


    public void emergencyContactOneOnClick(View view) {
        emergencyContactBox();
    }

    public void emergencyContactTwoOnClick(View view) {
        emergencyContactBox();
    }

    public void emergencyContactThreeOnClick(View view) {
        emergencyContactBox();
    }

    public AlertDialog.Builder emergencyContactBox () {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialogue_contact_info, null));

        builder.setPositiveButton("Add Contact", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //todo: add code to save to shared Preferences Here! or Database
                //todo: set text for imagebtn emergency contact one to name of contact
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();

        return builder;
    }
    
}

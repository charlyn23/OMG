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
import android.widget.ImageButton;
import android.widget.TextView;
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

    ImageButton emergencyContactOne;
    ImageButton emergencyContactTwo;
    ImageButton emergencyContactThree;
    TextView contactOneInfo;
    TextView contactTwoInfo;
    TextView contactThreeInfo;


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

            String savedContactOneNameTxt = prefs.getString("Contact Name One", "null");
            long savedContactOneNumTxt = prefs.getLong("Contact Number One", 0);
            String savedContactTwoNameTxt = prefs.getString("Contact Name Two", "null");
            long savedContactTwoNumTxt = prefs.getLong("Contact Number Two", 0);
            String savedContactThreeNameTxt = prefs.getString("Contact Name Three", "null");
            long savedContactThreeNumTxt = prefs.getLong("Contact Number Three", 0);

            if(savedContactOneNameTxt.equals("null") && savedContactOneNumTxt == 0){
                contactOneInfo.setText("");
                emergencyContactOne.setImageResource(R.drawable.ic_add_white_24dp);
            } else {
                contactOneInfo.setText(savedContactOneNameTxt);
                emergencyContactOne.setImageResource(R.drawable.ic_check_white_24dp);
            }

            if(savedContactTwoNameTxt.equals("null") && savedContactTwoNumTxt == 0){
                contactTwoInfo.setText("");
                emergencyContactTwo.setImageResource(R.drawable.ic_add_white_24dp);
            } else {
                contactTwoInfo.setText(savedContactTwoNameTxt);
                emergencyContactTwo.setImageResource(R.drawable.ic_check_white_24dp);
            }

            if(savedContactThreeNameTxt.equals("null") && savedContactThreeNumTxt == 0){
                contactThreeInfo.setText("");
                emergencyContactThree.setImageResource(R.drawable.ic_add_white_24dp);
            } else {
                contactThreeInfo.setText(savedContactThreeNameTxt);
                emergencyContactThree.setImageResource(R.drawable.ic_check_white_24dp);
            }

        }
        userInfoIsEnabled(false);
    }

    public void initializeViews () {
        nameEditTxt = (EditText) findViewById(R.id.name_et);
        ageEditTxt = (EditText) findViewById(R.id.age_et);
        genderEditTxt = (EditText) findViewById(R.id.gender_et);
        zipcodeEditTxt = (EditText) findViewById(R.id.zipcode_et);

        emergencyContactOne = (ImageButton) findViewById(R.id.emergency_contact_1);
        emergencyContactTwo = (ImageButton) findViewById(R.id.emergency_contact_2);
        emergencyContactThree = (ImageButton) findViewById(R.id.emergency_contact_3);

        contactOneInfo = (TextView) findViewById(R.id.emergency_contact_1_info_tv);
        contactTwoInfo = (TextView) findViewById(R.id.emergency_contact_2_info_tv);
        contactThreeInfo = (TextView) findViewById(R.id.emergency_contact_3_info_tv);
    }

    public void saveOnClick (View view) {
        editor = prefs.edit();
        editor.putString("user name", nameEditTxt.getText().toString()).apply();
        editor.putInt("user age", Integer.parseInt(ageEditTxt.getText().toString())).apply();
        editor.putString("user gender", genderEditTxt.getText().toString()).apply();
        editor.putInt("user zipcode", Integer.parseInt(zipcodeEditTxt.getText().toString())).apply();

        userInfoIsEnabled(false);

        Toast.makeText(this, "User Info Saved!",Toast.LENGTH_SHORT).show();

    }

    public void editOnClick (View view) {
        userInfoIsEnabled(true);
    }

    public void userInfoIsEnabled(boolean isEnabled){
        nameEditTxt.setEnabled(isEnabled);
        ageEditTxt.setEnabled(isEnabled);
        genderEditTxt.setEnabled(isEnabled);
        zipcodeEditTxt.setEnabled(isEnabled);
    }

    public void emergencyContactOneOnClick(View view) {
        emergencyContactBox("Contact Name One", "Contact Num One");
    }

    public void emergencyContactTwoOnClick(View view) {
        emergencyContactBox("Contact Name Two", "Contact Number Two");
    }

    public void emergencyContactThreeOnClick(View view) {
        emergencyContactBox("Contact Name Three", "Contact Number Three");
    }

    public AlertDialog.Builder emergencyContactBox (final String ContactName, final String ContactNumber) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogue_contact_info, null);
        builder.setView(view);

        final EditText nameDialogueET = (EditText) view.findViewById(R.id.name_dialogue_et);
        final EditText phoneDialogueET = (EditText) view.findViewById(R.id.phone_number_dialogue_et);

        builder.setPositiveButton("Add Contact", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //todo: add code to save to shared Preferences Here!
                //todo: set text for imagebtn emergency contact one to name of contact
                editor = prefs.edit();
                editor.putString(ContactName, nameDialogueET.getText().toString()).apply();
                editor.putLong(ContactNumber, Long.parseLong(phoneDialogueET.getText().toString())).apply();

                setEmergencyContactInfoDisplay(ContactName);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();

        return builder;
    }

    public void setEmergencyContactInfoDisplay (String Contact) {
        if(Contact.equals("Contact Name One")) {
            emergencyContactOne.setImageResource(R.drawable.ic_check_white_24dp);

            String contactNameTxt = prefs.getString("Contact Name One", "null");
            contactOneInfo.setText(contactNameTxt);

        }else if (Contact.equals("Contact Name Two")) {
            emergencyContactTwo.setImageResource(R.drawable.ic_check_white_24dp);

            String contactNameTxt = prefs.getString("Contact Name Two", "null");
            contactTwoInfo.setText(contactNameTxt);

        }else if (Contact.equals("Contact Name Three")) {
            emergencyContactThree.setImageResource(R.drawable.ic_check_white_24dp);

            String contactNameTxt = prefs.getString("Contact Name Three", "null");
            contactThreeInfo.setText(contactNameTxt);
        }
    }
    
}

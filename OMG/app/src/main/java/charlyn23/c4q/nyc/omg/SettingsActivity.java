package charlyn23.c4q.nyc.omg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

// Some of the icons used in Buttons were provided by https://creativecommons.org/licenses/by/4.0/

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
    TextView contactOneNameInfo;
    TextView contactTwoNameInfo;
    TextView contactThreeNameInfo;
    TextView contactOneNumInfo;
    TextView contactTwoNumInfo;
    TextView contactThreeNumInfo;


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
                contactOneNameInfo.setText("");
                emergencyContactOne.setImageResource(R.drawable.ic_add_white_24dp);
            } else {
                contactOneNameInfo.setText(savedContactOneNameTxt);
                contactOneNumInfo.setText(savedContactOneNumTxt + "");
                emergencyContactOne.setImageResource(R.drawable.ic_check_white_24dp);
            }

            if(savedContactTwoNameTxt.equals("null") && savedContactTwoNumTxt == 0){
                contactTwoNameInfo.setText("");
                emergencyContactTwo.setImageResource(R.drawable.ic_add_white_24dp);
            } else {
                contactTwoNameInfo.setText(savedContactTwoNameTxt);
                contactTwoNumInfo.setText(savedContactTwoNumTxt + "");
                emergencyContactTwo.setImageResource(R.drawable.ic_check_white_24dp);
            }

            if(savedContactThreeNameTxt.equals("null") && savedContactThreeNumTxt == 0){
                contactThreeNameInfo.setText("");
                emergencyContactThree.setImageResource(R.drawable.ic_add_white_24dp);
            } else {
                contactThreeNameInfo.setText(savedContactThreeNameTxt);
                contactThreeNumInfo.setText(savedContactThreeNumTxt + "");
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

        contactOneNameInfo = (TextView) findViewById(R.id.emergency_contact_1_info_tv);
        contactTwoNameInfo = (TextView) findViewById(R.id.emergency_contact_2_info_tv);
        contactThreeNameInfo = (TextView) findViewById(R.id.emergency_contact_3_info_tv);

        contactOneNumInfo = (TextView) findViewById(R.id.emergency_num_1_info_tv);
        contactTwoNumInfo = (TextView) findViewById(R.id.emergency_num_2_info_tv);
        contactThreeNumInfo = (TextView) findViewById(R.id.emergency_num_3_info_tv);

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
        emergencyContactBox("Contact Name One", "Contact Number One");
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
                editor = prefs.edit();


                editor.putString(ContactName, nameDialogueET.getText().toString()).apply();
                editor.putLong(ContactNumber, Long.parseLong(phoneDialogueET.getText().toString())).apply();

                Log.i("Contact Number : " , Long.parseLong(phoneDialogueET.getText().toString()) + "" );

                //sets contact info underneath imageButton
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
            contactOneNameInfo.setText(contactNameTxt);

            long ContactOneNumTxt = prefs.getLong("Contact Number One", 0);
            Log.i("Contact One Num Text : ", ContactOneNumTxt + "");
            contactOneNumInfo.setText(ContactOneNumTxt + "");

        }else if (Contact.equals("Contact Name Two")) {
            emergencyContactTwo.setImageResource(R.drawable.ic_check_white_24dp);

            String contactNameTxt = prefs.getString("Contact Name Two", "null");
            contactTwoNameInfo.setText(contactNameTxt);

            long ContactTwoNumTxt = prefs.getLong("Contact Number Two", 0);
            Log.i("Contact Two Num Text : ", ContactTwoNumTxt + "");
            contactTwoNumInfo.setText(ContactTwoNumTxt + "");

        }else if (Contact.equals("Contact Name Three")) {
            emergencyContactThree.setImageResource(R.drawable.ic_check_white_24dp);

            String contactNameTxt = prefs.getString("Contact Name Three", "null");
            contactThreeNameInfo.setText(contactNameTxt);

            long ContactThreeNumTxt = prefs.getLong("Contact Number Three", 0);
            Log.i("Contact Two Num Text : ", ContactThreeNumTxt + "");
            contactThreeNumInfo.setText(ContactThreeNumTxt + "");

        }
    }
    
}

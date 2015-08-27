package charlyn23.c4q.nyc.omg;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import charlyn23.c4q.nyc.omg.model.ContactInfo;
import charlyn23.c4q.nyc.omg.model.Hours;
import charlyn23.c4q.nyc.omg.model.Location;
import charlyn23.c4q.nyc.omg.model.Offices;
import charlyn23.c4q.nyc.omg.model.Program;
import charlyn23.c4q.nyc.omg.model.SearchResult;
import android.view.View;

public class MainActivity extends Activity {
    private final static String url1 = "https://searchbertha-hrd.appspot.com/_ah/api/search/v1/zipcodes/";
    int zipCode;

    String AB_URL;
    Button not_safe_button;
    Button mNineOneOne, mTextForHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button not_safe_button = (Button)findViewById(R.id.not_safe_button);
        Button food_button = (Button)findViewById(R.id.food_button);
        Button shelter_button= (Button)findViewById(R.id.shelter_button);
        Button mental_button= (Button)findViewById(R.id.mental_button);
        Button money_button= (Button)findViewById(R.id.money_button);
        Button missing_person_button= (Button)findViewById(R.id.missing_person_button);

        View.OnClickListener notSafeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MappingImmediateHelp.class);
                startActivity(intent);            }
        };
        not_safe_button.setOnClickListener(notSafeListener);

        View.OnClickListener foodListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MappingImmediateFood.class);
                startActivity(intent);            }
        };
        food_button.setOnClickListener(foodListener);

//        Intent intent= new Intent(this, MappingImmediateHelp.class);
//        startActivity(intent);
        View.OnClickListener hurtListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MappingImmediateHelp.class);
                startActivity(intent);            }
        };

        View.OnClickListener missingPersonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MappingMissingPerson.class);
                startActivity(intent);
            }
        };
        missing_person_button.setOnClickListener(missingPersonListener);

        View.OnClickListener shelterListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MappingImmediateShelter.class);
                startActivity(intent);            }
        };
        shelter_button.setOnClickListener(shelterListener);

        View.OnClickListener emotionalListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MappingDepressed.class);
                startActivity(intent);
            }
        };
        mental_button.setOnClickListener(emotionalListener);

        View.OnClickListener moneyListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MappingEmergencyMoney.class);
                startActivity(intent);            }
        };
        money_button.setOnClickListener(moneyListener);
    }

    public long[] getPhoneNumbers(){
        SharedPreferences prefs = getSharedPreferences("MyPresFile", MODE_PRIVATE);
        SettingsActivity mSettingsActivity = new SettingsActivity();

        long savedContactOneNumTxt = prefs.getLong("Contact Number One", 0);
        long savedContactTwoNumTxt = prefs.getLong("Contact Number Two", 0);
        long savedContactThreeNumTxt = prefs.getLong("Contact Number Three", 0);

        long[] mphoneNumbers = new long[3];
        mphoneNumbers[0] = savedContactOneNumTxt;
        mphoneNumbers[1] = savedContactTwoNumTxt;
        mphoneNumbers[2] = savedContactThreeNumTxt;

        return mphoneNumbers;

    }

    public void getData(String url1, int zipcode, String url2) {
        SharedPreferences pref = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        SettingsActivity settingsActivity = new SettingsActivity();
        zipCode = pref.getInt("user zipcode", -1);

        if (zipCode == -1) {
            Toast.makeText(this, "Please enter your zip code in Settings.", Toast.LENGTH_SHORT);
        }
        else if (((zipCode <= 10001) || (zipCode >= 11697))){
            Toast.makeText(this, "Please enter a valid NYC zip code in Settings", Toast.LENGTH_SHORT).show();
        }
        String AB_URL = (url1 + zipCode + url2);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AB_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        SearchResult searchResult = new Gson().fromJson(response, SearchResult.class);


                        List<Program> programs = searchResult.getPrograms();


                        for (Program program : searchResult.getPrograms()) {
                            Toast.makeText(getApplicationContext(), program.getName(), Toast.LENGTH_SHORT).show();
                            for (ContactInfo contactInfo : program.getNext_steps()) {
                                if (contactInfo.getChannel().equals("phone")) {

                                }
                            }
                            for (Offices offices : program.getOffices()) {
                                Location location = offices.getLocation();
                                Hours hours = offices.getHours();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    public void settingsOnClick(View view) {
        Intent settingsIntent =  new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }


    //Sends an SMS message to another device.

    public void textAllYourFamilyMember(View v){
        String text = "Please help me, I really need you guys.";

        SharedPreferences sharedPreferences=getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String name= sharedPreferences.getString("user name", "");

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        android.location.Location myLocation = locationManager.getLastKnownLocation(provider);
        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        if(city==null){
            city="";
        }
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();

        String currentLocation= address + " , "+city+","+state;
        Log.i("Location: ",currentLocation);


        text+="\nLocation"+currentLocation;

        long[] mPhoneNumbers = getPhoneNumbers();

        for(int i=0; i< mPhoneNumbers.length; i++){
            String mFamily = "";
            mFamily = Long.toString(mPhoneNumbers[i]);
            sendSMS(mFamily,text);
        }

        Toast.makeText(this,"Emergency Text Messages Sent",Toast.LENGTH_LONG).show();

    }

    //pull up the 911 ready to call.
    //add toast

    public void call911(View view){
        String policeAndFireDep = "911";

        Intent callNineOneOne = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + policeAndFireDep));


        if(callNineOneOne.resolveActivity(getPackageManager()) != null){
            startActivity(callNineOneOne);
        }
    }


    public void sendSMS(String phoneNumber, String message){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

}

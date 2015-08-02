package charlyn23.c4q.nyc.omg;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.List;

import charlyn23.c4q.nyc.omg.model.ContactInfo;
import charlyn23.c4q.nyc.omg.model.Hours;
import charlyn23.c4q.nyc.omg.model.Location;
import charlyn23.c4q.nyc.omg.model.Offices;
import charlyn23.c4q.nyc.omg.model.Program;
import charlyn23.c4q.nyc.omg.model.SearchResult;

public class MainActivity extends Activity {
//    public final String AB_URL = "https://searchbertha-hrd.appspot.com/_ah/api/search/v1/zipcodes/10101/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=immediate%20safety";

    final static String url1 = "https://searchbertha-hrd.appspot.com/_ah/api/search/v1/zipcodes/";
     String url2;
    int zipCode;
     String AB_URL;
    Button not_safe_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SettingsActivity settingsActivity = new SettingsActivity();


        Button not_safe_button = (Button)findViewById(R.id.not_safe_button);
        Button food_button = (Button)findViewById(R.id.food_button);
        Button hurt_button = (Button)findViewById(R.id.hurt_button);
        Button shelter_button= (Button)findViewById(R.id.shelter_button);
        Button mental_button= (Button)findViewById(R.id.mental_button);
        Button money_button= (Button)findViewById(R.id.money_button);
        Button pet_help_button= (Button)findViewById(R.id.pet_help_button);
        Button missing_person_button= (Button)findViewById(R.id.missing_person_button);

        View.OnClickListener notSafeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("https://searchbertha-hrd.appspot.com/_ah/api/search/v1/zipcodes/", zipCode, "/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=immediate%20safety");
            }
        };
        not_safe_button.setOnClickListener(notSafeListener);

        View.OnClickListener foodListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(url1, zipCode, "/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=emergency%20food");
            }
        };
        food_button.setOnClickListener(foodListener);

        View.OnClickListener hurtListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getData(url1, zipCode, url2);
            }
        };

        View.OnClickListener missingPersonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(url1, zipCode, "/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=help%20find%20missing%20persons");
            }
        };
        missing_person_button.setOnClickListener(missingPersonListener);

        View.OnClickListener shelterListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(url1, zipCode, "/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=emergency%20shelter");
            }
        };
        shelter_button.setOnClickListener(shelterListener);

        View.OnClickListener emotionalListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(url1, zipCode, "/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=psychiatric%20emergency%20services");
            }
        };
        mental_button.setOnClickListener(emotionalListener);

        View.OnClickListener moneyListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(url1, zipCode, "/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=emergency%20payments");
            }
        };
        money_button.setOnClickListener(moneyListener);

//        Intent intent= new Intent(this, MappingImmediateHelp.class);
//        startActivity(intent);



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


        Log.i("Zip Code: " , String.valueOf(zipCode));
//        String url2 = "/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=emergency%20food";
        String AB_URL = (url1 + zipCode + url2);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AB_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        SearchResult searchResult = new Gson().fromJson(response, SearchResult.class);
                        Log.i("Results : ", searchResult.getPrograms().toString());


                        List<Program> programs = searchResult.getPrograms();
                        Log.i("List: " , programs.toString());


                        for (Program program : searchResult.getPrograms()) {
                            Log.i("Results : ", program.getName());
                            Toast.makeText(getApplicationContext(), program.getName(), Toast.LENGTH_SHORT).show();
                            for (ContactInfo contactInfo : program.getNext_steps()) {
                                if (contactInfo.getChannel().equals("phone")) {
                                    Log.i("Phone Number : ", contactInfo.getContact());
//                                    Toast.makeText(getApplicationContext(), contactInfo.getContact(), Toast.LENGTH_SHORT).show();

                                }
                            }
                            for (Offices offices : program.getOffices()) {
                                Log.i("Address: ", offices.getAddress1());

                                Location location = offices.getLocation();
                                Log.i("Latitude : " , String.valueOf(location.getLatitude()));
                                Log.i("Longitude : " , String.valueOf(location.getLongitude()));

                                Hours hours = offices.getHours();
                                Log.i("Monday Hours: " , String.valueOf(hours.getMonday_start()) + " - " + String.valueOf(hours.getMonday_finish()));
                                Log.i("Tuesday Hours: " , String.valueOf(hours.getThursday_start()) + " - " + String.valueOf(hours.getTuesday_finish()));
                                Log.i("Wednesday Hours: " , String.valueOf(hours.getWednesday_start()) + " - " + String.valueOf(hours.getWednesday_finish()));
                                Log.i("Thursday Hours: " , String.valueOf(hours.getThursday_start()) + " - " + String.valueOf(hours.getThursday_finish()));
                                Log.i("Friday Hours: ", String.valueOf(hours.getFriday_start()) + " - " + String.valueOf(hours.getFriday_finish()));
                                Log.i("Saturday Hours: ", String.valueOf(hours.getSaturday_start()) + " - " + String.valueOf(hours.getSaturday_finish()));
                                Log.i("Sunday Hours: " , String.valueOf(hours.getSunday_start() + " - " + String.valueOf(hours.getSunday_finish())));

                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Please enter a valid zip code", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void settingsOnClick(View view) {
        Intent settingsIntent =  new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

}

package charlyn23.c4q.nyc.omg;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.net.URL;
import java.util.List;
import charlyn23.c4q.nyc.omg.db.ResourcesHelper;
import charlyn23.c4q.nyc.omg.model.ContactInfo;
import charlyn23.c4q.nyc.omg.model.Hours;
import charlyn23.c4q.nyc.omg.model.Location;
import charlyn23.c4q.nyc.omg.model.Offices;
import charlyn23.c4q.nyc.omg.model.Program;
import charlyn23.c4q.nyc.omg.model.SearchResult;
import android.view.View;

public class MainActivity extends ActionBarActivity {
    public final String IMMEDIATE_SAFETY_URL = "https://searchbertha-hrd.appspot.com/_ah/api/search/v1/zipcodes/10101/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=immediate%20safety";
    private final int IMMEDIATE_SAFTEY_DB_ID = 1;
    private final String FOOD_URL = " https://searchbertha-hrd.appspot.com/_ah/api/search/v1/zipcodes/10101/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=emergency%20food";
    public final int FOOD_DB_ID = 2;
    private final String HURT_URL ="https://data.cityofnewyork.us/api/views/f7b6-v6v3/rows.json?accessType=DOWNLOAD";
    public final int HURT_DB_ID = 3;
    private final String SHELTER_URL =" https://searchbertha-hrd.appspot.com/_ah/api/search/v1/zipcodes/10101/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=emergency%20shelter";
    public final int SHELTER_DB_ID = 4;
    public final String MISSING_URL =" https://searchbertha-hrd.appspot.com/_ah/api/search/v1/zipcodes/10101/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=help%20find%20missing%20persons";
    public final int MISSING_DB_ID = 5;
    private final String EMOTIONAL_URL =" https://searchbertha-hrd.appspot.com/_ah/api/search/v1/zipcodes/10101/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=psychiatric%20emergency%20services";
    public final int EMOTIONAL_DB_ID = 6;
    private final String MONEY_URL = "https://searchbertha-hrd.appspot.com/_ah/api/search/v1/zipcodes/10101/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=emergency%20payments";
    public final int MONEY_DB_ID = 7;

    ResourcesHelper helper = ResourcesHelper.getInstance(getApplicationContext());
    SQLiteDatabase mDatabase = helper.getWritableDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isNetworkAvailable()){
            loadInfo(IMMEDIATE_SAFETY_URL);
            loadInfo(FOOD_URL);
            loadInfo(HURT_URL);
            loadInfo(SHELTER_URL);
            loadInfo(MISSING_URL);
            loadInfo(EMOTIONAL_URL);
            loadInfo(MONEY_URL);
        }else if(!isNetworkAvailable()){
            loadFromDatabases();
        }else {
            Toast.makeText(this,"Connect to Internet to Download information",Toast.LENGTH_SHORT).show();
        }

//        Intent intent= new Intent(this, MappingImmediateHelp.class);
//        startActivity(intent);
    }

    private void loadInfo(final String url) {

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        SearchResult searchResult = new Gson().fromJson(response, SearchResult.class);
                        Log.i("Results : ", searchResult.getPrograms().toString());

                        List<Program> programs = searchResult.getPrograms();
                        Log.i("List: ", programs.toString());

                         String table;
                        if(url.equalsIgnoreCase(IMMEDIATE_SAFETY_URL))
                            table = "IMMEDIATE SAFETY";
                        else if(url.equalsIgnoreCase(FOOD_URL))
                            table = "FOOD BANKS";
                        else if(url.equalsIgnoreCase(HURT_URL))
                            table = "HOSPITALS";
                        else if(url.equalsIgnoreCase(SHELTER_URL))
                            table = "SHELTERS";
                        else if(url.equalsIgnoreCase(MISSING_URL))
                            table = "MISSING PERSON";
                        else if(url.equalsIgnoreCase(EMOTIONAL_URL))
                            table = "EMOTIONAL HELP";
                        else if(url.equalsIgnoreCase(MONEY_URL))
                            table = "MONEY RESOURCES";
                        else
                            table = "";


                        for (Program program : searchResult.getPrograms()) {


                            Log.i("Results : ", program.getName());
                            for (ContactInfo contactInfo : program.getNext_steps()) {
                                String phoneNumber;
                                if (contactInfo.getChannel().equals("phone")) {
                                    //ADDING PHONE NUMBER TO DB
                                    phoneNumber = contactInfo.getContact();
                                    Log.i("Phone Number : ", contactInfo.getContact());
                                }
                                else{
                                    phoneNumber = "Unavailable";
                                }

                            for (Offices offices : program.getOffices()) {

                                Log.i("Address: ", offices.getAddress1());

                                Location location = offices.getLocation();
                                Log.i("Latitude : ", String.valueOf(location.getLatitude()));
                                Log.i("Longitude : ", String.valueOf(location.getLongitude()));

                                Hours hours = offices.getHours();

                                String hoursString = "Monday: " + hours.getMonday_start() + " - " + hours.getMonday_finish() + "\n" +
                                        "Tuesday: " + hours.getTuesday_start() + " - " + hours.getTuesday_finish() + "\n" +
                                        "Wednesday: " + hours.getWednesday_start() + " - " + hours.getWednesday_finish() + "\n" +
                                        "Thursday: " + hours.getThursday_start() + " - " + hours.getThursday_finish() + "\n" +
                                        "Friday: " + hours.getFriday_start() + " - " + hours.getFriday_finish() + "\n" +
                                        "Saturday: " + hours.getSaturday_start() + " - " + hours.getSaturday_finish() + "\n" +
                                        "Sunday:" + hours.getSunday_start() + " - " + hours.getSunday_finish();

                                Log.i("Monday Hours: ", String.valueOf(hours.getMonday_start()) + " - " + String.valueOf(hours.getMonday_finish()));
                                Log.i("Tuesday Hours: ", String.valueOf(hours.getThursday_start()) + " - " + String.valueOf(hours.getTuesday_finish()));
                                Log.i("Wednesday Hours: ", String.valueOf(hours.getWednesday_start()) + " - " + String.valueOf(hours.getWednesday_finish()));
                                Log.i("Thursday Hours: ", String.valueOf(hours.getThursday_start()) + " - " + String.valueOf(hours.getThursday_finish()));
                                Log.i("Friday Hours: ", String.valueOf(hours.getFriday_start()) + " - " + String.valueOf(hours.getFriday_finish()));
                                Log.i("Saturday Hours: ", String.valueOf(hours.getSaturday_start()) + " - " + String.valueOf(hours.getSaturday_finish()));
                                Log.i("Sunday Hours: ", String.valueOf(hours.getSunday_start() + " - " + String.valueOf(hours.getSunday_finish())));


                                mDatabase.beginTransaction();
                            helper.insertRow(program.getName(),offices.getAddress1(),phoneNumber,hoursString,table);
                                mDatabase.endTransaction();
                            }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "That didn't work!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);

    }

    public void settingsOnClick(View view) {
        Intent settingsIntent =  new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void loadFromDatabases(){

    }

}

package charlyn23.c4q.nyc.omg;

import android.content.Intent;

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

import java.sql.SQLException;
import java.util.List;

import charlyn23.c4q.nyc.omg.model.ContactInfo;
import charlyn23.c4q.nyc.omg.model.Hours;
import charlyn23.c4q.nyc.omg.model.Location;
import charlyn23.c4q.nyc.omg.model.Offices;
import charlyn23.c4q.nyc.omg.model.Program;
import charlyn23.c4q.nyc.omg.model.SearchResult;

import android.view.View;

public class MainActivity extends ActionBarActivity {
    public final String AB_URL = "https://searchbertha-hrd.appspot.com/_ah/api/search/v1/zipcodes/10101/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=immediate%20safety";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Intent intent= new Intent(this, MappingImmediateHelp.class);
        startActivity(intent);


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AB_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        SearchResult searchResult = new Gson().fromJson(response, SearchResult.class);
                        Log.i("Results : " , searchResult.getPrograms().toString());


                        List<Program> programs = searchResult.getPrograms();
                        Log.i("List: " , programs.toString());


                        for (Program program : searchResult.getPrograms()) {
                            Log.i("Results : ", program.getName());
                            for (ContactInfo contactInfo : program.getNext_steps()) {
                                if (contactInfo.getChannel().equals("phone")) {
                                    Log.i("Phone Number : ", contactInfo.getContact());
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
                Toast.makeText(getApplicationContext(), "That didn't work!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);

    }

    public void settingsOnClick(View view) {
        Intent settingsIntent =  new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

}

package charlyn23.c4q.nyc.omg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
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
import java.sql.SQLException;
import java.util.List;
import charlyn23.c4q.nyc.omg.model.ContactInfo;
import charlyn23.c4q.nyc.omg.model.Hours;
import charlyn23.c4q.nyc.omg.model.Location;
import charlyn23.c4q.nyc.omg.model.Offices;
import charlyn23.c4q.nyc.omg.model.Program;
import charlyn23.c4q.nyc.omg.model.SearchResult;
import android.view.View;

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

        Intent intent= new Intent(this, MappingImmediateHelp.class);
        startActivity(intent);
        View.OnClickListener hurtListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MappingMissingPerson.class);
                startActivity(intent);            }
        };

        hurt_button.setOnClickListener(hurtListener);
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

}

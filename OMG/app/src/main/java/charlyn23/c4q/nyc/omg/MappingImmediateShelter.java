package charlyn23.c4q.nyc.omg;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import charlyn23.c4q.nyc.omg.model.ContactInfo;
import charlyn23.c4q.nyc.omg.model.Hours;
import charlyn23.c4q.nyc.omg.model.Location;
import charlyn23.c4q.nyc.omg.model.Offices;
import charlyn23.c4q.nyc.omg.model.Program;
import charlyn23.c4q.nyc.omg.model.SearchResult;


public class MappingImmediateShelter extends ActionBarActivity {

    public final String AB_URL = "https://searchbertha-hrd.appspot.com/_ah/api/search/v1/zipcodes/10101/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=emergency%20shelter";
    ViewPager viewPager;
    MapFragment mapFragment;
    MapListFragment mapListFragment;
    List<Program> programs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mapping_immediate_help);
        mapFragment = new MapFragment();
        mapListFragment = new MapListFragment();

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AB_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        SearchResult searchResult = new Gson().fromJson(response, SearchResult.class);
                        Log.i("Results : ", searchResult.getPrograms().toString());


                        programs = searchResult.getPrograms();
                        Log.i("List: ", programs.toString());

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
                                Log.i("Latitude : ", String.valueOf(location.getLatitude()));
                                Log.i("Longitude : ", String.valueOf(location.getLongitude()));

                                Hours hours = offices.getHours();
                                Log.i("Monday Hours: ", String.valueOf(hours.getMonday_start()) + " - " + String.valueOf(hours.getMonday_finish()));
                                Log.i("Tuesday Hours: ", String.valueOf(hours.getThursday_start()) + " - " + String.valueOf(hours.getTuesday_finish()));
                                Log.i("Wednesday Hours: ", String.valueOf(hours.getWednesday_start()) + " - " + String.valueOf(hours.getWednesday_finish()));
                                Log.i("Thursday Hours: ", String.valueOf(hours.getThursday_start()) + " - " + String.valueOf(hours.getThursday_finish()));
                                Log.i("Friday Hours: ", String.valueOf(hours.getFriday_start()) + " - " + String.valueOf(hours.getFriday_finish()));
                                Log.i("Saturday Hours: ", String.valueOf(hours.getSaturday_start()) + " - " + String.valueOf(hours.getSaturday_finish()));
                                Log.i("Sunday Hours: ", String.valueOf(hours.getSunday_start() + " - " + String.valueOf(hours.getSunday_finish())));

                            }
                        }

                        mapListFragment.updateData(programs);
                        mapFragment.loadPlaces(programs);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "That didn't work!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
        getInfoFromCache();
    }

    public void saveInfoToCache(){
        File file;
        FileOutputStream outputStream;
        try {
            // file = File.createTempFile("MyCache", null, getCacheDir());
            file = new File(getCacheDir(), "MyCache");

            outputStream = new FileOutputStream(file);
            //outputStream.write(content.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getInfoFromCache(){
        BufferedReader input = null;
        File file = null;
        try {
            file = new File(getCacheDir(), "MyCache"); // Pass getFilesDir() and "MyFile" to read file

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }

            //Log.d(TAG, buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //mapFragment.loadPlaces();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    //Use isNetWorkAvailable or haveNetworkConnection to determine if there is a connection
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            if(i==0){
                return mapFragment;
            }
            else{
                return mapListFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position==0){
                return "Map of Locations";
            } else{
                return "List Of Locations";
            }
        }
    }
}

package charlyn23.c4q.nyc.omg;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by charlynbuchanan on 8/1/15.
 */
public class DataFetcher {
    public DataFetcher() {
        new AsyncClass().execute();
    }

    class AsyncClass extends AsyncTask<Void, Void, String> {



        @Override
        protected String doInBackground(Void... params) {
            Log.v("JSON", "doInBackground");
            String result = "";

            String jsonURL = "https://searchbertha-hrd.appspot.com/_ah/api/search/v1/zipcodes/10101/programs?api_key=b0f6c6a6a8be355fc04be76ab3f0c5e6&serviceTag=immediate%20safety";
            URL url = null;
            try {
                url = new URL(jsonURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if ((connection != null) || (url != null)) {
                    Log.v("status: ", "CONNECTED");
                }
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                result = stringBuilder.toString();
                Log.d("json: ", result);
                connection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("JSON", "Malformed url: " + e.toString());

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("JSON", "IOException url: " + e.toString());
            }
            return result;

        }
    }

     private static String jsonString = "";
    public static String getJsonString() {
        return jsonString;
    }

    public String getResource() {
        String resource = "";
        String json = getJsonString();

        try {
            JSONObject object = new JSONObject(json);
            JSONArray programs = object.getJSONArray("programs");



        } catch (JSONException e) {
            e.printStackTrace();
        }
            return null;
    }
}

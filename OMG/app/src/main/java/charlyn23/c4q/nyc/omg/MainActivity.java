package charlyn23.c4q.nyc.omg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void settingsOnClick(View view) {
        Intent settingsIntent =  new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

}

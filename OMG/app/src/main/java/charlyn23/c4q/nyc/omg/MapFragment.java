package charlyn23.c4q.nyc.omg;


import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends SupportMapFragment {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GoogleMap googleMap = getMap();
        googleMap.setMyLocationEnabled(true);

        Location myLocation= googleMap.getMyLocation();
        Double lat= myLocation.getLatitude();
        Double lon= myLocation.getLongitude();

        LatLng myLatLon= new LatLng(lat,lon);
        MarkerOptions marker= new MarkerOptions();
        marker.position(myLatLon);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(, 150));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(100), 2000, null);

    }
}
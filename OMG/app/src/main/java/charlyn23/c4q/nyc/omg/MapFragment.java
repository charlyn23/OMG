package charlyn23.c4q.nyc.omg;


import android.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import charlyn23.c4q.nyc.omg.model.Program;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends SupportMapFragment {

    List<Program> programList1=new ArrayList<>();
    GoogleMap googleMap;
    MapListFragment mapListFragment;
    Location myLocation;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mapListFragment= new MapListFragment();
        googleMap = getMap();
        googleMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        myLocation = locationManager.getLastKnownLocation(provider);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet("Consider yourself located"));
    }

    public void loadPlaces(List<Program> programsList){
        if(programList1!=null) {
            programList1.clear();
        }
        if(programsList!=null) {
            programList1.addAll(programsList);

            for (int i = 0; i < programList1.size(); i++) {

                float lat = programList1.get(i).getOffices().get(0).getLocation().getLatitude();
                float lon = programList1.get(i).getOffices().get(0).getLocation().getLongitude();


                Marker marker=googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title(programList1.get(i).getName()));

                marker.setSnippet("Phone Number:" + programList1.get(i).getNext_steps().get(0).getContact());
                marker.isInfoWindowShown();

            }
        }

        //TODO fix title pages
        //TODO onMarkerClick update listView

    }
}
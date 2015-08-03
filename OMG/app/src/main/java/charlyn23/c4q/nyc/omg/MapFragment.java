package charlyn23.c4q.nyc.omg;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    List<Program> programList1 = new ArrayList<>();
    GoogleMap googleMap;
    MapListFragment mapListFragment;
    Location myLocation;
    ArrayList<Marker> markersList;
    Marker marker;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mapListFragment = new MapListFragment();
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

    public void loadPlaces(final List<Program> programsList) {
        if (programList1 != null) {
            programList1.clear();
        }
        if (programsList != null) {
            programList1.addAll(programsList);

            for (int i = 0; i < programList1.size(); i++) {

                float lat = programList1.get(i).getOffices().get(0).getLocation().getLatitude();
                float lon = programList1.get(i).getOffices().get(0).getLocation().getLongitude();
                String phoneNumber=programList1.get(i).getNext_steps().get(0).getContact();
                String address=programList1.get(i).getOffices().get(0).getAddress1();


                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title(programList1.get(i).getName()).snippet(phoneNumber+","+address));


                //marker.setSnippet("Phone Number:" + programList1.get(i).getNext_steps().get(0).getContact());
                markersList = new ArrayList<>();
                markersList.add(marker);


                googleMap.setInfoWindowAdapter(new Yourcustominfowindowadpater());
            }
        }
    }


    class Yourcustominfowindowadpater implements GoogleMap.InfoWindowAdapter {
        private View mymarkerview;


        Yourcustominfowindowadpater() {
            mymarkerview = getActivity().getLayoutInflater()
                    .inflate(R.layout.info_box, null);
        }


        public View getInfoWindow(Marker marker) {
            render(marker, mymarkerview);
            return mymarkerview;
        }

        public View getInfoContents(Marker marker) {
            return null;
        }

        private void render(Marker marker, View view) {
            TextView name = (TextView) view.findViewById(R.id.name);
            String name1 = marker.getTitle();
            name.setText(name1);

            String[] phoneAdd=marker.getSnippet().split(",");
            final String phone=phoneAdd[0];
            final String address=phoneAdd[1];

            final TextView address1 = (TextView) view.findViewById(R.id.address);
            address1.setText(address);

            TextView phone1 = (TextView) view.findViewById(R.id.phone);
            phone1.setText(phone);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                    startActivity(intent);
                }
            });



            // Add the code to set the required values
            // for each element in your custominfowindow layout file
        }
    }
}



        //TODO fix title pages
        //TODO onMarkerClick update listView


package charlyn23.c4q.nyc.omg;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import charlyn23.c4q.nyc.omg.model.Program;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapListFragment extends android.support.v4.app.Fragment {

    ListView listView;
    List<Program> list= new ArrayList<>();
            //= new ArrayList<Program>();
    ListAdapter adapter;
    public MapListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View fragmentView= inflater.inflate(R.layout.fragment_map_list, container, false);

        //rowView=getActivity().getLayoutInflater().inflate(R.layout.custom_location_list_view_item, null);
        listView=(ListView) fragmentView.findViewById(R.id.listView);
        adapter=new ListAdapter(getActivity(),R.id.location_name,list);
        listView.setAdapter(adapter);


        return fragmentView;
    }

    public void updateData(List<Program> eventDataList) {
       if(adapter!=null) {
           adapter.clear();
       }
        adapter.addAll(eventDataList);
        adapter.notifyDataSetChanged();
    }
    private class ListAdapter extends ArrayAdapter {
        public ListAdapter(Context context, int resource, List<Program> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = getActivity().getLayoutInflater().inflate(R.layout.custom_location_list_view_item, null);
                String name = list.get(position).getName();

                String address = list.get(position).getOffices().get(0).getAddress1();

                TextView nameView = (TextView) rowView.findViewById(R.id.location_name);
                nameView.setText(name);
                TextView addressView = (TextView) rowView.findViewById(R.id.location_address_field);
                addressView.setText(address);


            String phoneNumber = list.get(position).getNext_steps().get(0).getContact();
           TextView phoneNumberView = (TextView) rowView.findViewById(R.id.location_phone_number);
            phoneNumberView.setText(phoneNumber);
            String hours = "Monday: " + list.get(position).getOffices().get(0).getHours().getMonday_start() + " - " + list.get(position).getOffices().get(0).getHours().getMonday_finish() + "\nTuesday: " + list.get(position).getOffices().get(0).getHours().getTuesday_start() + " - " + list.get(position).getOffices().get(0).getHours().getTuesday_finish() + "\nWednesday: " + list.get(position).getOffices().get(0).getHours().getWednesday_start() + " - " + list.get(position).getOffices().get(0).getHours().getWednesday_finish() + "\nThursday: " + list.get(position).getOffices().get(0).getHours().getThursday_start() + " - " + list.get(position).getOffices().get(0).getHours().getThursday_finish() + "\nFriday: " + list.get(position).getOffices().get(0).getHours().getFriday_start() + " - " + list.get(position).getOffices().get(0).getHours().getFriday_finish();

            if (!list.get(position).getOffices().get(0).getHours().getSaturday_start().equals("")){

                hours += "\nSaturday: "+
            list.get(position).getOffices().get(0).getHours().getSaturday_start() + " - " + list.get(position).getOffices().get(0).getHours().getSaturday_finish();
        }
                   if(!list.get(position).getOffices().get(0).getHours().getSaturday_start().equals( ""))

        {
            hours += "\nSunday:" + list.get(position).getOffices().get(0).getHours().getSunday_start() + "-" + list.get(position).getOffices().get(0).getHours().getSunday_finish();
        }
            TextView hoursView=(TextView) rowView.findViewById(R.id.location_hours);
            hoursView.setText(hours);

            return rowView;

        }
    }

}


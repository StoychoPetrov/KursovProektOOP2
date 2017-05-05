package com.example.lubomir.kursovproektoop2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lubomir.kursovproektoop2.R;
import com.example.lubomir.kursovproektoop2.models.Flight;

import java.util.ArrayList;

public class FlightsAdapter extends ArrayAdapter<Flight> {

    //Adapter parameters
    Context context;
    ArrayList<Flight> mFlightList;
    LayoutInflater inflater;

    /**
     * Adapters constructor
     * @param context
     * @param flightList
     */
    public FlightsAdapter(Context context, ArrayList<Flight> flightList) {
        super(context, R.layout.flight_item, flightList);
        this.context = context;
        this.mFlightList = flightList;
        inflater = LayoutInflater.from(context);
    }

    /**
     * Create adapter's view and set flight text
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        ViewHolder viewHolder;
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.flight_item, null);
            viewHolder = new ViewHolder(itemView);

            itemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) itemView.getTag();
        }

        final Flight mFlightObject = mFlightList.get(position);

        viewHolder.mFromAirport.setText(mFlightObject.getFromAirport());
        viewHolder.mToAirport.setText(mFlightObject.getToAirport());
        viewHolder.mAirplane.setText(mFlightObject.getAirplane());
        viewHolder.mDate.setText(mFlightObject.getDate());
        viewHolder.mTime.setText(mFlightObject.getTime());
        if(mFlightObject.getFlightTimeString() != null
                && mFlightObject.getFlightTimeString().equals("Време:")){
            viewHolder.mFlightTime.setText(mFlightObject.getFlightTimeString());
        } else {
            viewHolder.mFlightTime.setText(mFlightObject.getFlightTime() + " часа");
        }

        return itemView;
    }

    /**
     * Creating ViewHolder , witch create adapter item elements
     */
    static class ViewHolder {
        public TextView mFromAirport, mToAirport, mAirplane, mDate, mTime, mFlightTime;

        public ViewHolder(View view) {
            mFromAirport = (TextView) view.findViewById(R.id.tvFromAirport);
            mToAirport = (TextView) view.findViewById(R.id.tvToAirport);
            mAirplane = (TextView) view.findViewById(R.id.tvAirplane);
            mDate = (TextView) view.findViewById(R.id.tvDate);
            mTime = (TextView) view.findViewById(R.id.tvTime);
            mFlightTime = (TextView) view.findViewById(R.id.tvMaxFlightTime);
        }
    }
}

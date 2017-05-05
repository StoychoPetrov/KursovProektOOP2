package com.example.lubomir.kursovproektoop2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lubomir.kursovproektoop2.R;
import com.example.lubomir.kursovproektoop2.models.Airplane;

import java.util.ArrayList;

public class AirplanesAdapter extends ArrayAdapter<Airplane> {

    //Adapter parameters
    Context context;
    ArrayList<Airplane> mAirplaneList;
    LayoutInflater inflater;

    /**
     * Adapters constructor
     * @param context
     * @param mAirplaneList
     */
    public AirplanesAdapter(Context context, ArrayList<Airplane> mAirplaneList) {
        super(context, R.layout.airplane_item, mAirplaneList);
        this.context = context;
        this.mAirplaneList = mAirplaneList;
        inflater = LayoutInflater.from(context);
    }

    /**
     * Create adapter's view and set airplane name
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
            itemView = inflater.inflate(R.layout.airplane_item, null);
            viewHolder = new ViewHolder(itemView);

            itemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) itemView.getTag();
        }

        final Airplane mAirplaneObject = mAirplaneList.get(position);

        viewHolder.mAirplaneName.setText(mAirplaneObject.getName());

        return itemView;
    }

    /**
     * Creating ViewHolder , witch create adapter item elements
     */
    static class ViewHolder {
        public TextView mAirplaneName;

        public ViewHolder(View view) {
            mAirplaneName = (TextView) view.findViewById(R.id.tvFromAirport);
        }
    }
}

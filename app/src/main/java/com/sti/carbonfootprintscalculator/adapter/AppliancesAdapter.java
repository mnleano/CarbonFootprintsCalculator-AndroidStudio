package com.sti.carbonfootprintscalculator.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sti.carbonfootprintscalculator.R;
import com.sti.carbonfootprintscalculator.model.AppliancesItem;

import java.util.ArrayList;
import java.util.Locale;


public class AppliancesAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<AppliancesItem> item;
    LayoutInflater inflater;

    public AppliancesAdapter(Context mContext, ArrayList<AppliancesItem> item) {
        super();
        this.mContext = mContext;
        this.item = item;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return item.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.item_appliances, parent, false);

        TextView tvAppliancesName = (TextView) v.findViewById(R.id.tvAppliancesName);
        tvAppliancesName.setText(item.get(position).getAppliancesName());

        TextView tvWattage = (TextView) v.findViewById(R.id.tvWattage);
        tvWattage.setText(String.valueOf(item.get(position).getWattage()));

        TextView tVHours = (TextView) v.findViewById(R.id.tvHours);
        tVHours.setText(String.valueOf(item.get(position).getHours()));

        TextView tvKWH = (TextView) v.findViewById(R.id.tvKWH);

        double kwh = (double) item.get(position).getKwh() / 1000;
        tvKWH.setText(String.format(Locale.getDefault(), "%.2f", kwh));

        return v;
    }

}

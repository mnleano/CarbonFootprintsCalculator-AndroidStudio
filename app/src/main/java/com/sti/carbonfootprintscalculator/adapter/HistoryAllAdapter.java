package com.sti.carbonfootprintscalculator.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.sti.carbonfootprintscalculator.R;
import com.sti.carbonfootprintscalculator.activity.HistoryAppliancesActivity;
import com.sti.carbonfootprintscalculator.activity.HistoryVehiclesActivity;
import com.sti.carbonfootprintscalculator.model.HistoryAllItem;
import com.sti.carbonfootprintscalculator.widget.TextviewGillSans;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HistoryAllAdapter extends BaseAdapter {

    Context ctx;
    List<HistoryAllItem> items;
    LayoutInflater inflater;

    public HistoryAllAdapter(Context ctx, List<HistoryAllItem> items) {
        super();
        this.ctx = ctx;
        this.items = items;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        // TODO Auto-generated method stub
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        // TODO Auto-generated method stub
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub

        final ViewHolder holder;

        if (view != null)
            holder = (ViewHolder) view.getTag();
        else {
            view = inflater.inflate(R.layout.item_history_all, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }


        final HistoryAllItem item = items.get(position);

        String emissionType[] = {"Appliances", "Vehicle"};
        final double emission = item.getEmission();
        final String sEmission;
        if (emission > 1000) {
            sEmission = String.format(Locale.getDefault(), "%.2f", emission / 1000) + " Ton";
        } else
            sEmission = String.format(Locale.getDefault(), "%.2f", emission) + " Kg";


        final String area = item.getArea();
        final String timeStamp = item.getTimeStamp();

        holder.tvArea.setText(item.getArea());
        holder.tvEmission.setText(sEmission);
        holder.tvDate.setText(emissionType[item.getEmissionType()]);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle extras = new Bundle();
                extras.putString("area", area);
                extras.putString("emission", sEmission);
                extras.putString("timeStamp", timeStamp);

                Intent intent = null;

                switch (item.getEmissionType()) {
                    case 0:
                        intent = new Intent(ctx, HistoryAppliancesActivity.class);
                        break;
                    case 1:
                        intent = new Intent(ctx, HistoryVehiclesActivity.class);
                        break;
                }

                if (intent != null) {
                    intent.putExtras(extras);
                    ctx.startActivity(intent);
                }
            }
        });

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.tvArea)
        TextviewGillSans tvArea;
        @Bind(R.id.tvEmission)
        TextviewGillSans tvEmission;
        @Bind(R.id.tvDate)
        TextviewGillSans tvDate;
        @Bind(R.id.container)
        LinearLayout container;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

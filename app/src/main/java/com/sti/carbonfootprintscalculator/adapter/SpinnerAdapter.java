package com.sti.carbonfootprintscalculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sti.carbonfootprintscalculator.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    Context ctx;
    List<String> list;

    LayoutInflater inflater;

    public SpinnerAdapter(Context ctx, List<String> list) {
        this.ctx = ctx;
        this.list = list;

        inflater = LayoutInflater.from(ctx);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_spinner, null);
        }
        TextView tvItem = (TextView) view.findViewById(R.id.tvItem);
        tvItem.setText(list.get(i));
        return view;
    }
}
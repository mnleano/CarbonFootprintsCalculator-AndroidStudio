package com.sti.carbonfootprintscalculator.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sti.carbonfootprintscalculator.DatabaseHelper;
import com.sti.carbonfootprintscalculator.R;
import com.sti.carbonfootprintscalculator.adapter.HistoryAllAdapter;
import com.sti.carbonfootprintscalculator.model.HistoryAllItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    String TAG = HistoryFragment.class.getSimpleName();

    @Bind(R.id.list)
    ListView list;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    List<HistoryAllItem> items;
    HistoryAllAdapter adapter;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);

        initData();
        return view;
    }

    private void initData() {
//        int page = getArguments().getInt("page", 0);
        dbHelper = DatabaseHelper.getInstance(getActivity());
        db = dbHelper.getReadableDatabase();

        items = new ArrayList<>();
        adapter = new HistoryAllAdapter(getActivity(), items);
        list.setAdapter(adapter);

        initList(getArguments().getInt("page", 0));
    }

    private void initList(int page) {
        switch (page) {
            case 0:
                initAllList();
                break;
            case 1:
                initAppliancesList();
                break;
            case 2:
                initVehicleList();
                break;
        }
    }

    private void initAllList() {
        cursor = db.query("tbl_emission_history", null, null, null, null, null, "emission_value desc");
        populateList(cursor);
    }

    private void initAppliancesList() {
        cursor = db.query("tbl_emission_history", null, "emission_type = ?", new String[]{"0"}, null, null, "emission_value desc");
        populateList(cursor);
    }

    private void initVehicleList() {
        cursor = db.query("tbl_emission_history", null, "emission_type = ?", new String[]{"1"}, null, null, "emission_value desc");
        populateList(cursor);
    }

    private void populateList(Cursor cursor) {
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String timeStamp = cursor.getString(0);
                int emissionType = cursor.getInt(1);
                double emission = cursor.getDouble(2);
                String area = cursor.getString(3);

                items.add(new HistoryAllItem(timeStamp, emissionType, emission, area));
            }
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

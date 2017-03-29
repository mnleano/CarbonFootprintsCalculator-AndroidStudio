package com.sti.carbonfootprintscalculator.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.sti.carbonfootprintscalculator.DatabaseHelper;
import com.sti.carbonfootprintscalculator.R;
import com.sti.carbonfootprintscalculator.adapter.AppliancesAdapter;
import com.sti.carbonfootprintscalculator.model.AppliancesItem;
import com.sti.carbonfootprintscalculator.widget.TextviewGillSans;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HistoryAppliancesActivity extends AppCompatActivity {

    @Bind(R.id.tvArea)
    TextviewGillSans tvArea;
    @Bind(R.id.tvEmission)
    TextviewGillSans tvEmission;
    @Bind(R.id.tvTimeStamp)
    TextviewGillSans tvTimeStamp;
    @Bind(R.id.textviewGillSans)
    TextviewGillSans textviewGillSans;
    @Bind(R.id.listAppliances)
    ListView listAppliances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_appliances);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
    }


    private void initData() {
        Bundle extras = getIntent().getExtras();
        String area = extras.getString("area");
        String emission = extras.getString("emission");
        String timeStamp = extras.getString("timeStamp");

        tvArea.setText(area);
        tvEmission.setText(emission);
        tvTimeStamp.setText(timeStamp);

        ArrayList<AppliancesItem> items = new ArrayList<>();
        AppliancesAdapter adapter = new AppliancesAdapter(this, items);
        listAppliances.setAdapter(adapter);

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("tbl_emission_household", null, "id = ?", new String[]{timeStamp}, null, null, null);
//        Cursor cursor = db.query("tbl_emission_household", null, null, null, null, null, null);
        Log.d("HistoryAppliances", "Count: " + cursor.getCount());

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                int wattage = cursor.getInt(2);
                int hours = cursor.getInt(3);
                int kWh = wattage * hours * 30;

                items.add(new AppliancesItem(name, wattage, hours, kWh));
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

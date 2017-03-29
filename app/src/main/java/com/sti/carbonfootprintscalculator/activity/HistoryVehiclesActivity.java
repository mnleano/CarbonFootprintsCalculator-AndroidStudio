package com.sti.carbonfootprintscalculator.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sti.carbonfootprintscalculator.DatabaseHelper;
import com.sti.carbonfootprintscalculator.R;
import com.sti.carbonfootprintscalculator.widget.TextviewGillSans;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HistoryVehiclesActivity extends AppCompatActivity {

    @Bind(R.id.tvArea)
    TextviewGillSans tvArea;
    @Bind(R.id.tvEmission)
    TextviewGillSans tvEmission;
    @Bind(R.id.tvTimeStamp)
    TextviewGillSans tvTimeStamp;
    @Bind(R.id.tvDistance)
    TextviewGillSans tvDistance;
    @Bind(R.id.tvFuel)
    TextviewGillSans tvFuel;
    @Bind(R.id.tvCar)
    TextviewGillSans tvCar;
    @Bind(R.id.tvYear)
    TextviewGillSans tvYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_vehicles);
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

        DatabaseHelper dbHelper = DatabaseHelper
                .getInstance(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("tbl_emission_vehicle", null, "id = ?",
                new String[]{timeStamp}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                tvDistance.setText(cursor.getString(1) + "KM");

                tvFuel.setText(cursor.getString(2));

                tvCar.setText(cursor.getString(3));

                tvYear.setText(cursor.getString(4));
            }
        }


    }
}

package com.sti.carbonfootprintscalculator.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.sti.carbonfootprintscalculator.DatabaseHelper;
import com.sti.carbonfootprintscalculator.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChartActivity extends AppCompatActivity {

    String TAG = ChartActivity.class.getSimpleName();

    @Bind(R.id.chart)
    BarChart chart;
    @Bind(R.id.activity_chart)
    RelativeLayout activityChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);
        populateChart();
    }

    private void populateChart() {

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int ctr = 0; ctr < 23; ctr++) {
            double emission = 0;
            cursor = db.query("tbl_emission_history", null, "area_id = ?", new String[]{String.valueOf(ctr)}, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
//                    Log.d(TAG, ctr + " Emission: " + emission + " add: " + cursor.getDouble(2));
                    emission += cursor.getDouble(2);
                }
            } else {
                emission = 0;
            }
            barEntries.add(new BarEntry((float) emission, ctr));
        }
        Log.d(TAG, "Entries: " + barEntries.size());
        BarDataSet bds = new BarDataSet(barEntries, "Carbon Emission");
        bds.setColor(Color.rgb(0, 255, 0));

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(bds);

        BarData data = new BarData(getMunicipalities(), dataSets);
        chart.setData(data);
        chart.setDescription("Cavite Carbon Emission Chart");
        chart.animateXY(3000, 3000);
        chart.getXAxis().setAdjustXLabels(true);
        chart.invalidate();
    }

    private ArrayList<String> getMunicipalities() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Alfonso");
        list.add("Amadeo");
        list.add("Bacoor");
        list.add("Carmona");
        list.add("Cavite City");
        list.add("Dasmariñas");
        list.add("General Emilio Aguinaldo");
        list.add("General Mariano Alvarez");
        list.add("General Trias");
        list.add("Imus");
        list.add("Indang");
        list.add("Kawit");
        list.add("Magallanes");
        list.add("Maragondon");
        list.add("Mendez");
        list.add("Naic");
        list.add("Noveleta");
        list.add("Rosario");
        list.add("Silang");
        list.add("Tagaytay");
        list.add("Tanza");
        list.add("Trece Martires");
        list.add("Ternate");
        return list;
    }
}

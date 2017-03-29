package com.sti.carbonfootprintscalculator.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.sti.carbonfootprintscalculator.DatabaseHelper;
import com.sti.carbonfootprintscalculator.NetworkUtils;
import com.sti.carbonfootprintscalculator.R;
import com.sti.carbonfootprintscalculator.adapter.AppliancesAdapter;
import com.sti.carbonfootprintscalculator.adapter.SpinnerAdapter;
import com.sti.carbonfootprintscalculator.api.ApiClient;
import com.sti.carbonfootprintscalculator.api.ApiInterface;
import com.sti.carbonfootprintscalculator.api.EmissionRequest;
import com.sti.carbonfootprintscalculator.builder.ToastBuilder;
import com.sti.carbonfootprintscalculator.model.AppliancesItem;
import com.sti.carbonfootprintscalculator.widget.ButtonGillSans;
import com.sti.carbonfootprintscalculator.widget.EditTextGillSans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppliancesActivity extends BaseActivity {

    String TAG = AppliancesActivity.class.getSimpleName();

    @Bind(R.id.spAppliances)
    Spinner spAppliances;
    @Bind(R.id.etWattage)
    EditTextGillSans etWattage;
    @Bind(R.id.etHours)
    EditTextGillSans etHours;
    @Bind(R.id.btnAdd)
    ButtonGillSans btnAdd;
    @Bind(R.id.listAppliances)
    ListView listAppliances;
    @Bind(R.id.spAreas)
    Spinner spAreas;
    @Bind(R.id.btnCalculate)
    ButtonGillSans btnCalculate;

    ArrayList<AppliancesItem> items;
    AppliancesAdapter adapter;

    List<String> spApplianceItems;
    List<String> spAreasItems;

    double totalKWH = 0;
    @Bind(R.id.container)
    LinearLayout container;

    /**
     * AIRCON=====  3500 WATTS
     * DESKTOP COMPUTER===== 80 WATTS
     * ELECTRICFAN===== 50 WATTTS
     * ELECTRIC KETTLE=== 3 WATTS
     * MICROWAVE===1200 WATTS
     * OVEN TOASTER==1700WATTS
     * 20-inch LCD TV ====45 WATTS
     *
     * @param savedInstanceState
     */

    String watts[] = {"3500", "80", "50", "3", "1200", "1700", "45"};
    int selectedArea = 0;
    List<String> listArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);
        ButterKnife.bind(this);

        Utils.hideSoftKeyboard(this, container);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();

    }

    private void initData() {
        SpinnerAdapter spAdapter;

        spApplianceItems = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.list_appliances)));
        spAdapter = new SpinnerAdapter(AppliancesActivity.this, spApplianceItems);
        spAppliances.setAdapter(spAdapter);
        spAppliances.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                etWattage.setText(watts[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listArea = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.list_cavite)));
        spAreasItems = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.list_cavite_with_distance)));
        spAdapter = new SpinnerAdapter(AppliancesActivity.this, spAreasItems);
        spAreas.setAdapter(spAdapter);
        spAreas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedArea = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        items = new ArrayList<>();
        adapter = new AppliancesAdapter(this, items);
        listAppliances.setAdapter(adapter);

    }


    @OnClick({R.id.btnAdd, R.id.btnCalculate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                onAddClick();
                break;
            case R.id.btnCalculate:
                onCalculateClick();
                break;
        }
    }


    private void onAddClick() {

        boolean isValid = true;

        String watt = etWattage.getText().toString();
        String hours = etHours.getText().toString();


        if (watt.equals("")) {
            ToastBuilder.createLongToast(this, "Enter your appliance electricity consumption!");
            isValid = false;
        }

        if (hours.equals("")) {
            ToastBuilder.createLongToast(this, "Enter your the average hours you used this appliance");
            isValid = false;
        }

        if (isValid) {
            addAppliance(Integer.parseInt(watt), Integer.parseInt(hours));
        }

    }

    private void addAppliance(int watt, int hours) {
        String applianceName = spApplianceItems.get(spAppliances.getSelectedItemPosition());
        Log.d(TAG, "Appliances name: " + applianceName);
        int kWh = watt * hours * 30;
        totalKWH += kWh;

        items.add(new AppliancesItem(applianceName, watt, hours, kWh));
        adapter.notifyDataSetChanged();

        spAppliances.setSelection(0);
        etHours.setText("");
        etWattage.setText("");

    }


    private void onCalculateClick() {

        if (selectedArea == 0)
            ToastBuilder.createLongToast(this, "Please select your area");
        else if (!(items.size() == 0))
            calculateEmission();
        else
            ToastBuilder.createLongToast(this, "Select and Add Appliances first!");

    }

    private void calculateEmission() {
        double ef = 0.50;
        double emission = totalKWH / 1000 * ef;

        addRecord(emission);
        clearData();
    }

    private void addRecord(double emission) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String id = Utils.getCurrentTime();
        Log.d(TAG, "ID: " + id);

        ContentValues values = new ContentValues();
        int area = selectedArea - 1;
        values.put("id", id);
        values.put("emission_type", 0);
        values.put("emission_value", emission);
        values.put("area", listArea.get(area));
        values.put("area_id", spAreas.getSelectedItemPosition());
        db.insert("tbl_emission_history", null, values);

        values.clear();

        for (int ctr = 0; ctr < items.size(); ctr++) {
            values.put("id", id);
            values.put("appliances_name", items.get(ctr).getAppliancesName());
            values.put("wattage", items.get(ctr).getWattage());
            values.put("hours", items.get(ctr).getHours());
            db.insert("tbl_emission_household", null, values);
            values.clear();
        }

        Log.d(TAG, "Municipality ID: " + area + " EMISSION: " + emission);

        if (NetworkUtils.isInternetAvailable(this) && area !=  23) {
            startProgressDialog("Processing...");
            postEmission(String.valueOf(selectedArea - 1), emission);
        } else {
            startActivity(new Intent(this, ResultActivity.class).putExtra("emission", emission));
        }
    }

    private void postEmission(String id, final double emission) {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = api.postEmission(new EmissionRequest(id, emission));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                stopProgressDialog();
                startActivity(new Intent(getApplicationContext(), ResultActivity.class).putExtra("emission", emission));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                stopProgressDialog();
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), ResultActivity.class).putExtra("emission", emission));
            }
        });
    }


    private void clearData() {
        spAppliances.setSelection(0);
        spAreas.setSelection(0);
        items.clear();
        adapter.notifyDataSetChanged();
        totalKWH = 0;
        etHours.setText("");
        etWattage.setText("");
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

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
import android.widget.Spinner;
import android.widget.Toast;

import com.sti.carbonfootprintscalculator.DatabaseHelper;
import com.sti.carbonfootprintscalculator.NetworkUtils;
import com.sti.carbonfootprintscalculator.R;
import com.sti.carbonfootprintscalculator.adapter.SpinnerAdapter;
import com.sti.carbonfootprintscalculator.api.ApiClient;
import com.sti.carbonfootprintscalculator.api.ApiInterface;
import com.sti.carbonfootprintscalculator.api.EmissionRequest;
import com.sti.carbonfootprintscalculator.builder.ToastBuilder;
import com.sti.carbonfootprintscalculator.widget.ButtonGillSans;
import com.sti.carbonfootprintscalculator.widget.EditTextGillSans;
import com.sti.carbonfootprintscalculator.widget.RadioButtonGillSans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehiclesActivity extends BaseActivity {

    String TAG = VehiclesActivity.class.getSimpleName();

    @Bind(R.id.etTraveled)
    EditTextGillSans etTraveled;
    @Bind(R.id.rbGasoline)
    RadioButtonGillSans rbGasoline;
    @Bind(R.id.rbDiesel)
    RadioButtonGillSans rbDiesel;
    @Bind(R.id.spVehicleType)
    Spinner spVehicleType;
    @Bind(R.id.spVehicleYear)
    Spinner spVehicleYear;
    @Bind(R.id.spAreas)
    Spinner spAreas;
    @Bind(R.id.btnCalculate)
    ButtonGillSans btnCalculate;
    @Bind(R.id.container)
    LinearLayout container;

    List<String> spVehicleItems;
    List<String> spYearItems;
    List<String> spAreasItems;

    int selectedArea = 0;
    List<String> listArea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles);
        ButterKnife.bind(this);

        Utils.hideSoftKeyboard(this, container);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();

    }

    private void initData() {
        SpinnerAdapter adapter;

        spVehicleItems = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.list_vehicle_type)));
        adapter = new SpinnerAdapter(this, spVehicleItems);
        spVehicleType.setAdapter(adapter);

        spYearItems = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.year_model)));
        adapter = new SpinnerAdapter(this, spYearItems);
        spVehicleYear.setAdapter(adapter);

        listArea = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.list_cavite)));
        spAreasItems = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.list_cavite_with_distance)));
        adapter = new SpinnerAdapter(this, spAreasItems);
        spAreas.setAdapter(adapter);
        spAreas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedArea = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @OnClick(R.id.btnCalculate)
    public void onClick() {

        if (selectedArea == 0) {
            ToastBuilder.createLongToast(this, "Please select your area");
        } else {
            String sKM = etTraveled.getText().toString();

            if (sKM.equals("")) {
                ToastBuilder.createLongToast(this, "Please enter total distance travelled");
            } else {

                int km = 0;
                String fuel;
                int fuelType;

                km = Integer.parseInt(sKM);

                if (rbDiesel.isChecked()) {
                    fuel = "Diesel";
                    fuelType = 0;
                } else {
                    fuel = "Gasoline";
                    fuelType = 1;
                }

                int carType = spVehicleType.getSelectedItemPosition();
                String type = spVehicleItems.get(carType);

                int yearModel = spVehicleYear.getSelectedItemPosition();
                String year = spYearItems.get(yearModel);

                Log.d("Vehicles", "Car Type: " + type + " : " + carType);
                Log.d("Vehicles", "Year Model: " + year + " : " + yearModel);
                double ef = 0;
                if (fuelType == 0) {
                    switch (carType) {
                        case 0:
                            ef = diesel0(yearModel);
                            break;
                        case 1:
                            ef = diesel1(yearModel);
                            break;
                        case 2:
                        case 3:
                            ef = diesel23(yearModel);
                            break;
                    }
                } else {
                    switch (carType) {
                        case 0:
                            ef = gas0(yearModel);
                            break;
                        case 1:
                        case 2:
                            ef = gas12(yearModel);
                            break;
                        case 3:
                            ef = gas3(yearModel);
                            break;
                    }
                }

                Log.d("Vehicles", "KM: " + km);
                Log.d("Vehicles", "Fuel Type: " + fuel);
                Log.d("Vehicles", "Car Type: " + type);
                Log.d("Vehicles", "Year Model: " + year);
                Log.d("Vehicles", "Emission Factor: " + ef);

                double emission = km * ef;
                addRecord(km, fuel, type, year, emission);
            }
        }
    }

    private void addRecord(int km, String fuel, String type, String year, double emission) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String id = Utils.getCurrentTime();

        ContentValues values = new ContentValues();

        values.put("id", id);
        values.put("emission_type", 1);
        values.put("emission_value", emission);
        values.put("area", listArea.get(selectedArea - 1));
        values.put("area_id", spAreas.getSelectedItemPosition());
        db.insert("tbl_emission_history", null, values);

        values.clear();
        values.put("id", id);
        values.put("km", km);
        values.put("fuelType", fuel);
        values.put("carType", type);
        values.put("yearModel", year);
        db.insert("tbl_emission_vehicle", null, values);
        values.clear();

        Log.d(TAG, "Municipality ID: " + (selectedArea - 1) + " EMISSION: " + emission);

        if (NetworkUtils.isInternetAvailable(this)) {
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
    private double gas0(int yearModel) {
        switch (yearModel) {
            case 0:
                return 0.0704;
            case 1:
                return 0.0531;
            case 2:
                return 0.0358;
            case 3:
                return .0272;
            case 4:
                return .0268;
            case 5:
                return .0249;
            case 6:
                return .0216;
            case 7:
                return .0178;
            case 8:
                return .0110;
            case 9:
                return .0107;
            case 10:
                return .0114;
            case 11:
                return .0145;
            case 12:
                return 0.0147;
            case 13:
                return .0161;
            case 14:
                return .0170;
            case 15:
                return .0172;
            default:
                return 0;
        }
    }

    private double gas12(int yearModel) {
        switch (yearModel) {
            case 0:
                return 0.0813;
            case 1:
                return 0.0646;
            case 2:
                return 0.0517;
            case 3:
            case 4:
                return 0.0452;
            case 5:
                return 0.0391;
            case 6:
                return 0.0321;
            case 7:
                return 0.0346;
            case 8:
                return 0.0151;
            case 9:
                return 0.0178;
            case 10:
                return 0.0155;
            case 11:
                return 0.0152;
            case 12:
                return 0.0157;
            case 13:
                return 0.0159;
            case 14:
                return 0.0161;
            case 15:
                return 0.0163;
            default:
                return 0;

        }
    }

    private double gas3(int yearModel) {
        switch (yearModel) {
            case 0:

            case 1:

            case 2:
                return 0.3246;
            case 3:
                return 0.1278;
            case 4:
                return 0.0924;
            case 5:
                return 0.0641;
            case 6:
                return 0.0578;
            case 7:
                return 0.0493;
            case 8:
                return 0.0528;
            case 9:
                return 0.0546;
            case 10:
                return 0.0533;
            case 11:
                return 0.0341;
            case 12:
                return 0.0326;
            case 13:
                return 0.0327;
            case 14:
                return 0.0330;
            case 15:
                return 0.0333;
            default:
                return 0;

        }
    }

    private double diesel0(int yearModel) {
        if (yearModel <= 1995)
            return 0.0006;
        else
            return 0.0005;
    }

    private double diesel1(int yearModel) {
        if (yearModel <= 1995)
            return 0.0009;
        else
            return 0.0010;
    }

    private double diesel23(int yearModel) {
        return 0.0051;
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

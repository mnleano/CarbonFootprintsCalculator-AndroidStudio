package com.sti.carbonfootprintscalculator.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sti.carbonfootprintscalculator.DatabaseHelper;
import com.sti.carbonfootprintscalculator.NetworkUtils;
import com.sti.carbonfootprintscalculator.R;
import com.sti.carbonfootprintscalculator.api.ApiClient;
import com.sti.carbonfootprintscalculator.api.ApiInterface;
import com.sti.carbonfootprintscalculator.api.TotalEmissionResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends BaseActivity {
//    static final LatLng bacoor = new LatLng(14.412932, 120.972697);
//    static final LatLng sineguelas = new LatLng(14.459319, 120.932316);

    GoogleMap map;

    LatLng alfonso = new LatLng(14.116175, 120.852441);
    LatLng amadeo = new LatLng(14.191333, 120.929249);
    LatLng bacoor = new LatLng(14.412932, 120.972697);
    LatLng carmona = new LatLng(14.297531, 121.038924);
    LatLng caviteCity = new LatLng(14.479398, 120.896536);
    LatLng dasmarinas = new LatLng(14.299919, 120.960939);
    LatLng genEmilio = new LatLng(14.193024, 120.790528);
    LatLng genMariano = new LatLng(14.307183, 121.013359);
    LatLng genTrias = new LatLng(14.322209, 120.905546);
    LatLng imus = new LatLng(14.405721, 120.939578);
    LatLng indang = new LatLng(14.193390, 120.871426);
    LatLng kawit = new LatLng(14.441323, 120.901195);
    LatLng magallanes = new LatLng(14.158011, 120.747618);
    LatLng maragondon = new LatLng(14.251438, 120.733403);
    LatLng mendez = new LatLng(14.131557, 120.902529);
    LatLng naic = new LatLng(14.297149, 120.792840);
    LatLng noveleta = new LatLng(14.427801, 120.879950);
    LatLng rosario = new LatLng(14.427801, 120.879950);
    LatLng silang = new LatLng(14.217035, 120.972327);
    LatLng tagaytay = new LatLng(14.113018, 120.959996);
    LatLng tanza = new LatLng(14.352789, 120.839174);
    LatLng treceMartires = new LatLng(14.287312, 120.861461);
    LatLng ternante = new LatLng(14.270996, 120.678251);

    /**
     * Good air
     * clean air
     * slightly polluted
     * polluted air
     * severe polluted
     */

    List<String> spAreasItems;
    private LatLng markerLatlong[] = {alfonso, amadeo, bacoor, carmona, caviteCity, dasmarinas, genEmilio,
            genMariano, genTrias, imus, indang, kawit, magallanes, maragondon, mendez, naic, noveleta, rosario,
            silang, tagaytay, tanza, treceMartires, ternante};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        spAreasItems = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.list_cavite)));
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        if (NetworkUtils.isInternetAvailable(this)) {
            requestEmissions();
        } else {
            populateMap();
        }
        CameraPosition position = CameraPosition.builder()
                .target(imus)
                .zoom(13f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();
        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);


//        if (map != null) {
//            map.addMarker(new MarkerOptions()
//                    .position(sineguelas)
//                    .title("Sineguelasan Barangay Hall")
//                    .snippet("Sineguelasan Barangay Hall")
//                    .icon(BitmapDescriptorFactory
//                            .fromResource(R.drawable.rsz_location_map_pin_green5)));
//        }

    }

    private void requestEmissions() {

        startProgressDialog("Downloading data...");
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<TotalEmissionResponse> call = api.getTotalEmission();
        call.enqueue(new Callback<TotalEmissionResponse>() {
            @Override
            public void onResponse(Call<TotalEmissionResponse> call, Response<TotalEmissionResponse> response) {
                if (response.isSuccessful()) {
                    stopProgressDialog();
                    TotalEmissionResponse ter = response.body();
                    populateMap(ter.getEmission());
                } else {
                    stopProgressDialog();
                    populateMap();
                }
            }

            @Override
            public void onFailure(Call<TotalEmissionResponse> call, Throwable t) {
                populateMap();
            }
        });

    }

    private void populateMap(double[] emission) {
        for (int ctr = 0; ctr < emission.length; ctr++) {
            createMarker(ctr, emission[ctr]);
        }
    }


    private void populateMap() {

        // ArrayList<Double> list = new ArrayList<>();
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;

        for (int ctr = 0; ctr < 23; ctr++) {
            double emission = 0;
            cursor = db.query("tbl_emission_history", null, "area_id = ?", new String[]{String.valueOf(ctr)}, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    emission += cursor.getDouble(2);
                }
            } else {
                emission = 0;
            }
//            list.add(emission);
            createMarker(ctr, emission);
        }


    }

    private void createMarker(int position, double emission) {

        String airQuality[] = {"Good Quality", "Clean Air", "Slightly Polluted", "Polluted Air", "Severe Polluted"};

        int marker;

        if (emission <= 500000)
            marker = 0;
        else if (emission > 500000 && emission <= 1000000)
            marker = 1;
        else if (emission > 1000000 && emission <= 2300000)
            marker = 2;
        else if (emission > 2300000 && emission <= 5500000)
            marker = 3;
        else
            marker = 4;

        String snippet = airQuality[marker] + " Emission: " + emission;

        if (map != null) {
            map.addMarker(new MarkerOptions()
                    .position(markerLatlong[position])
                    .title(spAreasItems.get(position))
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.fromBitmap(markerCreator(marker))))
                    .showInfoWindow();
        }
    }

    private Bitmap markerCreator(int type) {
//        int size = (int) (magnitude + 25) * 8;
        BitmapDrawable bitmapdraw;
        int size = 500;
//        if (magnitude <= 4.0)
//            bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.wave_moderate);
//        else
//
        int drawables[] = {R.drawable.smoke_good, R.drawable.smoke_clean, R.drawable.smoke_slight, R.drawable.smoke_polluted, R.drawable.smoke_severe};

        bitmapdraw = (BitmapDrawable) getResources().getDrawable(drawables[type]);

        Bitmap b = bitmapdraw.getBitmap();

        Bitmap smallMarker = Bitmap.createScaledBitmap(b, size, size, false);
        return smallMarker;

    }
}

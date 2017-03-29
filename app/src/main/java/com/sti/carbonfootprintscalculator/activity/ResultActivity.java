package com.sti.carbonfootprintscalculator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.sti.carbonfootprintscalculator.R;
import com.sti.carbonfootprintscalculator.widget.TextviewGillSans;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    @Bind(R.id.tvResult)
    TextviewGillSans tvResult;
    @Bind(R.id.tvHow)
    TextviewGillSans tvHow;

    String str = "Can reduce carbon footprint emission up to 10 kilograms if\n\n"
            + "Households took sustainable action:\n"
            + "Replacing lights with LED bulbs and turn off while not use. "
            + "One of the most important advancements in technology that can help lower CO2 emissions is with lighting. "
            + "You might not immediately associate light bulbs with Carbon Dioxide but when you switch on lights in your house, "
            + "this deadly greenhouse gas is produced in the generation of electricity that powers those light bulbs.\n\n "
            + "Walk to Work for a year Biking or walking to work as compared to driving. "
            + "Both of these offer enormous sustainable benefits and also health benefits s"
            + "uch as exercise and a period of time to relax and reduce stress on the way home. "
            + "This solution assumes ownership of a vehicle and biking or walking when able, "
            + "or else the 'Exclusive Sustainable Transportation' solution is more appropriate";

    String str2 = "Can reduce carbon footprint  emission  up 20  kilograms if\n\n "
            + "Exclusive Sustainable Transportation for a year this solution means to "
            + "use sustainable transportation as a replacement for non-sustainable forms such as a personal vehicle. "
            + "Those who choose to ride public transportation reduce their carbon footprint and "
            + "conserve energy by eliminating travel that would have otherwise been made in a private vehicle. "
            + "The result is fewer vehicle miles of travel and reduced emissions.\n\n "
            + "Replace an old appliance with an efficient model for a year Older, inefficient, appliances waste a surprising amount of energy. "
            + "For example, the average fridge manufactured before 1993 uses 1,000 kWh/year compared to a new model which can use as little as 500 kWh/year.";

    String str3 = "Can reduce carbon footprint  emission  up to 50 kilograms if\n\n"
            + "Unplug your phone charger (and other appliances) when not in use. "
            + "According to the US Department of Energy, appliances consume a significant amount of electricity even when they are switched off. "
            + "Even if they are turned off, electronics consume energy when they are plugged in. "
            + "By pulling the plug when something is not in use you could save 1,000 lbs of carbon dioxide a year.\n\n"
            + "Switch to a clean energy provider for a year Your home does not get energy directly from the clean provider, rather your payments purchase the clean electricity, "
            + "which has a slightly higher cost than dirty electricity, and is then fed onto the normal electricity grid.";

    String str4 = "Can reduce carbon footprint emission up 60 kilograms and Above if\n\n"
            + "Plant more trees\n\nTrees are the best technology to suck carbon dioxide from the atmosphere and reverse global warming:\n"
            + "Forests balance the Earth�s water-cycle essential for cooling our climate. "
            + "In addition, Trees play another vital role in stabilising the climate by sucking carbon dioxide (CO2) "
            + "out of the atmosphere and fixing it into soils and biomass 50% of a tree�s biomass is carbon which remains stored, "
            + "acting as a �carbon sink�, unless the tree decays or is burned. ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
    }

    private void initData() {
        double emission = getIntent().getExtras().getDouble("emission");
        String emi;
        if (emission > 1000) {
            emi = String.format(Locale.getDefault(), "%.2f", emission / 1000) + " Ton";
        } else
            emi = String.format(Locale.getDefault(), "%.2f", emission) + " Kg";
        tvResult.setText(emi);

        if (emission < 10)
            tvHow.setText(str);
        else if (emission < 20)
            tvHow.setText(str2);
        else if (emission < 50)
            tvHow.setText(str3);
        else
            tvHow.setText(str4);
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

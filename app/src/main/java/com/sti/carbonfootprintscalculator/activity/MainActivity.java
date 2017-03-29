package com.sti.carbonfootprintscalculator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sti.carbonfootprintscalculator.R;
import com.sti.carbonfootprintscalculator.widget.ButtonGillSans;
import com.sti.carbonfootprintscalculator.widget.TextviewGillSans;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tvTitle)
    TextviewGillSans tvTitle;
    @Bind(R.id.btnAppliances)
    ButtonGillSans btnAppliances;
    @Bind(R.id.btnVechicle)
    ButtonGillSans btnVechicle;
    @Bind(R.id.btnHistory)
    ButtonGillSans btnHistory;
    @Bind(R.id.btnReport)
    ButtonGillSans btnReport;
    @Bind(R.id.btnMap)
    ButtonGillSans btnMap;
    @Bind(R.id.btnGraph)
    ButtonGillSans btnGraph;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnAppliances, R.id.btnVechicle, R.id.btnHistory, R.id.btnReport, R.id.btnMap, R.id.btnGraph})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAppliances:
                startActivity(new Intent(getApplicationContext(), AppliancesActivity.class));
                break;
            case R.id.btnVechicle:
                startActivity(new Intent(getApplicationContext(), VehiclesActivity.class));
                break;
            case R.id.btnHistory:
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                break;
            case R.id.btnReport:
                onReportClick();
                break;
            case R.id.btnMap:
                startActivity(new Intent(getApplicationContext(), MapActivity.class));
                break;
            case R.id.btnGraph:
                startActivity(new Intent(getApplicationContext(), ChartActivity.class));
                break;
        }
    }

    private void onReportClick() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL,
                new String[]{"ored4a@yahoo.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Carbon Footprint report");
        i.putExtra(Intent.EXTRA_TEXT, "");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "There are no email clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

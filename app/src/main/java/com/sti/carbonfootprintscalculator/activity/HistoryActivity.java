package com.sti.carbonfootprintscalculator.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.sti.carbonfootprintscalculator.R;
import com.sti.carbonfootprintscalculator.adapter.HistoryPagerAdapter;
import com.sti.carbonfootprintscalculator.sliding.SlidingTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity {

    @Bind(R.id.tabs)
    SlidingTabLayout tabs;
    @Bind(R.id.pager)
    ViewPager pager;

    CharSequence titles[] = {"All", "Appliances", "Vehicles"};
    HistoryPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupPager();
    }

    private void setupPager() {
        adapter = new HistoryPagerAdapter(getSupportFragmentManager(), titles);
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }
        });
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

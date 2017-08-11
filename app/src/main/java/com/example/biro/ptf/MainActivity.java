package com.example.biro.ptf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DividerDrawerItem test = new DividerDrawerItem();
        setSupportActionBar(toolbar);
        PrimaryDrawerItem events = new PrimaryDrawerItem();
        SecondaryDrawerItem meeting = new SecondaryDrawerItem();
        SecondaryDrawerItem camps = new SecondaryDrawerItem();
        SecondaryDrawerItem dayUse = new SecondaryDrawerItem();
        events.withName("Events");
        meeting.withName("Meetings");
        camps.withName("Camps");
        dayUse.withName("DayUser");
        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAnimateDrawerItems(true)
                .withDisplayBelowToolbar(true)

                .addDrawerItems(events, meeting, camps, dayUse, test)
                .build();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }
}

package com.example.biro.ptf;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle("PTF");
        toolbar.setTitleTextColor(Color.WHITE);


        SecondaryDrawerItem meeting = new SecondaryDrawerItem();
        SecondaryDrawerItem camps = new SecondaryDrawerItem();
        SecondaryDrawerItem dayUse = new SecondaryDrawerItem();
        SecondaryDrawerItem notifications = new SecondaryDrawerItem();
        DividerDrawerItem d1 = new DividerDrawerItem();


        meeting.withName("Meetings");
        camps.withName("Camps");
        dayUse.withName("DayUser");
        notifications.withName("Notifications");
//        AccountHeader headerResult = new AccountHeaderBuilder()
//                .withActivity(this)
//                .withHeaderBackground(R.color.primary)
//                .withProfileImagesClickable(true);


        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withAnimateDrawerItems(true)
                .addDrawerItems( meeting,d1, camps, d1,dayUse,d1,notifications,d1).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        switch (position) {
                            case 0:
                                MeetingFrag mF = new MeetingFrag();
                                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                                ft = fragmentManager.beginTransaction();
                                ft.replace(R.id.fragContainer, mF).commit();
                                toolbar.setTitle("Meetings");
                                break;
                            case 6:
                                NotificationsFrag notificationsFrag = new NotificationsFrag();
                                fragmentManager = getSupportFragmentManager();
                                ft = fragmentManager.beginTransaction();
                                ft.replace(R.id.fragContainer,notificationsFrag).commit();
                                toolbar.setTitle("Notifications");

                            default:
                                Log.d("error on click", "onItemClick: wrong item");
                                break;
                        }
                        return false;
                    }
                })
                .build();

    }
}

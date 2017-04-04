package com.hq.demoviewpagerandtabhost;

import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.Toast;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {
    private ViewPager viewPager;
    private List<Fragment> list;
    private TabHost tabHost;

    private Socket mSocket;


    //    {
//        try {
//            mSocket = IO.socket("http://192.168.56.1:3000");
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Socket getSocket() {
//        return mSocket;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MSocket ms = new MSocket();
        mSocket = ms.getSocket();
//        mSocket.connect();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Chit Chat");
        toolbar.setNavigationIcon(R.mipmap.c_logo);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Chit", Toast.LENGTH_LONG).show();
            }
        });

        list = new ArrayList<>();
        list.add(new MFragment1());
        list.add(new MFragment2());
        list.add(new MFragment3());

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MFragmentAdapterPager(list, getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(this);


        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("tab1").setIndicator("Friends");
        tabSpec1.setContent(new Content());
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab2").setIndicator("More");
        tabSpec2.setContent(new Content());
        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("tab3").setIndicator("List online");
        tabSpec3.setContent(new Content());


        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);
        tabHost.addTab(tabSpec3);

        tabHost.setOnTabChangedListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        viewPager.setCurrentItem(tabHost.getCurrentTab());
    }

    class Content implements TabHost.TabContentFactory {

        @Override
        public View createTabContent(String tag) {
            FrameLayout layout = new FrameLayout(MainActivity.this);
            return layout;
        }
    }
}

package com.zubin.ewriter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.zubin.ewriter.R;
import com.zubin.ewriter.adapter.NavigationAdapter;
import com.zubin.ewriter.ui.contents.ContentFragment;
import com.zubin.ewriter.ui.settings.SettingsActivity;
import com.zubin.ewriter.utils.Settings;


public class MainActivity extends ActionBarActivity  {

    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView mRecyclerView;
    private NavigationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Settings mSets;

    private String[] mTitles;
    private int[] mIcons = new int[]{R.mipmap.all, R.mipmap.settings};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mSets = Settings.getsInstance(this);
        if (mSets.getBoolean(Settings.KEY_FIRST_SCREEN, false) ) {
            startActivity(new Intent(this, WriterActivity.class));

        }
        setSupportActionBar(mToolBar);

    }

    private void initView() {
        mTitles = new String[]{getString(R.string.all), getString(R.string.settings)};

        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };


        // init RecyclerView Drawer
        mRecyclerView = (RecyclerView) findViewById(R.id.drawer_revcycler_view);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new NavigationAdapter(mTitles, mIcons);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter.setOnItemClickListener(new NavigationAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                guideMethod(position);
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, new ContentFragment()).commit();
    }

    private void guideMethod(int position) {
        Intent intent = null;
        if (position == 1) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
        if (position == 2) {
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

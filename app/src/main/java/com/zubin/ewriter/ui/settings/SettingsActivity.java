package com.zubin.ewriter.ui.settings;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.zubin.ewriter.R;

/**
 * Created by zubin on 15/6/3.
 */
public class SettingsActivity extends ActionBarActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mToolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getFragmentManager().beginTransaction().replace(R.id.settings, new SettingsFragment()).commit();
    }
}

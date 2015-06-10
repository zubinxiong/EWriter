package com.zubin.ewriter.ui.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AlertDialog;
import android.webkit.WebView;
import android.widget.Toast;

import com.zubin.ewriter.R;
import com.zubin.ewriter.utils.Settings;

/**
 * Created by zubin on 15/6/3.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private Preference pref_weibo, pref_rate, pref_about;
    private SwitchPreference pref_first;

    private Settings mSets;


    public SettingsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        mSets = Settings.getsInstance(getActivity().getApplicationContext());

        pref_weibo = findPreference("sina_weibo");
        pref_first = (SwitchPreference) findPreference("first_screen");
        pref_rate = findPreference("rate_me");
        pref_about = findPreference("about");

        pref_weibo.setOnPreferenceClickListener(this);
        pref_rate.setOnPreferenceClickListener(this);
        pref_about.setOnPreferenceClickListener(this);

        pref_first.setChecked(mSets.getBoolean(Settings.KEY_FIRST_SCREEN, false));
        pref_first.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Boolean b = (Boolean) newValue;
                mSets.putBoolean(Settings.KEY_FIRST_SCREEN, b);
                pref_first.setChecked(b);
                return true;
            }
        });

        getActivity().setTitle(R.string.title_setting);
    }


    private void openWebSite(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == pref_weibo) {
            openWebSite(getString(R.string.item_weibo_author_url));
            return true;
        }
        if (preference == pref_rate) {
            Uri uri = Uri.parse("market://details?id="
                    + getActivity().getApplicationContext().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(goToMarket);
            } catch (Exception e) {
                Toast.makeText(getActivity(), R.string.no_market, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (preference == pref_about) {
            showAboutDialog();
            return true;
        }

        return false;
    }

    private void showAboutDialog() {
        WebView v = new WebView(getActivity());
        v.loadUrl("file:///android_asset/about.html");

        new AlertDialog.Builder(getActivity())
                .setView(v)
                .setNegativeButton(R.string.msg_close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Noting to do
                    }
                })
                .show();
    }
}

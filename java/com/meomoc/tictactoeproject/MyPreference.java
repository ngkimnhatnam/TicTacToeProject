package com.meomoc.tictactoeproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.widget.Toast;

public class MyPreference extends PreferenceActivity {

    public static final String KEY = "key";
    public String pref_name = "MyPref";
    public static final String COLOR_KEY = "color_key";
    public static int BG_COLOR = 0;

    public static final String ICON = "icon";
    public static final String ICON_KEY = "icon_key";
    public static int BG_ICON = 0;

    public static final String ICON_2 = "icon_2";
    public static final String ICON_KEY_2 = "icon_2_key";
    public static int BG_ICON_2 = 0;

    public static final String BUTTON_COLOR = "button_color";
    public static final String BUTTON_COLOR_KEY = "button_color_key";
    public static int BG_BUTTON = 0;

    public static final String SOUND = "sound_control";
    public static final String SOUND_KEY = "sound_key";
    public static int BG_SOUND = 0;

    public int mode = Activity.MODE_PRIVATE;
    ListPreference bgColor, bgIcon,bgIcon_2, bgButtonColor, bgSound;
    //String[] icon_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //Intent intent = getIntent();

        //This gets the string array from array.xml file
       // icon_list = getResources().getStringArray(R.array.icon_entry);

        addPreferencesFromResource(R.xml.preference);
        PreferenceScreen pref = getPreferenceScreen();

        Context context = getApplicationContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        BG_COLOR = preferences.getInt(MyPreference.COLOR_KEY, BG_COLOR);
        BG_ICON = preferences.getInt(MyPreference.ICON_KEY, BG_ICON);
        BG_ICON_2 = preferences.getInt(MyPreference.ICON_KEY_2,BG_ICON_2);
        BG_BUTTON = preferences.getInt(MyPreference.BUTTON_COLOR_KEY, BG_BUTTON);
        BG_SOUND = preferences.getInt(MyPreference.SOUND_KEY, BG_SOUND);


        //This part adds list preference to background color selection
        bgColor = (ListPreference) pref.findPreference(KEY);
        bgColor.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int i = Integer.parseInt(newValue.toString());
                BG_COLOR = i;
                return true;
            }
        });

        //This part adds list preference for player 1 icon selection
        bgIcon = (ListPreference) pref.findPreference(ICON);
        bgIcon.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                int i = Integer.parseInt(o.toString());
                BG_ICON = i;
                return true;
            }
        });

        //This part adds list preference for button color selection
        bgButtonColor = (ListPreference) pref.findPreference(BUTTON_COLOR);
        bgButtonColor.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                int i = Integer.parseInt(o.toString());
                BG_BUTTON = i;
                return true;
            }
        });

        //This part adds list preference for sound selection
        bgSound = (ListPreference) pref.findPreference(SOUND);
        bgSound.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {

                int i = Integer.parseInt(o.toString());
                BG_SOUND = i;
                return true;
            }
        });

        //This part adds list preference for player 2 icon selection
        bgIcon_2 = (ListPreference) pref.findPreference(ICON_2);
        bgIcon_2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {

                int i= Integer.parseInt(o.toString());
                BG_ICON_2 =i;
                return true;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

       savePreference();
    }

    //This saves preferences chosen to the class variables
    public void savePreference(){

        Context context = getApplicationContext();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(MyPreference.COLOR_KEY, BG_COLOR);
        editor.putInt(MyPreference.ICON_KEY, BG_ICON);
        editor.putInt(MyPreference.ICON_KEY_2,BG_ICON_2);
        editor.putInt(MyPreference.BUTTON_COLOR_KEY, BG_BUTTON);
        editor.putInt(MyPreference.SOUND_KEY, BG_SOUND);
        editor.commit();

    }

}

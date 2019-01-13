package com.meomoc.tictactoeproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button play, settings;
    int icon_chosen;
    final CharSequence[] items = {"Black","White", "Blue", "Green", "Red", "Yellow"};
    final String[] color = {"#202020","#FDFCFF","#122096", "#018A10", "#C22A07", "#FFE509"};
    final String[] button_color = {"#00023C","#A6FF89","#FBFFD6","#FFF36E","#E8A9D3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.play);
        settings = findViewById(R.id.settings);

        loadPreference();
    }

    public void goPlay(View view){

        Intent intent = new Intent(MainActivity.this,playGame.class);
        startActivity(intent);
    }

    public void goSettings(View view){

        Intent intent = new Intent( MainActivity.this, Settings.class);
        startActivity(intent);
    }

    //This loads preferences saved in MyPreference class
    public void loadPreference(){
        Context context = getApplicationContext();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        int color_item = pref.getInt(MyPreference.COLOR_KEY, MyPreference.BG_COLOR);
        paintBackground(color_item);

        int button_color = pref.getInt(MyPreference.BUTTON_COLOR_KEY, MyPreference.BG_BUTTON);
        paintButtons(button_color);

        icon_chosen = pref.getInt(MyPreference.ICON_KEY,MyPreference.BG_ICON);
    }

    //This paints the background with a color set by preference
    public void paintBackground(int item){
        View mainLayout = findViewById(R.id.main_menu);
        View root = mainLayout.getRootView();
        new Color();
        root.setBackgroundColor(Color.parseColor(color[item]));
    }

    //This paints the buttons
    public void paintButtons(int item){

        play.setBackgroundColor(Color.parseColor(button_color[item]));
        settings.setBackgroundColor(Color.parseColor(button_color[item]));

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreference();
    }
}

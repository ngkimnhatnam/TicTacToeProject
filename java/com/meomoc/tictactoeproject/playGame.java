package com.meomoc.tictactoeproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;


public class playGame extends AppCompatActivity {

    boolean count=true;
    boolean win= false;
    int[] gamestate = {4,4,4,4,4,4,4,4,4};
    int place;
    final Context context = this;
    int[] icon_list = { R.drawable.bluechip, R.drawable.diamond, R.drawable.pirate_flag, R.drawable.chest};
    int[] icon_2_list ={ R.drawable.redcross, R.drawable.angrybird, R.drawable.muumipeikko, R.drawable.simpson};
    int sound_on = 0;
    int icon_picked, icon_2_picked;

    ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9;
    ImageView[] list_of_slots;
    int[] current_gamestate;
    int slot1=4;int slot2=4;int slot3=4;int slot4=4;int slot5=4;int slot6=4;int slot7=4;int slot8=4;int slot9=4;
    Button button;
    final String[] button_color = {"#00023C","#A6FF89","#FBFFD6","#FFF36E","#E8A9D3"};

    @Override
    public void onBackPressed() {
       saveGameState();
       Intent intent = new Intent(this, MainActivity.class);
       finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveGameState();
    }

    //This saves the game state to preferences
    public void saveGameState(){
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("Slot 1", gamestate[0]);
        editor.putInt("Slot 2", gamestate[1]);
        editor.putInt("Slot 3", gamestate[2]);
        editor.putInt("Slot 4", gamestate[3]);
        editor.putInt("Slot 5", gamestate[4]);
        editor.putInt("Slot 6", gamestate[5]);
        editor.putInt("Slot 7", gamestate[6]);
        editor.putInt("Slot 8", gamestate[7]);
        editor.putInt("Slot 9", gamestate[8]);
        editor.commit();
    }

    //Loads the current game state with paused game state
    public void loadOldGame(){
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        slot1 = pref.getInt("Slot 1", slot1);
        slot2 = pref.getInt("Slot 2", slot2);
        slot3 = pref.getInt("Slot 3", slot3);
        slot4 = pref.getInt("Slot 4", slot4);
        slot5 = pref.getInt("Slot 5", slot5);
        slot6 = pref.getInt("Slot 6", slot6);
        slot7 = pref.getInt("Slot 7", slot7);
        slot8 = pref.getInt("Slot 8", slot8);
        slot9 = pref.getInt("Slot 9", slot9);
        current_gamestate = new int[] {slot1,slot2,slot3,slot4,slot5,slot6,slot7,slot8,slot9};
    }

    public void loadFreshGame(){
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        slot1 = pref.getInt("Slot 1", slot1);
        slot2 = pref.getInt("Slot 2", slot2);
        slot3 = pref.getInt("Slot 3", slot3);
        slot4 = pref.getInt("Slot 4", slot4);
        slot5 = pref.getInt("Slot 5", slot5);
        slot6 = pref.getInt("Slot 6", slot6);
        slot7 = pref.getInt("Slot 7", slot7);
        slot8 = pref.getInt("Slot 8", slot8);
        slot9 = pref.getInt("Slot 9", slot9);
        current_gamestate = new int[] {slot1,slot2,slot3,slot4,slot5,slot6,slot7,slot8,slot9};
    }

    //This sets tokens to its places saved in game state
    public void setTokenToItsPlace(){
        for (int i =0; i<current_gamestate.length; i++) {
            if (current_gamestate[i]==0){
                gamestate[i]=0;
                list_of_slots[i].setTranslationY(-1500f);
                list_of_slots[i].setImageResource(icon_list[icon_picked]);
                list_of_slots[i].animate().translationYBy(1500f).setDuration(300);
                count=false;

            }
            else if (current_gamestate[i]==1){
                gamestate[i]=1;
                list_of_slots[i].setTranslationY(-1500f);
                list_of_slots[i].setImageResource(icon_2_list[icon_2_picked]);
                list_of_slots[i].animate().translationYBy(1500f).setDuration(300);
                count=true;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        button= findViewById(R.id.reset);
        img1 = findViewById(R.id.imageView1);img2 = findViewById(R.id.imageView2);img3=findViewById(R.id.imageView3);img4=findViewById(R.id.imageView4);img5=findViewById(R.id.imageView5);img6=findViewById(R.id.imageView6);img7=findViewById(R.id.imageView7);img8=findViewById(R.id.imageView8);img9=findViewById(R.id.imageView9);
        list_of_slots = new ImageView[]{img1,img2,img3,img4,img5,img6,img7,img8,img9};
        loadOldGame();
        loadPreference();
        setTokenToItsPlace();


        Intent intent = new Intent(this, MediaService.class);
        if (sound_on==0){
            context.startService(intent);
        }

    }
    public void loadPreference(){

        Context context = getApplicationContext();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        sound_on = pref.getInt(MyPreference.SOUND_KEY,MyPreference.BG_SOUND);
        icon_picked = pref.getInt(MyPreference.ICON_KEY,MyPreference.BG_ICON);
        icon_2_picked = pref.getInt(MyPreference.ICON_KEY_2,MyPreference.BG_ICON_2);

        int reset_button_color = pref.getInt(MyPreference.BUTTON_COLOR_KEY,MyPreference.BG_BUTTON);
        paintResetButton(reset_button_color);

    }
    public void paintResetButton(int item){
        button.setBackgroundColor(Color.parseColor(button_color[item]));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(this, MediaService.class);
        context.stopService(intent);
    }

    //This handles relocation of tokens to where it is clicked and also game logic
    public void middle(View view) {
        ImageView counter = (ImageView) view;
        button = (Button) findViewById(R.id.reset);

        if (win) {
            Toast.makeText(this, "Game done!", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Play again?", Toast.LENGTH_SHORT).show();

        }

        else {
            if (count) {
                place = Integer.parseInt(counter.getTag().toString());
                if (gamestate[place] == 4) {
                    counter.setTranslationY(-1500f);
                    counter.setImageResource(icon_list[icon_picked]);
                    counter.animate().translationYBy(1500f).setDuration(300);
                    gamestate[place] = 0;
                    count = false;
                    if (gamestate[0] + gamestate[1] + gamestate[2] == 0 ||
                            gamestate[3] + gamestate[4] + gamestate[5] == 0 ||
                            gamestate[6] + gamestate[7] + gamestate[8] == 0 ||
                            gamestate[0] + gamestate[4] + gamestate[8] == 0 ||
                            gamestate[2] + gamestate[4] + gamestate[6] == 0 ||
                            gamestate[0] + gamestate[3] + gamestate[6] == 0 ||
                            gamestate[1] + gamestate[4] + gamestate[7] == 0 ||
                            gamestate[2] + gamestate[5] + gamestate[8] == 0) {
                        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
                        button.setVisibility(View.VISIBLE);
                        win=true;
                    }
                    //else clause for restarting after a draw should go here

                } else {
                    Toast.makeText(this, "Already ticked", Toast.LENGTH_SHORT).show();
                }
            } else {
                place = Integer.parseInt(counter.getTag().toString());
                if (gamestate[place] == 4) {
                    counter.setTranslationY(-1500f);
                    counter.setImageResource(icon_2_list[icon_2_picked]);
                    counter.animate().translationYBy(1500f).setDuration(300);
                    gamestate[place] = 1;
                    count = true;
                    if (gamestate[0] + gamestate[1] + gamestate[2] == 3 ||
                            gamestate[3] + gamestate[4] + gamestate[5] == 3 ||
                            gamestate[6] + gamestate[7] + gamestate[8] == 3 ||
                            gamestate[0] + gamestate[4] + gamestate[8] == 3 ||
                            gamestate[2] + gamestate[4] + gamestate[6] == 3 ||
                            gamestate[0] + gamestate[3] + gamestate[6] == 3 ||
                            gamestate[1] + gamestate[4] + gamestate[7] == 3 ||
                            gamestate[2] + gamestate[5] + gamestate[8] == 3) {
                        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
                        button.setVisibility(View.VISIBLE);
                        win=true;
                    }
                } else {
                    Toast.makeText(this, "Already ticked", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //This resets the board (images and values)
    public void reset(View view) {

        int i=gamestate.length;
        int x;
        for (x=0; x<i; x++) {
            gamestate[x] = 4;
        }
        slot1=4;int slot2=4;int slot3=4;int slot4=4;int slot5=4;int slot6=4;int slot7=4;int slot8=4;int slot9=4;
        saveGameState();
        loadFreshGame();

        for (int y =0; y< list_of_slots.length; y++){
            list_of_slots[y].setImageResource(0);
        }

        count = true;
        win = false;
        Log.i("Button", "Button worked");
        button.setVisibility(View.INVISIBLE);
        //Toast.makeText(MainActivity.this, Arrays.toString(gamestate), Toast.LENGTH_SHORT).show();

    }
}

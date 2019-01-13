package com.meomoc.tictactoeproject;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    Button notification;Button background;Button progress;Button preference;ProgressDialog pd;ProgressTask pt = null;
     private int ID = 1;

     final CharSequence[] items = {"Black","White", "Blue", "Green", "Red", "Yellow"};
     final String[] color = {"#202020","#FDFCFF","#122096", "#018A10", "#C22A07", "#FFE509"};
     final String[] button_color = {"#00023C","#A6FF89","#FBFFD6","#FFF36E","#E8A9D3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notification = (Button)this.findViewById(R.id.start_notification);
        background = (Button)this.findViewById(R.id.change_background);
        progress = (Button)this.findViewById(R.id.progress);
        preference = (Button)this.findViewById(R.id.preference);

                  notification.setOnClickListener(new View.OnClickListener() {
                      public void onClick(View v) {
                          //startNotification();
                      }});

                  background.setOnClickListener(new View.OnClickListener() {
                      public void onClick(View v) {
                          showDialog(0);
                      }});

                  progress.setOnClickListener(new View.OnClickListener() {
                      public void onClick(View v) {
                          showDialog(1);
                      }});

                  preference.setOnClickListener(new View.OnClickListener() {
                      public void onClick(View v) {
                          Intent intent = new Intent(Settings.this, MyPreference.class);
                          startActivity(intent);
                      }});
    loadPreference();

    }

    @Override
     protected void onResume() {
              super.onResume();
              loadPreference();
            }

            //This handles Notification button and its job
              /*private void startNotification(){
                Intent intent = new Intent(Home.this, NotificationView.class);
                intent.putExtra("ID", ID);

                PendingIntent pendingIntent =
                        PendingIntent.getActivity(Home.this, 0, intent, 0);

                NotificationManager nm = (NotificationManager)
                            getSystemService(NOTIFICATION_SERVICE);
                    Notification notif = new Notification(
                            R.drawable.ball,"Reminder: Game starts in 15 minutes",
                            System.currentTimeMillis());
                    CharSequence from = "Sports Center";
                    CharSequence message = "Game: L.A Lakers vs Miami Heat";
                    notif.setLatestEventInfo(this, from, message, pendingIntent);

                    notif.vibrate = new long[] { 100, 250, 100, 500};

                    nm.notify(ID, notif);
              }*/

              //This handles progress bar
              //Set background color via Dialog, not--
              @Override
               protected Dialog onCreateDialog(int id) {
                   final CharSequence[] items = {"Black","White", "Blue", "Green", "Red", "Yellow"};
                   final String[] color = {"#202020","#FDFCFF","#122096", "#018A10", "#C22A07", "#FFE509"};
                   switch(id) {
                       case 0:
                           return new AlertDialog.Builder(this)
                                   .setTitle("Set Background Color")
                                   .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog, int item) {
                                           View mainLayout = findViewById(R.id.main_layout);
                                           View root = mainLayout.getRootView();
                                           new Color();
                                           root.setBackgroundColor(Color.parseColor(color[item]));
                                           dismissDialog(0);
                                       }
                                   }).create();
                       case 1:
                           pd = new ProgressDialog(Settings.this);
                           pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                           pd.setMessage("Downloading...");
                           return pd;
                 }
                 return null;
               }
               @Override
               protected void onPrepareDialog(int id, Dialog dialog) {
                   switch(id) {
                       case 1:
                           pd.setProgress(0);
                           pt = new ProgressTask(handler);
                           pt.start();
                   }
               }
               final Handler handler = new Handler() {
                   public void handleMessage(Message msg) {
                       int count = msg.arg1;
                       pd.setProgress(count);
                       if (count >= 100){
                           dismissDialog(1);
                           pt.setStatus(false);
                       }
                   }
               };
              //--saved into preference

                //This handles the progress bar
                private class ProgressTask extends Thread {
                   Handler myHandler;
                   int count = 0;
                   boolean status = true;

                   ProgressTask(Handler _handler) {
                       myHandler = _handler;
                   }
                   public void run() {
                       while (status) {
                           try {
                               Thread.sleep(100);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                           Message msg = myHandler.obtainMessage();
                           msg.arg1 = count;
                           myHandler.sendMessage(msg);
                           count++;
                       }
                   }
                   public void setStatus(boolean m){
                       status = m;
                   }
               }


               public void loadPreference(){

                   Context context = getApplicationContext();
                   SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

                   int color_item = pref.getInt(MyPreference.COLOR_KEY, MyPreference.BG_COLOR);
                   paintBackground(color_item);

                   int button_color_item = pref.getInt(MyPreference.BUTTON_COLOR_KEY,MyPreference.BG_BUTTON);
                   paintButtons(button_color_item);


               }

               public void paintBackground(int item){
                   View mainLayout = findViewById(R.id.main_layout);
                   View root = mainLayout.getRootView();
                   new Color();
                   root.setBackgroundColor(Color.parseColor(color[item]));
               }

               public void paintButtons(int item){
                    notification.setBackgroundColor(Color.parseColor(button_color[item]));
                    background.setBackgroundColor(Color.parseColor(button_color[item]));
                    progress.setBackgroundColor(Color.parseColor(button_color[item]));
                    preference.setBackgroundColor(Color.parseColor(button_color[item]));

               }

}



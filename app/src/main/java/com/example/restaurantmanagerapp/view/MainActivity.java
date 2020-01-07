package com.example.restaurantmanagerapp.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.restaurantmanagerapp.R;
import com.example.restaurantmanagerapp.tasks.RestaurantTasks;
import com.example.restaurantmanagerapp.util.HandlerUtil;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView thread1Tv, thread2Tv, thread3Tv;
    ProgressBar threadBar1, threadBar2, threadBar3;
    Button addButton;

    private String [] names;
    private ArrayList<Runnable> nameList = new ArrayList<>();
    Handler handler = new Handler();
    private Long thread1, thread2, thread3;
    private int counter = 0;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalSeconds = intent.getIntExtra("total_seconds",0);
            int secondsLeft = intent.getIntExtra("seconds_left",0);
            String name = intent.getStringExtra("name");
            Long threadId = intent.getLongExtra("thread_id", 0);

            Log.d("TAG_X", name+ " "+threadId);

            if(thread1 == null)
                thread1 = threadId;

            if(thread2 == null && thread1 != threadId)
                thread2 = threadId;

            if(thread3 == null && thread1 != threadId && thread2 != threadId)
                thread3 = threadId;

            setProgressBar(name, totalSeconds, secondsLeft, threadId);
        }
    };

    private void setProgressBar(String name, int totalSeconds, int secondsLeft, Long threadId){
        //Log.d("TAG_X", name+ " "+threadId);

       if(threadId.equals(thread1)){
           thread1Tv.setText(name);

           switch(totalSeconds) {
               case 5:
                   threadBar1.setProgress(100 - (secondsLeft*20));
                   break;
               case 4:
                   threadBar1.setProgress(100 - (secondsLeft*25));
                   break;
               case 3:
                   threadBar1.setProgress(100 - (secondsLeft*33));
                   break;
               case 2:
                   threadBar1.setProgress(100 - (secondsLeft*50));
                   break;
               case 1:
                   threadBar1.setProgress(100 - (secondsLeft*100));
                   break;
           }

           if(secondsLeft == 1){
               threadBar1.setProgress(100);
           }
       }

       else if(threadId.equals(thread2)){
           thread2Tv.setText(name);
           switch(totalSeconds) {
               case 5:
                   threadBar2.setProgress(100 - (secondsLeft*20));
                   break;
               case 4:
                   threadBar2.setProgress(100 - (secondsLeft*25));
                   break;
               case 3:
                   threadBar2.setProgress(100 - (secondsLeft*33));
                   break;
               case 2:
                   threadBar2.setProgress(100 - (secondsLeft*50));
                   break;
               case 1:
                   threadBar2.setProgress(100 - (secondsLeft*100));
                   break;
           }

           if(secondsLeft == 1){
               threadBar2.setProgress(100);
           }

           else if(secondsLeft == 0)
               threadBar2.setProgress(0);
       }

       else if(threadId.equals(thread3)){
           thread3Tv.setText(name);
           switch(totalSeconds) {
               case 5:
                   threadBar3.setProgress(100 - (secondsLeft*20));
                   break;
               case 4:
                   threadBar3.setProgress(100 - (secondsLeft*25));
                   break;
               case 3:
                   threadBar3.setProgress(100 - (secondsLeft*33));
                   break;
               case 2:
                   threadBar3.setProgress(100 - (secondsLeft*50));
                   break;
               case 1:
                   threadBar3.setProgress(100 - (secondsLeft*100));
                   break;
           }

           if(secondsLeft == 1){
               threadBar3.setProgress(100);
           }
       }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thread1Tv = findViewById(R.id.progress_1_tv);
        thread2Tv = findViewById(R.id.progress_2_tv);
        thread3Tv = findViewById(R.id.progress_3_tv);

        threadBar1 = findViewById(R.id.progress_1_bar);
        threadBar2 = findViewById(R.id.progress_2_bar);
        threadBar3 = findViewById(R.id.progress_3_bar);

        addButton = findViewById(R.id.add_button);

        registerReceiver(receiver, new IntentFilter("progress.intent"));

        Resources res = getResources();
        names = res.getStringArray(R.array.names);

        addButton.setOnClickListener(new View.OnClickListener() {
            Random randomNum = new Random();
            @Override
            public void onClick(View v) {
                if(counter >= names.length-3)
                    counter = 0;

                for(int i = 0; i < names.length;i++){
                    //Log.d("TAG_X", counter+" "+names[i]);
                    nameList.add(new RestaurantTasks(names[i], randomNum.nextInt(5) + 1, getApplicationContext()));
                }

                HandlerUtil handlerUtil = new HandlerUtil(handler, nameList);
                handlerUtil.runTasks();

                counter += 3;
                //nameList.clear();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}

package com.example.restaurantmanagerapp.tasks;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.core.content.ContextCompat;

public class RestaurantTasks implements Runnable {
    private String name;
    private int seconds;
    private Context appContext;

    public RestaurantTasks(String name, int seconds, Context appContext) {
        this.name = name;
        this.seconds = seconds;
        this.appContext = appContext;
    }

    @Override
    public void run() {
        for(int i =0 ; i < seconds; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                //Log.d("TAG_X", ""+ Thread.currentThread().getId());
            //Log.d("TAG_X", name + " " + seconds);

            Intent progressIntent = new Intent("progress.intent");
            progressIntent.putExtra("total_seconds", seconds);
            progressIntent.putExtra("seconds_left", seconds - i);
            progressIntent.putExtra("name", name);
            progressIntent.putExtra("thread_id", Thread.currentThread().getId());

            appContext.sendBroadcast(progressIntent);
        }
        //Log.d("TAG_X", name + " is done with task");
    }
}

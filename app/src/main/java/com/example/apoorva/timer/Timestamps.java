package com.example.apoorva.timer;

import android.util.Log;

/**
 * Created by Apoorva on 10/5/2016.
 */

public class Timestamps {
    private Clock[] times;
    private int timeCtr;

    public Timestamps () {
        times = new Clock[7];
        for (int i = 0; i < 7; i++){
            times[i] = new Clock();
            times[i].setSeconds(0);
            times[i].setMinutes(0);
            times[i].setHours(0);
        }
        timeCtr = 0;
    };

    public void addTime(int seconds, int minutes, int hours){
        Clock tempClock = new Clock();
        tempClock.setSeconds(seconds);
        tempClock.setMinutes(minutes);
        tempClock.setHours(hours);
        if (timeCtr > 6){
            moveTimes();
            times[6] = tempClock;
        }
        else {
            times[timeCtr] = tempClock;
            timeCtr++;
        }
    }

    private void moveTimes(){
        for (int i = 0; i < 6; i++){
            times[i] = times[i+1];
        }
    }

    public void clearTimes(){

        for (int i = 0; i < timeCtr; i++){
            times[i].setSeconds(0);
            times[i].setMinutes(0);
            times[i].setHours(0);
        }
        timeCtr = 0;
    }

    public String[] getTimeStamps(){
        String [] timestamps = new String[timeCtr];
        for (int i = 0; i < timeCtr; i++){
            timestamps[i] = getTime(times[i].getSeconds(),times[i].getMinutes(),times[i].getHours());
        }
        return timestamps;
    }

    private String getTime(int seconds, int minutes, int hours) {
        String time = "";
        if (hours <= 9) time = time.concat("0");
        time = time.concat(Integer.toString(hours));
        time = time.concat(":");
        if (minutes <= 9) time = time.concat("0");
        time = time.concat(Integer.toString(minutes));
        time = time.concat(":");
        if (seconds <= 9) time = time.concat("0");
        time = time.concat(Integer.toString(seconds));
        return time;
    }

    public int getTimeCtr(){
        return timeCtr;
    }
}

package com.example.apoorva.timer;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ControlFragment.OnFragmentInteractionListener, ListFragment.OnFragmentInteractionListener, View.OnClickListener {

    TextView seconds;
    TextView minutes;
    TextView hours;
    TextView time;
    Button start;
    Button timestamps;
    Timestamps listOfTimes;
    Clock clock;
    String [] times;
    String [] tempTime;
    boolean clockStopped;
    boolean reset;
    ClockAsyncTask clockAsyncTask;

    static final String THREAD_STATUS="thread status";
    static final String SECONDS_UPDATE="seconds";
    static final String MINUTES_UPDATE="minutes";
    static final String HOURS_UPDATE="hours";
    static final String CLOCK_UPDATE="false";
    static final String BUTTON_STATUS="button status";

    protected void onDestroy() {
        if (clockAsyncTask!=null&&clockAsyncTask.getStatus()== AsyncTask.Status.RUNNING) {
            clockAsyncTask.cancel(true);
            clockAsyncTask=null;
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time = (TextView)findViewById(R.id.time);
        start = (Button) findViewById(R.id.start);
        clock = new Clock();
        times = new String[7];
        clockStopped = true;
        reset = false;
        clockAsyncTask = new ClockAsyncTask();
        listOfTimes = new Timestamps();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            timestamps = (Button) findViewById(R.id.seeTimes);
            timestamps.setOnClickListener(this);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            Bundle extras = data.getExtras();

            clock.setSeconds(extras.getInt("SECONDS"));
            clock.setMinutes(extras.getInt("MINUTES"));
            clock.setHours(extras.getInt("HOURS"));
            clockStopped = extras.getBoolean("CLOCK_STOPPED");
            reset = false;

            time.setText(getTime(clock.getSeconds(),clock.getMinutes(),clock.getHours()));

            clockAsyncTask = new ClockAsyncTask();
            clockAsyncTask.execute();

        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.seeTimes){

            times = listOfTimes.getTimeStamps();

            Intent intent = new Intent(this,Main2Activity.class);
            Bundle extras = new Bundle();

            extras.putInt("LENGTH", times.length);
            extras.putInt("SECONDS", clock.getSeconds());
            extras.putInt("MINUTES", clock.getMinutes());
            extras.putInt("HOURS", clock.getHours());
            extras.putBoolean("CLOCK_STOPPED", clockStopped);
            extras.putBoolean("RESET", reset);

            for(int i = 0; i < times.length; i++){
                extras.putString("TIME".concat(Integer.toString(i)), times[i]);
            }

            reset = true;
            clockAsyncTask.cancel(false);
            clockAsyncTask = null;
            intent.putExtras(extras);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLapClicked() {
        ListFragment listFragment = (ListFragment)getSupportFragmentManager().findFragmentById(R.id.listFrag);
        if(!reset){
            listOfTimes.addTime(clock.getSeconds(), clock.getMinutes(), clock.getHours());
            if(listFragment!=null && listFragment.isInLayout()){
                listFragment.setTimes(listOfTimes);
            }
        }
    }

    @Override
    public void onResetClicked() {
        reset = true;
        clockStopped = true;
        start.setText("Start");
        time.setText(getTime(0,0,0));

        listOfTimes.clearTimes();

        ListFragment listFragment = (ListFragment)getSupportFragmentManager().findFragmentById(R.id.listFrag);
        if(listFragment!=null && listFragment.isInLayout()){
            listFragment.setTimes(listOfTimes);
        }
    }

    @Override
    public void onStartClicked() {
        if(clockStopped) {
            clockStopped = false;
            reset = false;
            start.setText("Stop");
            if (clockAsyncTask!=null && clockAsyncTask.getStatus()!=AsyncTask.Status.RUNNING) {
                clockAsyncTask = new ClockAsyncTask();
                clockAsyncTask.execute();
            }
        }
        else {
            start.setText("Start");
            clockStopped = true;
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (clockAsyncTask!=null&&clockAsyncTask.getStatus()==AsyncTask.Status.RUNNING) {
            outState.putBoolean(THREAD_STATUS,true);
            reset = true;
            clockAsyncTask.cancel(true);
        }else{
            outState.putBoolean(THREAD_STATUS,false);
        }

        tempTime = listOfTimes.getTimeStamps();
        for(int i = 0; i < tempTime.length; i++){
            outState.putString("TIME".concat(Integer.toString(i)), tempTime[i]);
        }
        outState.putInt("LENGTH",tempTime.length);
        outState.putInt(SECONDS_UPDATE,clock.getSeconds());
        outState.putInt(MINUTES_UPDATE,clock.getMinutes());
        outState.putInt(HOURS_UPDATE,clock.getHours());
        outState.putBoolean(CLOCK_UPDATE,clockStopped);
        outState.putString(BUTTON_STATUS,start.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        boolean isRunning=savedInstanceState.getBoolean(THREAD_STATUS);
        clock = new Clock();
        if(isRunning){
            reset = false;
            clockStopped = savedInstanceState.getBoolean(CLOCK_UPDATE);
            clock.setSeconds(savedInstanceState.getInt(SECONDS_UPDATE));
            clock.setMinutes(savedInstanceState.getInt(MINUTES_UPDATE));
            clock.setHours(savedInstanceState.getInt(HOURS_UPDATE));
            start.setText(savedInstanceState.getString(BUTTON_STATUS));
            clockAsyncTask = new ClockAsyncTask();
            clockAsyncTask.execute();
        }
        else {
            reset = true;
        }
        String curTime = getTime(clock.getSeconds(),clock.getMinutes(),clock.getHours());
        time.setText(curTime);

        tempTime = new String[savedInstanceState.getInt("LENGTH")];
        for (int i = 0; i < tempTime.length; i++){
            tempTime[i] = savedInstanceState.getString("TIME".concat(Integer.toString(i)));
        }
        String[] separated_time = new String[3];
        Log.i("LENGTH:" , Integer.toString(tempTime.length));
        for (int i = 0; i < tempTime.length; i++){
            separated_time = tempTime[i].split(":");
            listOfTimes.addTime(Integer.parseInt(separated_time[2]), Integer.parseInt(separated_time[1]), Integer.parseInt(separated_time[0]));
        }

        ListFragment listFragment = (ListFragment)getSupportFragmentManager().findFragmentById(R.id.listFrag);
        if(listFragment!=null && listFragment.isInLayout() && !reset){
            listFragment.setTimes(listOfTimes);
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    private class ClockAsyncTask extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            int interval = 1000;
            while(!reset){
                if(!clockStopped){
                    try {
                            clock.increment();
                            publishProgress(clock.getSeconds(),
                                    clock.getMinutes(),
                                    clock.getHours());
                            Thread.sleep(interval);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            String restartTime = getTime(0, 0, 0);
            time.setText(restartTime);
            clock.setHours(0);
            clock.setMinutes(0);
            clock.setSeconds(0);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            String curTime = getTime(values[0], values[1], values[2]);
            time.setText(curTime);
            super.onProgressUpdate(values);
        }
    }

}


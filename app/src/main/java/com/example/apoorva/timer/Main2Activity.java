package com.example.apoorva.timer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    String []times;
    TextView time1;
    TextView time2;
    TextView time3;
    TextView time4;
    TextView time5;
    TextView time6;
    TextView time7;
    Button timer;
    Clock clock;
    ClockAsyncTask clockAsyncTask;
    Boolean clockStopped;
    Boolean reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }
        setContentView(R.layout.fragment_list);

        timer = (Button) findViewById(R.id.seeTimer);
        timer.setOnClickListener(this);

        time1 = (TextView) findViewById(R.id.time1);
        time2 = (TextView) findViewById(R.id.time2);
        time3 = (TextView) findViewById(R.id.time3);
        time4 = (TextView) findViewById(R.id.time4);
        time5 = (TextView) findViewById(R.id.time5);
        time6 = (TextView) findViewById(R.id.time6);
        time7 = (TextView) findViewById(R.id.time7);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        times = new String[extras.getInt("LENGTH")];
        for (int i = 0; i < times.length; i++){
            times[i] = extras.getString("TIME".concat(Integer.toString(i)));
        }

        if (times.length > 0 ) time1.setText(times[0]);
        if (times.length > 1 ) time2.setText(times[1]);
        if (times.length > 2 ) time3.setText(times[2]);
        if (times.length > 3 ) time4.setText(times[3]);
        if (times.length > 4 ) time5.setText(times[4]);
        if (times.length > 5 ) time6.setText(times[5]);
        if (times.length > 6 ) time7.setText(times[6]);

        clock = new Clock();
        clock.setSeconds(extras.getInt("SECONDS"));
        clock.setMinutes(extras.getInt("MINUTES"));
        clock.setHours(extras.getInt("HOURS"));
        clockStopped = extras.getBoolean("CLOCK_STOPPED");
        Log.i("CLOCK_STOPPED", Boolean.toString(clockStopped));
        reset = extras.getBoolean("RESET");
        Log.i("RESET", Boolean.toString(reset));

        clockAsyncTask = new ClockAsyncTask();
        clockAsyncTask.execute();

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("SECONDS", clock.getSeconds());
        extras.putInt("MINUTES", clock.getMinutes());
        extras.putInt("HOURS", clock.getHours());
        extras.putBoolean("CLOCK_STOPPED", clockStopped);
        intent.putExtras(extras);
        setResult(RESULT_OK, intent);
        reset = true;
        clockAsyncTask.cancel(false);
        clockAsyncTask=null;
        finish();
        return;
    }

/*    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Intent intent = new Intent(this, MainActivity.class);
            Bundle extras = new Bundle();
            extras.putInt("SECONDS", clock.getSeconds());
            extras.putInt("MINUTES", clock.getMinutes());
            extras.putInt("HOURS", clock.getHours());
            extras.putBoolean("CLOCK_STOPPED", clockStopped);
            intent.putExtras(extras);
            setResult(RESULT_OK, intent);
            reset = true;
            clockAsyncTask.cancel(false);
            clockAsyncTask=null;
            finish();
            return;
        }
    }*/

    private class ClockAsyncTask extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            int interval = 1000;
            if(!clockStopped){
                while(!reset){
                    try {
                        clock.increment();
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
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("PROGRESS",Integer.toString(clock.getSeconds()));
            super.onProgressUpdate(values);
        }
    }

}

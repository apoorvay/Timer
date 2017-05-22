package com.example.apoorva.timer;

/**
 * Created by Apoorva on 10/3/2016.
 */
public class Clock {

    int seconds = 0;
    int minutes = 0;
    int hours = 0;

    public Clock () {};

    public int getSeconds() {return seconds;}
    public int getMinutes() {return minutes;}
    public int getHours() {return hours;}

    public void setSeconds(int seconds) {this.seconds = seconds;}
    public void setMinutes(int minutes) {this.minutes = minutes;}
    public void setHours(int hours) {this.hours = hours;}

    public void increment(){

        if(this.seconds>=59){
            if(this.minutes>=59){
                this.hours++;
                this.minutes=0;
            }
            else {
                this.minutes++;
            }
            this.seconds=0;
        }
        else {
            this.seconds++;
        }
    }
}



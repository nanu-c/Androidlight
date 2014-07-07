package org.nanuc.androidlight.app;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;



public class Main extends Activity {
    final AndroidLight artnetClass = new AndroidLight();



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_androlight);



        SeekBar seek1 = (SeekBar) findViewById(R.id.seek1);
        SeekBar seek2 = (SeekBar) findViewById(R.id.seek2);
        SeekBar seek3 = (SeekBar) findViewById(R.id.seek3);
        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (artnetClass.run!=2) artnetClass.run=2;
                artnetClass.wertR=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (artnetClass.run!=2) artnetClass.run=2;
                artnetClass.wertG=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seek3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (artnetClass.run!=2) artnetClass.run=2;
                artnetClass.wertB=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button button = (Button) findViewById(R.id.buttonA1);

        button.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {

                if (artnetClass.run==1) artnetClass.run=0;
                else {
                    artnetClass.run=1;}

            }
        });
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //start the artnet server
        new Thread(new Runnable() {
            public void run(){
                artnetClass.artnet_start();
            }
        }).start();

    }

    @Override
    protected void onDestroy(){}
}

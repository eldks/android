package com.example.timer2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Chronometer cm;
    Button startButton, pauseButton, stopButton;
    String record;
    boolean running;
    private long pauseOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cm = findViewById(R.id.timer);
        startButton = findViewById(R.id.btnStart);
        pauseButton = findViewById(R.id.btnPause);
        stopButton = findViewById(R.id.btnStop);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running){
                    view.setVisibility(View.GONE);
                    pauseButton.setVisibility(View.VISIBLE);
                    stopButton.setVisibility(View.VISIBLE);

                    Intent serviceIntent = new Intent(MainActivity.this, MyTimerService.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        startForegroundService(serviceIntent);
                    else startService(serviceIntent);

                    cm.setBase(SystemClock.elapsedRealtime());
                    cm.start();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = !running;
                if (running){
                    cm.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - cm.getBase() ;
                    pauseButton.setText("계속");
                } else {
                    cm.setBase(SystemClock.elapsedRealtime() - pauseOffset );
                    cm.start();
                    pauseButton.setText("중지");
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.GONE);
                startButton.setVisibility(View.VISIBLE);

                record = "오늘 하루 공부한 시간은 " + cm.getText() + "입니다!";
                Toast.makeText(getApplicationContext(), record, Toast.LENGTH_LONG).show();
                cm.setBase(SystemClock.elapsedRealtime());
                cm.stop();
                pauseOffset = 0;
            }
        });
    }
}
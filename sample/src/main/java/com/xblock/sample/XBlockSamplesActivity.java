package com.xblock.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Activity to hold different samples of Components
 */
public class XBlockSamplesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xblock_samples);

        Button btnVideoXBlock = (Button) findViewById(R.id.btn_launch_video_xblock);
        btnVideoXBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoXBlockActivity = new Intent(XBlockSamplesActivity.this, XBlockSampleActivity.class);
                startActivity(videoXBlockActivity);
            }
        });

        Button btnLongAnswerXBlock = (Button) findViewById(R.id.btn_launch_long_answer_xblock);
        btnLongAnswerXBlock.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent longAnswerXBlockActivity = new Intent(XBlockSamplesActivity.this, XBlockLongAnswerSampleActivity.class);
                startActivity(longAnswerXBlockActivity);
            }
        });
    }

}

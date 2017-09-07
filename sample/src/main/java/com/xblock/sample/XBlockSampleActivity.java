package com.xblock.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class XBlockSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xblock_sample);

        XBlockSampleFragment mainFragment = XBlockSampleFragment.newInstance
                ("5pYWsyOn2JpXqegtZIVztPieF6Ev", "lmcGE1YzE6iXRFGtVGCqDV-ZXqOsWzqa",
                        "http://ooyala.com");
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame, mainFragment).commit();
    }
}

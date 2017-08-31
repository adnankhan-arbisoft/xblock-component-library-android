package com.xblock.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Sample Activity to hold the Long-Answer XBlock Fragment
 */
public class XBlockLongAnswerSampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // using same layout as in XBlockSampleActivity as we just need to replace the fragment
        setContentView(R.layout.activity_xblock_sample);

        XBlockLongAnswerSampleFragment fragment = null;

        // Sample Data to show on UI
        String title = "Like it or not, we all face problems that we need to solve immediately.";
        String description = "Because of the compressed timeframe, the actions we take can have an outsized impact. " +
                "Despite moving quickly and connecting with several partners, Brian was unfortunately not very successful. " +
                "What do you think went wrong? \n\n" +
                "In the field below, write one or two sentences describing what you think Brain did wrong. When you are " +
                "finished, choose \'Submit\' to compare your response to an experts.";
        fragment = XBlockLongAnswerSampleFragment.newInstance(title, description);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }
}

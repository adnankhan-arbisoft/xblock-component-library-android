package com.xblock.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

/**
 * Sample Activity for Raw HTML XBlock
 */
public class XBlockRawHtmlSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // using same layout as in XBlockSampleActivity as we just need to replace the fragment
        setContentView(R.layout.activity_xblock_sample);

        RawHtmlXBlockSampleFragment fragment = null;

        // Sample Data to show on UI
        // String studentViewUrl = "https://courses.qa.mckinsey.edx.org/xblock/i4x://ag/cs/html/51db3ba1167648668c9049fa1c7e8f64";
        String rawHtml = "<h1>Get some quality sleep</h1>\\n\\n<p>Working a long day and then <em>burning the midnight oil</em> is a recipe for <i>fatigue</i> and <i>sickness</i>, especially if you need to be back on the job early the next morning. To recover properly, make sure you’re getting at least 8 hours of quality sleep: keep your room cool, dark, and quiet and try to avoid eating too close to bedtime.</p>\\n";
        fragment = RawHtmlXBlockSampleFragment.newInstance("", "", "", "", rawHtml);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }
}

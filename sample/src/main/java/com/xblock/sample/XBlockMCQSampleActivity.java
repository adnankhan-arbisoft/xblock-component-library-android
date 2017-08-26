package com.xblock.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.mckinsey.academy.xblocks.model.MCQOption;

import java.util.ArrayList;
import java.util.List;

import static com.xblock.sample.XBlockSamplesActivity.IS_MULTI_SELECT_ENABLE;

/**
 * Sample Activity to demonstrate the use of MCQXBlock for both MCQs and MRQs
 */

public class XBlockMCQSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xblock_sample);

        XBlockMCQSampleFragment xBlockMCQSampleFragment = XBlockMCQSampleFragment.newInstance(
                "While many of the tools and techniques discussed in this lesson might be useful, "
                        + "which of the below do you think could have the most impact on Mel?",
                getListOfOptions(), getIntent().getBooleanExtra(IS_MULTI_SELECT_ENABLE, false)
        );
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame, xBlockMCQSampleFragment).commit();
    }

    private List<MCQOption> getListOfOptions() {
        List<MCQOption> options = new ArrayList<>();
        options.add(new MCQOption("Creating a SMART problem demo question" +
                " answer one text flowing onto multiple lines.", ""));
        options.add(new MCQOption("Using the Problem Statement Workshop, " +
                "I can’t see the rest of the screenshot text answer.", ""));
        options.add(new MCQOption("Key Assumptions check.", ""));
        options.add(new MCQOption("Key Assumptions check.", ""));
        options.add(new MCQOption("Key Assumptions check.", ""));
        return options;
    }
}
package com.xblock.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Sample Activity for Raw HTML XBlock
 */
public class XBlockRawHtmlSampleActivity extends AppCompatActivity {

    private static final String TAG = XBlockRawHtmlSampleActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // using same layout as in XBlockSampleActivity as we just need to replace the fragment
        setContentView(R.layout.activity_xblock_sample);

        RawHtmlXBlockSampleFragment fragment = null;

        // Sample Data to show on UI
        // String studentViewUrl = "https://courses.qa.mckinsey.edx.org/xblock/i4x://ag/cs/html/51db3ba1167648668c9049fa1c7e8f64";
        String rawHtml = "<p></p>\\n<table style=\\\"border: none;padding-bottom:0px\\\">\\n<tbody>\\n<tr>\\n<td width=\\\"185\\\" style=\\\"vertical-align:top\\\"><img alt=\\\"Next steps\\\" src=\\\"/static/MAPS_L1_nextsteps.png\\\" width=\\\"185\\\" /></td>\\n<td style=\\\"vertical-align:top\\\">\\n<p>In this lesson, you've been introduced to some fundamentals of problem solving, including:</p>\\n<ul><ul type=\\\"disc\\\">\\n<li>Problem solving ability is a critical component of professional success, and there are focused ways to improve your skill.</li>\\n<li>Problems can be categorized in a number of ways (analytical vs. conceptual, convergent vs. divergent). One practical means of thinking about how to solve problems is to consider the time you have available to work on them.</li>\\n  </ul></ul>\\n</td>\\n</tr>\\n</tbody>\\n</table>\\n\\n<p><b>Try it now. Put these new skills to work right away:</b></p>\\n<ul> <ul type=\\\"disc\\\"><li><b>Identify your problem. </b>Think about a problem you have to tackle. Big or small. It can be from your personal life or your professional life. Before you begin to problem solve, ask yourself:  Is my problem analytical or conceptual?  Is it convergent or divergent? Becoming fluent in these problem characteristics will help you apply the most effective tools and techniques.</li></ul></ul>\\n<br/>\\n\\n<p><b>Before you move on, you can download and keep:</b></p>\\n<ul class=\\\"fa-ul\\\">\\n<li><i class=\\\"fa-li fa fa-file-text-o\\\" ></i><a href=\\\"/static/MAPS_Lesson1_KeyTakeaways.pdf\\\" target=\\\"_blank\\\"> Lesson 1: Key takeaways</a> (a summary of key points covered in this lesson).</li>\\n</ul>\\n<!--<br/>-->\\n<p><b>You may also wish to download as further reading:</b></p>\\n<ul class=\\\"fa-ul\\\">\\n<li><i class=\\\"fa-li fa fa-file-text-o\\\" ></i><a href=\\\"/static/Five_routes_to_more_innovative_problem_solving.pdf\\\" target=\\\"_blank\\\"> \\\"Five routes to more innovative problem solving\\\"</a> (Article | <i>The McKinsey Quarterly</i>, 2013)</li>  \\n<li><i class=\\\"fa-li fa fa-file-text-o\\\" ></i><a href=\\\"/static/3_Performance_from_problem_solving.pdf\\\" target=\\\"_blank\\\"> \\\"Performance from problem solving: An interview with three leaders at Mass Mutual\\\"</a> (Article | McKinsey &amp; Company, 2014)</li> \\n</ul>\\n  \\n  \\n<!--<p><i class=\\\"fa fa-fw\\\"></i><a target=\\\"_blank\\\" href=\\\"/static/Five_routes_to_more_innovative_problem_solving.pdf\\\"><i class=\\\"fa fa-file-text-o\\\"></i> \\\"Five routes to more innovative problem solving\\\"</a> (Article | <i>The McKinsey Quarterly</i>, 2013)</p>\\n  \\n<p><i class=\\\"fa fa-fw\\\"></i><a target=\\\"_blank\\\" href=\\\"/static/3_Performance_from_problem_solving.pdf\\\"><i class=\\\"fa fa-file-text-o\\\"></i> \\\"Performance from problem solving: An interview with three leaders at Mass Mutual\\\"</a><br/> <i class=\\\"fa fa-fw\\\"></i><i class=\\\"fa fa-fw\\\"></i>(Article | McKinsey &amp; Company, 2014)</p>-->\\n<br/>\\n<p>McKinsey has a structured approach to problem solving comprising tools and techniques that can be used in a variety of problem solving situations. The next lesson introduces that approach.</p>\\n<p><i>When you are ready, <b>get started on Lesson 2</b> by clicking the forward arrow.</i></p>";
        LogUtils.LOGD(TAG, ">> before " + rawHtml);
        rawHtml = rawHtml.replace("\\n\\n", "");
        // remove consecutive \\n
        rawHtml = rawHtml.replace("\\n\\n", "");
        // remove \\n
        rawHtml = rawHtml.replace("\\n", "");
        // remove tab character
        rawHtml = rawHtml.replace("\\t", "");
        // replace \\\" (tripple backslash and double quotes) with \" (single bakcslash & double quotes) as \\\" does not let images load
        rawHtml = rawHtml.replace("\\\"", "\"");
        LogUtils.LOGD(TAG, ">> after " + rawHtml);
        fragment = RawHtmlXBlockSampleFragment.newInstance("", "", "", "", rawHtml, "https://qa.mckinsey.edx.org");
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }
}

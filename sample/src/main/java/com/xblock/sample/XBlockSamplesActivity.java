package com.xblock.sample;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Activity to hold different samples of Components
 */
public class XBlockSamplesActivity extends AppCompatActivity {

    private static final String TAG = XBlockSamplesActivity.class.getSimpleName();

    public static final String IS_MULTI_SELECT_ENABLE = "isMultiSelectEnable";

    private static final String INTERNAL_ASSETS_DIRECTORY = "internal_assets";
    private static final String ZIPPED_ASSETS = "assets.zip";

    private boolean mReceiversRegistered;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xblock_samples);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_view);

        Button btnVideoXBlock = (Button) findViewById(R.id.btn_launch_video_xblock);
        btnVideoXBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoXBlockActivity = new Intent(XBlockSamplesActivity.this, XBlockSampleActivity.class);
                startActivity(videoXBlockActivity);
            }
        });

        Button btnLongAnswerXBlock = (Button) findViewById(R.id.btn_launch_long_answer_xblock);
        btnLongAnswerXBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent longAnswerXBlockActivity = new Intent(XBlockSamplesActivity.this, XBlockLongAnswerSampleActivity.class);
                startActivity(longAnswerXBlockActivity);
            }
        });

        Button btnLaunchMCQXBlock = (Button) findViewById(R.id.btn_launch_mcq_xblock);
        btnLaunchMCQXBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mcqSampleActivity = new Intent(XBlockSamplesActivity.this, XBlockMCQSampleActivity.class);
                mcqSampleActivity.putExtra(IS_MULTI_SELECT_ENABLE, false);
                startActivity(mcqSampleActivity);
            }
        });

        Button btnLaunchMRQXBlock = (Button) findViewById(R.id.btn_launch_mrq_xblock);
        btnLaunchMRQXBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mrqSampleActivity = new Intent(XBlockSamplesActivity.this, XBlockMCQSampleActivity.class);
                mrqSampleActivity.putExtra(IS_MULTI_SELECT_ENABLE, true);
                startActivity(mrqSampleActivity);
            }
        });

        Button btnRawHtmlXBlock = findViewById(R.id.btn_launch_raw_html_xblock);
        btnRawHtmlXBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rawHtmlIntent = new Intent(XBlockSamplesActivity.this, XBlockRawHtmlSampleActivity.class);
                startActivity(rawHtmlIntent);
            }
        });

        Button btnAnnouncements = (Button) findViewById(R.id.btn_launch_announcement);
        btnAnnouncements.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent announcementsIntent = new Intent(XBlockSamplesActivity.this, AnnouncementsActivity.class);
                startActivity(announcementsIntent);
            }
        });

        mProgressBar.setVisibility(View.VISIBLE);
        // Launch Assets Copying Service
        Intent intent = new Intent(XBlockSamplesActivity.this, AssetCopyingService.class);
        startService(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver();
    }


    private void registerReceiver() {
        unregisterReceiver();
        IntentFilter intentToReceiveFilter = new IntentFilter();
        intentToReceiveFilter
                .addAction(AssetCopyingService.PROGRESS_UPDATE_ACTION);
        LocalBroadcastManager.getInstance(XBlockSamplesActivity.this).registerReceiver(
                mDownloadingProgressReceiver, intentToReceiveFilter);
        mReceiversRegistered = true;
    }

    private void unregisterReceiver() {
        if (mReceiversRegistered) {
            LocalBroadcastManager.getInstance(XBlockSamplesActivity.this).unregisterReceiver(
                    mDownloadingProgressReceiver);
            mReceiversRegistered = false;
        }
    }

    public static boolean isAssetCopied(Context context) {
        // check for existence of destination dir, in case of app was uninstalled
        File assetsDir = new File(context.getFilesDir(), INTERNAL_ASSETS_DIRECTORY);
        return assetsDir.exists() && PreferenceManager.getDefaultSharedPreferences(context).getString("ASSETS_ZIP_HASH", "").equals(BuildConfig.ASSETS_ZIP_HASH);
    }

    public static void markAssetsCopied(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("ASSETS_ZIP_HASH", BuildConfig.ASSETS_ZIP_HASH).apply();
    }

    private final BroadcastReceiver mDownloadingProgressReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AssetCopyingService.PROGRESS_UPDATE_ACTION)) {
                final boolean finished = intent.getBooleanExtra("finished", false);
                if (finished) {
                    markAssetsCopied(XBlockSamplesActivity.this);
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        }
    };

    private void onProgressUpdate(int progress) {
        mProgressBar.setProgress(progress > 100 ? 100 : progress);
    }

    /**
     * Service that copies the assets to the internal storage for efficient reading
     */
    public static class AssetCopyingService extends IntentService {
        private static final int INTERVAL_BROADCAST = 500;
        public static final String PROGRESS_UPDATE_ACTION = AssetCopyingService.class.getName() + ".progress_update";
        private boolean mIsAlreadyRunning;
        private int mLastProgress;
        private LocalBroadcastManager mBroadcastManager;
        private Handler mHandler;
        private long mLastUpdate;

        public AssetCopyingService() {
            super("AssetCopyingService");
            mHandler = new Handler();
            mBroadcastManager = LocalBroadcastManager.getInstance(this);
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            if (mIsAlreadyRunning) {
                publishProgress(mLastProgress);
            }
            return super.onStartCommand(intent, flags, startId);
        }

        private void publishProgress(int progress) {
            // send broadcast after 500ms
            if (System.currentTimeMillis() - mLastUpdate > INTERVAL_BROADCAST) {
                mLastUpdate = System.currentTimeMillis();
                Intent i = new Intent();
                i.setAction(PROGRESS_UPDATE_ACTION);
                i.putExtra("progress", progress);
                mBroadcastManager.sendBroadcast(i);
            }

            mLastProgress = progress;
        }

        private void onFinished() {
            mHandler.removeCallbacksAndMessages(null);
            Intent i = new Intent();
            i.setAction(PROGRESS_UPDATE_ACTION);
            i.putExtra("progress", 100);
            i.putExtra("finished", true);
            mBroadcastManager.sendBroadcast(i);
            mLastProgress = 100;
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            if (mIsAlreadyRunning) {
                return;
            }
            mIsAlreadyRunning = true;
            File to = new File(getFilesDir(), INTERNAL_ASSETS_DIRECTORY);
            if (to.exists()) {
                try {
                    // delete the folder if exists
                    FileUtils.deleteRecursive(to);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            ZipUtils.unzip(this, ZIPPED_ASSETS, true, to.getAbsolutePath(), new ZipUtils.ProgressUpdateListener() {
                @Override
                public void onProgressUpdate(int progress) {
                    publishProgress(progress);
                }

                @Override
                public void onFinished() {
                    AssetCopyingService.this.onFinished();
                }
            });
        }
    }

}

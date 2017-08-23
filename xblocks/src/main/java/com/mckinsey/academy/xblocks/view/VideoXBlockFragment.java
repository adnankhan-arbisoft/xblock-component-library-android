package com.mckinsey.academy.xblocks.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mckinsey.academy.xblocks.R;
import com.mckinsey.academy.xblocks.callbacks.Callback;
import com.mckinsey.academy.xblocks.callbacks.VideoXBlockCallback;
import com.mckinsey.academy.xblocks.exception.CallbackCastException;
import com.mckinsey.academy.xblocks.exception.PlayingException;
import com.mckinsey.academy.xblocks.info.VideoXBlockInfo;
import com.mckinsey.academy.xblocks.info.XBlockInfo;
import com.mckinsey.academy.xblocks.info.XBlockUserAnswer;
import com.mckinsey.academy.xblocks.player.ObservableOoyalaPlayer;
import com.ooyala.android.OoyalaException;
import com.ooyala.android.OoyalaNotification;
import com.ooyala.android.OoyalaPlayer;
import com.ooyala.android.OoyalaPlayerLayout;
import com.ooyala.android.PlayerDomain;
import com.ooyala.android.configuration.Options;
import com.ooyala.android.item.Stream;
import com.ooyala.android.item.Video;
import com.ooyala.android.notifications.BitrateChangedNotificationInfo;
import com.ooyala.android.ui.OoyalaPlayerLayoutController;

import java.util.Observable;

import static com.mckinsey.academy.xblocks.common.Constants.ERROR_MSG_CALLBACK_NOT_FOUND;
import static com.mckinsey.academy.xblocks.common.Constants.EXTRA_XBLOCK_INFO;
import static com.mckinsey.academy.xblocks.common.Constants.X_BLOCK_TAG;


/**
 * Ooyala Video XBlock Component. It Includes Ooyala player and component description UI.
 */
public class VideoXBlockFragment extends LifecycleOwnerFragment {

    private static final String TAG = VideoXBlockFragment.class.getSimpleName();

    private VideoXBlockCallback mCallback;

    protected ObservableOoyalaPlayer player;
    protected OoyalaPlayerLayoutController playerLayoutController;

    private TextView videoTitle;
    private TextView videoDescription;
    private OoyalaPlayerLayout playerLayout;

    public static VideoXBlockFragment newInstance(XBlockInfo xBlockInfo) {
        VideoXBlockFragment fragment = new VideoXBlockFragment();

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_XBLOCK_INFO, xBlockInfo);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (mCallback == null) {
            Fragment parent = getParentFragment();
            if (parent != null && parent instanceof VideoXBlockCallback) {
                mCallback = (VideoXBlockCallback) parent;
            } else if (context instanceof VideoXBlockCallback) {
                mCallback = (VideoXBlockCallback) context;
            } else {
                Log.e(X_BLOCK_TAG, ERROR_MSG_CALLBACK_NOT_FOUND);
                mCallback = VideoXBlockCallback.NULL_CALLBACK;
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        VideoXBlockInfo xBlockInfo = (VideoXBlockInfo) args.getSerializable(EXTRA_XBLOCK_INFO);

        videoTitle.setText(xBlockInfo.getTitle());
        videoDescription.setText(xBlockInfo.getDetails());

        Options options = new Options.Builder().setUseExoPlayer(true).build();
        player = new ObservableOoyalaPlayer(xBlockInfo.getPcode(), new PlayerDomain(xBlockInfo
                .getDomain()), options);
        playerLayoutController = new OoyalaPlayerLayoutController(playerLayout, player);
        player.addObserver(this);

        if (player.setEmbedCode(xBlockInfo.getEmbedId())) {
            player.play();
        }
        getLifecycle().addObserver(player);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xblock_video, container, false);

        videoTitle = (TextView) view.findViewById(R.id.video_title);
        videoDescription = (TextView) view.findViewById(R.id.video_description);
        playerLayout = (OoyalaPlayerLayout) view.findViewById(R.id.player_layout);

        return view;
    }

    @Override
    public void setCallback(@NonNull Callback callback) throws CallbackCastException {
        try {
            mCallback = (VideoXBlockCallback) callback;
        } catch (ClassCastException e) {
            throw new CallbackCastException("VideoXBlockCallback not passed");
        }
    }

    /**
     * Listen to all notifications from the OoyalaPlayer
     */
    @Override
    public void update(Observable observable, Object ooyalaNotification) {
        final String name = OoyalaNotification.getNameOrUnknown(ooyalaNotification);

        switch (name) {
            case OoyalaPlayer.TIME_CHANGED_NOTIFICATION_NAME:
                int playHeadTime = player.getPlayheadTime();
                Log.d(TAG, "Player timer changed " + playHeadTime);
                int seconds = playHeadTime / 1000;
                if (seconds > 0) {
                    mCallback.onProgressChange(seconds);
                }
                return;

            case OoyalaPlayer.EMBED_CODE_SET_NOTIFICATION_NAME:
                Log.d(X_BLOCK_TAG, "The Embed Code has been set, effectively restarting the " +
                        "OoyalaPlayer");
                break;

            // Notifications for when Ooyala API requests are completed
            case OoyalaPlayer.CONTENT_TREE_READY_NOTIFICATION_NAME:
                break;

            case OoyalaPlayer.METADATA_READY_NOTIFICATION_NAME:
                break;

            case OoyalaPlayer.AUTHORIZATION_READY_NOTIFICATION_NAME:
                break;

            // Notification when player has all information from Ooyala APIs
            case OoyalaPlayer.CURRENT_ITEM_CHANGED_NOTIFICATION_NAME:
                Log.d(X_BLOCK_TAG, "Current Item has now changed, and all metadata has been " +
                        "updated " + "for it");
                Video video = player.getCurrentItem();
                if (video != null) {
                    Stream stream = video.getStream();
                    if (stream != null) {
                        Log.d(X_BLOCK_TAG, "Content ID: " + video.getEmbedCode() + ", Stream " +
                                "URL:" + " " + stream.decodedURL() + ", Stream Type: " + stream
                                .getDeliveryType());
                    } else {
                        Log.e(X_BLOCK_TAG, "No Streams found for this asset");
                    }
                } else {
                    Log.e(X_BLOCK_TAG, "Video did not load correctly");
                }
                break;

            // Notification when the playback starts or completes
            case OoyalaPlayer.PLAY_STARTED_NOTIFICATION_NAME:
                Log.d(TAG, "The player has started playback of this asset for the first time");
                mCallback.onInit();
                break;

            case OoyalaPlayer.PLAY_COMPLETED_NOTIFICATION_NAME:
                Log.d(TAG, "The player has reached the end of the current asset");
                mCallback.onComplete();
                break;

            // Notification when the player goes into one of the OoyalaPlayer State
            case OoyalaPlayer.STATE_CHANGED_NOTIFICATION_NAME:
                switch (player.getState()) {

                    /** Initial state, player is created but no content is loaded */
                    case INIT:
                        break;

                    /** Loading content */
                    case LOADING:
                        Log.d(X_BLOCK_TAG, "State: Video is buffering");
                        break;
                    case READY:
                        Log.d(X_BLOCK_TAG, "State: Video is initially ready");
                        break;
                    case PLAYING:
                        Log.d(X_BLOCK_TAG, "State: Video is playing");
                        mCallback.onPlay();
                        break;
                    case PAUSED:
                        Log.d(X_BLOCK_TAG, "State: Video is paused");
                        mCallback.onPause();
                        break;
                    case COMPLETED:
                        Log.d(X_BLOCK_TAG, "State: Video is now complete");
                        break;
                    case SUSPENDED:
                        Log.d(X_BLOCK_TAG, "State: Video is now suspended");
                        break;
                    case ERROR:
                        /** NOTE: It's suggested to listen to the Error Notification instead of
                         * handling errors here **/
                        Log.d(X_BLOCK_TAG, "State: Video has run into an error");
                        break;
                    default:
                        break;

                }
                break;

            case OoyalaPlayer.DESIRED_STATE_CHANGED_NOTIFICATION_NAME:
                switch (player.getDesiredState()) {
                    case DESIRED_PLAY:
                        Log.d(X_BLOCK_TAG, "Desired state is playing. After any loading, video "
                                + "should " + "" + "" + "" + "" + "" + "" + "be actively " +
                                "rendering");
                        break;
                    case DESIRED_PAUSE:
                        Log.d(X_BLOCK_TAG, "Desired state is paused. Video should not be " +
                                "actively" + " " + "rendering while desired state is paused");
                        break;
                    default:
                        break;
                }
                break;
            case OoyalaPlayer.BUFFER_CHANGED_NOTIFICATION_NAME:
                break;

            case OoyalaPlayer.SEEK_STARTED_NOTIFICATION_NAME:
                Log.d(X_BLOCK_TAG, "Seek Started");
                break;

            case OoyalaPlayer.SEEK_COMPLETED_NOTIFICATION_NAME:
                Log.d(X_BLOCK_TAG, "Seek Complete");
                break;

            case OoyalaPlayer.ERROR_NOTIFICATION_NAME:
                Log.d(X_BLOCK_TAG, "Playback Error. Code:" + player.getError().getCode() + ", " +
                        "Description: " + player.getError().getMessage());
                OoyalaException error = player.getError();
                PlayingException exception = new PlayingException(error.getMessage(), error
                        .getCause());
                mCallback.onError(exception);
                break;

            case OoyalaPlayer.CC_STYLING_CHANGED_NOTIFICATION_NAME:
                break;

            case OoyalaPlayer.AD_OVERLAY_NOTIFICATION_NAME:
                break;

                // NOTE: Unused for BaseStreamPlayer. However, these are usable in ExoPlayer
            case OoyalaPlayer.BUFFERING_STARTED_NOTIFICATION_NAME:
                break;

            // NOTE: Unused for BaseStreamPlayer. However, these are usable in ExoPlayer
            case OoyalaPlayer.BUFFERING_COMPLETED_NOTIFICATION_NAME:
                break;

            case OoyalaPlayer.LIVE_CC_AVAILABILITY_CHANGED_NOTIFICATION_NAME:
                break;

            case OoyalaPlayer.LIVE_CC_CHANGED_NOTIFICATION_NAME:
                break;

            case OoyalaPlayer.CC_CHANGED_NOTIFICATION_NAME:
                break;

            case OoyalaPlayer.CLOSED_CAPTIONS_LANGUAGE_CHANGED_NAME:
                break;

            case OoyalaPlayer.BITRATE_CHANGED_NOTIFICATION_NAME:
                BitrateChangedNotificationInfo info = (BitrateChangedNotificationInfo) (
                        (OoyalaNotification) ooyalaNotification).getData();
                Log.d(X_BLOCK_TAG, "Bitrate Changed. Old: " + info.getOldBitrate() + ", New: " +
                        info.getNewBitrate());
                break;
        }

    }


    @Override
    public XBlockUserAnswer getUserAnswer() {
        return null;
    }
}

package com.xblock.sample;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mckinsey.academy.xblocks.XBlock;
import com.mckinsey.academy.xblocks.callbacks.RawHtmlXBlockCallback;
import com.mckinsey.academy.xblocks.info.XBlockInfo;
import com.mckinsey.academy.xblocks.info.XBlockInfoBuilder;
import com.mckinsey.academy.xblocks.view.RawHtmlXBlockFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * RawHtml sample fragment
 */

public class RawHtmlXBlockSampleFragment extends Fragment implements RawHtmlXBlockCallback {

    private static final String ARG_TITLE = "param_title";
    private static final String ARG_DESC = "param_description";
    private static final String ARG_STUDENT_VIEW_URL = "param_student_view_url";
    private static final String ARG_AUTH_TOKEN = "param_auth_token";
    private static final String ARG_RAW_HTML = "param_raw_html";

    private String mParamTitle;
    private String mParamDesc;
    private String mParamStudentViewUrl;
    private String mParamAuthToken;
    private String mHtml;

    public static RawHtmlXBlockSampleFragment newInstance(String title, String desc, String studentViewUrl, String authToken, String html) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESC, desc);
        args.putString(ARG_STUDENT_VIEW_URL, studentViewUrl);
        args.putString(ARG_AUTH_TOKEN, authToken);
        args.putString(ARG_AUTH_TOKEN, authToken);
        args.putString(ARG_RAW_HTML, html);

        RawHtmlXBlockSampleFragment fragment = new RawHtmlXBlockSampleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mParamTitle = bundle.getString(ARG_TITLE);
            mParamDesc = bundle.getString(ARG_DESC);
            mParamStudentViewUrl = bundle.getString(ARG_STUDENT_VIEW_URL);
            mParamAuthToken = bundle.getString(ARG_AUTH_TOKEN);
            mHtml = bundle.getString(ARG_RAW_HTML);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_xblock_raw_html_sample, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // XBlock integration
        XBlockInfo xBlockInfo = XBlockInfoBuilder.buildRawHtmlXBlockInfo()
                .setTitle(mParamTitle)
                .setDescription(mParamDesc)
                .setAuthToken(mParamAuthToken)
                .setStudentViewUrl(mParamStudentViewUrl)
                .setHtml(mHtml)
                .build();

        final XBlock xBlock = XBlock.with(getChildFragmentManager(), R.id.xblock_container, xBlockInfo, this/*call back*/);
        xBlock.injectXBlock();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onWebViewSetup() {
        // nothing required
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.xblock_container);
        if(fragment != null && fragment instanceof RawHtmlXBlockFragment) {
            Map<String, String> cookieMap = new HashMap<String, String>();
            ((RawHtmlXBlockFragment) fragment).setCustomHeaders(cookieMap);
        }
    }

    @Override
    public void onLoadStudentViewUrl() {
        // nothing required
    }

    @Override
    public void onError(String errorMessage) {
        // nothing required
    }
}

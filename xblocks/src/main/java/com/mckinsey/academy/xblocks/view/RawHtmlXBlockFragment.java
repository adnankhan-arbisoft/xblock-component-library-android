package com.mckinsey.academy.xblocks.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.mckinsey.academy.xblocks.R;
import com.mckinsey.academy.xblocks.callbacks.RawHtmlXBlockCallback;
import com.mckinsey.academy.xblocks.info.RawHtmlXBlockInfo;
import com.mckinsey.academy.xblocks.info.XBlockInfo;
import com.mckinsey.academy.xblocks.info.XBlockSubmitResponse;
import com.mckinsey.academy.xblocks.info.XBlockUserAnswer;

import java.util.Map;

import static com.mckinsey.academy.xblocks.common.Constants.EXTRA_XBLOCK_INFO;

/**
 * XBlock Component to show Raw HTML.
 */
public class RawHtmlXBlockFragment extends LifecycleOwnerFragment<RawHtmlXBlockCallback, Void, Void> {

    private static final String TAG = RawHtmlXBlockFragment.class.getSimpleName();

    private WebView mWebView = null;

    private RawHtmlXBlockInfo rawHtmlXBlockInfo = null;

    private Map<String, String> mapHeaders = null;

    public static RawHtmlXBlockFragment newInstance(XBlockInfo xBlockInfo) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_XBLOCK_INFO, xBlockInfo);
        RawHtmlXBlockFragment fragment = new RawHtmlXBlockFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_xblock_raw_html, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupWebView();
    }

    private void setupWebView() {
        View view = getView();
        if (view == null) {
            return;
        }
        WebView webView = mWebView = view.findViewById(R.id.web_view_raw_html);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);

        webView.setWebViewClient(new RawHTMLWebViewClient());
        webView.setWebChromeClient(new RawHTMLWebChromeClient());

        mCallback.onWebViewSetup();
    }

    /**
     * Loads the URL or Raw HTML into webview
     */
    private void tryToLoadWebView() {

        Bundle args = getArguments();

        if (args != null) {

            rawHtmlXBlockInfo = (RawHtmlXBlockInfo) args.getSerializable(EXTRA_XBLOCK_INFO);

            if (rawHtmlXBlockInfo != null) {

                if (!TextUtils.isEmpty(rawHtmlXBlockInfo.getHtml())) {

                    // when we get raw html from server - load it on priority
                    if (!TextUtils.isEmpty(rawHtmlXBlockInfo.getBaseUrl())) {

                        mWebView.loadDataWithBaseURL(rawHtmlXBlockInfo.getBaseUrl(),
                                rawHtmlXBlockInfo.getHtml(), "text/html", "UTF-8", null);

                    } else {

                        mWebView.loadData(rawHtmlXBlockInfo.getHtml(), "text/html", "UTF-8");

                    }

                } else if (!TextUtils.isEmpty(rawHtmlXBlockInfo.getStudentViewUrl())) {

                    // if HTML is not returned, fall back on student_view_url
                    if (mapHeaders != null && mapHeaders.size() > 0) {

                        mWebView.loadUrl(rawHtmlXBlockInfo.getStudentViewUrl(), mapHeaders);

                    } else {

                        // this will eventually fail for secure url if cookies are not set
                        mWebView.loadUrl(rawHtmlXBlockInfo.getStudentViewUrl());

                    }

                } else {

                    Toast.makeText(getActivity(), "Unable to complete your request",
                            Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    @Override
    public XBlockUserAnswer<Void> getUserAnswer() {
        return null;
    }

    @Override
    public void setSubmitResponse(XBlockSubmitResponse<Void> xBlockSubmitResponse) {
        // TODO nothing required for now
    }

    @Override
    public void setLatestUserAnswer(XBlockUserAnswer<Void> userAnswer) {
        // TODO nothing required for now
    }

    @Override
    public void setCustomHeaders(Map<String, String> customHeaders) {
        mapHeaders = customHeaders;
        tryToLoadWebView();
    }

    @NonNull
    @Override
    public RawHtmlXBlockCallback getNullCallback() {
        return RawHtmlXBlockCallback.NULL_CALLBACK;
    }

    /**
     * Web View Client to intercept requests where required
     */
    class RawHTMLWebViewClient extends WebViewClient {

        public RawHTMLWebViewClient() {
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mCallback.onPageStarted();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mCallback.onPageFinished();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            mCallback.onPageFinished();
            Log.d(TAG, "Inside on received error " + errorCode + " description " + description + " failing url " + failingUrl);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }
    }

    /**
     * Web Chrome Client for callbacks of events inside {@code {@link WebView}}
     */
    private class RawHTMLWebChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage cm) {
            Log.d(TAG, cm.message() + " -- From line " + cm.lineNumber() + " of " + cm.sourceId());
            return true;
        }
    }
}

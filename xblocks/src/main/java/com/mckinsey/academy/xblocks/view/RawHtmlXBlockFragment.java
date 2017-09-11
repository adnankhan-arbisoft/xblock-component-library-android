package com.mckinsey.academy.xblocks.view;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mckinsey.academy.xblocks.R;
import com.mckinsey.academy.xblocks.callbacks.RawHtmlXBlockCallback;
import com.mckinsey.academy.xblocks.info.RawHtmlXBlockInfo;
import com.mckinsey.academy.xblocks.info.XBlockInfo;
import com.mckinsey.academy.xblocks.info.XBlockSubmitResponse;
import com.mckinsey.academy.xblocks.info.XBlockUserAnswer;

import java.util.Map;

/**
 * XBlock Component to show Raw HTML.
 */

public class RawHtmlXBlockFragment extends LifecycleOwnerFragment<RawHtmlXBlockCallback, Void, Void> {

    private static final String TAG = RawHtmlXBlockFragment.class.getSimpleName();
    private static final String EXTRA_XBLOCK_INFO = "xblock_info";

    private WebView mWebView = null;
    private ProgressBar mProgressBar = null;

    private RawHtmlXBlockInfo rawHtmlXBlockInfo = null;

    private Map<String, String> mapHeaders = null;

    public RawHtmlXBlockFragment() {
        // default constructor
    }

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
        mProgressBar = view.findViewById(R.id.progress_bar_view);
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

        if (mCallback != null) {
            mCallback.onWebViewSetup();
        }
    }

    /**
     * Loads the URL or Raw HTML into webview
     */
    private void tryToLoadWebView() {
        if (getArguments() != null) {
            Bundle args = getArguments();
            rawHtmlXBlockInfo = (RawHtmlXBlockInfo) args.getSerializable(EXTRA_XBLOCK_INFO);
            if (rawHtmlXBlockInfo != null) {
                if (!TextUtils.isEmpty(rawHtmlXBlockInfo.getStudentViewUrl())) {
                    if (mapHeaders != null && mapHeaders.size() > 0) {
                        mWebView.loadUrl(rawHtmlXBlockInfo.getStudentViewUrl(), mapHeaders);
                    } else {
                        // this will eventually fail for secure url if cookies are not set
                        mWebView.loadUrl(rawHtmlXBlockInfo.getStudentViewUrl());
                    }
                } else if (!TextUtils.isEmpty(rawHtmlXBlockInfo.getHtml())) {
                    // when we get raw html from server
                    if (!TextUtils.isEmpty(rawHtmlXBlockInfo.getBaseUrl())) {
                        mWebView.loadDataWithBaseURL(rawHtmlXBlockInfo.getBaseUrl(), rawHtmlXBlockInfo.getHtml(), "text/html", "UTF-8", null);
                    } else {
                        mWebView.loadData(rawHtmlXBlockInfo.getHtml(), "text/html", "UTF-8");
                    }
                } else {
                    Toast.makeText(getActivity(), "Unable to complete your request", Toast.LENGTH_SHORT).show();
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
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Log.d(TAG, "Inside on received error " + errorCode + " description " + description + " failing url " + failingUrl);
        }

        // http://stackoverflow.com/questions/13954049/intercept-post-requests-in-a-webview
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return super.shouldInterceptRequest(view, url);
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

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }
}

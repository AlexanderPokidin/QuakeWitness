package com.pokidin.a.quakewitness;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {
    private static final String TAG = WebActivity.class.getSimpleName();

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        // Get URI object from the intent
        Uri earthquakeUri = getIntent().getData();

        // Find a reference to the WebView in the layout
        mWebView = findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);

        // Create your own browser to view application pages
        mWebView.setWebViewClient(new EarthquakeWebViewClient());

        // Download the current earthquake details page.
        mWebView.loadUrl(String.valueOf(earthquakeUri));

        Log.d(TAG, "earthquakeUri checked: " + earthquakeUri);
    }

    // Handling the Back button for WebView
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private class EarthquakeWebViewClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            // Hide loading indicator because the data has been loaded
            View loadingIndicator = findViewById(R.id.web_loading_spinner);
            loadingIndicator.setVisibility(View.GONE);

            super.onPageFinished(view, url);
        }
    }
}

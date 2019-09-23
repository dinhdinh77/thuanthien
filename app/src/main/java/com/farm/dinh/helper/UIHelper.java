package com.farm.dinh.helper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.farm.dinh.R;
import com.farm.dinh.ui.fragment.SpinnerDialog;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

public class UIHelper {
    private static SpinnerDialog spinnerDialog;

    public static void showLoading(FragmentManager fragmentManager) {
        if (spinnerDialog == null)
            spinnerDialog = new SpinnerDialog();
        spinnerDialog.show(fragmentManager, null);
    }

    public static void hideLoading() {
        if (spinnerDialog != null) spinnerDialog.dismiss();
    }

    public static void hideSoftKeyboard(Window wd, View view) {
        int flag = android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
        if (wd != null) {
            wd.setSoftInputMode(wd.getAttributes().softInputMode & ~flag);
        }
        if (view == null) return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!imm.isActive()) return;
        final IBinder windowToken = view.getWindowToken();
        if (!imm.hideSoftInputFromWindow(windowToken, 0)) {
            if (!imm.isActive()) {
                return;
            }
            Activity activity = scanForActivity(view.getContext());
            if (activity != null) {
                Window window = activity.getWindow();
                window.setSoftInputMode(window.getAttributes().softInputMode & ~flag);
            }
        }
        System.out.printf("Hide KB called");
    }

    public static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }

    public static void showMessageDialog(Context context, String message){
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.alert_title))
                .setMessage(message)
                .setNegativeButton(context.getResources().getString(R.string.alert_cancel), null)
                .show();
    }

    public static class CustomWebViewClient extends WebViewClient {
        private Runnable pageFinishedJob;
        private Context context;
        private WebView webView;

        public CustomWebViewClient(WebView webView) {
            this.initWebViewSettings(webView, true);
        }

        public CustomWebViewClient(WebView webView, boolean isJavascriptEnabled) {
            this.initWebViewSettings(webView, isJavascriptEnabled);
        }

        private void initWebViewSettings(WebView webView, boolean isJavascriptEnabled) {
            this.webView = webView;
            this.context = webView.getContext();
            this.webView.setWebViewClient(this);
            WebSettings webSettings = this.webView.getSettings();
            webSettings.setJavaScriptEnabled(isJavascriptEnabled);
            if (isJavascriptEnabled)
                webView.addJavascriptInterface(this, "Android");
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            webSettings.setLoadsImagesAutomatically(true);
            boolean zoom = this.supportZoom();
            webSettings.setSupportZoom(zoom);
            webSettings.setBuiltInZoomControls(zoom);

            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
        }

        @JavascriptInterface
        public void showFullImage(String imgBase64) {
            Log.i("showFullImage", "called " + imgBase64);
        }

        @JavascriptInterface
        public void onReadyIframe(String src) {

        }

        protected boolean supportZoom() {
            return false;
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if (url != null && url.matches("^https?://.*")) {
//                return UIHelper.openWebLink(this.context, url);
//            } else {
            return false;
//            }
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            final Uri uri = request.getUrl();
            return handleUri(uri.toString());
        }

        protected boolean handleUri(String url) {
//            if (url != null && url.matches("^https?://.*")) {
//                return UIHelper.openWebLink(this.context, url);
//            } else {
            return false;
//            }
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (pageFinishedJob != null) {
                pageFinishedJob.run();
            }
            if (!supportZoom()) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    webView.zoomBy(1);
                } else {
                    webView.setInitialScale(1);
                    webView.getSettings().setLoadWithOverviewMode(true);
                    webView.getSettings().setUseWideViewPort(true);
                }
            }
        }

        public void setPageFinishedJob(Runnable pageFinishedJob) {
            this.pageFinishedJob = pageFinishedJob;
        }
    }
}

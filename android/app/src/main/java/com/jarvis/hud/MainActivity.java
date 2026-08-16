package com.jarvis.hud;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.os.Build;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.speech.tts.TextToSpeech;
import java.util.Locale;

public class MainActivity extends Activity {

    private WebView webView;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
                tts.setSpeechRate(0.95f);
                tts.setPitch(1.0f);
            }
        });

        webView = new WebView(this);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setBackgroundColor(0xFF000000);

        webView.addJavascriptInterface(new JarvisBridge(), "AndroidBridge");

        setContentView(webView);
        webView.loadUrl("file:///android_asset/web/index.html");
    }

    public class JarvisBridge {

        @JavascriptInterface
        public void speak(String text) {
            if (text == null || text.trim().isEmpty()) return;

            runOnUiThread(() -> {
                if (tts != null) {
                    tts.speak(
                        text,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "JARVIS"
                    );
                }
            });
        }

        @JavascriptInterface
        public void stopSpeaking() {
            runOnUiThread(() -> {
                if (tts != null) {
                    tts.stop();
                }
            });
        }

        @JavascriptInterface
        public void vibrate() {
            Vibrator vibrator =
                (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            if (vibrator != null) {
                if (Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(
                        android.os.VibrationEffect.createOneShot(
                            80,
                            android.os.VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    );
                } else {
                    vibrator.vibrate(80);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

        if (webView != null) {
            webView.destroy();
        }

        super.onDestroy();
    }
}

package au.edu.jcu.cp3406.paranoidandroid;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import au.edu.jcu.cp3406.paranoidandroid.score.ScoreManager;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class AuthenticateActivity extends AppCompatActivity
{
    private WebView webView;
    private Twitter twitter = TwitterFactory.getSingleton();
    private String oauthVerifier;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);


        if(settings.getString("theme", "Light").equals("Dark"))
        {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);


        webView = findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onLoadResource(WebView view, String url)
            {
                super.onLoadResource(view, url);
                if(url.startsWith("https://github.com/GRAYZ16/ParanoidAndroid"))
                {
                    Uri uri = Uri.parse(url);
                    oauthVerifier = uri.getQueryParameter("oauth_verifier");
                    if(oauthVerifier != null)
                    {
                        Log.i("AuthenticateActivity", "Authenticated");
                        updateTwitterConfiguration();
                        webView.loadData("done", "text/html", null);
                        finish();
                    }
                }
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    RequestToken requestToken = twitter.getOAuthRequestToken();
                    final String requestUrl = requestToken.getAuthenticationURL();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl(requestUrl);
                        }
                    });

                } catch (final Exception e) {
                    Log.i("Authenticate", e.toString());
                }
            }
        });
        thread.start();
    }

    private void updateTwitterConfiguration() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    AccessToken accessToken = twitter.getOAuthAccessToken(oauthVerifier);
                    twitter.setOAuthAccessToken(accessToken);

                    SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("twitter-token", accessToken.getToken()).apply();
                    sharedPreferences.edit().putString("twitter-secret", accessToken.getTokenSecret()).apply();

                } catch (final Exception e) {
                    Log.e("Authenticate", e.toString());
                }
            }
        });
        thread.start();
    }
}

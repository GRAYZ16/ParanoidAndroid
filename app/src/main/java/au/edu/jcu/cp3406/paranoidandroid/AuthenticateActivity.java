package au.edu.jcu.cp3406.paranoidandroid;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
                } catch (final Exception e) {
                    Log.e("Authenticate", e.toString());
                }
            }
        });
        thread.start();
    }
}

package au.edu.jcu.cp3406.paranoidandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import au.edu.jcu.cp3406.paranoidandroid.score.Score;
import au.edu.jcu.cp3406.paranoidandroid.score.ScoreAdapter;
import au.edu.jcu.cp3406.paranoidandroid.score.ScoreManager;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class ScoresActivity extends AppCompatActivity
{
    private final int SCORES_TO_SHOW = 10;

    private ScoreAdapter scoreAdapter;
    private ScoreManager scoreManager;

    ListView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);

        if(settings.getString("theme", "Light").equals("Dark"))
        {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        scoreManager = new ScoreManager(this);

        scoreAdapter = new ScoreAdapter(this, new ArrayList<Score>());

        scoreView = findViewById(R.id.scoreView);
        scoreView.setAdapter(scoreAdapter);

        Score[] scores = scoreManager.getTopScores(SCORES_TO_SHOW);

        for (int i = 0; i < SCORES_TO_SHOW; i++)
        {
            if(scores[i].name != null)
            {
                Log.i("ScoreActivity", String.format("%s %d", scores[i].name, scores[i].score));
                scoreAdapter.add(scores[i]);
            }
        }
    }

    public void sendTweet(View view)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("twitter-token", null);
        String tokenSecret = sharedPreferences.getString("twitter-secret", null);

        if(token == null || tokenSecret == null)
        {
            Intent intent = new Intent(this, AuthenticateActivity.class);
            startActivity(intent);
        }
        else
        {
            LayoutInflater layoutInflater = LayoutInflater.from(ScoresActivity.this);
            View promptView = layoutInflater.inflate(R.layout.tweet_dialog, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ScoresActivity.this);
            alertDialogBuilder.setView(promptView);

            final EditText editText = promptView.findViewById(R.id.edittext);


            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> {
                        AccessToken accessToken = new AccessToken(token, tokenSecret);
                        Twitter twitter = TwitterFactory.getSingleton();

                        twitter.setOAuthAccessToken(accessToken);

                        ScoreManager scoreManager = new ScoreManager(this);
                        Score topScore = scoreManager.getTopScores(1)[0];

                        String tweet = editText.getText().toString() + "\n" + String.format(Locale.getDefault(), "I scored %d on Paranoid Android", topScore.score);
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run()
                            {
                                try
                                {

                                    twitter.updateStatus(tweet);
                                }
                                catch (TwitterException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        });

                        thread.start();

                        Toast.makeText(this, "Tweet Sent", Toast.LENGTH_SHORT).show();

                    })
                    .setNegativeButton("Cancel",
                            (dialog, id) ->
                            {
                                dialog.cancel();
                            });



            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
    }

}

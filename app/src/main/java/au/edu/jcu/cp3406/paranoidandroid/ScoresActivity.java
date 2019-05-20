package au.edu.jcu.cp3406.paranoidandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import au.edu.jcu.cp3406.paranoidandroid.score.Score;
import au.edu.jcu.cp3406.paranoidandroid.score.ScoreAdapter;
import au.edu.jcu.cp3406.paranoidandroid.score.ScoreManager;

public class ScoresActivity extends AppCompatActivity
{
    private final int SCORES_TO_SHOW = 10;

    private ScoreAdapter scoreAdapter;
    private ScoreManager scoreManager;

    ListView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
}

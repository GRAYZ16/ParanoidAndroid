package au.edu.jcu.cp3406.paranoidandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import au.edu.jcu.cp3406.paranoidandroid.game.Game;
import au.edu.jcu.cp3406.paranoidandroid.game.Question;
import au.edu.jcu.cp3406.paranoidandroid.game.state.State;
import au.edu.jcu.cp3406.paranoidandroid.game.state.StateListener;
import au.edu.jcu.cp3406.paranoidandroid.score.Score;
import au.edu.jcu.cp3406.paranoidandroid.score.ScoreManager;

public class GameActivity extends AppCompatActivity implements StateListener
{
    private Game game;

    private QuestionFragment questionFragment;
    private GameFragment gameFragment;

    private ShakeListener shakeListener;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);

        if(settings.getString("theme", "Light").equals("Dark"))
        {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        FragmentManager fragmentManager = getSupportFragmentManager();
        questionFragment = (QuestionFragment) fragmentManager.findFragmentById(R.id.questionFragment);
        gameFragment = (GameFragment) fragmentManager.findFragmentById(R.id.gameFragment);

        shakeListener = new ShakeListener(this);


        onUpdate(State.NEW_GAME);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        shakeListener.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        shakeListener.onResume();
    }

    @Override
    public void onUpdate(State state)
    {
        switch (state)
        {
            case NEW_GAME:
                game = new Game(getAssets());
                setQuestion(game.getNextQuestion());
                break;

            case CONTINUE_GAME:
                setQuestion(game.getNextQuestion());
                break;


            case END_GAME:
                Toast.makeText(this, "Game Ended", Toast.LENGTH_LONG).show();

                SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                String name = preferences.getString("name", "Unknown");

                if(name.equals("Unknown"))
                {
                    promptName();
                }
                else
                {
                    setScore(name);
                    finish();
                }

                break;
        }
    }

    private void setScore(String name)
    {
        ScoreManager manager = new ScoreManager(this);

        Score score = new Score();
        score.name = name;
        score.score = questionFragment.getScore();
        manager.addScore(score);
    }

    private void promptName()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(GameActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = promptView.findViewById(R.id.edittext);


        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
                    preferences.edit().putString("name", editText.getText().toString()).apply();
                    setScore(editText.getText().toString());
                    finish();
                })
                .setNegativeButton("Cancel",
                        (dialog, id) ->
                        {
                            dialog.cancel();
                            finish();
                        });



        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void setQuestion(Question question)
    {
        if(question == null)
        {
            onUpdate(State.END_GAME);
        }
        else
        {
            questionFragment.setQuestion(question);

            gameFragment.setQuestion(question.question);
            gameFragment.setContent(question.content);
        }
    }

    @Override
    public void onBackPressed()
    {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to quit game", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return super.onOptionsItemSelected(item);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package au.edu.jcu.cp3406.paranoidandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import au.edu.jcu.cp3406.paranoidandroid.game.state.State;
import au.edu.jcu.cp3406.paranoidandroid.game.state.StateListener;

public class GameActivity extends AppCompatActivity implements StateListener
{
    ShakeListener shakeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        shakeListener = new ShakeListener(this);
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


    public void buttonSkip(View view)
    {

    }

    @Override
    public void onUpdate(State state)
    {
        switch (state)
        {
            case NEW_GAME:

                break;

            case CONTINUE_GAME:

                break;


            case END_GAME:

                break;
        }
    }
}

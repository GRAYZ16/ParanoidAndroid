package au.edu.jcu.cp3406.paranoidandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void buttonClicked(View view)
    {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        Intent intent = null;

        if(buttonText.equals(getString(R.string.btn_newGame)))
        {
            intent = new Intent(this, GameActivity.class);
        }
        else if(buttonText.equals(getString(R.string.btn_scores)))
        {
            intent = new Intent(this, ScoresActivity.class);
        }
        else if(buttonText.equals(getString(R.string.btn_settings)))
        {
            intent = new Intent(this, SettingsActivity.class);
        }

        startActivity(intent);
    }
}

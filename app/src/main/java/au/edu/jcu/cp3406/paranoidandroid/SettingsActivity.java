package au.edu.jcu.cp3406.paranoidandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity
{
    private Spinner spTheme;
    private Spinner spDifficulty;
    private Button btnSound;


    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        settings = getSharedPreferences("settings", Context.MODE_PRIVATE);

        if(settings.getString("theme", "Light").equals("Dark"))
        {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spTheme = findViewById(R.id.spTheme);
        spDifficulty = findViewById(R.id.spDifficulty);
        btnSound = findViewById(R.id.btnSound);

        btnSound.setOnClickListener((v -> {
            if(btnSound.getText().toString().equals(getString(R.string.btnSettingSoundOn)))
            {
                btnSound.setText(getString(R.string.btnSettingSoundOff));
            }
            else
            {
                btnSound.setText(getString(R.string.btnSettingSoundOn));
            }
        }));

        String theme = settings.getString("theme", "Light");
        boolean difficulty = settings.getBoolean("difficulty", false);
        boolean sound = settings.getBoolean("sound", true);


        assert theme != null;
        if(theme.equals("Light"))
        {
            spTheme.setSelection(0);
        }
        else
        {
            spTheme.setSelection(1);
        }

        if(!difficulty)
        {
            spDifficulty.setSelection(0);
        }
        else
        {
            spDifficulty.setSelection(1);
        }

        if(sound)
        {
            btnSound.setText(getString(R.string.btnSettingSoundOn));
        }
        else
        {
            btnSound.setText(getString(R.string.btnSettingSoundOff));
        }
    }


    @Override
    public void onBackPressed()
    {
        backPressed();
        super.onBackPressed();
    }

    public void backPressed()
    {
        settings.edit().putBoolean("sound", btnSound.getText().equals(getString(R.string.btnSettingSoundOn))).apply();
        settings.edit().putBoolean("difficulty", spDifficulty.getSelectedItem().toString().equals("On")).apply();
        settings.edit().putString("theme", spTheme.getSelectedItem().toString()).apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                backPressed();
                Toast.makeText(this, "Settings Saved", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

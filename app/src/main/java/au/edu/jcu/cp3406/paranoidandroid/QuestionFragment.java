package au.edu.jcu.cp3406.paranoidandroid;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Locale;

import au.edu.jcu.cp3406.paranoidandroid.game.Question;
import au.edu.jcu.cp3406.paranoidandroid.game.sound.Sound;
import au.edu.jcu.cp3406.paranoidandroid.game.sound.SoundManager;
import au.edu.jcu.cp3406.paranoidandroid.game.state.State;
import au.edu.jcu.cp3406.paranoidandroid.game.state.StateListener;

public class QuestionFragment extends Fragment
{

    private final String SOUND_PATH = "Sounds/";
    private boolean soundOn;

    private StateListener listener;
    private Button btnSkip;
    private TextView tvScore;

    private SoundManager soundManager;

    private GridView answers;
    private ArrayAdapter<String> adapter;

    private String correctAnswer;
    private int score;

    private boolean isHardMode;


    public QuestionFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        listener = (StateListener) context;
        soundManager = new SoundManager(context.getAssets(), SOUND_PATH);
        adapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item);

        SharedPreferences settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE);

        soundOn = settings.getBoolean("sound", true);
        isHardMode = settings.getBoolean("difficulty", false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_question, container, false);

        btnSkip = view.findViewById(R.id.btnSkip);
        tvScore = view.findViewById(R.id.tvScore);

        answers = view.findViewById(R.id.answers);

        answers.setAdapter(adapter);

        score = 0;
        tvScore.setText(String.format(Locale.getDefault(), "Score: %d", score));

        answers.setOnItemClickListener(((parent, view1, position, id) -> {
            TextView item = (TextView) view1;
            if(item.getText().toString().equals(correctAnswer))
            {
                listener.onUpdate(State.CONTINUE_GAME);
                score++;
                tvScore.setText(String.format(Locale.getDefault(), "Score: %d", score));

                if(soundOn) soundManager.play(Sound.RIGHT);
            }
            else
            {
                if(isHardMode)
                {
                    listener.onUpdate(State.END_GAME);
                }
                else
                {
                    listener.onUpdate(State.CONTINUE_GAME);
                }

                if(soundOn) soundManager.play(Sound.WRONG);
            }
        }));


        btnSkip.setOnClickListener(v -> listener.onUpdate(State.CONTINUE_GAME));


        return view;
    }

    public void setQuestion(Question question)
    {
        adapter.clear();
        adapter.addAll(question.answers);
        correctAnswer = question.correctAnswer;
    }

    public int getScore()
    {
        return score;
    }
}

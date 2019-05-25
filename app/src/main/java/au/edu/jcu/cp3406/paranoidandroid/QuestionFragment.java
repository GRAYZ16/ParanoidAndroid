package au.edu.jcu.cp3406.paranoidandroid;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import au.edu.jcu.cp3406.paranoidandroid.game.state.State;
import au.edu.jcu.cp3406.paranoidandroid.game.state.StateListener;

public class QuestionFragment extends Fragment
{
    StateListener listener;
    Button btnSkip;


    public QuestionFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        listener = (StateListener) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_question, container, false);

        btnSkip = view.findViewById(R.id.btnSkip);

        btnSkip.setOnClickListener(v -> listener.onUpdate(State.CONTINUE_GAME));


        return inflater.inflate(R.layout.fragment_question, container, false);
    }
}

package au.edu.jcu.cp3406.paranoidandroid;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class GameFragment extends Fragment
{
    private TextView question;
    private ImageView content;

    public GameFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_game, container, false);

        question = view.findViewById(R.id.question);
        content = view.findViewById(R.id.content);

        return view;
    }

    public void setQuestion(String questionString)
    {
        question.setText(questionString);
    }

    public void setContent(Bitmap contentBitmap)
    {
        content.setImageBitmap(contentBitmap);
    }

}

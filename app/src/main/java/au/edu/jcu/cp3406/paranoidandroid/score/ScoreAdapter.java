package au.edu.jcu.cp3406.paranoidandroid.score;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import au.edu.jcu.cp3406.paranoidandroid.R;

public class ScoreAdapter extends BaseAdapter
{
    private Context context;
    private List<Score> scores;

    public ScoreAdapter(Context context, List<Score> scores)
    {
        this.context = context;
        this.scores = scores;
    }

    @Override
    public int getCount()
    {
        return scores.size();
    }

    @Override
    public Object getItem(int position)
    {
        return scores.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ScoreView view;

        if(convertView == null)
        {
            LayoutInflater scoreInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = scoreInflater.inflate(R.layout.score, null);

            view = new ScoreView();
            view.name = convertView.findViewById(R.id.name);
            view.score = convertView.findViewById(R.id.score);
            convertView.setTag(view);
        }
        else
        {
            view = (ScoreView) convertView.getTag();
        }

        Score score = (Score) getItem(position);

        view.name.setText(score.name);
        view.score.setText(Integer.toString(score.score));

        return convertView;
    }

    public void add(Score score)
    {
        scores.add(score);
        notifyDataSetChanged();
    }
}

package au.edu.jcu.cp3406.paranoidandroid.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class DataManager
{
    private AssetManager assetManager;

    public DataManager(AssetManager assetManager)
    {
        this.assetManager = assetManager;
    }

    public Question getQuestion(int questionNumber)
    {
        Question question = new Question();

        try
        {
            InputStream stream = assetManager.open("Questions/" + Integer.toString(questionNumber) + ".json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line = reader.readLine();
            StringBuilder sb = new StringBuilder();

            while(line != null)
            {
                sb.append(line);
                line = reader.readLine();
            }

            String jsonString = sb.toString();
            System.out.println(sb.toString());

            JSONObject jsonObject = new JSONObject(jsonString);

            question.question = jsonObject.getString("question");
            question.correctAnswer = jsonObject.getString("correct_answer");

            question.answers = new ArrayList<>();

            JSONArray answers = jsonObject.getJSONArray("answers");

            for (int i = 0; i < answers.length(); i++)
            {
                question.answers.add(answers.getString(i));
            }

            String content = jsonObject.getString("question_id");

            InputStream contentStream = assetManager.open("Images/"+ content + ".png");

            question.content = BitmapFactory.decodeStream(contentStream);

            return question;

        }
        catch (IOException e)
        {
            Log.e("DataManager","Cannot Find Question Assets");
            e.printStackTrace();
            return null;
        }
        catch (JSONException e)
        {
            Log.e("DataManager", "JSON is improperly formatted");
            e.printStackTrace();
            return null;
        }
    }

    public int getCount()
    {
        try
        {
            int count = assetManager.list("Questions/").length;
            return count;
        }
        catch (IOException e)
        {
            Log.e("DataManager", "Cannot find Question Assets");
            return -1;
        }
    }

}

package au.edu.jcu.cp3406.paranoidandroid.game;

import android.content.res.AssetManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import au.edu.jcu.cp3406.paranoidandroid.game.sound.SoundManager;

public class Game
{
    private DataManager dataManager;
    private int totalQuestions;

    private int currentQuestion;
    private List<Integer> questionOrder;
    

    public Game(AssetManager assetManager)
    {
        dataManager = new DataManager(assetManager);
        totalQuestions = dataManager.getCount();

        questionOrder = new ArrayList<>();

        for (int i = 0; i < totalQuestions; i++)
        {
            questionOrder.add(i+1);
        }

        Collections.shuffle(questionOrder);
        currentQuestion = 0;
    }

    public Question getNextQuestion()
    {
        if(currentQuestion < totalQuestions)
        {
            return dataManager.getQuestion(questionOrder.get(currentQuestion++));
        }
        else
        {
            return null;
        }
    }
}

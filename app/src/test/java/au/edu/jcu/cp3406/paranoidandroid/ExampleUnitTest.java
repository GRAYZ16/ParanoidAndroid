package au.edu.jcu.cp3406.paranoidandroid;

import org.junit.Test;

import au.edu.jcu.cp3406.paranoidandroid.game.DataManager;
import au.edu.jcu.cp3406.paranoidandroid.score.Score;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    @Test
    public void addition_isCorrect()
    {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void score_array()
    {
        Score[] scores = new Score[10];

        for (Score score: scores)
        {
            score = new Score();
            score.name = "Josh";
            score.score = 97;
            System.out.println(String.format("Name %s Score %d", score.name, score.score));
        }
    }

}
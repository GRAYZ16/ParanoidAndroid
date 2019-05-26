package au.edu.jcu.cp3406.paranoidandroid;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import au.edu.jcu.cp3406.paranoidandroid.game.DataManager;
import au.edu.jcu.cp3406.paranoidandroid.game.Question;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest
{
    @Test
    public void useAppContext()
    {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("au.edu.jcu.cp3406.paranoidandroid", appContext.getPackageName());
    }
    @Test
    public void questionTest()
    {
        Context context = InstrumentationRegistry.getTargetContext();

        DataManager manager = new DataManager(context.getAssets());

        System.out.println(manager.getCount());

        assertEquals(manager.getCount(), 3);
    }

    @Test
    public void questionLoadTest()
    {
        Context context = InstrumentationRegistry.getTargetContext();

        DataManager manager = new DataManager(context.getAssets());

        Question question = manager.getQuestion(1);

        System.out.println(question.question);


        assertNotNull(question.correctAnswer);
        assertNotNull(question.question);
        assertNotNull(question.answers);
        assertNotNull(question.content);
    }


}

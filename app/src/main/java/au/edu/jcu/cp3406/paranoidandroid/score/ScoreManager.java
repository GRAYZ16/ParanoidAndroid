package au.edu.jcu.cp3406.paranoidandroid.score;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ScoreManager
{
    private SQLiteOpenHelper scoreDatabaseHelper;
    private Context context;

    public ScoreManager(Context context)
    {
        this.context = context;
        scoreDatabaseHelper = new ScoreDatabaseHelper(this.context);

    }

    public Score[] getTopScores(int totalEntries)
    {
        try
        {
            Score[] scores = new Score[totalEntries];

            for (int i = 0; i < totalEntries; i++)
            {
                scores[i] = new Score();
            }

            SQLiteDatabase db = scoreDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(ScoreDatabaseHelper.SCORE_TABLE, new String[] {"NAME", "SCORE"}, null, null, null, null, "SCORE DESC", Integer.toString(totalEntries));

            if(cursor != null)
            {
                cursor.moveToFirst();
            }

            int i = 0;
            do
            {
                scores[i].name = cursor.getString(0);
                scores[i].score = cursor.getInt(1);

                i++;
            }while(cursor.moveToNext());

            cursor.close();
            db.close();

            return scores;
        }
        catch (SQLiteException e)
        {
            Log.e("ScoreManager", "Score Database is unavailable");
        }
        return null;
    }
}

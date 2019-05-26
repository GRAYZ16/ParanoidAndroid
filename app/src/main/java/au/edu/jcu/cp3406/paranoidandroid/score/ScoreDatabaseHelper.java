package au.edu.jcu.cp3406.paranoidandroid.score;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ScoreDatabaseHelper extends SQLiteOpenHelper
{
    public static final String SCORE_TABLE = "SCORE";

    private static final String DB_NAME = "paranoidandroid";
    private static final int DB_VERSION = 1;

    public ScoreDatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        updateDB(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        updateDB(db, oldVersion, newVersion);
    }

    private void addScore(SQLiteDatabase db, String name, int score)
    {
        ContentValues scoreValues = new ContentValues();
        scoreValues.put("NAME", name);
        scoreValues.put("SCORE", score);
        db.insert(SCORE_TABLE, null, scoreValues);
    }

    private void updateDB(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (oldVersion < 1)
        {
            db.execSQL("CREATE TABLE " + SCORE_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME TEXT," +
                    "SCORE INTEGER);");

            addScore(db, "Josh", 97);
            addScore(db, "Sara", 96);
        }
        else
        {
            Log.e("ScoreDatabaseHelper", "DB_VERSION not supported");
        }
    }
}

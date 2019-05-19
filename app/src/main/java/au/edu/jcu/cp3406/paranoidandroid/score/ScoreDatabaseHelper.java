package au.edu.jcu.cp3406.paranoidandroid.score;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoreDatabaseHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "paranoidandroid";
    private static final int DB_VERSION = 1;

    public ScoreDatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE SCORE(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "SCORE INTEGER);");

        addScore(db, "Josh", 97);
        addScore(db, "Sara", 96);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    private static void addScore(SQLiteDatabase db, String name, int score)
    {
        ContentValues scoreValues = new ContentValues();
        scoreValues.put("NAME", name);
        scoreValues.put("SCORE", score);
        db.insert("SCORE", null, scoreValues);
    }
}

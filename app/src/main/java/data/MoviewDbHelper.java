package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Douglas on 9/4/2015.
 */
public class MoviewDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Movie.db";

    public MoviewDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table to hold trailers
        final String CREATE_TABLE_TRAILERS = "CREATE TABLE" + MovieContract.TrailerEntry.TABLE_NAME + "(" +
                MovieContract.TrailerEntry._ID + "INTEGER PRIMARY KEY," +
                MovieContract.TrailerEntry.COLUMN_NAME_TRAILER_ID + "INTEGER NOT NULL," +
                " );";

        final String CREATE_TABLE_REVIEWS = "CREATE TABLE" + MovieContract.ReviewEntry.TABLE_NAME + "(" +
                MovieContract.ReviewEntry._ID + "INTEGER PRIMARY KEY," +
                MovieContract.ReviewEntry.COLUMN_NAME_REVIEW_ID + "INTEGER NOT NULL," +
                MovieContract.ReviewEntry.COLUMN_NAME_REVIEW_AUTHOR + "TEXT NOT NULL," +
                MovieContract.ReviewEntry.COLUMN_NAME_REVIEW_CONTENT + "TEXT NOT NULL," +
                MovieContract.ReviewEntry.COLUMN_NAME_REVIEW_URL + "TEXT NOT NULL," +
                ");";

        final String CREATE_TABLE_MOVIES = "CREATE TABLE" + MovieContract.MovieEntry.TABLE_NAME + "(" +
                MovieContract.MovieEntry._ID + "INTEGER PRIMARY KEY," +
                MovieContract.MovieEntry.COLUMN_NAME_MOVIE_ID + "INTEGER NOT NULL," +
                MovieContract.MovieEntry.COLUMN_NAME_MOVIE_REVIEW_FK + "INTEGER NOT NULL," +
                MovieContract.MovieEntry.COLUMN_NAME_MOVIE_TRAILER_FK + "INTEGER NOT NULL," +
                MovieContract.MovieEntry.COLUMN_NAME_MOVIE_TITLE + "TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_NAME_MOVIE_PATH + "TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_NAME_MOVIE_OVERVIEW + "TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_NAME_MOVIE_VOTE_AVERAGE + "INTEGER NOT NULL," +
                MovieContract.MovieEntry.COLUMN_NAME_MOVIE_RELEASE_DATE + "INTEGER NOT NULL," +

                // Set up the review column as a foreign key to review table.
                " FOREIGN KEY (" + MovieContract.MovieEntry.COLUMN_NAME_MOVIE_REVIEW_FK + ") REFERENCES " +
                MovieContract.ReviewEntry.TABLE_NAME + " (" + MovieContract.ReviewEntry._ID + "), " +

                // Set up the review column as a foreign key to review table.
                " FOREIGN KEY (" + MovieContract.MovieEntry.COLUMN_NAME_MOVIE_TRAILER_FK + ") REFERENCES " +
                MovieContract.ReviewEntry.TABLE_NAME + " (" + MovieContract.TrailerEntry._ID + "), " +

                ");";

        db.execSQL(CREATE_TABLE_TRAILERS);
        db.execSQL(CREATE_TABLE_REVIEWS);
        db.execSQL(CREATE_TABLE_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Replace with alter table so that user data is not lost
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.TrailerEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.ReviewEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}

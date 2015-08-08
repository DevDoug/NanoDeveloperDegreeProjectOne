package data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.example.douglas.popularmovies.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Listeners.ITaskCompleteListener;
import popularmovieconstants.constants;
import tools.MovieDataParser;

/**
 * Created by Douglas on 7/28/2015.
 */
public class FetchMovieData extends AsyncTask<String, Void, Void> {

    public Context mContext;
    private GridView mMoviesGrid;
    private String mMovieJsonStr = null;
    public boolean mSortByMostPopular;
    ITaskCompleteListener mTaskCompleteListener;

    public FetchMovieData(Context context, GridView grid, boolean sortType, ITaskCompleteListener listener) {
        mContext = context;
        this.mMoviesGrid = grid;
        this.mSortByMostPopular = sortType;
        this.mTaskCompleteListener = listener;
    }

    @Override
    protected Void doInBackground(String... params) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            URL url;
            if(mSortByMostPopular)
                url = new URL(mContext.getString(R.string.picasso_url_popular_movies));
            else
                url = new URL(mContext.getString(R.string.picasso_url_highest_rated));

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                mMovieJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                mMovieJsonStr = null;
            }
            mMovieJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            mMovieJsonStr = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(mMovieJsonStr != null)
            constants.mMovies = MovieDataParser.getMovieData(mMovieJsonStr);

        mTaskCompleteListener.onTaskCompleted(); //Task completed alert UI that we have our data
    }
}

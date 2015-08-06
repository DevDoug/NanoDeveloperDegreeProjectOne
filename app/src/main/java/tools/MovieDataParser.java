package tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import entity.Movie;

/**
 * Created by Douglas on 7/25/2015.
 */
public class MovieDataParser {

    public static ArrayList<Movie> getMovieData(String jsonString) {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            JSONObject movieData = new JSONObject(jsonString);
            JSONArray movieDataResult = movieData.getJSONArray("results");
            for(int i = 0; i < movieDataResult.length();i++){
                JSONObject movie = movieDataResult.getJSONObject(i);
                Movie newmovie = new Movie();
                newmovie.setTitle(movie.getString("title"));
                newmovie.setPath(movie.getString("poster_path"));
                newmovie.setOverview(movie.getString("overview"));
                newmovie.setVoteAverage(movie.getString("vote_average"));
                newmovie.setReleaseDate(movie.getString("release_date"));
                movies.add(newmovie);
            }
            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}

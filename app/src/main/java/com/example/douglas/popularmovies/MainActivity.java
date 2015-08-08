package com.example.douglas.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import listeners.ITaskCompleteListener;
import adapters.MovieAdapter;
import entity.Movie;
import data.FetchMovieData;
import popularmovieconstants.Constants;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, ITaskCompleteListener {

    private GridView mMoviesGrid;
    private static boolean mSortByMostPopular = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoviesGrid = (GridView) findViewById(R.id.movie_list_grid);
        mMoviesGrid.setOnItemClickListener(this);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            this.onTaskCompleted();
        } else {
            new FetchMovieData(this, mMoviesGrid, true,this).execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_sort) {
            new FetchMovieData(this, mMoviesGrid,mSortByMostPopular,this).execute();
            mSortByMostPopular = !mSortByMostPopular;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
/*        savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
        savedInstanceState.putInt(STATE_LEVEL, mCurrentLevel);*/

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //onclicking an item go to the detail view with and populate it with that moves data
        Intent movieDetailsIntent = new Intent(this,MovieDetailActivity.class);
        Movie selectedmovie = (Movie) view.findViewById(R.id.movie_image).getTag();
        movieDetailsIntent.putExtra(getString(R.string.moviedb_title_field),selectedmovie.getTitle());
        movieDetailsIntent.putExtra(getString(R.string.moviedb_poster_path_field),selectedmovie.getPath());
        movieDetailsIntent.putExtra(getString(R.string.movie_image_field),selectedmovie.getPath());
        movieDetailsIntent.putExtra(getString(R.string.moviedb_overview_field), selectedmovie.getOverview());
        movieDetailsIntent.putExtra(getString(R.string.moviedb_vote_average_field),selectedmovie.getVoteAverage());
        movieDetailsIntent.putExtra(getString(R.string.moviedb_release_date_field), selectedmovie.getReleaseDate());
        startActivity(movieDetailsIntent);
    }

    @Override
    public void onTaskCompleted() {
        if(Constants.mMovies != null) {
            Constants.mMoviesAdapter = new MovieAdapter(this, Constants.mMovies);
            mMoviesGrid.setAdapter(Constants.mMoviesAdapter);
        }
    }
}

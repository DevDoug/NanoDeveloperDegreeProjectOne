package com.example.douglas.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import listeners.ITaskCompleteListener;
import adapters.MovieAdapter;
import entity.Movie;
import data.FetchMovieData;
import popularmovieconstants.Constants;
import retrofit.RestAdapter;
import web.MovieDBService;
import web.WebService;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, ITaskCompleteListener {

    private GridView mMoviesGrid;
    public FetchMovieData mDataFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoviesGrid = (GridView) findViewById(R.id.movie_list_grid);
        mMoviesGrid.setOnItemClickListener(this);

        mDataFetcher = new FetchMovieData(this,this);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            this.onFetchMovieTaskCompleted();
        } else {
           mDataFetcher.getMovies();
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
            mDataFetcher.getMovies();
            mDataFetcher.mSortByMostPopular = !mDataFetcher.mSortByMostPopular;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //onclicking an item go to the detail view with and populate it with that moves data
        Intent movieDetailsIntent = new Intent(this,MovieDetailActivity.class);
        Movie selectedmovie = (Movie) view.findViewById(R.id.movie_image).getTag();
        movieDetailsIntent.putExtra(getString(R.string.moviedb_movie_id), selectedmovie.getID());
        movieDetailsIntent.putExtra(getString(R.string.moviedb_title_field),selectedmovie.getTitle());
        movieDetailsIntent.putExtra(getString(R.string.moviedb_poster_path_field),selectedmovie.getPath());
        movieDetailsIntent.putExtra(getString(R.string.movie_image_field),selectedmovie.getPath());
        movieDetailsIntent.putExtra(getString(R.string.moviedb_overview_field), selectedmovie.getOverview());
        movieDetailsIntent.putExtra(getString(R.string.moviedb_vote_average_field),selectedmovie.getVoteAverage());
        movieDetailsIntent.putExtra(getString(R.string.moviedb_release_date_field), selectedmovie.getReleaseDate());
        startActivity(movieDetailsIntent);
    }

    @Override
    public void onFetchMovieTaskCompleted() {
        if(Constants.mMovies != null) {
            Constants.mMoviesAdapter = new MovieAdapter(this, Constants.mMovies);
            mMoviesGrid.setAdapter(Constants.mMoviesAdapter);
        }
    }

    @Override
    public void onFetchReviewsTaskCompleted() {

    }

    @Override
    public void onFetchTrailerTaskCompleted() {

    }
}

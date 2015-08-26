package com.example.douglas.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import adapters.MovieTrailerAdapter;
import data.FetchMovieData;
import entity.Movie;
import listeners.ITaskCompleteListener;
import popularmovieconstants.Constants;

public class MovieDetailActivity extends Activity implements ITaskCompleteListener {

    public Movie mMovie = new Movie();
    public TextView mMovieTitleText;
    public ImageView mMoviePoster;
    public TextView mMovieOverview;
    public TextView mMovieVoteAverageText;
    public TextView mMovieDateReleasedfield;
    public FetchMovieData mDataFetcher;
    public ListView mMovieTrailerList;
    public MovieTrailerAdapter mTrailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            mMovie.setTitle(extras.getString(getString(R.string.moviedb_title_field)));
            mMovie.setPath(extras.getString(getString(R.string.moviedb_poster_path_field)));
            mMovie.setOverview(extras.getString(getString(R.string.moviedb_overview_field)));
            mMovie.setVoteAverage(extras.getString(getString(R.string.moviedb_vote_average_field)));
            mMovie.setReleaseDate(extras.getString(getString(R.string.moviedb_release_date_field)));
        }

        mMovieTitleText = (TextView) findViewById(R.id.movie_title);
        mMoviePoster = (ImageView) findViewById(R.id.movie_poster_detail);
        mMovieOverview = (TextView) findViewById(R.id.movie_overview);
        mMovieVoteAverageText = (TextView) findViewById(R.id.movie_average);
        mMovieDateReleasedfield = (TextView) findViewById(R.id.movie_release_date_field);
        mMovieTrailerList = (ListView) findViewById(R.id.movie_trailer_list);

        mMovieTitleText.setText(mMovie.getTitle());
        mMovieOverview.setText(mMovie.getOverview());
        mMovieVoteAverageText.setText(String.format(getString(R.string.movie_rating_date), mMovie.getVoteAverage()));
        mMovieDateReleasedfield.setText(mMovie.getReleaseDate());

        String movieposterurllarge = (this.getString(R.string.moviedb_poster_base_url).concat(this.getString(R.string.moviedb_size_w500)).concat(mMovie.getPath()));
        Picasso.with(this).load(movieposterurllarge).into(mMoviePoster);

        mDataFetcher = new FetchMovieData(this,this);

        mDataFetcher.getVideos();

        //mDataFetcher.getReviews();

        mTrailerAdapter = new MovieTrailerAdapter(this, Constants.mTrailers);
        mMovieTrailerList.setAdapter(mTrailerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void MarkAsFavorite() {

    }

    @Override
    public void onTaskCompleted() {

    }
}

package com.example.douglas.popularmovies;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import entity.Movie;
import popularmovieconstants.Constants;

public class MovieDetailActivity extends Activity {

    public Movie mMovie = new Movie();
    public TextView mMovieTitleText;
    public ImageView mMoviePoster;
    public EditText mMovieOverview;
    public TextView mMovieVoteAverageText;
    public TextView mMovieDateReleasedfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            mMovie.setTitle(extras.getString(getString(R.string.moviedb_title_field)));
            mMovie.setPath(extras.getString(getString(R.string.moviedb_poster_path_field)));
            mMovie.setPosterImage(Constants.getBitmap(extras.getByteArray(getString(R.string.movie_image_field))));
            mMovie.setOverview(extras.getString(getString(R.string.moviedb_overview_field)));
            mMovie.setVoteAverage(extras.getString(getString(R.string.moviedb_vote_average_field)));
            mMovie.setReleaseDate(extras.getString(getString(R.string.moviedb_release_date_field)));
        }

        mMovieTitleText = (TextView) findViewById(R.id.movie_title);
        mMoviePoster = (ImageView) findViewById(R.id.movie_poster_detail);
        mMovieOverview = (EditText) findViewById(R.id.movie_overview);
        mMovieVoteAverageText = (TextView) findViewById(R.id.movie_average);
        mMovieDateReleasedfield = (TextView) findViewById(R.id.movie_release_date_field);

        mMovieTitleText.setText(mMovie.getTitle());
        mMoviePoster.setImageBitmap(mMovie.getPosterImage());
        mMovieOverview.setText(mMovie.getOverview());
        mMovieVoteAverageText.setText(String.format(getString(R.string.movie_rating_date), mMovie.getVoteAverage()));
        mMovieDateReleasedfield.setText(mMovie.getReleaseDate());
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
}

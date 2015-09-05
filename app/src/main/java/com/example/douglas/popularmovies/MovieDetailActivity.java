package com.example.douglas.popularmovies;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import adapters.MovieReviewAdapter;
import adapters.MovieTrailerAdapter;
import data.FetchMovieData;
import entity.Movie;
import listeners.ITaskCompleteListener;
import popularmovieconstants.Constants;

public class MovieDetailActivity extends Activity implements ITaskCompleteListener, View.OnClickListener {

    public Movie mMovie = new Movie();
    public TextView mMovieTitleText;
    public ImageView mMoviePoster;
    public TextView mMovieOverview;
    public TextView mMovieVoteAverageText;
    public TextView mMovieDateReleasedfield;
    public FetchMovieData mDataFetcher;
    public ListView mMovieTrailerList;
    public ListView mReviewList;
    public MovieTrailerAdapter mMovieTrailerAdapter;
    public MovieReviewAdapter mMovieReviewAdapter;
    public Button mReviewButton;
    public Button mTrailerButton;
    public boolean mIsFavorited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            mMovie.setID(extras.getString(getString(R.string.moviedb_movie_id)));
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
        mReviewList = (ListView) findViewById(R.id.movie_review_list);
        mReviewButton = (Button) findViewById(R.id.switch_to_review_button);
        mTrailerButton = (Button) findViewById(R.id.switch_to_trailer_button);

        mMovieTitleText.setText(mMovie.getTitle());
        mMovieOverview.setText(mMovie.getOverview());
        mMovieVoteAverageText.setText(String.format(getString(R.string.movie_rating_date), mMovie.getVoteAverage()));
        mMovieDateReleasedfield.setText(mMovie.getReleaseDate().split("-")[0]);

        String movieposterurllarge = (this.getString(R.string.moviedb_poster_base_url).concat(this.getString(R.string.moviedb_size_w500)).concat(mMovie.getPath()));
        Picasso.with(this).load(movieposterurllarge).into(mMoviePoster);

        mDataFetcher = new FetchMovieData(this,this);
        mDataFetcher.setReviewID(mMovie.getID());
        mDataFetcher.setTrailerID(mMovie.getID());
        mDataFetcher.getVideos();
        mDataFetcher.getReviews();

        mReviewButton.setOnClickListener(this);
        mTrailerButton.setOnClickListener(this);
        mMovieTrailerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //play trailer
                TextView movietitleview = (TextView) view.findViewById(R.id.trailer_name);
                String movieid = movietitleview.getTag().toString();
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_app_uri) + movieid));
                    startActivity(intent);
                }catch (ActivityNotFoundException ex){
                    Intent intent=new Intent(Intent.ACTION_VIEW,
                            Uri.parse(getString(R.string.youtube_web_url) + movieid));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mIsFavorited)
            saveMovieToFavorites();
    }

    public void saveMovieToFavorites(){

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
        mIsFavorited = !mIsFavorited;
    }

    @Override
    public void onFetchMovieTaskCompleted() {

    }

    @Override
    public void onFetchReviewsTaskCompleted() {
        mMovieReviewAdapter = new MovieReviewAdapter(this, Constants.mReviews);
        mReviewList.setAdapter(mMovieReviewAdapter);
    }

    @Override
    public void onFetchTrailerTaskCompleted() {
        mMovieTrailerAdapter = new MovieTrailerAdapter(this, Constants.mTrailers);
        mMovieTrailerList.setAdapter(mMovieTrailerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_to_review_button:
                switchadaptertoreviews();
                mReviewButton.setVisibility(View.GONE);
                mTrailerButton.setVisibility(View.VISIBLE);
                break;
            case R.id.switch_to_trailer_button:
                mReviewButton.setVisibility(View.VISIBLE);
                mTrailerButton.setVisibility(View.GONE);
                switchadaptertotrailers();
                break;
            default:
                break;
        }
    }

    public void switchadaptertoreviews(){
        mMovieTrailerList.setVisibility(View.INVISIBLE);
        mReviewList.setVisibility(View.VISIBLE);
    }

    public void switchadaptertotrailers(){
        mMovieTrailerList.setVisibility(View.VISIBLE);
        mReviewList.setVisibility(View.INVISIBLE);
    }
}

package web;

import java.net.URLEncoder;

import entity.MovieJSON;
import entity.ReviewJSON;
import entity.VideoJSON;
import retrofit.RestAdapter;

/**
 * Created by Douglas on 8/13/2015.
 */
public class WebService {

    RestAdapter mRetrofit;

    MovieDBService mService;

    public WebService() {
        mRetrofit = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org")
                .build();
        mService = mRetrofit.create(MovieDBService.class);
    }

    public MovieJSON getMovies(boolean sortType) {
        if(sortType)
            return mService.listMovies("popularity.desc&api_key=d273a1a1fb9390dab97ac0032b12366a");
        else
            return mService.listMovies("vote_count.desc&api_key=d273a1a1fb9390dab97ac0032b12366a");
    }

    public ReviewJSON getReviews() {
        return mService.listReviews("76341");
    }

    public VideoJSON getVideos() {
        return mService.listVideos("76341");
    }
}

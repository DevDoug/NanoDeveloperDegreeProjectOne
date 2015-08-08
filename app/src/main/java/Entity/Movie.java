package Entity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by Douglas on 7/28/2015.
 */
public class Movie {

    public String mTitle;

    public String mPath;

    public String mOverview;

    public String mVoteAverage;

    public String mReleaseDate;

    public Bitmap mPosterImage;

    public String getPath() {
        return mPath;
    }

    public void setPath(String Path) {
        this.mPath = Path;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String Title) {
        this.mTitle = Title;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String Overview) {
        this.mOverview = Overview;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String VoteAverage) {
        this.mVoteAverage = VoteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String ReleaseDate) {
        this.mReleaseDate = ReleaseDate;
    }

    public Bitmap getPosterImage() {
        return mPosterImage;
    }

    public void setPosterImage(Bitmap posterImage) {
        mPosterImage = posterImage;
    }
}

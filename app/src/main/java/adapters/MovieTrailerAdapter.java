package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.douglas.popularmovies.R;
import java.util.ArrayList;
import entity.Movie;

/**
 * Created by Douglas on 8/25/2015.
 */
public class MovieTrailerAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<Movie> mItems;

    public MovieTrailerAdapter(Context context, ArrayList objects) {
        super(context, R.layout.movie_trailer_list_item, objects);
        this.mContext = context;
        this.mItems = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //if the view is null than inflate it otherwise just fill the list with
        if (convertView == null) {
            //inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.movie_grid_item, parent, false);
        }

        return convertView;
    }
}

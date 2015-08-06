package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.douglas.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Douglas on 7/28/2015.
 */
public class MovieAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<String> mItems;

    public MovieAdapter(Context context, ArrayList<String> objects) {
        super(context, R.layout.movie_grid_item, objects);
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

        ImageView image = (ImageView) convertView.findViewById(R.id.movie_image);
        String movieposterurl = (getString(R.string.moviedb_base_url).concat(getString(R.string.moviedb_size_w185)).concat(mItems.get(position)));
        Picasso.with(mContext).load(movieposterurl).into(image);
        return convertView;
    }
}

package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcel;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.DetailActivity;
import com.example.flixster.GlideApp;
import com.example.flixster.MainActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;
import com.example.flixster.view_holders.ViewHolder1;
import com.example.flixster.view_holders.ViewHolder2;
import com.example.flixster.view_holders.ViewHolder3;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Movie> movies;
    private final int POSTER = 1;
    private final int BACKDROP = 2;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case POSTER:
                View v1 = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new ViewHolder1(v1);
                break;
            case BACKDROP:
                View v2 = inflater.inflate(R.layout.item_movie_popular, parent, false);
                switch (context.getResources().getConfiguration().orientation) {
                    case Configuration.ORIENTATION_PORTRAIT:
                        viewHolder = new ViewHolder2(v2);
                        break;
                    case Configuration.ORIENTATION_LANDSCAPE:
                        viewHolder = new ViewHolder3(v2);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + viewType);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RelativeLayout container;

        switch (holder.getItemViewType()) {
            case POSTER:
                ViewHolder1 vh1 = (ViewHolder1) holder;
                configureViewHolder1(vh1, position);
                container = vh1.getContainer();
                break;
            case BACKDROP:
                switch (context.getResources().getConfiguration().orientation) {
                    case Configuration.ORIENTATION_PORTRAIT:
                        ViewHolder2 vh2 = (ViewHolder2) holder;
                        configureViewHolder2(vh2, position);
                        container = vh2.getContainer();
                        break;
                    case Configuration.ORIENTATION_LANDSCAPE:
                        ViewHolder3 vh3 = (ViewHolder3) holder;
                        configureViewHolder3(vh3, position);
                        container = vh3.getContainer();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + holder.getItemViewType());
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + holder.getItemViewType());
        }
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("movie", Parcels.wrap(movies.get(position)));
                context.startActivity(i);
            }
        });
    }

    private void configureViewHolder3(ViewHolder3 vh3, int position) {
        vh3.getTvTitle().setText(movies.get(position).getTitle());
        vh3.getTvOverview().setText(movies.get(position).getOverview());
        GlideApp.with(context).load(movies.get(position).getBackdropPath()).placeholder(R.drawable.ic_iconfinder_gallery_1214963).transform(new RoundedCornersTransformation(30,0)).into(vh3.getIvBackdrop());
    }

    private void configureViewHolder2(ViewHolder2 vh2, int position) {
        GlideApp.with(context).load(movies.get(position).getBackdropPath()).placeholder(R.drawable.ic_iconfinder_gallery_1214963).transform(new RoundedCornersTransformation(30,0)).into(vh2.getIvBackdrop());
    }

    private void configureViewHolder1(ViewHolder1 vh1, int position) {
        vh1.getTvTitle().setText(movies.get(position).getTitle());
        vh1.getTvOverview().setText(movies.get(position).getOverview());
        String imageURL;
        imageURL = movies.get(position).getPosterPath();
        GlideApp.with(context).load(imageURL).transform(new RoundedCornersTransformation(30,0)).placeholder(R.drawable.ic_iconfinder_gallery_1214963).into(vh1.getIvPoster());
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (movies.get(position).getVote() >= 5.0) {
            return BACKDROP;
        } else {
            return POSTER;
        }
    }

}

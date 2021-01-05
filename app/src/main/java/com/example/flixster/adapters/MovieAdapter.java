package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;
import com.example.flixster.view_holders.ViewHolder1;
import com.example.flixster.view_holders.ViewHolder2;

import java.util.List;

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
                viewHolder = new ViewHolder2(v2);
                break;
            default:
                View v3 = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new ViewHolder1(v3);
        }
        return viewHolder;

       // Log.d("MovieAdapter", "onCreateViewHolder");
     //   View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case POSTER:
                ViewHolder1 vh1 = (ViewHolder1) holder;
                configureViewHolder1(vh1, position);
                break;
            case BACKDROP:
                ViewHolder2 vh2 = (ViewHolder2) holder;
                configureViewHolder2(vh2, position);
                break;
        }
    }

    private void configureViewHolder2(ViewHolder2 vh2, int position) {
        Glide.with(context).load(movies.get(position).getBackdropPath()).placeholder(R.drawable.ic_iconfinder_gallery_1214963).into(vh2.getIvBackdrop());
    }

    private void configureViewHolder1(ViewHolder1 vh1, int position) {
        vh1.getTvTitle().setText(movies.get(position).getTitle());
        vh1.getTvOverview().setText(movies.get(position).getOverview());
        String imageURL;
        if(movies.get(position).getVote() >= 5.0) {
            imageURL = movies.get(position).getBackdropPath();
        } else {
            imageURL = movies.get(position).getPosterPath();
        }
        Glide.with(context).load(imageURL).placeholder(R.drawable.ic_iconfinder_gallery_1214963).into(vh1.getIvPoster());
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (movies.get(position).getVote() >= 5.0 && context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return BACKDROP;
        } else {
            return POSTER;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageURL;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageURL = movie.getBackdropPath();
            } else {
                imageURL = movie.getPosterPath();
            }
            Glide.with(context).load(imageURL).placeholder(R.drawable.ic_iconfinder_gallery_1214963).into(ivPoster);
        }
    }
}

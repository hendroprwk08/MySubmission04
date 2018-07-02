/*
 * Created by omrobbie.
 * Copyright (c) 2018. All rights reserved.
 * Last modified 11/11/17 3:14 PM.
 */

package com.dicoding.hendropurwoko.mysubmission04;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    public ArrayList<MovieModel> movieModels = new ArrayList<>();
    Context c ;

    public MovieAdapter(Context c) {this.c = c; }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        Log.d ("Info ", movieModels.get(position).getTitle() + " " +
                movieModels.get(position).getRelease_date()+ " " +
                movieModels.get(position).getOverview()+ " " +
                movieModels.get(position).getPopularity()+ " " +
                movieModels.get(position).getPoster());

        holder.tvTitle.setText(movieModels.get(position).getTitle());
        holder.tvOverview.setText(movieModels.get(position).getOverview());
        holder.tvReleaseDate.setText(movieModels.get(position).getRelease_date());

        Glide.with(c)
                .load(movieModels.get(position).getPoster())
                .override(350, 350)
                .into(holder.ivPoster);
    }

    public void addItem(ArrayList<MovieModel> movieList) {
        this.movieModels = movieList;
        notifyDataSetChanged();
    }
    public int getItemCount() {
        return movieModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvOverview, tvReleaseDate;
        ImageView ivPoster;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
            tvOverview = (TextView)itemView.findViewById(R.id.tv_overview);
            tvReleaseDate = (TextView)itemView.findViewById(R.id.tv_release_date);
            ivPoster = (ImageView)itemView.findViewById(R.id.img_poster);
        }
    }
}
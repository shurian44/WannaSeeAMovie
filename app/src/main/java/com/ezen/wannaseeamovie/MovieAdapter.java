package com.ezen.wannaseeamovie;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ezen.wannaseeamovie.model2.MovieSearchDTO;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    interface selectMovie{
        public void moveActivity(Intent intent);
    }

    selectMovie listener;

    public MovieAdapter(selectMovie listener) {
        this.listener = listener;
    }

    ArrayList<MovieSearchDTO> items = new ArrayList<>();
    View itemView;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        itemView = inflater.inflate(R.layout.search_results, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        MovieSearchDTO item = items.get(position);
        holder.setItem(item);

        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailedInfo.class);
                intent.putExtra("movieID", items.get(position).getId());
                listener.moveActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(MovieSearchDTO item) {
        items.add(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title, releaseDate, originalLanguage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.title);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            originalLanguage = itemView.findViewById(R.id.originalLanguage);
        }

        public void setItem(MovieSearchDTO item) {
            title.setText(item.getTitle());
            releaseDate.setText(item.getReleaseDate());
            originalLanguage.setText(item.getLanguage());
            Glide.with(itemView.getContext())
                    .load(item.getImg_path())
                    .into(poster);
        }
    } // class ViewHolder
} // classMovieAdapter

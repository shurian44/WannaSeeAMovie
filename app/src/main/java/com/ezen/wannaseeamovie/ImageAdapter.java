package com.ezen.wannaseeamovie;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ezen.wannaseeamovie.model2.ImageSearchDTO;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    interface selectMovie {
        public void moveActivity(Intent intent);
    }

    selectMovie listener;

    public ImageAdapter(selectMovie listener) {
        this.listener = listener;
    }

    ArrayList<ImageSearchDTO> items = new ArrayList<>();
    View itemView;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        itemView = inflater.inflate(R.layout.search_images, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        ImageSearchDTO item = items.get(position);
        holder.setItem(item);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ImageSearchDTO item) {
        items.add(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
        }

        public void setItem(ImageSearchDTO item) {
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/original" + item.getFile_path())
                    .into(image);
//            Log.e("test", "itemView.getContext() : " + itemView.getContext());
//            Log.e("test", "item.getFile_path() : " + item.getFile_path());
        }
    } // class ViewHolder
} // class ImageAdapter
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
import com.ezen.wannaseeamovie.model2.CastSearchDTO;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    interface selectMovie{
        public void moveActivity(Intent intent);
    }

    selectMovie listener;

    public CastAdapter(selectMovie listener) {
        this.listener = listener;
    }

    ArrayList<CastSearchDTO> items = new ArrayList<>();
    View itemView;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        itemView = inflater.inflate(R.layout.search_casts, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        CastSearchDTO item = items.get(position);
        holder.setItem(item);

        holder.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("test", "onBind~ onClick: position - " + position + "title - " + items.get(position).getName());
//                Intent intent = new Intent(holder.itemView.getContext(), DetailedInfo.class);
//                intent.putExtra("movieID", items.get(position).getId());
//                listener.moveActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(CastSearchDTO item) {
        items.add(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name, character;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.profile);
            name = itemView.findViewById(R.id.name);
            character = itemView.findViewById(R.id.character);
        }

        public void setItem(CastSearchDTO item) {
            name.setText(item.getName());
            character.setText(item.getCharacter());
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/original" + item.getProfile_path())
                    .into(profile);
        }
    } // class ViewHolder
} // class CastAdapter

package com.example.tertan.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tertan.Model.Stadium_model;
import com.example.tertan.Model.Stadium_model_original;
import com.example.tertan.R;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Stadium_adapter extends RecyclerView.Adapter<Stadium_adapter.MyViewHolder> {

    List<Stadium_model_original> stadium_model_originals;
    Context context;
    private final OnClickHander onClickHander;

    public interface OnClickHander {
        void onClick(int position);
    }

    public Stadium_adapter(List<Stadium_model_original> stadium_model_originals, Context context, OnClickHander onClickHander) {
        this.stadium_model_originals = stadium_model_originals;
        this.context = context;
        this.onClickHander = onClickHander;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item, null);
        return new Stadium_adapter.MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.stadium_image.setClipToOutline(true);
        Glide.with(context).load(stadium_model_originals.get(position).getImageURL()).into(holder.stadium_image);
        holder.stadium_name.setText(stadium_model_originals.get(position).getStadium_name());
        holder.stadium_place.setText(stadium_model_originals.get(position).getStadium_address());

    }

    @Override
    public int getItemCount() {
        return stadium_model_originals.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatImageView stadium_image;
        TextView stadium_name,stadium_place;
        LinearLayoutCompat item_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            stadium_image = itemView.findViewById(R.id.myimg);
            stadium_name = itemView.findViewById(R.id.stadium_name);
            stadium_place = itemView.findViewById(R.id.stadium_adress);
            item_layout = itemView.findViewById(R.id.item_layout);
            item_layout.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            onClickHander.onClick(position);
            notifyDataSetChanged();
        }

    }
}

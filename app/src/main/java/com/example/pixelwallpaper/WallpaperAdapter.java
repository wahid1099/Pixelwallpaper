package com.example.pixelwallpaper;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpperViewholder>{
    private Context context;
    List<WallpaperModel> wallpaperModelList;
    public WallpaperAdapter(Context context, List<WallpaperModel> wallpaperModelList) {
        this.context = context;
        this.wallpaperModelList = wallpaperModelList;
    }

    @NonNull
    @Override
    public WallpperViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.image_item,viewGroup,false);
        return new WallpperViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpperViewholder holder, final int position) {
        Glide.with(context).load(wallpaperModelList.get(position).getMediumurl()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,FullScreeen.class)
                        .putExtra("orginalurl",wallpaperModelList.get(position).getOrginalurl()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return wallpaperModelList.size();
    }
}
class WallpperViewholder extends RecyclerView.ViewHolder{
ImageView imageView;
    public WallpperViewholder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageViewitem);

    }
}

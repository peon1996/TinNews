package com.company.homework9.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.company.homework9.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailPhotoAdapter extends RecyclerView.Adapter<DetailPhotoAdapter.PhotoViewHolder> {
    private LayoutInflater mInflator;
    private List<String> data;

    public DetailPhotoAdapter(Context context, List<String> data) {
        this.mInflator = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflator.inflate(R.layout.detail_photo, parent, false);
        return new DetailPhotoAdapter.PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DetailPhotoAdapter.PhotoViewHolder holder, int position) {
        String url = data.get(position);
        Picasso.get().load(url).into(holder.photo_view);
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView photo_view;

        PhotoViewHolder(View v) {
            super(v);
            photo_view = v.findViewById(R.id.photo_item);
        }
    }
}

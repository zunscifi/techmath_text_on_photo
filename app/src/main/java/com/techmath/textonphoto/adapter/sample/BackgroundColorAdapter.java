package com.techmath.textonphoto.adapter.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techmath.textonphoto.R;
import com.techmath.textonphoto.interfaces.ItemClickListener;
import com.techmath.textonphoto.model.ImgModel;
import com.techmath.textonphoto.views.CircleImageView;
import com.bumptech.glide.Glide;

import java.util.List;

public class BackgroundColorAdapter extends RecyclerView.Adapter<BackgroundColorAdapter.ViewHolderColor> {

    List<ImgModel> sampleArrayList;
    Context context;
    ItemClickListener itemClickListener;

    public BackgroundColorAdapter(List<ImgModel> arrayList, Context context, ItemClickListener itemClickListener) {
        this.sampleArrayList = arrayList;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    public ViewHolderColor onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_color_background, viewGroup, false);
        final ViewHolderColor viewHolderColor = new ViewHolderColor(inflate);
        inflate.setOnClickListener(view -> BackgroundColorAdapter.this.itemClickListener.onItemClick(view, viewHolderColor.getLayoutPosition()));
        return viewHolderColor;
    }

    public void onBindViewHolder(@NonNull ViewHolderColor viewHolderColor, int i) {
        Glide.with(this.context).load(this.sampleArrayList.get(i).getImgModel()).thumbnail(0.1f).into(viewHolderColor.circleImageView);
    }

    public int getItemCount() {
        return this.sampleArrayList.size();
    }

    public static class ViewHolderColor extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;

        public ViewHolderColor(View view) {
            super(view);
            this.circleImageView = view.findViewById(R.id.img_background_circle);
        }
    }
}

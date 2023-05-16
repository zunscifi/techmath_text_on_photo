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
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class BackgroundImageAdapter extends RecyclerView.Adapter<BackgroundImageAdapter.ViewHolderImage> {


    List<ImgModel> sampleArrayList;


    Context context;


    ItemClickListener itemClickListener;

    public BackgroundImageAdapter(List<ImgModel> arrayList, Context context, ItemClickListener itemClickListener) {
        this.sampleArrayList = arrayList;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    public ViewHolderImage onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image_background, viewGroup, false);
        final ViewHolderImage viewHolderImage = new ViewHolderImage(inflate);
        inflate.setOnClickListener(view -> BackgroundImageAdapter.this.itemClickListener.onItemClick(view, viewHolderImage.getLayoutPosition()));
        return viewHolderImage;
    }

    public void onBindViewHolder(@NonNull ViewHolderImage viewHolderImage, int i) {
        Glide.with(this.context).load(this.sampleArrayList.get(i).getImgModel()).thumbnail(0.1f).into(viewHolderImage.roundedImageView);
    }

    public int getItemCount() {
        return this.sampleArrayList.size();
    }

    public static class ViewHolderImage extends RecyclerView.ViewHolder {


        RoundedImageView roundedImageView;

        public ViewHolderImage(View view) {
            super(view);
            this.roundedImageView = view.findViewById(R.id.img_background_rectange);
        }
    }
}

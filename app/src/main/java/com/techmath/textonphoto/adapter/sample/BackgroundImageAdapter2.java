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

public class BackgroundImageAdapter2 extends RecyclerView.Adapter<BackgroundImageAdapter2.ViewHolderImage2> {
    List<ImgModel> sampleArrayList;
    Context context;
    ItemClickListener itemClickListener;

    public BackgroundImageAdapter2(List<ImgModel> arrayList, Context context, ItemClickListener itemClickListener) {
        this.sampleArrayList = arrayList;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    public ViewHolderImage2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image_background2, viewGroup, false);
        final ViewHolderImage2 viewHolderImage2 = new ViewHolderImage2(inflate);
        inflate.setOnClickListener(view -> BackgroundImageAdapter2.this.itemClickListener.onItemClick(view, viewHolderImage2.getLayoutPosition()));
        return viewHolderImage2;
    }

    public void onBindViewHolder(@NonNull ViewHolderImage2 viewHolderImage2, int i) {
        Glide.with(this.context).load(this.sampleArrayList.get(i).getImgModel()).thumbnail(0.1f).into(viewHolderImage2.roundedImageView);
    }

    public int getItemCount() {
        return this.sampleArrayList.size();
    }

    public static class ViewHolderImage2 extends RecyclerView.ViewHolder {


        RoundedImageView roundedImageView;

        public ViewHolderImage2(View view) {
            super(view);
            this.roundedImageView = view.findViewById(R.id.img_background_2);
        }
    }
}

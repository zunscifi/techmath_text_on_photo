package com.techmath.textonphoto.fragments.photoedit.sticker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techmath.textonphoto.R;
import com.techmath.textonphoto.model.ImgModel;

import com.techmath.textonphoto.views.SquareImageView;
import com.bumptech.glide.Glide;

import java.util.List;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolderSticker> {

    public StickerAdaperListener listener;
    private final Context mContext;

    public List<ImgModel> stickerArrayList;

    int currentPos = -1;
    public interface StickerAdaperListener {
        void onStickerSelected(int i);
    }

    public StickerAdapter(List<ImgModel> arrayList, Context context, StickerAdaperListener stickerAdaperListener) {
        this.stickerArrayList = arrayList;
        this.mContext = context;
        this.listener = stickerAdaperListener;
    }

    @NonNull
    public ViewHolderSticker onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderSticker(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sticker, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolderSticker viewHolderSticker, @SuppressLint("RecyclerView") int i) {
        Glide.with(this.mContext).load(Integer.valueOf(this.stickerArrayList.get(i).getImgModel())).thumbnail(0.1f).into((ImageView) viewHolderSticker.imgSticker);

        if (i == currentPos){
            viewHolderSticker.imgSticker.setBackgroundResource(R.drawable.t_gradient_bg);
        }else {
            viewHolderSticker.imgSticker.setBackgroundResource(R.drawable.card_white);
        }

        viewHolderSticker.imgSticker.setOnClickListener(view1 -> {
            if (StickerAdapter.this.listener != null) {
                currentPos = i;
                notifyDataSetChanged();
                StickerAdapter.this.listener.onStickerSelected(StickerAdapter.this.stickerArrayList.get(i).getImgModel());
            }
        });
    }

    public int getItemCount() {
        return this.stickerArrayList.size();
    }

    public class ViewHolderSticker extends RecyclerView.ViewHolder {


        SquareImageView imgSticker;

        public ViewHolderSticker(View view) {
            super(view);
            this.imgSticker = view.findViewById(R.id.imgSticker);

        }
    }
}

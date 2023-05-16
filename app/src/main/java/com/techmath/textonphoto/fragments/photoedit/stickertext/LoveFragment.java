package com.techmath.textonphoto.fragments.photoedit.stickertext;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.techmath.textonphoto.R;
import com.techmath.textonphoto.interfaces.OverplayListener;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class LoveFragment extends BottomSheetDialogFragment {

    public OverplayListener listener;
    private final BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        public void onSlide(@NonNull View view, float f) {
        }

        public void onStateChanged(@NonNull View view, int i) {
            if (i == 5) {
                LoveFragment.this.dismiss();
            }
        }
    };

    public void setOverplayListener(OverplayListener overplayListener) {
        this.listener = overplayListener;
    }

    @SuppressLint({"RestrictedApi", "ResourceType"})
    @Override
    public void setupDialog(@NonNull Dialog dialog, int i) {
        super.setupDialog(dialog, i);
        View inflate = View.inflate(getContext(), R.layout.fragment_bottom_sticker_emoji_dialog,  null);
        dialog.setContentView(inflate);
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) ( (View)inflate.getParent()).getLayoutParams()).getBehavior();
        if ((behavior instanceof BottomSheetBehavior)) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(this.mBottomSheetBehaviorCallback);
        }
        ((View) inflate.getParent()).setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()),17170445));
        RecyclerView recyclerView = inflate.findViewById(R.id.rvEmoji);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(new LoveAdapter());
    }

    public class LoveAdapter extends RecyclerView.Adapter<LoveAdapter.ViewHolder> {


        int[] loveList = {R.drawable.love, R.drawable.love0, R.drawable.love1, R.drawable.love2, R.drawable.love3, R.drawable.love4, R.drawable.love5, R.drawable.love6, R.drawable.love7, R.drawable.love8, R.drawable.love9, R.drawable.love10, R.drawable.love11, R.drawable.love12, R.drawable.love13, R.drawable.love14, R.drawable.love15, R.drawable.love16, R.drawable.love17, R.drawable.love18, R.drawable.love19, R.drawable.love20, R.drawable.love21, R.drawable.love22, R.drawable.love23, R.drawable.love24, R.drawable.love25, R.drawable.love26, R.drawable.love27, R.drawable.love28, R.drawable.love29, R.drawable.love30, R.drawable.love31, R.drawable.love32, R.drawable.love33, R.drawable.love34, R.drawable.love35, R.drawable.love36, R.drawable.love37, R.drawable.love38, R.drawable.love39, R.drawable.love40, R.drawable.love41, R.drawable.love42};

        public LoveAdapter() {
        }

        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sticker, viewGroup, false));
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Glide.with(Objects.requireNonNull(LoveFragment.this.getActivity())).load(this.loveList[i]).thumbnail(0.1f).into(viewHolder.imgSticker);
        }

        public int getItemCount() {
            return this.loveList.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {


            ImageView imgSticker;

            ViewHolder(View view) {
                super(view);
                this.imgSticker =  view.findViewById(R.id.imgSticker);
                view.setOnClickListener(view1 -> {
                    if (LoveFragment.this.listener != null) {
                        LoveFragment.this.listener.onOverplayClick(BitmapFactory.decodeResource(LoveFragment.this.getResources(), LoveAdapter.this.loveList[ViewHolder.this.getLayoutPosition()]));
                    }
                    LoveFragment.this.dismiss();
                });
            }
        }
    }
}

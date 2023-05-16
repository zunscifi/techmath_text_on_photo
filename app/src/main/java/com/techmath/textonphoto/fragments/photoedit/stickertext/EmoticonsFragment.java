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

public class EmoticonsFragment extends BottomSheetDialogFragment {

    public OverplayListener listener;
    private final BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        public void onSlide(@NonNull View view, float f) {
        }

        public void onStateChanged(@NonNull View view, int i) {
            if (i == 5) {
                EmoticonsFragment.this.dismiss();
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
        View inflate = View.inflate(getContext(), R.layout.fragment_bottom_sticker_emoji_dialog, null);
        dialog.setContentView(inflate);
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) ((View) inflate.getParent()).getLayoutParams()).getBehavior();
        if ((behavior instanceof BottomSheetBehavior)) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(this.mBottomSheetBehaviorCallback);
        }
        ((View) inflate.getParent()).setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), 17170445));
        RecyclerView recyclerView = inflate.findViewById(R.id.rvEmoji);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(new EmoticonsAdapter());
    }

    public class EmoticonsAdapter extends RecyclerView.Adapter<EmoticonsAdapter.ViewHolder> {


        int[] emoList = {R.drawable.emoticon, R.drawable.emoticon1, R.drawable.emoticon2, R.drawable.emoticon3, R.drawable.emoticon4, R.drawable.emoticon5, R.drawable.emoticon6, R.drawable.emoticon7, R.drawable.emoticon8, R.drawable.emoticon9, R.drawable.emoticon10, R.drawable.emoticon11, R.drawable.emoticon12, R.drawable.emoticon13, R.drawable.emoticon14, R.drawable.emoticon15, R.drawable.emoticon16, R.drawable.emoticon17, R.drawable.emoticon18, R.drawable.emoticon19, R.drawable.emoticon20, R.drawable.emoticon21, R.drawable.emoticon22, R.drawable.emoticon23};

        public EmoticonsAdapter() {
        }

        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sticker, viewGroup, false));
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Glide.with(Objects.requireNonNull(EmoticonsFragment.this.getActivity())).load(this.emoList[i]).thumbnail(0.1f).into(viewHolder.imgSticker);
        }

        public int getItemCount() {
            return this.emoList.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {


            ImageView imgSticker;

            ViewHolder(View view) {
                super(view);
                this.imgSticker = view.findViewById(R.id.imgSticker);
                view.setOnClickListener(view1 -> {
                    if (EmoticonsFragment.this.listener != null) {
                        EmoticonsFragment.this.listener.onOverplayClick(BitmapFactory.decodeResource(EmoticonsFragment.this.getResources(), EmoticonsAdapter.this.emoList[ViewHolder.this.getLayoutPosition()]));
                    }
                    EmoticonsFragment.this.dismiss();
                });
            }
        }
    }
}

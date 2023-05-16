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

public class NativeFragment extends BottomSheetDialogFragment {

    public OverplayListener listener;
    private final BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        public void onSlide(@NonNull View view, float f) {
        }

        public void onStateChanged(@NonNull View view, int i) {
            if (i == 5) {
                NativeFragment.this.dismiss();
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
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) ((View) inflate.getParent()).getLayoutParams()).getBehavior();
        if ((behavior instanceof BottomSheetBehavior)) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(this.mBottomSheetBehaviorCallback);
        }
        ( (View)inflate.getParent()).setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()),17170445));
        RecyclerView recyclerView = inflate.findViewById(R.id.rvEmoji);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(new NativaAdapter());
    }

    public class NativaAdapter extends RecyclerView.Adapter<NativaAdapter.ViewHolder> {


        int[] imgNatives = {R.drawable.native0, R.drawable.native1, R.drawable.native2, R.drawable.native3, R.drawable.native4, R.drawable.native5, R.drawable.native6, R.drawable.native7, R.drawable.native8, R.drawable.native9, R.drawable.native10, R.drawable.native11, R.drawable.native12, R.drawable.native13, R.drawable.native14, R.drawable.native111};

        public NativaAdapter() {
        }

        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sticker, viewGroup, false));
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Glide.with(Objects.requireNonNull(NativeFragment.this.getActivity())).load(this.imgNatives[i]).thumbnail(0.1f).into(viewHolder.imageView);
        }

        public int getItemCount() {
            return this.imgNatives.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {


            ImageView imageView;

            ViewHolder(View view) {
                super(view);
                this.imageView =  view.findViewById(R.id.imgSticker);
                view.setOnClickListener(view1 -> {
                    if (NativeFragment.this.listener != null) {
                        NativeFragment.this.listener.onOverplayClick(BitmapFactory.decodeResource(NativeFragment.this.getResources(), NativaAdapter.this.imgNatives[ViewHolder.this.getLayoutPosition()]));
                    }
                    NativeFragment.this.dismiss();
                });
            }
        }
    }
}

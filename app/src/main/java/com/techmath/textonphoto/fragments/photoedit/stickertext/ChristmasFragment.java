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

public class ChristmasFragment extends BottomSheetDialogFragment {

    public OverplayListener listener;
    private final BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        public void onSlide(@NonNull View view, float f) {
        }

        public void onStateChanged(@NonNull View view, int i) {
            if (i == 5) {
                ChristmasFragment.this.dismiss();
            }
        }
    };

    public void setOverplayListener(OverplayListener overplayListener) {
        this.listener = overplayListener;
    }

    @SuppressLint({"RestrictedApi", "ResourceType", "UseRequireInsteadOfGet"})
    @Override
    public void setupDialog(@NonNull Dialog dialog, int i) {
        super.setupDialog(dialog, i);
        View inflate = View.inflate(getContext(), R.layout.fragment_bottom_sticker_emoji_dialog, null);
        dialog.setContentView(inflate);
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) ((View) inflate.getParent()).getLayoutParams()).getBehavior();
        if ((behavior instanceof BottomSheetBehavior)) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(this.mBottomSheetBehaviorCallback);
        }
        ((View) inflate.getParent()).setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.main_bg));
        RecyclerView recyclerView = inflate.findViewById(R.id.rvEmoji);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(new ChristmasAdapter());
    }

    public class ChristmasAdapter extends RecyclerView.Adapter<ChristmasAdapter.ViewHolder> {


        int[] chrisList = {R.drawable.christmas0, R.drawable.christmas1, R.drawable.christmas2, R.drawable.christmas3, R.drawable.christmas4, R.drawable.christmas5, R.drawable.christmas6, R.drawable.christmas7, R.drawable.christmas8, R.drawable.christmas9, R.drawable.christmas10, R.drawable.christmas11, R.drawable.christmas12, R.drawable.christmas13, R.drawable.christmas14, R.drawable.christmas15, R.drawable.christmas16, R.drawable.christmas17, R.drawable.christmas18, R.drawable.christmas19, R.drawable.christmas20, R.drawable.christmas21, R.drawable.christmas22, R.drawable.christmas23, R.drawable.christmas24, R.drawable.christmas25, R.drawable.christmas26, R.drawable.christmas27, R.drawable.christmas28, R.drawable.christmas29, R.drawable.christmas30};

        public ChristmasAdapter() {
        }

        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sticker, viewGroup, false));
        }

        @SuppressLint("UseRequireInsteadOfGet")
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Glide.with(Objects.requireNonNull(ChristmasFragment.this.getActivity())).load(this.chrisList[i]).thumbnail(0.1f).into(viewHolder.imgSticker);
        }

        public int getItemCount() {
            return this.chrisList.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {


            ImageView imgSticker;

            ViewHolder(View view) {
                super(view);
                this.imgSticker = view.findViewById(R.id.imgSticker);
                view.setOnClickListener(view1 -> {
                    if (ChristmasFragment.this.listener != null) {
                        ChristmasFragment.this.listener.onOverplayClick(BitmapFactory.decodeResource(ChristmasFragment.this.getResources(), ChristmasAdapter.this.chrisList[ViewHolder.this.getLayoutPosition()]));
                    }
                    ChristmasFragment.this.dismiss();
                });
            }
        }
    }
}

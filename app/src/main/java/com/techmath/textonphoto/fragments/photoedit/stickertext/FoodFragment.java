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

public class FoodFragment extends BottomSheetDialogFragment {

    public OverplayListener listener;
    private final BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        public void onSlide(@NonNull View view, float f) {
        }

        public void onStateChanged(@NonNull View view, int i) {
            if (i == 5) {
                FoodFragment.this.dismiss();
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
        recyclerView.setAdapter(new FoodAdapter());
    }

    public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {


        int[] foodList = {R.drawable.food, R.drawable.food0, R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4, R.drawable.food5, R.drawable.food6, R.drawable.food7, R.drawable.food8, R.drawable.food9, R.drawable.food10, R.drawable.food11, R.drawable.food12, R.drawable.food13, R.drawable.food14, R.drawable.food15};

        public FoodAdapter() {
        }

        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sticker, viewGroup, false));
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Glide.with(Objects.requireNonNull(FoodFragment.this.getActivity())).load(this.foodList[i]).thumbnail(0.1f).into(viewHolder.imgSticker);
        }

        public int getItemCount() {
            return this.foodList.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {


            ImageView imgSticker;

            ViewHolder(View view) {
                super(view);
                this.imgSticker = view.findViewById(R.id.imgSticker);
                view.setOnClickListener(view1 -> {
                    if (FoodFragment.this.listener != null) {
                        FoodFragment.this.listener.onOverplayClick(BitmapFactory.decodeResource(FoodFragment.this.getResources(), FoodAdapter.this.foodList[ViewHolder.this.getLayoutPosition()]));
                    }
                    FoodFragment.this.dismiss();
                });
            }
        }
    }
}

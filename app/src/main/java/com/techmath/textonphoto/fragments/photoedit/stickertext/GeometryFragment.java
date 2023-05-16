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

public class GeometryFragment extends BottomSheetDialogFragment {

    public OverplayListener listener;
    private final BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        public void onSlide(@NonNull View view, float f) {
        }

        public void onStateChanged(@NonNull View view, int i) {
            if (i == 5) {
                GeometryFragment.this.dismiss();
            }
        }
    };

    public void setOverplayListener(OverplayListener overplayListener) {
        this.listener = overplayListener;
    }

    @SuppressLint({"RestrictedApi", "ResourceType"})
    @Override
    public void setupDialog(Dialog dialog, int i) {
        super.setupDialog(dialog, i);
        View inflate = View.inflate(getContext(), R.layout.fragment_bottom_sticker_emoji_dialog,  null);
        dialog.setContentView(inflate);
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) ((View) inflate.getParent()).getLayoutParams()).getBehavior();
        if ((behavior instanceof BottomSheetBehavior)) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(this.mBottomSheetBehaviorCallback);
        }
        ((View) inflate.getParent()).setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), 17170445));
        RecyclerView recyclerView = inflate.findViewById(R.id.rvEmoji);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(new GeoAdapter());
    }

    public class GeoAdapter extends RecyclerView.Adapter<GeoAdapter.ViewHolder> {


        int[] layerList = {R.drawable.layer, R.drawable.layer0, R.drawable.layer1, R.drawable.layer2, R.drawable.layer3, R.drawable.layer4, R.drawable.layer5, R.drawable.layer6, R.drawable.layer7, R.drawable.layer8, R.drawable.layer9, R.drawable.layer10, R.drawable.layer11, R.drawable.layer12, R.drawable.layer13, R.drawable.layer14, R.drawable.layer15, R.drawable.layer16, R.drawable.layer17, R.drawable.shape0, R.drawable.shape1, R.drawable.shape2, R.drawable.shape3, R.drawable.shape4, R.drawable.shape5, R.drawable.shape6, R.drawable.shape7, R.drawable.shape8, R.drawable.shape9, R.drawable.shape10, R.drawable.shape11, R.drawable.shape12, R.drawable.shape13, R.drawable.shape14};

        public GeoAdapter() {
        }

        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sticker, viewGroup, false));
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Glide.with(Objects.requireNonNull(GeometryFragment.this.getActivity())).load(this.layerList[i]).thumbnail(0.1f).into(viewHolder.imgSticker);
        }

        public int getItemCount() {
            return this.layerList.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {


            ImageView imgSticker;

            ViewHolder(View view) {
                super(view);
                this.imgSticker = view.findViewById(R.id.imgSticker);
                view.setOnClickListener(view1 -> {
                    if (GeometryFragment.this.listener != null) {
                        GeometryFragment.this.listener.onOverplayClick(BitmapFactory.decodeResource(GeometryFragment.this.getResources(), GeoAdapter.this.layerList[ViewHolder.this.getLayoutPosition()]));
                    }
                    GeometryFragment.this.dismiss();
                });
            }
        }
    }
}

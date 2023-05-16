package com.techmath.textonphoto.fragments.photoedit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.techmath.textonphoto.R;

import com.techmath.textonphoto.views.SquareImageView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhotoFragment extends Fragment {


    RecyclerView recyclerView;

    public PhotoListener mPhotoListener;

    public interface PhotoListener {
        void onPhotoClick(String str);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_image_photo, viewGroup, false);
        this.recyclerView = inflate.findViewById(R.id.recycler_photo);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        this.recyclerView.setAdapter(new PhotoAdapter());
        return inflate;
    }

    public void setPhotoListener(PhotoListener photoListener) {
        this.mPhotoListener = photoListener;
    }

    public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {


        List<String> stringArrayList;

        public PhotoAdapter() {
            PhotoFragment photoFragment = PhotoFragment.this;
            this.stringArrayList = photoFragment.getAllShownImagesPath(photoFragment.getActivity());
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_photo_small, viewGroup, false));
        }

        @SuppressLint("UseRequireInsteadOfGet")
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Glide.with(Objects.requireNonNull(PhotoFragment.this.getActivity())).load(this.stringArrayList.get(i)).thumbnail(0.001f).into( viewHolder.squareImageView);
        }

        public int getItemCount() {
            return this.stringArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {


            SquareImageView squareImageView;

            public ViewHolder(View view) {
                super(view);
                this.squareImageView = view.findViewById(R.id.imgPhoto);
                view.setOnClickListener(view1 -> {
                    if (PhotoFragment.this.mPhotoListener != null) {
                        PhotoFragment.this.mPhotoListener.onPhotoClick(PhotoAdapter.this.stringArrayList.get(ViewHolder.this.getLayoutPosition()));
                    }
                });
            }
        }
    }


    public List<String> getAllShownImagesPath(Activity activity) {
        List<String> arrayList = new ArrayList<>();
        if (activity == null) {
            return arrayList;
        }

        Cursor query = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "bucket_display_name"}, null, null,  null);
        int columnIndexOrThrow = Objects.requireNonNull(query).getColumnIndexOrThrow("_data");
        query.getColumnIndexOrThrow("bucket_display_name");
        while (query.moveToNext()) {
            arrayList.add(query.getString(columnIndexOrThrow));
        }
        return arrayList;
    }
}

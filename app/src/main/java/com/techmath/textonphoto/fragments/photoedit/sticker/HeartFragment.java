package com.techmath.textonphoto.fragments.photoedit.sticker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techmath.textonphoto.R;
import com.techmath.textonphoto.interfaces.StickerListener;
import com.techmath.textonphoto.model.ImgModel;

import java.util.ArrayList;

public class HeartFragment extends Fragment implements StickerAdapter.StickerAdaperListener {
    RecyclerView recyclerView;
    ArrayList<ImgModel> sampleArrayList;
    StickerListener stickerListener;

    public void setStickerListener(StickerListener stickerListener) {
        this.stickerListener = stickerListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_sticker_recyclerview, viewGroup, false);
        this.recyclerView =  inflate.findViewById(R.id.recyclerSticker);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        this.sampleArrayList = heartList();
        this.recyclerView.setAdapter(new StickerAdapter(this.sampleArrayList, getActivity(), this));
        return inflate;
    }

    private ArrayList<ImgModel> heartList() {
        ArrayList<ImgModel> arrayList = new ArrayList<>();
        arrayList.add(new ImgModel(R.drawable.heart01));
        arrayList.add(new ImgModel(R.drawable.heart02));
        arrayList.add(new ImgModel(R.drawable.heart03));
        arrayList.add(new ImgModel(R.drawable.heart04));
        arrayList.add(new ImgModel(R.drawable.heart05));
        arrayList.add(new ImgModel(R.drawable.heart06));
        arrayList.add(new ImgModel(R.drawable.heart07));
        arrayList.add(new ImgModel(R.drawable.heart08));
        arrayList.add(new ImgModel(R.drawable.heart09));
        arrayList.add(new ImgModel(R.drawable.heart10));
        arrayList.add(new ImgModel(R.drawable.heart11));
        arrayList.add(new ImgModel(R.drawable.heart12));
        arrayList.add(new ImgModel(R.drawable.heart13));
        arrayList.add(new ImgModel(R.drawable.heart14));
        arrayList.add(new ImgModel(R.drawable.heart15));
        arrayList.add(new ImgModel(R.drawable.heart16));
        arrayList.add(new ImgModel(R.drawable.heart18));
        arrayList.add(new ImgModel(R.drawable.heart19));
        arrayList.add(new ImgModel(R.drawable.heart20));
        return arrayList;
    }

    public void onStickerSelected(int i) {

        if (stickerListener != null) {
            stickerListener.onStickerClick(i);
        }
    }
}

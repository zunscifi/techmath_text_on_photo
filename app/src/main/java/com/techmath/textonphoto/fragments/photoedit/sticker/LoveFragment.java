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

public class LoveFragment extends Fragment implements StickerAdapter.StickerAdaperListener {
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
        arrayList.add(new ImgModel(R.drawable.slove1));
        arrayList.add(new ImgModel(R.drawable.slove2));
        arrayList.add(new ImgModel(R.drawable.slove3));
        arrayList.add(new ImgModel(R.drawable.slove4));
        arrayList.add(new ImgModel(R.drawable.slove5));
        arrayList.add(new ImgModel(R.drawable.slove6));
        arrayList.add(new ImgModel(R.drawable.slove7));
        arrayList.add(new ImgModel(R.drawable.slove8));
        arrayList.add(new ImgModel(R.drawable.slove9));
        arrayList.add(new ImgModel(R.drawable.slove10));
        arrayList.add(new ImgModel(R.drawable.slove11));
        arrayList.add(new ImgModel(R.drawable.slove12));
        arrayList.add(new ImgModel(R.drawable.slove13));
        arrayList.add(new ImgModel(R.drawable.slove14));
        arrayList.add(new ImgModel(R.drawable.slove15));
        arrayList.add(new ImgModel(R.drawable.slove16));
        arrayList.add(new ImgModel(R.drawable.slove17));
        return arrayList;
    }

    public void onStickerSelected(int i) {

        if (stickerListener != null) {
            stickerListener.onStickerClick(i);
        }
    }
}

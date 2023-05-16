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
import java.util.List;

public class RomanticFragment extends Fragment implements StickerAdapter.StickerAdaperListener {


    RecyclerView recyclerView;


    List<ImgModel> sampleArrayList;


    StickerListener stickerListener;

    public void setStickerListener(StickerListener stickerListener) {
        this.stickerListener = stickerListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_sticker_recyclerview, viewGroup, false);
        this.recyclerView = inflate.findViewById(R.id.recyclerSticker);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        this.sampleArrayList = light();
        this.recyclerView.setAdapter(new StickerAdapter(this.sampleArrayList, getActivity(), this));
        return inflate;
    }

    public List<ImgModel> light() {
        ArrayList<ImgModel> arrayList = new ArrayList<>();
        arrayList.add(new ImgModel(R.drawable.roman01));
        arrayList.add(new ImgModel(R.drawable.roman02));
        arrayList.add(new ImgModel(R.drawable.roman03));
        arrayList.add(new ImgModel(R.drawable.roman04));
        arrayList.add(new ImgModel(R.drawable.roman05));
        arrayList.add(new ImgModel(R.drawable.roman06));
        arrayList.add(new ImgModel(R.drawable.roman07));
        arrayList.add(new ImgModel(R.drawable.roman08));
        arrayList.add(new ImgModel(R.drawable.roman09));
        arrayList.add(new ImgModel(R.drawable.roman10));
        arrayList.add(new ImgModel(R.drawable.roman11));
        arrayList.add(new ImgModel(R.drawable.roman12));
        arrayList.add(new ImgModel(R.drawable.roman13));
        arrayList.add(new ImgModel(R.drawable.roman14));
        arrayList.add(new ImgModel(R.drawable.roman15));
        arrayList.add(new ImgModel(R.drawable.roman16));
        arrayList.add(new ImgModel(R.drawable.roman17));
        arrayList.add(new ImgModel(R.drawable.roman18));
        arrayList.add(new ImgModel(R.drawable.roman19));
        arrayList.add(new ImgModel(R.drawable.roman20));

        return arrayList;
    }

    public void onStickerSelected(int i) {

        if (stickerListener != null) {
            stickerListener.onStickerClick(i);
        }
    }
}

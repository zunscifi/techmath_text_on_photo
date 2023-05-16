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

public class ChristFragment extends Fragment implements StickerAdapter.StickerAdaperListener {


    RecyclerView recyclerView;

    List<ImgModel> chistmasList;


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
        this.chistmasList = chistmasList();
        this.recyclerView.setAdapter(new StickerAdapter(this.chistmasList, getActivity(), this));
        return inflate;
    }

    public List<ImgModel> chistmasList() {
        List<ImgModel> arrayList = new ArrayList<>();
        arrayList.add(new ImgModel(R.drawable.chris0));
        arrayList.add(new ImgModel(R.drawable.chris1));
        arrayList.add(new ImgModel(R.drawable.chris2));
        arrayList.add(new ImgModel(R.drawable.chris3));
        arrayList.add(new ImgModel(R.drawable.chris4));
        arrayList.add(new ImgModel(R.drawable.chris5));
        arrayList.add(new ImgModel(R.drawable.chris6));
        arrayList.add(new ImgModel(R.drawable.chris7));
        arrayList.add(new ImgModel(R.drawable.chris8));
        arrayList.add(new ImgModel(R.drawable.chris9));
        arrayList.add(new ImgModel(R.drawable.chris11));
        arrayList.add(new ImgModel(R.drawable.chris12));
        arrayList.add(new ImgModel(R.drawable.chris13));
        arrayList.add(new ImgModel(R.drawable.chris14));
        arrayList.add(new ImgModel(R.drawable.chris15));
        arrayList.add(new ImgModel(R.drawable.chris16));
        arrayList.add(new ImgModel(R.drawable.chris17));
        arrayList.add(new ImgModel(R.drawable.chris18));
        arrayList.add(new ImgModel(R.drawable.chris19));
        arrayList.add(new ImgModel(R.drawable.chris20));
        arrayList.add(new ImgModel(R.drawable.chris6));
        return arrayList;
    }

    public void onStickerSelected(int i) {

        if (stickerListener != null) {
            stickerListener.onStickerClick(i);
        }
    }
}

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

public class FlowersFragment extends Fragment implements StickerAdapter.StickerAdaperListener {


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
        this.sampleArrayList = flowerList();
        this.recyclerView.setAdapter(new StickerAdapter(this.sampleArrayList, getActivity(), this));
        return inflate;
    }

    private ArrayList<ImgModel> flowerList() {
        ArrayList<ImgModel> arrayList = new ArrayList<>();
        arrayList.add(new ImgModel(R.drawable.sflower1));
        arrayList.add(new ImgModel(R.drawable.sflower2));
        arrayList.add(new ImgModel(R.drawable.sflower3));
        arrayList.add(new ImgModel(R.drawable.sflower4));
        arrayList.add(new ImgModel(R.drawable.sflower5));
        arrayList.add(new ImgModel(R.drawable.sflower6));
        arrayList.add(new ImgModel(R.drawable.sflower7));
        arrayList.add(new ImgModel(R.drawable.sflower8));
        arrayList.add(new ImgModel(R.drawable.sflower9));
        arrayList.add(new ImgModel(R.drawable.sflower10));
        arrayList.add(new ImgModel(R.drawable.sflower11));
        arrayList.add(new ImgModel(R.drawable.sflower12));
        arrayList.add(new ImgModel(R.drawable.sflower13));
        arrayList.add(new ImgModel(R.drawable.sflower14));
        arrayList.add(new ImgModel(R.drawable.sflower15));
        arrayList.add(new ImgModel(R.drawable.sflower16));
        arrayList.add(new ImgModel(R.drawable.sflower17));
        arrayList.add(new ImgModel(R.drawable.sflower18));
        arrayList.add(new ImgModel(R.drawable.sflower19));
        arrayList.add(new ImgModel(R.drawable.sflower20));
        arrayList.add(new ImgModel(R.drawable.sflower21));
        arrayList.add(new ImgModel(R.drawable.sflower22));
        arrayList.add(new ImgModel(R.drawable.sflower23));
        arrayList.add(new ImgModel(R.drawable.sflower24));
        return arrayList;
    }

    public void onStickerSelected(int i) {

        if (stickerListener != null) {
            stickerListener.onStickerClick(i);
        }
    }
}

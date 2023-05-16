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

public class BirthdayFragment extends Fragment implements StickerAdapter.StickerAdaperListener {
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
        this.sampleArrayList = fireWorks();
        this.recyclerView.setAdapter(new StickerAdapter(this.sampleArrayList, getActivity(), this));
        return inflate;
    }

    public List<ImgModel> fireWorks() {
        List<ImgModel> arrayList = new ArrayList<>();
        arrayList.add(new ImgModel(R.drawable.bday01));
        arrayList.add(new ImgModel(R.drawable.bday02));
        arrayList.add(new ImgModel(R.drawable.bday03));
        arrayList.add(new ImgModel(R.drawable.bday04));
        arrayList.add(new ImgModel(R.drawable.bday05));
        arrayList.add(new ImgModel(R.drawable.bday06));
        arrayList.add(new ImgModel(R.drawable.bday07));
        arrayList.add(new ImgModel(R.drawable.bday08));
        arrayList.add(new ImgModel(R.drawable.bday09));
        arrayList.add(new ImgModel(R.drawable.bday10));
        arrayList.add(new ImgModel(R.drawable.bday11));
        arrayList.add(new ImgModel(R.drawable.bday12));
        arrayList.add(new ImgModel(R.drawable.bday13));
        arrayList.add(new ImgModel(R.drawable.bday14));
        arrayList.add(new ImgModel(R.drawable.bday15));
        arrayList.add(new ImgModel(R.drawable.bday16));
        arrayList.add(new ImgModel(R.drawable.bday17));
        arrayList.add(new ImgModel(R.drawable.bday18));
        arrayList.add(new ImgModel(R.drawable.bday19));
        arrayList.add(new ImgModel(R.drawable.bday20));
        return arrayList;
    }

    public void onStickerSelected(int i) {

        if (stickerListener != null) {
            stickerListener.onStickerClick(i);
        }
    }
}

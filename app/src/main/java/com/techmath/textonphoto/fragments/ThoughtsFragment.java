package com.techmath.textonphoto.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techmath.textonphoto.R;
import com.techmath.textonphoto.adapter.ThoughtsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ThoughtsFragment extends Fragment {
    ThoughtsFragmentListener thoughtsFragmentListener;
    ImageView btnBackThoughts;
    RecyclerView recyclerThoughts;
    ThoughtsAdapter thoughtsAdapter;
    List<Integer> listThought;

    public interface ThoughtsFragmentListener {
        void onThought(int i);
    }

    public void setThoughtListener(ThoughtsFragmentListener thoughtFragmentListener) {
        this.thoughtsFragmentListener = thoughtFragmentListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_more_thoughts, viewGroup, false);
        this.btnBackThoughts = inflate.findViewById(R.id.btnBackThoughts);
        this.recyclerThoughts = inflate.findViewById(R.id.recyclerThoughts);
        this.recyclerThoughts.setHasFixedSize(true);
        this.recyclerThoughts.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        this.listThought = genThoughts();
        this.thoughtsAdapter = new ThoughtsAdapter(this.listThought, getActivity(), (view, i) -> {
            int intValue = ThoughtsFragment.this.listThought.get(i);
            if (ThoughtsFragment.this.thoughtsFragmentListener != null) {
                ThoughtsFragment.this.thoughtsFragmentListener.onThought(intValue);
                ThoughtsFragment.this.requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
        this.recyclerThoughts.setAdapter(this.thoughtsAdapter);
        this.btnBackThoughts.setOnClickListener(view -> ThoughtsFragment.this.requireActivity().getSupportFragmentManager().popBackStack());
        return inflate;
    }

    private List<Integer> genThoughts() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.string.thought1);
        arrayList.add((R.string.thought2));
        arrayList.add((R.string.thought3));
        arrayList.add((R.string.thought4));
        arrayList.add((R.string.thought5));
        arrayList.add((R.string.thought6));
        arrayList.add((R.string.thought7));
        arrayList.add((R.string.thought8));
        arrayList.add((R.string.thought9));
        arrayList.add((R.string.thought10));
        arrayList.add((R.string.thought11));
        arrayList.add((R.string.thought12));
        arrayList.add((R.string.thought13));
        arrayList.add((R.string.thought14));
        arrayList.add((R.string.thought15));
        arrayList.add((R.string.thought16));
        arrayList.add((R.string.thought17));
        arrayList.add((R.string.thought18));
        arrayList.add((R.string.thought19));
        arrayList.add((R.string.thought20));
        arrayList.add((R.string.thought21));
        arrayList.add((R.string.thought22));
        arrayList.add((R.string.thought23));
        arrayList.add((R.string.thought24));
        arrayList.add((R.string.thought25));
        arrayList.add((R.string.thought26));
        arrayList.add((R.string.thought27));
        arrayList.add((R.string.thought28));
        arrayList.add((R.string.thought29));
        arrayList.add((R.string.thought30));
        arrayList.add((R.string.thought31));
        arrayList.add((R.string.thought32));
        arrayList.add((R.string.thought33));
        arrayList.add((R.string.thought34));
        arrayList.add((R.string.thought35));
        arrayList.add((R.string.thought36));
        arrayList.add((R.string.thought37));
        arrayList.add((R.string.thought38));
        arrayList.add((R.string.thought39));
        arrayList.add((R.string.thought40));
        arrayList.add((R.string.thought41));
        arrayList.add((R.string.thought42));
        arrayList.add((R.string.thought43));
        arrayList.add((R.string.thought44));
        arrayList.add((R.string.thought45));
        arrayList.add((R.string.thought46));
        arrayList.add((R.string.thought47));
        arrayList.add((R.string.thought48));
        arrayList.add((R.string.thought49));
        arrayList.add((R.string.thought50));
        arrayList.add((R.string.thought51));
        arrayList.add((R.string.thought52));
        arrayList.add((R.string.thought53));
        arrayList.add((R.string.thought54));
        arrayList.add((R.string.thought55));
        arrayList.add((R.string.thought56));
        arrayList.add((R.string.thought57));
        arrayList.add((R.string.thought58));
        arrayList.add((R.string.thought59));
        arrayList.add((R.string.thought60));
        arrayList.add((R.string.thought61));
        arrayList.add((R.string.thought62));
        arrayList.add((R.string.thought63));
        arrayList.add((R.string.thought64));
        arrayList.add((R.string.thought65));
        arrayList.add((R.string.thought66));
        arrayList.add((R.string.thought67));
        arrayList.add((R.string.thought68));
        arrayList.add((R.string.thought69));
        arrayList.add((R.string.thought70));
        arrayList.add((R.string.thought71));
        arrayList.add((R.string.thought72));
        arrayList.add((R.string.thought73));
        arrayList.add((R.string.thought74));
        arrayList.add((R.string.thought75));
        arrayList.add((R.string.thought76));
        arrayList.add((R.string.thought77));
        arrayList.add((R.string.thought78));
        arrayList.add((R.string.thought79));
        arrayList.add((R.string.thought80));
        arrayList.add((R.string.thought81));
        arrayList.add((R.string.thought82));
        arrayList.add((R.string.thought83));
        arrayList.add((R.string.thought84));
        arrayList.add((R.string.thought85));
        arrayList.add((R.string.thought86));
        arrayList.add((R.string.thought87));
        arrayList.add((R.string.thought88));
        arrayList.add((R.string.thought89));
        arrayList.add((R.string.thought90));
        return arrayList;
    }
}

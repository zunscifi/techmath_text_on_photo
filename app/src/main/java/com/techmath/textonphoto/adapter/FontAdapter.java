package com.techmath.textonphoto.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.techmath.textonphoto.R;

import java.util.List;

public class FontAdapter extends RecyclerView.Adapter<FontAdapter.FontViewHolder> {


    Context context;


    FontAdapterClickListener fontAdapterClickListener;

    List<String> list;

    public int currentPos = 1;
    public interface FontAdapterClickListener {
        void onFontItemSelected(String str);
    }

    public FontAdapter(Context context, FontAdapterClickListener fontAdapterClickListener, List<String> list) {
        this.context = context;
        this.fontAdapterClickListener = fontAdapterClickListener;
        this.list = list;
    }

    @NonNull
    public FontViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FontViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_font, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull FontViewHolder fontViewHolder, int i) {
        AssetManager assets = this.context.getAssets();
        fontViewHolder.txtFontDemo.setTypeface(Typeface.createFromAsset(assets, "font/" + this.list.get(i)));

        if (i == currentPos){
            fontViewHolder.txtFontDemo.setBackgroundResource(R.drawable.font_bg_select);
        }else {
            fontViewHolder.txtFontDemo.setBackgroundResource(R.color.main_bg);
        }
    }

    public int getItemCount() {
        return this.list.size();
    }

    public static class FontViewHolder extends RecyclerView.ViewHolder {


        TextView txtFontDemo;


        FrameLayout fontSection;

        public FontViewHolder(View view) {
            super(view);
            this.txtFontDemo = view.findViewById(R.id.txt_font_demo);
            this.fontSection = view.findViewById(R.id.font_section);
        }
    }
}

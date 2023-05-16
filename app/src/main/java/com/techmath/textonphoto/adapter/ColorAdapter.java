package com.techmath.textonphoto.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.techmath.textonphoto.R;
import com.techmath.textonphoto.photoeditor.RoundFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {


    Context context;

    List<Integer> colorList;

    ColorAdapterListener colorAdapterListener;

    int index = -1;

    public interface ColorAdapterListener {
        void onColorItemSelected(int i);
    }

    public ColorAdapter(Context context, ColorAdapterListener colorAdapterListener) {
        this.context = context;
        this.colorList = genColorList();
        this.colorAdapterListener = colorAdapterListener;
    }

    @NonNull
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ColorViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_color, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ColorViewHolder colorViewHolder, int i) {
        if (this.index == i) {
            colorViewHolder.colorSection.getDelegate().setStrokeColor(ContextCompat.getColor(context, R.color.icChecked));
        } else {
            colorViewHolder.colorSection.getDelegate().setStrokeColor(17170445);
        }
        colorViewHolder.colorSection.getDelegate().setBackgroundColor(this.colorList.get(i));
    }

    public int getItemCount() {
        return this.colorList.size();
    }

    public class ColorViewHolder extends RecyclerView.ViewHolder {


        RoundFrameLayout colorSection;

        public ColorViewHolder(View view) {
            super(view);
            this.colorSection = view.findViewById(R.id.color_section);
            view.setOnClickListener(view1 -> {
                if (ColorViewHolder.this.getAdapterPosition() < ColorAdapter.this.colorList.size()) {
                    ColorAdapter.this.colorAdapterListener.onColorItemSelected(ColorAdapter.this.colorList.get(ColorViewHolder.this.getAdapterPosition()));
                    ColorAdapter.this.index = ColorViewHolder.this.getAdapterPosition();
                    ColorAdapter.this.notifyDataSetChanged();
                }
            });
        }
    }

    private List<Integer> genColorList() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Color.parseColor("#FFFFFF"));
        arrayList.add((Color.parseColor("#000000")));
        arrayList.add((Color.parseColor("#0098F1")));
        arrayList.add((Color.parseColor("#4CC259")));
        arrayList.add((Color.parseColor("#FFC859")));
        arrayList.add(Color.parseColor("#FF8523"));
        arrayList.add((Color.parseColor("#FF3A4A")));
        arrayList.add((Color.parseColor("#E90060")));
        arrayList.add((Color.parseColor("#B300B6")));
        arrayList.add((Color.parseColor("#FF0000")));
        arrayList.add((Color.parseColor("#FF7E88")));
        arrayList.add((Color.parseColor("#FFD0D1")));
        arrayList.add((Color.parseColor("#FFDAB2")));
        arrayList.add((Color.parseColor("#FFC07E")));
        arrayList.add((Color.parseColor("#E18B42")));
        arrayList.add((Color.parseColor("#a36138")));
        arrayList.add((Color.parseColor("#4A2829")));
        arrayList.add((Color.parseColor("#004C30")));
        arrayList.add((Color.parseColor("#2C2C2C")));
        arrayList.add((Color.parseColor("#393939")));
        arrayList.add((Color.parseColor("#555555")));
        arrayList.add((Color.parseColor("#727272")));
        arrayList.add((Color.parseColor("#989898")));
        arrayList.add((Color.parseColor("#B1B1B1")));
        arrayList.add((Color.parseColor("#C7C7C7")));
        arrayList.add((Color.parseColor("#DBDBDB")));
        arrayList.add((Color.parseColor("#F0F0F0")));
        return arrayList;
    }
}

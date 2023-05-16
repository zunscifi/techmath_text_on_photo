package com.techmath.textonphoto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.techmath.textonphoto.R;
import com.techmath.textonphoto.interfaces.ItemClickListener;

import java.util.List;

public class ThoughtsAdapter extends RecyclerView.Adapter<ThoughtsAdapter.QuotesViewHolder> {


    List<Integer> list;

    Context context;

    ItemClickListener itemClickListener;

    public ThoughtsAdapter(List<Integer> list, Context context, ItemClickListener itemClickListener) {
        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    public QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_quotes, viewGroup, false);
        final QuotesViewHolder quotesViewHolder = new QuotesViewHolder(inflate);
        inflate.setOnClickListener(view -> ThoughtsAdapter.this.itemClickListener.onItemClick(view, quotesViewHolder.getLayoutPosition()));
        return quotesViewHolder;
    }

    public void onBindViewHolder(@NonNull QuotesViewHolder quotesViewHolder, int i) {
        quotesViewHolder.tvQuote.setText(this.list.get(i));
    }

    public int getItemCount() {
        return this.list.size();
    }

    public static class QuotesViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuote;

        public QuotesViewHolder(View view) {
            super(view);
            this.tvQuote = view.findViewById(R.id.tvQuote);
        }
    }
}

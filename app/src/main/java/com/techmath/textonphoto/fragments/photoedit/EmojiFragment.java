package com.techmath.textonphoto.fragments.photoedit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techmath.textonphoto.R;
import com.techmath.textonphoto.photoeditor.PhotoEditor;
import java.util.List;
import java.util.Objects;

public class EmojiFragment extends Fragment {


    RecyclerView recyclerView;

    public EmojiListener mEmojiListener;

    public interface EmojiListener {
        void onEmojiClick(String str);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_image_emoji, viewGroup, false);
        this.recyclerView = inflate.findViewById(R.id.recyclerEmoji);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        this.recyclerView.setAdapter(new EmojiAdapter());
        return inflate;
    }

    public void setEmojiListener(EmojiListener emojiListener) {
        this.mEmojiListener = emojiListener;
    }

    public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.ViewHolder> {


        @SuppressLint("UseRequireInsteadOfGet")
        List<String> emojis = PhotoEditor.getEmojis(Objects.requireNonNull(EmojiFragment.this.getActivity()));

        public EmojiAdapter() {
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_emoji, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.textView.setText(this.emojis.get(i));
        }

        public int getItemCount() {
            return this.emojis.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {


            TextView textView;

            public ViewHolder(View view) {
                super(view);
                this.textView = view.findViewById(R.id.txtEmoji);
                view.setOnClickListener(view1 -> {
                    if (EmojiFragment.this.mEmojiListener != null) {
                        EmojiFragment.this.mEmojiListener.onEmojiClick(EmojiAdapter.this.emojis.get(ViewHolder.this.getLayoutPosition()));
                    }
                });
            }
        }
    }
}

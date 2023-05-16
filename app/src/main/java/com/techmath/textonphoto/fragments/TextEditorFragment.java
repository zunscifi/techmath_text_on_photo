package com.techmath.textonphoto.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.techmath.textonphoto.R;

import java.util.Objects;
import java.util.Random;

public class TextEditorFragment extends Fragment implements View.OnClickListener, ThoughtsFragment.ThoughtsFragmentListener {


    EditText editText;


    TextFragmentListener textFragmentListener;


    int[] quotes = {R.string.thought1, R.string.thought2, R.string.thought3, R.string.thought4, R.string.thought5, R.string.thought6, R.string.thought7, R.string.thought8, R.string.thought9, R.string.thought10, R.string.thought11, R.string.thought12, R.string.thought13, R.string.thought14, R.string.thought15, R.string.thought16, R.string.thought17, R.string.thought18, R.string.thought19, R.string.thought20, R.string.thought21, R.string.thought22, R.string.thought23, R.string.thought24, R.string.thought25, R.string.thought26, R.string.thought27, R.string.thought28, R.string.thought29, R.string.thought30, R.string.thought31, R.string.thought32, R.string.thought33, R.string.thought34, R.string.thought35, R.string.thought36, R.string.thought37, R.string.thought38, R.string.thought39, R.string.thought40, R.string.thought41, R.string.thought42, R.string.thought43, R.string.thought44, R.string.thought45, R.string.thought46, R.string.thought47, R.string.thought48, R.string.thought49, R.string.thought50, R.string.thought51, R.string.thought52, R.string.thought53, R.string.thought54, R.string.thought55, R.string.thought56, R.string.thought57, R.string.thought58, R.string.thought59, R.string.thought60, R.string.thought61, R.string.thought62, R.string.thought63, R.string.thought64, R.string.thought65, R.string.thought66, R.string.thought67, R.string.thought68, R.string.thought69, R.string.thought70, R.string.thought71, R.string.thought72, R.string.thought73, R.string.thought74, R.string.thought75, R.string.thought76, R.string.thought77, R.string.thought78, R.string.thought79, R.string.thought80, R.string.thought81, R.string.thought82, R.string.thought83, R.string.thought84, R.string.thought85, R.string.thought86, R.string.thought87, R.string.thought88, R.string.thought89, R.string.thought90};

    @Override
    public void onThought(int i) {
        this.editText.setText(i);
    }

    public interface TextFragmentListener {
        void onText(String str);
    }

    public void setTextListener(TextFragmentListener textFragmentListener) {
        this.textFragmentListener = textFragmentListener;
    }

    @SuppressLint("UseRequireInsteadOfGet")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(32);
        View inflate = layoutInflater.inflate(R.layout.fragment_text_editor, viewGroup, false);
        inflate.findViewById(R.id.btnCancel).setOnClickListener(this);
        inflate.findViewById(R.id.btnDone).setOnClickListener(this);
        this.editText = inflate.findViewById(R.id.edtQuotes);
        inflate.findViewById(R.id.btnRandom).setOnClickListener(this);
        inflate.findViewById(R.id.btnMoreQuotes).setOnClickListener(this);
        if (getArguments() != null) {
            this.editText.setText(getArguments().getString("edittext"));
        }

        if (this.editText.requestFocus()) {
            new Handler().postDelayed(() -> ((InputMethodManager) TextEditorFragment.this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(TextEditorFragment.this.editText, 1), 200);
        }
        return inflate;
    }

    @SuppressLint("UseRequireInsteadOfGet")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                this.editText.setText("");
                return;
            case R.id.btnDone:
                String obj = this.editText.getText().toString();

                if (textFragmentListener != null) {
                    textFragmentListener.onText(obj);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack(null, 1);
                    View currentFocus = getActivity().getCurrentFocus();
                    if (currentFocus != null) {
                        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                    }
                }
                return;
            case R.id.btnMoreQuotes:
                ThoughtsFragment quotesFragment = new ThoughtsFragment();
                quotesFragment.setThoughtListener(this);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).add(R.id.frameLayoutEditMai, quotesFragment, "QUOTES").addToBackStack("QUOTES").commit();
                View currentFocus2 = getActivity().getCurrentFocus();
                if (currentFocus2 != null) {
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus2.getWindowToken(), 0);
                }
                return;
            case R.id.btnRandom:
                this.editText.setText(this.quotes[new Random().nextInt(this.quotes.length)]);
                return;
            default:
        }
    }

}

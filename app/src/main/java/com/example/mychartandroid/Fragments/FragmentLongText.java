package com.example.mychartandroid.Fragments;


import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.mychartandroid.Component.ComponentLongTextView;
import com.example.mychartandroid.Component.ComponentLongTextView_;
import com.example.mychartandroid.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_longtext)
public class FragmentLongText extends Fragment {

    @ViewById
    LinearLayout layoutContainer;

    @AfterViews
    protected void init() {
        // Criação programática do ComponentLongTextView
        //ComponentLongTextView longTextView = new ComponentLongTextView(getActivity());
        ComponentLongTextView longTextView = ComponentLongTextView_.build(getContext());

        // Adicionando o ComponentLongTextView ao layoutContainer da Fragment
        layoutContainer.addView(longTextView);

        String historia = "Em uma pacata vila nas colinas, um artesão criava violinos únicos, dizendo capturar a essência da natureza. Uma jovem, atraída por estas histórias, procurou um instrumento que expressasse sua alma. O artesão, inspirado, dedicou-se a criar um violino especial, que ao ser tocado pela primeira vez, encantou a todos com sua melodia emocionante. Este violino uniu a jovem e o artesão numa jornada de música e inspiração, tocando corações por onde passavam.";

        longTextView.setText(historia);

        // Ajustar LayoutParams conforme necessário
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        longTextView.setLayoutParams(layoutParams);
    }
}

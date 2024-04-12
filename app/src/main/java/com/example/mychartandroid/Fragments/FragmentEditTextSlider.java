package com.example.mychartandroid.Fragments;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.mychartandroid.Component.EditTextSlider;
import com.example.mychartandroid.Component.EditTextSliderComponent;

import com.example.mychartandroid.Component.EditTextSliderComponent_;
import com.example.mychartandroid.Component.EditTextSlider_;
import com.example.mychartandroid.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_editslider)
public class FragmentEditTextSlider extends Fragment {




    @ViewById
    LinearLayout fragmentContainer;

    @AfterViews
    void setupEditTextSlider() {
        EditTextSlider editTextSlider = EditTextSlider_.build(getContext(),null);
        editTextSlider.setPrefix("$");
        editTextSlider.setSuffix(" USD");
        editTextSlider.setDecimalPlaces(2);
        editTextSlider.setRange(20.0, 30.0, 0.01);
        fragmentContainer.addView(editTextSlider);
    }

}

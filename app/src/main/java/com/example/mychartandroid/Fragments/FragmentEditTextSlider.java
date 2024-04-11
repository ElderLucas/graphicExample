package com.example.mychartandroid.Fragments;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.mychartandroid.Component.EditTextSliderComponent;

import com.example.mychartandroid.Component.EditTextSliderComponent_;
import com.example.mychartandroid.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_editslider)
public class FragmentEditTextSlider extends Fragment {



    @ViewById
    LinearLayout fragmentContainer;

    @AfterViews
    protected void init() {
        // Aqui você pode instanciar e adicionar seu EditTextSliderComponent programaticamente
        EditTextSliderComponent customComponent = EditTextSliderComponent_.build(getContext(),null);

        // Configure seu componente conforme necessário
        customComponent.setPrefix("$");
        customComponent.setSuffix(" USD");
        customComponent.setDecimalPlaces(1);
        customComponent.setMinValue(0);
        customComponent.setMaxValue(100);
        //customComponent.updateView();

        // Adiciona o componente ao container da fragment
        fragmentContainer.addView(customComponent);

        configureComponent(customComponent);
    }


    private void configureComponent(EditTextSliderComponent component) {
        // Configura o componente aqui
        component.setPrefix("$");
        component.setSuffix(" USD");
        component.setDecimalPlaces(2);
        component.setMinValue(0);
        component.setMaxValue(100);
    }
}

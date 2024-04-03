package com.example.mychartandroid.Fragments;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mychartandroid.Component.PieChartComponent;
import com.example.mychartandroid.Component.PieChartComponent_;
import com.example.mychartandroid.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@EFragment(R.layout.fragment_mp_piechart)
public class PieChartFragment extends Fragment {

    @ViewById(R.id.containerLayout)
    ViewGroup containerLayout;

    @AfterViews
    void setupViews() {

        PieChartComponent pieChartComponent = PieChartComponent_.build(getContext());

        containerLayout.addView(pieChartComponent, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(40f, "Categoria 1"));
        entries.add(new PieEntry(60f, "Categoria 2"));

        List<Integer> colors = Arrays.asList(Color.BLUE, Color.RED);

        pieChartComponent.setData(entries, colors);
    }
}
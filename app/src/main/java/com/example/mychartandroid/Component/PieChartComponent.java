package com.example.mychartandroid.Component;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.mychartandroid.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EViewGroup(R.layout.pie_chart_component)
public class PieChartComponent extends LinearLayout {

    @ViewById
    PieChart pieChart;

    public PieChartComponent(Context context) {
        super(context);
    }

    @AfterViews
    void initView() {
        // Aqui, você pode fazer inicializações adicionais se necessário
    }

    public void setData(List<PieEntry> entries, List<Integer> colors) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.invalidate(); // Refresh o gráfico
    }
}


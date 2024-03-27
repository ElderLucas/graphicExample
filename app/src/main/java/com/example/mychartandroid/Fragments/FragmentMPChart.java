package com.example.mychartandroid.Fragments;

import android.graphics.Color;
import android.view.MotionEvent;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;

import com.example.mychartandroid.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EFragment(R.layout.fragment_mpchart)
public class FragmentMPChart extends Fragment {

    @ViewById
    CombinedChart barChart;

    @ViewById
    SeekBar zoomSeekBar, scrollSeekBar;

    private int totalBars = 60;

    @AfterViews
    void init() {


        // Supondo que totalBars é o número total de entradas de dados que você tem,
        // e que você deseja inicialmente mostrar 10 barras.
        int totalBars = 60; // Ou o número real de barras que você tem.
        int initialVisibleBars = 10;

        setupChart(); // Configura e preenche o gráfico com dados
        setupZoomSeekBar(totalBars); // Configura o SeekBar de zoom se necessário
        setupScrollSeekBar(totalBars, initialVisibleBars); // Configura o SeekBar de scroll

        barChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
                // Verifica se o zoom atual está tentando mostrar mais do que o número máximo permitido de barras
                if (barChart.getVisibleXRange() > totalBars) {
                    barChart.zoomToCenter(initialVisibleBars, 1f);
                }
            }

            // Implementação vazia para os outros métodos de OnChartGestureListener
            @Override public void onChartLongPressed(MotionEvent me) {}
            @Override public void onChartDoubleTapped(MotionEvent me) {}
            @Override public void onChartSingleTapped(MotionEvent me) {}
            @Override public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {}

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

                // Calcula a posição de scroll baseada na posição atual do gráfico
                float position = barChart.getLowestVisibleX();

                // O máximo de scroll possível corresponde ao número total de barras menos o número de barras visíveis
                float maxScroll = barChart.getXChartMax() - initialVisibleBars;

                // Normaliza a posição de scroll para o alcance do SeekBar
                if (maxScroll > 0) {
                    int seekBarPosition = (int) ((position / maxScroll) * scrollSeekBar.getMax());
                    scrollSeekBar.setProgress(seekBarPosition);
                }
            }

            @Override public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {}
            @Override public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {}
        });

    }

    void setupZoomSeekBar(int totalBars) {
        // Defina o máximo do SeekBar baseado no que você considera um zoom máximo útil
        zoomSeekBar.setMax(100); // Uma escala de 0 a 100 para o zoom

        zoomSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    float zoomLevel = 1 + (progress / 100f) * (totalBars / 3f - 1);
                    // Aplica o zoom baseado no progresso do slider
                    barChart.zoom(zoomLevel, 1f, barChart.getWidth() / 2f, barChart.getHeight() / 2f);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Implementação opcional
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Implementação opcional
            }
        });
    }



    void setupScrollSeekBar(int totalBars, int visibleBars) {
        scrollSeekBar.setMax(totalBars - visibleBars);
        scrollSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // Calcula o deslocamento necessário para garantir que a primeira barra não fique cortada
                    float xOffset = 0.5f; // Este valor deve corresponder ao offset usado na configuração do eixo X
                    barChart.moveViewToX(progress - xOffset);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Implementação opcional
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Implementação opcional
            }
        });
    }


    public void setupChart() {
        List<BarEntry> entriesBar1 = new ArrayList<>();
        List<BarEntry> entriesBar2 = new ArrayList<>();
        List<Entry> entriesLine = new ArrayList<>();

        // Simulando dados para 60 meses
        for (int i = 0; i < 60; i++) {
            float val1 = (float) (Math.random() * 500);
            float val2 = (float) (Math.random() * 500);
            float lineVal = (float) (Math.random() * 500);

            entriesBar1.add(new BarEntry(i, val1));
            entriesBar2.add(new BarEntry(i, val2)); // Usando o mesmo valor de X para sobrepor
            entriesLine.add(new Entry(i, lineVal));
        }

        BarDataSet setBar1 = new BarDataSet(entriesBar1, "Grupo 1");
        setBar1.setColor(Color.BLUE);

        BarDataSet setBar2 = new BarDataSet(entriesBar2, "Grupo 2");
        setBar2.setColor(Color.GREEN);

        LineDataSet setLine = new LineDataSet(entriesLine, "Linha");
        setLine.setColor(Color.RED);
        setLine.setLineWidth(2.5f);
        setLine.setCircleRadius(5f);
        setLine.setCircleHoleRadius(2.5f);
        setLine.setFillColor(Color.YELLOW);
        setLine.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        setLine.setDrawValues(false);

        BarData barData = new BarData(setBar1, setBar2);
        barData.setBarWidth(0.5f); // Ajuste conforme necessário para sobreposição

        CombinedData data = new CombinedData();
        data.setData(barData);
        data.setData(new LineData(setLine));

        barChart.setData(data);

        // Configuração do Eixo X
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false); // Remove as linhas de grade do eixo X
        xAxis.setDrawAxisLine(false); // Remove a linha do eixo X

        // Adicionando um pequeno offset ao mínimo e máximo do eixo X para garantir que a primeira e última barras sejam totalmente visíveis
        float xOffset = 0.5f;
        xAxis.setAxisMinimum(data.getXMin() - xOffset);
        xAxis.setAxisMaximum(data.getXMax() + xOffset);


        // Configuração do Eixo Y esquerdo
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false); // Remove as linhas de grade do eixo Y esquerdo
        leftAxis.setDrawAxisLine(false); // Remove a linha do eixo Y esquerdo

        // Configuração do Eixo Y direito
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false); // Remove as linhas de grade do eixo Y direito
        rightAxis.setDrawAxisLine(false); // Remove a linha do eixo Y direito
        rightAxis.setEnabled(false); // Desabilita completamente o eixo Y direito

        // Adiciona espaço extra ao redor do gráfico para evitar cortes
        barChart.setExtraOffsets(10f, 0f, 10f, 0f);

        // Outras configurações visuais do gráfico
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1500);

        barChart.invalidate(); // Atualiza o gráfico

        // Ajusta o gráfico para mostrar apenas as 10 primeiras barras inicialmente.
        // Este comando ajusta o zoom para limitar o número de entradas visíveis.
        barChart.setVisibleXRangeMaximum(10);
        //barChart.moveViewToX(0); // Opcional: se você quiser iniciar a visualização a partir de um ponto específico, como o início dos dados.

    }


    private List<String> getMonths(int count) {
        List<String> months = new ArrayList<>();
        String[] monthNames = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        for (int year = 0; year < count / 12; year++) {
            for (String month : monthNames) {
                months.add(month + " " + (year + 2020)); // Ajuste o ano base conforme necessário
            }
        }
        return months;
    }

    private List<String> getMonths() {
        return Arrays.asList("Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez");
    }


    /**
     * Calcula a largura das barras, o espaço entre as barras e o espaço entre os grupos.
     *
     * @param numGroups Número de grupos no gráfico.
     * @param barsPerGroup Número de barras em cada grupo.
     * @param groupSpacePercent Percentual do espaço do gráfico dedicado ao espaço entre os grupos.
     * @param barSpacePercent Percentual do espaço do gráfico dedicado ao espaço entre as barras dentro de um grupo.
     * @return Um array contendo, respectivamente, a largura da barra, o espaço entre as barras e o espaço entre os grupos.
     */
    public float[] calculateBarAndSpaceWidth(int numGroups, int barsPerGroup, float groupSpacePercent, float barSpacePercent) {
        // A largura total disponível para cada grupo é 1 dividido pelo número de grupos.
        // Ajustamos isso com base no percentual dedicado ao espaço entre os grupos.
        float groupSpace = (1f / numGroups) * groupSpacePercent;
        float remainingSpace = (1f / numGroups) - groupSpace;

        // O espaço disponível para as barras, ajustado com base no percentual dedicado ao espaço entre as barras.
        float barSpace = remainingSpace / barsPerGroup * barSpacePercent;
        float barWidth = (remainingSpace - (barSpace * (barsPerGroup - 1))) / barsPerGroup;

        return new float[]{barWidth, barSpace, groupSpace};
    }


}
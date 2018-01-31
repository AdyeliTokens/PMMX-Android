package com.pmi.ispmmx.maya.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.pmi.ispmmx.maya.Modelos.Entidades.CRR;
import com.pmi.ispmmx.maya.Modelos.Entidades.Indicador;
import com.pmi.ispmmx.maya.Modelos.Entidades.PlanAttainment;
import com.pmi.ispmmx.maya.Modelos.Entidades.VQI;
import com.pmi.ispmmx.maya.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IndicadoresFragment extends Fragment {
    private View _view;
    private SwipeRefreshLayout swipeRefreshLayout;

    private LineChart _chartVQI;
    private LineChart _chartCRR;
    private LineChart _chartPLANATTAINMENT;

    private TextView _vqiIndicador;
    private TextView _vqiObjetivo;

    private TextView _crrIndicador;
    private TextView _crrObjetivo;

    private TextView _planIndicador;
    private TextView _planObjetivo;


    private CardView _cvVQI;
    private CardView _cvPLAN;
    private CardView _cvCRR;
    private CardView _cvCPQI;


    private OnInteractionListener mListener;

    public IndicadoresFragment() {

    }


    public static IndicadoresFragment newInstance(List<Indicador> indicadorList) {
        IndicadoresFragment fragment = new IndicadoresFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_indicadores, container, false);
        elementosUI();
        return _view;


    }

    private void elementosUI() {
        _cvVQI = _view.findViewById(R.id.cv_vqi);
        _cvVQI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickVQI();
            }
        });
        _cvPLAN = _view.findViewById(R.id.cv_plan);
        _cvPLAN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickPLAN();
            }
        });
        _cvCRR = _view.findViewById(R.id.cv_crr);
        _cvCRR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickCRR();
            }
        });
        _cvCPQI = _view.findViewById(R.id.cv_cpqi);
        _cvCPQI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickCPQI();
            }
        });

        _vqiIndicador = _view.findViewById(R.id.indicador_vqi);
        _vqiObjetivo = _view.findViewById(R.id.tv_objetivo_vqi);

        _crrIndicador = _view.findViewById(R.id.indicador_crr);
        _crrObjetivo = _view.findViewById(R.id.tv_objetivo_crr);

        _planIndicador = _view.findViewById(R.id.indicador_plan);
        _planObjetivo = _view.findViewById(R.id.tv_objetivo_plan);

        _chartVQI = _view.findViewById(R.id.chart_vqi);
        _chartVQI.setTouchEnabled(true);
        _chartVQI.setDoubleTapToZoomEnabled(true);
        _chartVQI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        _chartVQI.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });

        _chartPLANATTAINMENT = _view.findViewById(R.id.chart_plan);
        _chartPLANATTAINMENT.setTouchEnabled(true);
        _chartPLANATTAINMENT.setDoubleTapToZoomEnabled(true);
        _chartPLANATTAINMENT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        _chartPLANATTAINMENT.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });

        _chartCRR = _view.findViewById(R.id.chart_crr);
        _chartCRR.setTouchEnabled(true);
        _chartCRR.setDoubleTapToZoomEnabled(true);
        _chartCRR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        _chartCRR.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });

        swipeRefreshLayout = _view.findViewById(R.id.sw_indicadores);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListener.refreshCharts();
                mListener.refreshCharts();
            }
        });
    }

    public void llenarInformacion_VQI(List<VQI> vqiList) {
        _vqiIndicador.setText(vqiList.get(vqiList.size() - 1).getVqi_total() + "");
        _vqiObjetivo.setText(vqiList.get(vqiList.size() - 1).getObjetivo() + "");
        configVQIChart(vqiList);
    }

    public void llenarInformacion_CRR(List<CRR> crrList) {
        _crrIndicador.setText(crrList.get(crrList.size() - 1).getCRR_total() + "");
        _crrObjetivo.setText(crrList.get(crrList.size() - 1).getObjetivo() + "");
        configCRRChart(crrList);
    }

    public void llenarInformacion_PLAN(List<PlanAttainment> planList) {
        _planIndicador.setText(planList.get(planList.size() - 1).getPlan_total() + "");
        _planObjetivo.setText(planList.get(planList.size() - 1).getObjetivo() + "");
        configPlanChart(planList);
    }

    public void cargaOff() {
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteractionListener) {
            mListener = (OnInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private LineData setDataVQI(List<VQI> vqiList) {
        Collections.sort(vqiList, new Comparator<VQI>() {
            public int compare(VQI obj1, VQI obj2) {
                // ## Ascending order
                return obj1.getFecha().compareTo(obj2.getFecha()); // To compare string values
                // return Integer.valueOf(obj1.empId).compareTo(obj2.empId); // To compare integer values

                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                // return Integer.valueOf(obj2.empId).compareTo(obj1.empId); // To compare integer values
            }
        });


        ArrayList<Entry> vals1 = new ArrayList<>();
        int i = 1;
        for (VQI data : vqiList) {
            vals1.add(new Entry(i++, data.getVqi_total()));
        }

        ArrayList<Entry> vals2 = new ArrayList<>();
        i = 1;
        for (VQI data : vqiList) {
            vals2.add(new Entry(i++, data.getObjetivo()));
        }


        LineDataSet set1 = new LineDataSet(vals1, "VQI");
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);
        set1.setDrawCircles(true);
        set1.setLineWidth(1.8f);
        set1.setCircleRadius(4f);
        set1.setCircleColor(Color.WHITE);
        set1.setColor(Color.WHITE);
        set1.setFillAlpha(100);

        LineDataSet set2 = new LineDataSet(vals2, "OBJETIVO");
        set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set2.setCubicIntensity(0.2f);
        set2.setDrawCircles(true);
        set2.setLineWidth(1.8f);
        set2.setCircleRadius(4f);
        set2.setCircleColor(Color.GREEN);
        set2.setColor(Color.GREEN);
        set2.setFillAlpha(100);

        LineData data = new LineData(set1, set2);
        data.setValueTextSize(9f);
        data.setDrawValues(true);
        return data;

    }

    private LineData setDataCRR(List<CRR> crrList) {
        Collections.sort(crrList, new Comparator<CRR>() {
            public int compare(CRR obj1, CRR obj2) {
                // ## Ascending order
                return obj1.getFecha().compareTo(obj2.getFecha()); // To compare string values
                // return Integer.valueOf(obj1.empId).compareTo(obj2.empId); // To compare integer values

                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                // return Integer.valueOf(obj2.empId).compareTo(obj1.empId); // To compare integer values
            }
        });


        ArrayList<Entry> vals1 = new ArrayList<>();
        int i = 1;
        for (CRR data : crrList) {
            vals1.add(new Entry(i++, (float) data.getCRR_total()));
        }

        ArrayList<Entry> vals2 = new ArrayList<>();
        i = 1;
        for (CRR data : crrList) {
            vals2.add(new Entry(i++, (float) data.getObjetivo()));
        }


        LineDataSet set1 = new LineDataSet(vals1, "CRR");
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);
        set1.setDrawCircles(true);
        set1.setLineWidth(1.8f);
        set1.setCircleRadius(4f);
        set1.setCircleColor(Color.WHITE);
        set1.setColor(Color.WHITE);
        set1.setFillAlpha(100);

        LineDataSet set2 = new LineDataSet(vals2, "OBJETIVO");
        set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set2.setCubicIntensity(0.2f);
        set2.setDrawCircles(true);
        set2.setLineWidth(1.8f);
        set2.setCircleRadius(4f);
        set2.setCircleColor(Color.GREEN);
        set2.setColor(Color.GREEN);
        set2.setFillAlpha(100);

        LineData data = new LineData(set1, set2);
        data.setValueTextSize(9f);
        data.setDrawValues(true);
        return data;

    }

    private LineData setDataPlan(List<PlanAttainment> planList) {
        Collections.sort(planList, new Comparator<PlanAttainment>() {
            public int compare(PlanAttainment obj1, PlanAttainment obj2) {
                // ## Ascending order
                return obj1.getFecha().compareTo(obj2.getFecha()); // To compare string values
                // return Integer.valueOf(obj1.empId).compareTo(obj2.empId); // To compare integer values

                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                // return Integer.valueOf(obj2.empId).compareTo(obj1.empId); // To compare integer values
            }
        });


        ArrayList<Entry> vals1 = new ArrayList<>();
        int i = 1;
        for (PlanAttainment data : planList) {
            vals1.add(new Entry(i++, (float) data.getPlan_total()));
        }

        ArrayList<Entry> vals2 = new ArrayList<>();
        i = 1;
        for (PlanAttainment data : planList) {
            vals2.add(new Entry(i++, (float) data.getObjetivo()));
        }


        LineDataSet set1 = new LineDataSet(vals1, "CRR");
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);
        set1.setDrawCircles(true);
        set1.setLineWidth(1.8f);
        set1.setCircleRadius(4f);
        set1.setCircleColor(Color.WHITE);
        set1.setColor(Color.WHITE);
        set1.setFillAlpha(100);

        LineDataSet set2 = new LineDataSet(vals2, "OBJETIVO");
        set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set2.setCubicIntensity(0.2f);
        set2.setDrawCircles(true);
        set2.setLineWidth(1.8f);
        set2.setCircleRadius(4f);
        set2.setCircleColor(Color.GREEN);
        set2.setColor(Color.GREEN);
        set2.setFillAlpha(100);

        LineData data = new LineData(set1, set2);
        data.setValueTextSize(9f);
        data.setDrawValues(true);
        return data;

    }

    private void configVQIChart(List<VQI> vqiList) {

        _chartVQI.setViewPortOffsets(20, 20, 20, 20);
        _chartVQI.setTouchEnabled(false);
        _chartVQI.setDragEnabled(false);
        _chartVQI.setScaleEnabled(false);
        _chartVQI.setPinchZoom(false);
        _chartVQI.setDrawGridBackground(false);


        XAxis xAxis = _chartVQI.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);


        YAxis yAxis = _chartVQI.getAxisLeft();
        yAxis.setEnabled(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);

        _chartVQI.getDescription().setEnabled(false);
        _chartVQI.getAxisRight().setEnabled(false);

        _chartVQI.setData(setDataVQI(vqiList));
        _chartVQI.getLegend().setEnabled(true);
        _chartVQI.animateXY(2000, 2000);
        _chartVQI.invalidate();
    }

    private void configCRRChart(List<CRR> crrList) {

        _chartCRR.setViewPortOffsets(20, 20, 20, 20);
        _chartCRR.setTouchEnabled(false);
        _chartCRR.setDragEnabled(false);
        _chartCRR.setScaleEnabled(false);
        _chartCRR.setPinchZoom(false);
        _chartCRR.setDrawGridBackground(false);


        XAxis xAxis = _chartCRR.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);


        YAxis yAxis = _chartCRR.getAxisLeft();
        yAxis.setEnabled(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);

        _chartCRR.getDescription().setEnabled(false);
        _chartCRR.getAxisRight().setEnabled(false);

        _chartCRR.setData(setDataCRR(crrList));
        _chartCRR.getLegend().setEnabled(true);
        _chartCRR.animateXY(2000, 2000);
        _chartCRR.invalidate();
    }

    private void configPlanChart(List<PlanAttainment> planList) {

        _chartPLANATTAINMENT.setViewPortOffsets(20, 20, 20, 20);
        _chartPLANATTAINMENT.setTouchEnabled(false);
        _chartPLANATTAINMENT.setDragEnabled(false);
        _chartPLANATTAINMENT.setScaleEnabled(false);
        _chartPLANATTAINMENT.setPinchZoom(false);
        _chartPLANATTAINMENT.setDrawGridBackground(false);


        XAxis xAxis = _chartPLANATTAINMENT.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);


        YAxis yAxis = _chartPLANATTAINMENT.getAxisLeft();
        yAxis.setEnabled(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);

        _chartPLANATTAINMENT.getDescription().setEnabled(false);
        _chartPLANATTAINMENT.getAxisRight().setEnabled(false);

        _chartPLANATTAINMENT.setData(setDataPlan(planList));
        _chartPLANATTAINMENT.getLegend().setEnabled(true);
        _chartPLANATTAINMENT.animateXY(2000, 2000);
        _chartPLANATTAINMENT.invalidate();
    }

    public interface OnInteractionListener {
        void refreshCharts();

        void onClickVQI();

        void onClickCPQI();

        void onClickPLAN();

        void onClickCRR();
    }
}

package com.pmi.ispmmx.maya.Activities.Indicadores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.pmi.ispmmx.maya.Adapters.NoConformidadesPorSeccionAdapter;
import com.pmi.ispmmx.maya.Interfaces.IVolumenService;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.ModuloSeccion;
import com.pmi.ispmmx.maya.Modelos.Entidades.PlanAttainment;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlanAttainmentActivity extends AppCompatActivity {
    public RecyclerView.Adapter _mAdapter;
    private SharedPreferences pref;
    private Retrofit retrofit;
    private LineChart _chartPlan;
    private int idWorkCenter;
    private List<ModuloSeccion> seccionList;
    private RecyclerView _rvSecciones;
    private RecyclerView.LayoutManager _managerSecciones;
    private IVolumenService volumenService;
    private Toolbar _toolbar;
    private CollapsingToolbarLayout _toolbarLayout;

    private int semana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicadores_planattainment);
        pref = getSharedPreferences(OperadorPreference.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            idWorkCenter = (int) bundle.get("idWorkCenter");

            elementosUI();


            iniciarRetrofit();
            createServicios();
            IniciarEntidades();
            iniciarAdapter();
            iniciarRecycle();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void elementosUI() {
        _toolbarLayout = findViewById(R.id.toolbar_layout);
        _toolbarLayout.setTitleEnabled(false);
        _toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        _chartPlan = findViewById(R.id.chart_plan);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -12);
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.datePicker)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .showDayName(true)
                .showMonthName(true)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                SelecciondeDia(date);

            }
        });


        _rvSecciones = findViewById(R.id.rv_secciones);


    }

    private void ValidarSemana(Date dateSelected) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(dateSelected);
        if (semana != calender.get(Calendar.WEEK_OF_YEAR)) {
            semana = calender.get(Calendar.WEEK_OF_YEAR);
            _toolbar.setTitle("Semana: " + calender.get(Calendar.WEEK_OF_YEAR));
            _toolbar.setSubtitle("");

            String dayOfTheWeek = (String) DateFormat.format("EEEE", dateSelected); // Thursday
            String day = (String) DateFormat.format("dd", dateSelected); // 20
            String monthString = (String) DateFormat.format("MMM", dateSelected); // Jun
            String monthNumber = (String) DateFormat.format("MM", dateSelected); // 06
            String year = (String) DateFormat.format("yyyy", dateSelected); // 2013

            int dia = dateSelected.getDay();
            int mes = dateSelected.getMonth();
            int anio = dateSelected.getYear();
            String fecha = monthNumber + "/" + day + "/" + year;

            retrofiCallPlan(fecha);
        } else {

        }
    }

    private void SelecciondeDia(Date dateSelected) {
        String dayOfTheWeek = (String) DateFormat.format("EEEE", dateSelected); // Thursday
        String day = (String) DateFormat.format("dd", dateSelected); // 20
        String monthString = (String) DateFormat.format("MMM", dateSelected); // Jun
        String monthNumber = (String) DateFormat.format("MM", dateSelected); // 06
        String year = (String) DateFormat.format("yyyy", dateSelected); // 2013

        int dia = dateSelected.getDay();
        int mes = dateSelected.getMonth();
        int anio = dateSelected.getYear();
        String fecha = monthNumber + "/" + day + "/" + year;


        retrofiCallPlanPorFechaYWorkCenter(fecha);

        ValidarSemana(dateSelected);

    }

    private void iniciarRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void IniciarEntidades() {
        seccionList = new ArrayList<>();
    }

    private void createServicios() {
        volumenService = retrofit.create(IVolumenService.class);
    }

    public void iniciarAdapter() {
        _mAdapter = new NoConformidadesPorSeccionAdapter(seccionList, R.layout.cardview_no_conformidades, new NoConformidadesPorSeccionAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(ModuloSeccion moduloSeccion, int position) {

            }
        });
    }

    private void iniciarRecycle() {
        _managerSecciones = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        _rvSecciones.setHasFixedSize(false);
        _rvSecciones.setItemAnimator(new DefaultItemAnimator());
        _rvSecciones.setLayoutManager(_managerSecciones);
        _rvSecciones.setNestedScrollingEnabled(false);
        _rvSecciones.setAdapter(_mAdapter);
        _rvSecciones.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

    }


    private void retrofiCallPlan(String fecha) {
        volumenService.getPlanAttainmentByWorkCenter(fecha, idWorkCenter).enqueue(new Callback<List<PlanAttainment>>() {
            @Override
            public void onResponse(@NonNull Call<List<PlanAttainment>> call, @NonNull Response<List<PlanAttainment>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        llenarInformacion_PLAN(response.body());


                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PlanAttainment>> call, @NonNull Throwable t) {

            }
        });
    }

    private void retrofiCallPlanPorFechaYWorkCenter(String fecha) {

    }


    public void llenarInformacion_PLAN(List<PlanAttainment> planAttainments) {
        configPlanChart(planAttainments);
    }

    private LineData setData(List<PlanAttainment> planAttainments) {
        Collections.sort(planAttainments, new Comparator<PlanAttainment>() {
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
        for (PlanAttainment data : planAttainments) {
            vals1.add(new Entry(i++, (float) data.getPlan_total()));
        }

        ArrayList<Entry> vals2 = new ArrayList<>();
        i = 1;
        for (PlanAttainment data : planAttainments) {
            vals2.add(new Entry(i++, (float) data.getObjetivo()));
        }


        LineDataSet set1 = new LineDataSet(vals1, "Plan Attainment");
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

    private void configPlanChart(List<PlanAttainment> planAttainments) {

        _chartPlan.setViewPortOffsets(20, 20, 20, 20);
        _chartPlan.setTouchEnabled(false);
        _chartPlan.setDragEnabled(false);
        _chartPlan.setScaleEnabled(false);
        _chartPlan.setPinchZoom(false);
        _chartPlan.setDrawGridBackground(false);


        XAxis xAxis = _chartPlan.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);


        YAxis yAxis = _chartPlan.getAxisLeft();
        yAxis.setEnabled(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);

        _chartPlan.getDescription().setEnabled(false);
        _chartPlan.getAxisRight().setEnabled(false);

        _chartPlan.setData(setData(planAttainments));
        _chartPlan.getLegend().setEnabled(true);
        _chartPlan.animateXY(2000, 2000);
        _chartPlan.invalidate();
    }

}



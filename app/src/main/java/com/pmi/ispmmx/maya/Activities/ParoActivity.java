package com.pmi.ispmmx.maya.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pmi.ispmmx.maya.Adapters.Pages.ParoPagerAdapter;
import com.pmi.ispmmx.maya.DialogFragments.TextoDialogFragment;
import com.pmi.ispmmx.maya.Fragments.ActividadEnParoFragment;
import com.pmi.ispmmx.maya.Fragments.DetalleParoFragment;
import com.pmi.ispmmx.maya.Interfaces.IParoService;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParoActivity extends AppCompatActivity implements
        DetalleParoFragment.OnDetalleParoInteractionListener,
        TextoDialogFragment.OnInteractionListener {


    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private SharedPreferences pref;


    private ViewPager _viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private int idParo;
    private String nombreModulo;
    private String nombreWorkCenter;

    private BottomSheetDialogFragment newFragment;


    private Retrofit retrofit;
    private IParoService paroServicio;


    private DetalleParoFragment detalleParoFragment;
    private ActividadEnParoFragment actividadEnParoFragment;


    private Paro paro;


    private boolean puedeCerrarse;
    private boolean puedeAsignarce;
    private boolean puedeComentarce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paro);
        pref = getSharedPreferences(OperadorPreference.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getArguments();
        elementosUI();
        setSupportActionBar(toolbar);

        iniciarRetrofit();
        instanciarFragments();
        personalizarToolbar();
        createTabLayout();


    }


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///                Metodos utilizados por Menu              //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.operador_main, menu);
        //final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        //SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///            Funciones de Inicio de la View               //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    private void elementosUI() {
        toolbar = findViewById(R.id.toolbar);
        _viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setVisibility(View.GONE);


    }

    private void getArguments() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            try {
                puedeCerrarse = (boolean) bundle.get("puedeCerrarse");
                puedeAsignarce = (boolean) bundle.get("puedeAsignarce");
                puedeComentarce = (boolean) bundle.get("puedeComentarce");
            } catch (Exception e) {
                puedeCerrarse = true;
                puedeAsignarce = true;
                puedeComentarce = true;
            }


            idParo = (int) bundle.get("idParo");
            nombreModulo = (String) bundle.get("nombreModulo");
            nombreWorkCenter = (String) bundle.get("nombreWorkCenter");
        }
    }

    private void personalizarToolbar() {
        setTitle(nombreWorkCenter + " - " + nombreModulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void instanciarFragments() {
        actividadEnParoFragment = new ActividadEnParoFragment();
        detalleParoFragment = new DetalleParoFragment();
        detalleParoFragment.puedeAsignarce = puedeAsignarce;
        detalleParoFragment.puedeComentarce = puedeComentarce;
        detalleParoFragment.puedeCerrarse = puedeCerrarse;

    }

    private void iniciarRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        paroServicio = retrofit.create(IParoService.class);
    }

    private void createTabLayout() {

        tabLayout.addTab(tabLayout.newTab().setText("Resumen"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ParoPagerAdapter paroPagerAdapter = new ParoPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), detalleParoFragment, actividadEnParoFragment);

        _viewPager.setAdapter(paroPagerAdapter);
        _viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                _viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///                ParoFragment                             //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    @Override
    public void llenarParo() {
        retrofitCallGetParo(idParo);
    }

    @Override
    public void onClickAsignarParo() {
        dialogAsignarDelParo();

    }

    @Override
    public void onClickMotivoParo(String motivo) {
        startBSTexto();
    }

    @Override
    public void onClickCerrarParo(Paro paro) {
        dialogCambiarActivo();
    }

    @Override
    public void onClickAgregarTexto(String texto) {
        retrofitMotivoParo(texto);
    }

    @Override
    public void onClickBorrarTexto() {

    }

    @Override
    public void onClickMicrofono() {

    }


    private void startBSTexto() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment prev = manager.findFragmentByTag("yourTag");

        if (prev != null) {
            ft.remove(prev);
        }

        newFragment = TextoDialogFragment.newInstance();
        newFragment.show(ft, "yourTag");

    }


    private void retrofitCallGetParo(int idParo) {
        paroServicio.getParo(idParo).enqueue(new Callback<Paro>() {
            @Override
            public void onResponse(Call<Paro> call, Response<Paro> response) {
                if (response.isSuccessful()) {
                    paro = response.body();
                    if (paro.getActivo()) {
                        interacionActivo();

                    } else {
                        interacionDesactivado();
                    }
                    detalleParoFragment.llenarInformacionDeParo(paro);
                }
            }

            @Override
            public void onFailure(Call<Paro> call, Throwable t) {
            }
        });
    }

    private void retrofitMotivoParo(String motivo) {
        paroServicio.putMotivoParo(paro.getId(), paro.getIdMecanico(), motivo).enqueue(new Callback<Paro>() {
            @Override
            public void onResponse(Call<Paro> call, Response<Paro> response) {
                if (response.isSuccessful()) {
                    paro = response.body();
                    if (paro.getActivo()) {
                        detalleParoFragment.llenarInformacionDeParo(paro);
                    } else {

                    }

                }
            }

            @Override
            public void onFailure(Call<Paro> call, Throwable t) {

            }
        });

    }

    private void retrofitAsignar() {
        paro.setIdMecanico(pref.getInt(OperadorPreference.ID_PERSONA_SHARED_PREF, 0));
        paroServicio.putAsignarParo(paro.getId(), paro.getIdMecanico()).enqueue(new Callback<Paro>() {
            @Override
            public void onResponse(Call<Paro> call, Response<Paro> response) {
                if (response.isSuccessful()) {
                    paro = response.body();
                    if (paro.getActivo()) {
                        detalleParoFragment.llenarMecanico();
                    } else {

                    }

                }
            }

            @Override
            public void onFailure(Call<Paro> call, Throwable t) {

            }
        });

    }

    private void retrofitcambiarActivo() {
        if (paro.getActivo()) {
            paro.setActivo(false);
        } else {
            paro.setActivo(true);
        }
        int idPersona = pref.getInt(OperadorPreference.ID_PERSONA_SHARED_PREF, 0);
        paroServicio.putCambiarActivo(paro.getId(), idPersona, paro.getActivo()).enqueue(new Callback<Paro>() {
            @Override
            public void onResponse(Call<Paro> call, Response<Paro> response) {
                if (response.isSuccessful()) {
                    paro = response.body();
                    if (paro.getActivo()) {
                        interacionActivo();
                    } else {
                        interacionDesactivado();
                    }
                    detalleParoFragment.llenarInformacionDeParo(paro);
                }
            }

            @Override
            public void onFailure(Call<Paro> call, Throwable t) {

            }
        });

    }


    private void interacionActivo() {
        //_fab.setImageResource(R.drawable.ic_pause_white);
        //toolbar.setBackgroundColor(getResources().getColor(R.color.colorRedScale7));
        //toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

    }

    private void interacionDesactivado() {
    }


    private void dialogAsignarDelParo() {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("¿Estas seguro de que quieres atender esta falla?");
        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        retrofitAsignar();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void dialogCambiarActivo() {
        String mensaje;
        if (paro.getActivo()) {
            mensaje = "¿Confirmas que la falla ha sido CORREGIDA?";
        } else {
            mensaje = "¿Deseas REABRIR la falla?";
        }

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(mensaje);
        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        retrofitcambiarActivo();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }


}

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
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pmi.ispmmx.maya.Adapters.Pages.DefectoPagerAdapter;
import com.pmi.ispmmx.maya.DialogFragments.TextoDialogFragment;
import com.pmi.ispmmx.maya.Fragments.DetalleDefectoFragment;
import com.pmi.ispmmx.maya.Interfaces.IDefectoService;
import com.pmi.ispmmx.maya.Interfaces.IPersonaService;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Persona;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FOTOS_DEFECTOS;

public class DefectoActivity extends AppCompatActivity implements
        DetalleDefectoFragment.OnFragmentDefectoInteractionListener,
        TextoDialogFragment.OnInteractionListener {


    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private SharedPreferences pref;

    private BottomSheetDialogFragment newFragment;


    private Toolbar _toolbar;
    private ViewPager _viewPager;
    private TabLayout tabLayout;
    private DetalleDefectoFragment detalle;
    private ImageView portada;
    private String path;
    private int idDefecto;
    private Boolean cerrarDefecto;
    private Boolean asignarDefecto;
    private Defecto defecto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defecto);
        elementosUI();
        _toolbar.setTitle("");
        _toolbar.setSubtitle("");

        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pref = getSharedPreferences(OperadorPreference.SHARED_PREF_NAME,
                Context.MODE_PRIVATE);


        getbundle();
        crearFragment();
        createTabLayout();
    }


    private void elementosUI() {
        _toolbar = findViewById(R.id.toolbar);
        _viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setVisibility(View.GONE);
        portada = findViewById(R.id.image_defecto);
        portada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityPhotoOrigen();
            }
        });
    }

    private void crearFragment() {
        detalle = new DetalleDefectoFragment();
        detalle.asignarDefecto = asignarDefecto;
        detalle.cerrarDefecto = cerrarDefecto;
    }

    private void createTabLayout() {

        tabLayout.addTab(tabLayout.newTab().setText("Resumen"));
        //tabLayout.addTab(tabLayout.newTab().setText("Actividades"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        DefectoPagerAdapter defectoPagerAdapter = new DefectoPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), detalle);

        _viewPager.setAdapter(defectoPagerAdapter);
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

    private void getbundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        if (bundle != null) {

            try {
                idDefecto = (int) bundle.get("idDefecto");
                cerrarDefecto = (Boolean) bundle.get("cerrarDefecto");
                asignarDefecto = (Boolean) bundle.get("asignarDefecto");
                Picasso.with(this)
                        .load(URL_FOTOS_DEFECTOS + idDefecto)
                        //.placeholder(R.drawable.ic_watch_later_gray)
                        .error(R.drawable.ic_error_outline_gray)
                        .into(portada);

            } catch (Exception e) {
            }
        }
    }


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
        int id = item.getItemId();

        if (id == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void llenarDefecto() {
        retrofitGetDefecto();
    }

    @Override
    public void llenarResponsable() {
        retrofitGetPersona();
    }

    @Override
    public void onCerrarDefectoClick() {
        dialogCambiarActivo();
    }

    @Override
    public void onFechaEstimadaClick(int dia, int mes, int year) {
        retrofitFechaEstimada(dia, mes, year);
    }

    @Override
    public void onAsignarceDEfectoClick() {
        dialogAsignar();
    }

    @Override
    public void onComentariosClick() {
        startActivityComentarios();
    }

    @Override
    public void onActividadesClick() {

    }

    @Override
    public void onClickSap() {
        startBSTexto();
    }

    @Override
    public void onClickReportante() {
        startActivityPhotoReportante();
    }

    @Override
    public void onClickAgregarTexto(String texto) {
        retrofitPutSAP(texto);
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


    private void retrofitGetDefecto() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        IDefectoService defectoServicio = retrofit.create(IDefectoService.class);
        Call<Defecto> defectoCall = defectoServicio.getDefecto(idDefecto);

        defectoCall.enqueue(new Callback<Defecto>() {
            @Override
            public void onResponse(Call<Defecto> call, Response<Defecto> response) {
                if (response.isSuccessful()) {
                    defecto = response.body();

                    detalle.llenarInformacionDeDefecto(defecto);
                } else {
                }
            }

            @Override
            public void onFailure(Call<Defecto> call, Throwable t) {
            }
        });

    }

    private void retrofitGetPersona() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        IPersonaService personaService = retrofit.create(IPersonaService.class);
        Call<Persona> personaCall = personaService.getPersonaApi(defecto.getIdResponsable());

        personaCall.enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, Response<Persona> response) {
                if (response.isSuccessful()) {
                    detalle.llenarInformacionResponsable(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<Persona> call, Throwable t) {
            }
        });

    }

    private void retrofitPutSAP(String sap) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        IDefectoService defectoServicio = retrofit.create(IDefectoService.class);
        Call<Defecto> defectoCall = defectoServicio.putDefectoBySAP(idDefecto, sap);

        defectoCall.enqueue(new Callback<Defecto>() {
            @Override
            public void onResponse(Call<Defecto> call, Response<Defecto> response) {
                if (response.isSuccessful()) {
                    defecto = response.body();

                    detalle.llenarInformacionDeDefecto(defecto);
                } else {
                }
            }

            @Override
            public void onFailure(Call<Defecto> call, Throwable t) {
            }
        });

    }

    private void retrofitCambiarActivo() {
        if (defecto.getActivo()) {
            defecto.setActivo(false);
        } else {
            defecto.setActivo(true);
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        IDefectoService defectoServicio = retrofit.create(IDefectoService.class);
        Call<Defecto> defectoCall = defectoServicio.putDefectoByActivo(idDefecto, defecto.getActivo());

        defectoCall.enqueue(new Callback<Defecto>() {
            @Override
            public void onResponse(Call<Defecto> call, Response<Defecto> response) {
                if (response.isSuccessful()) {

                    defecto = response.body();
                    detalle.llenarInformacionDeDefecto(defecto);
                } else {
                }
            }

            @Override
            public void onFailure(Call<Defecto> call, Throwable t) {
            }
        });

    }

    private void retrofitCambiarResponsable() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        IDefectoService defectoServicio = retrofit.create(IDefectoService.class);
        Call<Defecto> defectoCall = defectoServicio.putDefectoByResponsable(idDefecto, pref.getInt(OperadorPreference.ID_PERSONA_SHARED_PREF, 0));

        defectoCall.enqueue(new Callback<Defecto>() {
            @Override
            public void onResponse(Call<Defecto> call, Response<Defecto> response) {
                if (response.isSuccessful()) {

                    defecto = response.body();
                    Persona reponsable = new Persona();
                    reponsable.setId(pref.getInt(OperadorPreference.ID_PERSONA_SHARED_PREF, 0));
                    reponsable.setNombre(pref.getString(OperadorPreference.NOMBRE_PERSONA_SHARED_PREF, ""));
                    reponsable.setApellido1(pref.getString(OperadorPreference.APELLIDO1_PERSONA_SHARED_PREF, ""));
                    reponsable.setApellido2(pref.getString(OperadorPreference.APELLIDO2_PERSONA_SHARED_PREF, ""));
                    defecto.setResponsable(reponsable);
                    detalle.llenarInformacionDeDefecto(defecto);

                } else {
                }
            }

            @Override
            public void onFailure(Call<Defecto> call, Throwable t) {
            }
        });

    }

    private void retrofitFechaEstimada(int dia, int mes, int year) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        IDefectoService defectoServicio = retrofit.create(IDefectoService.class);
        Call<Defecto> defectoCall = defectoServicio.putDefectoByFechaEstimada(idDefecto, dia, mes, year);

        defectoCall.enqueue(new Callback<Defecto>() {
            @Override
            public void onResponse(Call<Defecto> call, Response<Defecto> response) {
                if (response.isSuccessful()) {
                    defecto = response.body();


                    detalle.llenarInformacionDeDefecto(defecto);
                } else {
                }
            }

            @Override
            public void onFailure(Call<Defecto> call, Throwable t) {
            }
        });

    }


    private void dialogCambiarActivo() {
        String mensaje;
        if (defecto.getActivo()) {
            mensaje = "¿Confirmas que este defecto ha sido CERRADO?";
        } else {
            mensaje = "¿Deseas REABRIR el defecto?";
        }

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(mensaje);
        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        retrofitCambiarActivo();
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

    private void dialogAsignar() {
        String mensaje;
        mensaje = "¿Confirmas que te ASIGNARAS este defecto?";


        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(mensaje);
        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        retrofitCambiarResponsable();
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

    private void startActivityComentarios() {
        Intent intent = new Intent(this, ComentariosActivity.class);
        intent.putExtra("idDefecto", defecto.getId());
        startActivity(intent);
    }

    private void startActivityPhotoOrigen(){
        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("idDefecto", defecto.getId());
        startActivity(intent);
    }

    private void startActivityPhotoReportante(){
        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("idPersona", defecto.getIdReportador());
        startActivity(intent);
    }


}

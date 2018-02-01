package com.pmi.ispmmx.maya.Activities.Operaciones;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pmi.ispmmx.maya.Activities.AgregarDefectoActivity;
import com.pmi.ispmmx.maya.Activities.DefectoActivity;
import com.pmi.ispmmx.maya.Activities.ParoActivity;
import com.pmi.ispmmx.maya.Adapters.Pages.ModuloPagerAdapter;
import com.pmi.ispmmx.maya.Fragments.DefectosFragment;
import com.pmi.ispmmx.maya.Fragments.ParosFragment;
import com.pmi.ispmmx.maya.Interfaces.IDefectoService;
import com.pmi.ispmmx.maya.Interfaces.IParoService;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;
import com.pmi.ispmmx.maya.Modelos.Secciones.DefectoSeccion;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrigenActivity extends AppCompatActivity implements
        ParosFragment.OnParoInteractionListener,
        DefectosFragment.OnDefectoInteractionListener {
    private SharedPreferences pref;
    ////Fragment
    private ParosFragment parosFragment;
    private DefectosFragment defectosFragment;
    ////Elementos UI
    private TabLayout _tabLayout;
    private ViewPager _viewPager;
    private Toolbar _toolbar;
    private String nombreModulo;
    /////Retrofit
    private Retrofit retrofit;
    ////Servicios
    private IParoService paroService;
    private IDefectoService defectoService;

    ////Entidades y datos
    private int idOrigen;
    private List<Defecto> defectosSinAsignar;
    private List<Defecto> defectosUltimaSemana;
    private List<Defecto> defectosUltimoMes;
    private List<Defecto> defectosAnteriores;

    private List<Paro> parosActivosList;
    private List<Paro> paroCerradosList;

    ////
    private boolean puedeCerrarse;
    private boolean puedeAsignarce;
    private boolean puedeComentarce;


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///                WorkCenterFragment                       //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo);
        pref = getSharedPreferences(OperadorPreference.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        elementosUI();

        getIntents();

        iniciarRetrofit();
        createServicios();
        createdefectosFragment();
        createparosFragment();


        personalizarToolbar();
        generarTabs();

        inicio();

    }

    @Override
    protected void onResume() {
        super.onResume();

        retornar();
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
    ///                WorkCenterFragment                       //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    private void iniciarFragments() {
        parosFragment = new ParosFragment();
        defectosFragment = new DefectosFragment();
    }

    private void createdefectosFragment() {
        defectosFragment = new DefectosFragment();
        defectosFragment.defectos = new ArrayList<DefectoSeccion>();
        defectosFragment.iniciarAdapter();

    }

    private void createparosFragment() {
        parosFragment = new ParosFragment();

        parosFragment.parosAbiertos = new ArrayList<Paro>();
        parosFragment.iniciarAdapterAbirtos();
        parosFragment.cerrarParos = puedeCerrarse;
        parosFragment.asignarParos = puedeAsignarce;


        parosFragment.parosCerrados = new ArrayList<Paro>();
        parosFragment.iniciarAdapterCerrados();


    }

    private void iniciarRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void createServicios() {
        paroService = retrofit.create(IParoService.class);
        defectoService = retrofit.create(IDefectoService.class);
    }

    private void elementosUI() {
        _toolbar = findViewById(R.id.toolbar);
        _tabLayout = findViewById(R.id.tabLayoutModulo);
        _viewPager = findViewById(R.id.viewPagerModulo);

    }

    private void getIntents() {
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

            idOrigen = (int) bundle.get("idOrigen");
            nombreModulo = (String) bundle.get("nombreModulo");
            String nombreWorkCenter = (String) bundle.get("nombreWorkCenter");
        }
    }

    private void personalizarToolbar() {
        _toolbar.setTitle(nombreModulo);
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void generarTabs() {
        _tabLayout.addTab(_tabLayout.newTab().setText("Fallas"));
        _tabLayout.addTab(_tabLayout.newTab().setText("Defectos"));

        _tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ModuloPagerAdapter operadorPagerAdapter = new ModuloPagerAdapter(getSupportFragmentManager(), _tabLayout.getTabCount(), parosFragment, defectosFragment);

        _viewPager.setAdapter(operadorPagerAdapter);
        _viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_tabLayout));

        _tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    retrofitParosActivosByOrigen();
                    retrofitParosCerradosByOrigen();
                } else if (position == 1) {
                    llenarDefectos();
                }

                _viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    parosFragment.parosAbiertos.clear();
                    parosFragment.adapparosactivos.notifyDataSetChanged();
                    parosFragment.parosCerrados.clear();
                    parosFragment.adapparoscerrados.notifyDataSetChanged();

                } else if (position == 1) {

                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void ActivityAgregarDefecto() {
        Intent intent = new Intent(this, AgregarDefectoActivity.class);
        intent.putExtra("idOrigen", idOrigen);
        startActivity(intent);
    }

    private void inicio() {
        retrofitParosActivosByOrigen();
        retrofitParosCerradosByOrigen();
    }

    private void retornar() {
        retrofitParosActivosByOrigen();
        retrofitParosCerradosByOrigen();
        llenarDefectos();
    }


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///                ParoFragment                             //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    @Override
    public void llenarParosActivos() {
        //retrofitParosActivosByWorkCenter();
    }

    @Override
    public void abrirActivityParo(Paro paro) {
        Intent intent = new Intent(this, ParoActivity.class);

        intent.putExtra("puedeAsignarce", puedeAsignarce);
        intent.putExtra("puedeCerrarse", puedeCerrarse);
        intent.putExtra("puedeComentarce", puedeComentarce);


        intent.putExtra("nombreModulo", paro.getOrigen().getModulo().getNombreCorto().toString());
        intent.putExtra("nombreWorkCenter", paro.getOrigen().getWorkCenter().getNombreCorto().toString());
        intent.putExtra("idParo", paro.getId());
        startActivity(intent);


    }

    @Override
    public void llenarParosCerrados() {
        //retrofitParosCerradosByWorkCenter();
    }


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///                DefectosFragment                       //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    @Override
    public void llenarDefectos() {
        defectosFragment.defectos.clear();
        defectosFragment.mAdapter.notifyDataSetChanged();

        retrofitDefectos(1, idOrigen, true, -1, 0, 1000, "Abiertos - Hoy");
        retrofitDefectos(2, idOrigen, false, -1, 0, 10, "Cerrados - Hoy");
        retrofitDefectos(3, idOrigen, false, -7, -1, 10, "Cerrados - 7 dias");
        retrofitDefectos(4, idOrigen, false, -14, -6, 10, "Cerrados - 14 dias");
        retrofitDefectos(5, idOrigen, false, -21, -13, 10, "Cerrados - 21 dias");
        retrofitDefectos(6, idOrigen, false, -28, -20, 10, "Cerrados - 28 dias");
    }

    @Override
    public void OnClickDefecto(Defecto defecto, int position, View foto) {

        Intent intent = new Intent(this, DefectoActivity.class);
        intent.putExtra("idDefecto", defecto.getId());

        intent.putExtra("cerrarDefecto", true);
        intent.putExtra("asignarDefecto", false);

        if (defecto.getFotos() != null && defecto.getFotos().size() > 0) {
            String imagen = defecto.getFotos().get(0).getPath();
            intent.putExtra("path", "http://serverpmi.tr3sco.net" + imagen);
        }
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, foto, "portada");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///                Retrofit Interacciones                   //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    private void retrofitPostParo(Paro paro) throws IOException {
        paroService.postParo(paro).enqueue(new Callback<Paro>() {
            @Override
            public void onResponse(Call<Paro> call, Response<Paro> response) {
                if (response.body() != null) {
                    parosFragment.parosAbiertos.add(response.body());
                    parosFragment.adapparosactivos.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<Paro> call, Throwable t) {

            }
        });

    }

    private void retrofitParosActivosByOrigen() {
        if (!parosFragment.cargaParosActivos) {
            parosFragment.parosAbiertos.clear();
            parosFragment.errorParosActivos = false;
            parosFragment.cargaParosActivos = true;
            parosFragment.mensajeParosActivos = "";
            parosFragment.adapparosactivos.notifyDataSetChanged();

            paroService.getParosByOrigen(idOrigen, true, 100).enqueue(new Callback<List<Paro>>() {
                @Override
                public void onResponse(Call<List<Paro>> call, Response<List<Paro>> response) {

                    if (response.isSuccessful()) {
                        List<Paro> paros = response.body();

                        if (paros.size() > 0) {
                            for (Paro paro : paros) {
                                parosFragment.parosAbiertos.add(paro);
                            }
                            parosFragment.errorParosActivos = false;
                        }

                    } else {
                        parosFragment.errorParosActivos = true;
                        parosFragment.mensajeParosActivos = response.errorBody().toString();
                    }


                    parosFragment.cargaParosActivos = false;
                    parosFragment.adapparosactivos.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Paro>> call, Throwable t) {
                    parosFragment.errorParosActivos = true;
                    parosFragment.cargaParosActivos = false;
                    parosFragment.mensajeParosActivos = "Por favor!! Validatu conexion a internet";
                }
            });
        }
    }

    private void retrofitParosCerradosByOrigen() {
        paroService.getParosByOrigen(idOrigen, false, 10).enqueue(new Callback<List<Paro>>() {
            @Override
            public void onResponse(Call<List<Paro>> call, Response<List<Paro>> response) {
                if (response.isSuccessful()) {
                    List<Paro> paros = response.body();
                    for (Paro paro : paros) {
                        parosFragment.parosCerrados.add(paro);
                    }

                    parosFragment.adapparoscerrados.notifyDataSetChanged();
                } else {
                    //parosFragment.estadoErrorParosCerrados(R.drawable.ic_error_outline_gray, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Paro>> call, Throwable t) {
                //parosFragment.estadoErrorParosCerrados(R.drawable.ic_error_outline_gray, "Por favor!! Validatu conexion a internet");
            }
        });
    }

    private void retrofitDefectos(final int posicion, int idOrigen, boolean activo, int diasAtras, int diasAdelante, int cantidad, final String titulo) {
        defectoService.getDefectosByOrigen(idOrigen, activo, diasAtras, diasAdelante, cantidad).enqueue(new Callback<List<Defecto>>() {

            @Override
            public void onResponse(Call<List<Defecto>> call, Response<List<Defecto>> response) {
                if (response.isSuccessful()) {
                    DefectoSeccion seccion = new DefectoSeccion();
                    seccion.setPocision(posicion);
                    seccion.setTitulo(titulo);
                    seccion.setElementos(response.body());
                    seccion.setError(false);
                    seccion.setWait(false);

                    defectosFragment.defectos.add(seccion);
                    Collections.sort(defectosFragment.defectos, new Comparator<DefectoSeccion>() {

                        @Override
                        public int compare(DefectoSeccion tiempoDeParo, DefectoSeccion t1) {
                            String left = tiempoDeParo.getPocision() + "";
                            String right = t1.getPocision() + "";
                            return left.compareTo(right);
                        }
                    });


                    defectosFragment.mAdapter.notifyDataSetChanged();


                } else {
                    DefectoSeccion seccion = new DefectoSeccion();
                    seccion.setError(true);
                    seccion.setTitulo(titulo);
                    seccion.setPocision(posicion);
                    seccion.setMensajeError(response.errorBody().toString());
                    seccion.setWait(false);
                    defectosFragment.defectos.add(seccion);
                    Collections.sort(defectosFragment.defectos, new Comparator<DefectoSeccion>() {

                        @Override
                        public int compare(DefectoSeccion tiempoDeParo, DefectoSeccion t1) {
                            String left = tiempoDeParo.getPocision() + "";
                            String right = t1.getPocision() + "";
                            return left.compareTo(right);
                        }
                    });

                    defectosFragment.defectos.add(seccion);
                    defectosFragment.mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Defecto>> call, Throwable t) {
                DefectoSeccion seccion = new DefectoSeccion();
                seccion.setTitulo(titulo);
                seccion.setPocision(posicion);
                seccion.setError(true);
                seccion.setMensajeError("Por favor!! Validatu conexion a internet");

                seccion.setWait(false);
                defectosFragment.defectos.add(seccion);
                Collections.sort(defectosFragment.defectos, new Comparator<DefectoSeccion>() {

                    @Override
                    public int compare(DefectoSeccion tiempoDeParo, DefectoSeccion t1) {
                        String left = tiempoDeParo.getPocision() + "";
                        String right = t1.getPocision() + "";
                        return left.compareTo(right);
                    }
                });
                defectosFragment.defectos.add(seccion);
                defectosFragment.mAdapter.notifyDataSetChanged();
            }
        });
    }

    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///                        Alerts                         //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    private void AlertParo() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Seguro que quieres reportar una falla en " + nombreModulo + " ?");

        alertDialogBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Paro paro = new Paro();
                paro.setIdOrigen(idOrigen);
                paro.setIdReportador(pref.getInt(OperadorPreference.ID_PERSONA_SHARED_PREF, 0));
                try {
                    retrofitPostParo(paro);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}

package com.pmi.ispmmx.maya.Activities.Perfiles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmi.ispmmx.maya.Activities.DefectoActivity;
import com.pmi.ispmmx.maya.Activities.LoginActivity;
import com.pmi.ispmmx.maya.Activities.ParoActivity;
import com.pmi.ispmmx.maya.Adapters.Pages.MecanicoPagerAdapter;
import com.pmi.ispmmx.maya.CircleTransform;
import com.pmi.ispmmx.maya.DialogFragments.DefectosActivosPorWorkCenterDialogFragment;
import com.pmi.ispmmx.maya.DialogFragments.ParosActivosPorWorkCenterDialogFragment;
import com.pmi.ispmmx.maya.Fragments.BusinessUnitFragment;
import com.pmi.ispmmx.maya.Fragments.MisServiciosFragment;
import com.pmi.ispmmx.maya.Interfaces.IDefectoService;
import com.pmi.ispmmx.maya.Interfaces.IMecanicoPorBusinessUnitsService;
import com.pmi.ispmmx.maya.Interfaces.IMecanicoService;
import com.pmi.ispmmx.maya.Interfaces.IParoService;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.BussinesUnit;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;
import com.pmi.ispmmx.maya.Modelos.Entidades.Persona;
import com.pmi.ispmmx.maya.Activities.ProfileActivity;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FOTOS_PERSONAS;

public class MecanicoActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        MisServiciosFragment.OnFragmentInteractionListener,
        BusinessUnitFragment.OnInteractionListener,
        ParosActivosPorWorkCenterDialogFragment.OnInteractionListener,
        DefectosActivosPorWorkCenterDialogFragment.OnInteractionListener {


    private SharedPreferences pref;

    private MisServiciosFragment misServiciosFragment;
    private BusinessUnitFragment businessUnitFragment;


    private Toolbar _toolbar;
    private TabLayout _tabLayout;
    private DrawerLayout _drawer;
    private TextView _navUsername;
    private TextView _navPosition;
    private ViewPager _viewPager;
    private ImageView _imgUsuario;


    private IMecanicoPorBusinessUnitsService mecanicoPorBusinessUnits;
    private IMecanicoService mecanicoService;
    private IParoService paroService;
    private IDefectoService defectoService;


    private BottomSheetDialogFragment newFragment;

    private BussinesUnit bussinesUnit;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mecanico);
        pref = getSharedPreferences(OperadorPreference.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        elementosUI();

        inicializarEntidades();
        iniciarRetrofit();
        createServicios();
        createFragments();
        generarTabs();
        personalizarHeaderBar();
        inicio();
    }



    @Override
    public void onResume() {
        super.onResume();
        //retorno();

    }


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///            Funciones de Inicio de la View               //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    private void inicializarEntidades() {

        bussinesUnit = new BussinesUnit();

    }

    private void iniciarRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void createServicios() {
        mecanicoPorBusinessUnits = retrofit.create(IMecanicoPorBusinessUnitsService.class);
        paroService = retrofit.create(IParoService.class);
        defectoService = retrofit.create(IDefectoService.class);
        mecanicoService = retrofit.create(IMecanicoService.class);
    }

    private void elementosUI() {
        _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);

        _drawer = findViewById(R.id.drawer_layout);
        _viewPager = findViewById(R.id.viewPager);
        NavigationView _navigationView = findViewById(R.id.nav_view);
        _navigationView.setNavigationItemSelectedListener(this);
        View _headerView = _navigationView.getHeaderView(0);
        _tabLayout = findViewById(R.id.tabLayout);

        _imgUsuario = _headerView.findViewById(R.id.img_persona);
        _navUsername = _headerView.findViewById(R.id.textViewNombreUser);
        _navPosition = _headerView.findViewById(R.id.textViewPuestoUser);
    }
    private void personalizarHeaderBar() {
        String nombre = pref.getString(OperadorPreference.NOMBRE_PERSONA_SHARED_PREF, "no available");
        String apellido1 = pref.getString(OperadorPreference.APELLIDO1_PERSONA_SHARED_PREF, "no available");
        String apellido2 = pref.getString(OperadorPreference.APELLIDO2_PERSONA_SHARED_PREF, "no available");
        String puesto = pref.getString(OperadorPreference.NOMBRE_PUESTO_SHARED_PREF, "no available");

        Picasso.with(getBaseContext())
                .load(URL_FOTOS_PERSONAS + pref.getInt(OperadorPreference.ID_PERSONA_SHARED_PREF, 0))
                .transform(new CircleTransform())
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .error(R.drawable.ic_error_outline_gray)
                .into(_imgUsuario);


        _imgUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPerfilActivity();
            }
        });

        _navUsername.setText(nombre + " "+ apellido1  + " "+ apellido2+".");

        _navPosition.setText(puesto);

        menuAnimation();
    }
    private void menuAnimation() {
        ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(
                this, _drawer, _toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        _drawer.setDrawerListener(_toggle);
        _toggle.syncState();
    }



    private void createFragments() {
        businessUnitFragment = new BusinessUnitFragment();
        businessUnitFragment.bussinesUnitList = new ArrayList<>();
        businessUnitFragment.iniciarAdapter();

        misServiciosFragment = new MisServiciosFragment();
        misServiciosFragment.paroList = new ArrayList<>();
        misServiciosFragment.defectoList = new ArrayList<>();
        misServiciosFragment.iniciarAdapterParosAbirtos();
        misServiciosFragment.iniciarAdapterDefectosAbirtos();
    }
    private void generarTabs() {

        _tabLayout.addTab(_tabLayout.newTab().setText("Mis Servicios"));
        _tabLayout.addTab(_tabLayout.newTab().setText("Business Unit"));

        _tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        MecanicoPagerAdapter MecanicoPagerAdapter = new MecanicoPagerAdapter(getSupportFragmentManager(), _tabLayout.getTabCount(), misServiciosFragment, businessUnitFragment);
        _viewPager.setAdapter(MecanicoPagerAdapter);
        _viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_tabLayout));
        _tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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



    private void inicio() {
        retrofitBussinesUnit();
        retrofitMisServicios();
    }


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///                Metodos utilizados por Menu              //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void animationMenu() {
        ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(
                this, _drawer, _toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        _drawer.setDrawerListener(_toggle);
        _toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mecanico_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_actualizar) {
            String url = "https://serverpmi.tr3sco.net/Maya/Download";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.close_sesion) {
            alertLogOut();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void getWorkCenters() {

    }

    @Override
    public void onBadgeDefectoClick(WorkCenter workCenter, int position) {
        retrofitDefectosActivos(workCenter);
    }

    @Override
    public void onBadgeParoClick(WorkCenter workCenter, int position) {
        retrofitParosActivos(workCenter);
    }

    @Override
    public void onClickWorkCenter(WorkCenter workCenter) {

    }

    @Override
    public void onClickBusinessUnit(BussinesUnit bussinesUnit) {

    }

    @Override
    public boolean onLongClickWorkCenter(WorkCenter workCenter) {
        return false;
    }

    @Override
    public void onClickParo(Paro paro) {
        startParoActivity(paro);
    }

    @Override
    public void onClickDefecto(Defecto defecto, int position, View foto) {
        startDefectoActivity(defecto, foto);
    }


    private void startParoActivity(Paro paro) {
        Intent intent = new Intent(this, ParoActivity.class);

        intent.putExtra("puedeAsignarce", true);
        intent.putExtra("puedeCerrarse", false);
        intent.putExtra("puedeComentarce", true);


        intent.putExtra("nombreModulo", paro.getOrigen().getModulo().getNombreCorto());
        intent.putExtra("nombreWorkCenter", paro.getOrigen().getWorkCenter().getNombreCorto());
        intent.putExtra("idParo", paro.getId());
        startActivity(intent);


    }

    private void startDefectoActivity(Defecto defecto, View foto) {
        Intent intent = new Intent(this, DefectoActivity.class);
        intent.putExtra("idDefecto", defecto.getId());
        intent.putExtra("cerrarDefecto", false);
        intent.putExtra("asignarDefecto", true);

        if (defecto.getFotos() != null && defecto.getFotos().size() > 0) {
            String imagen = defecto.getFotos().get(0).getPath();
            intent.putExtra("path", "http://serverpmi.tr3sco.net" + imagen);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, foto, "portada");
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private void startBSParosActivos(WorkCenter workCenter, List<Paro> paros) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment prev = manager.findFragmentByTag("yourTag");

        if (prev != null) {
            ft.remove(prev);
        }

        newFragment = ParosActivosPorWorkCenterDialogFragment.newInstance(workCenter, paros);
        newFragment.show(ft, "yourTag");

    }

    private void startBSDefectosActivos(WorkCenter workCenter, List<Defecto> defectos) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment prev = manager.findFragmentByTag("yourTag");

        if (prev != null) {
            ft.remove(prev);
        }

        newFragment = DefectosActivosPorWorkCenterDialogFragment.newInstance(workCenter, defectos);
        newFragment.show(ft, "yourTag");

    }


    private void retrofitBussinesUnit() {

        mecanicoPorBusinessUnits.getBusinessUnitByMecanico(pref.getInt(OperadorPreference.ID_PERSONA_SHARED_PREF, 0)).enqueue(new Callback<BussinesUnit>() {
            @Override
            public void onResponse(@NonNull Call<BussinesUnit> call, @NonNull Response<BussinesUnit> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        bussinesUnit = response.body();
                        businessUnitFragment.bussinesUnitList.clear();
                        businessUnitFragment.bussinesUnitList.add(bussinesUnit);
                        businessUnitFragment.mAdapter.notifyDataSetChanged();
                    }


                } else {
                    messageDialog(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BussinesUnit> call, @NonNull Throwable t) {
                messageDialog(t.getMessage());
            }
        });

    }

    private void retrofitMisServicios() {
        mecanicoService.getMecanico(pref.getInt(OperadorPreference.ID_PERSONA_SHARED_PREF, 0)).enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(@NonNull Call<Persona> call, @NonNull Response<Persona> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        misServiciosFragment.paroList.clear();
                        misServiciosFragment.defectoList.clear();
                        Persona mecanico = response.body();


                        misServiciosFragment.defectoList.addAll(mecanico.getDefectosAsignados());
                        misServiciosFragment.paroList.addAll(mecanico.getParosAsignados());


                        misServiciosFragment.mAdapterDefectos.notifyDataSetChanged();
                        misServiciosFragment.mAdapterParos.notifyDataSetChanged();
                    }

                } else {
                    if(response.errorBody()!= null) messageDialog(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Persona> call, @NonNull Throwable t) {
                messageDialog(t.getMessage());
            }
        });

    }

    private void retrofitParosActivos(final WorkCenter workCenter) {
        paroService.getParosByWorkCenter(workCenter.getId(), true, 100).enqueue(new Callback<List<Paro>>() {
            @Override
            public void onResponse(@NonNull Call<List<Paro>> call, @NonNull Response<List<Paro>> response) {

                if (response.isSuccessful()) {
                    List<Paro> paros = response.body();

                    startBSParosActivos(workCenter, paros);

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Paro>> call, @NonNull Throwable t) {

            }
        });

    }

    private void retrofitDefectosActivos(final WorkCenter workCenter) {
        defectoService.getDefectosByWorkCenter(workCenter.getId(), true, -1000, 0, 100000)
                .enqueue(new Callback<List<Defecto>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Defecto>> call, @NonNull Response<List<Defecto>> response) {
                        if (response.isSuccessful()) {
                            List<Defecto> defectos = response.body();

                            startBSDefectosActivos(workCenter, defectos);

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Defecto>> call, @NonNull Throwable t) {

                    }
                });
    }

    private void startPerfilActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void messageDialog(String message) {
        Snackbar.make(findViewById(R.id.coordinatorMecanico), message, Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }).show();
    }


    private void alertLogOut() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Segurpde que quieres cerrar session?");
        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        remove();
                        logout();


                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void logout() {


        //Starting login activity
        Intent intent = new Intent(MecanicoActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }

    private void remove() {
        pref.edit().clear().apply();
    }


}

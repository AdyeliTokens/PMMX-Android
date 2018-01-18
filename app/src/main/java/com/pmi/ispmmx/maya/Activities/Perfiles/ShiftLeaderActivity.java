package com.pmi.ispmmx.maya.Activities.Perfiles;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pmi.ispmmx.maya.Activities.AboutMayaActivity;
import com.pmi.ispmmx.maya.Activities.AgregarDefectoActivity;
import com.pmi.ispmmx.maya.Activities.DefectoActivity;
import com.pmi.ispmmx.maya.Activities.LoginActivity;
import com.pmi.ispmmx.maya.Activities.Operaciones.OrigenActivity;
import com.pmi.ispmmx.maya.Activities.ParoActivity;
import com.pmi.ispmmx.maya.Activities.ParosActivity;
import com.pmi.ispmmx.maya.Activities.ProfileActivity;
import com.pmi.ispmmx.maya.CardPagerAdapter;
import com.pmi.ispmmx.maya.Utils.CircleTransform;
import com.pmi.ispmmx.maya.Activities.DefectosActivity;
import com.pmi.ispmmx.maya.DialogFragments.DefectoActivosPorOrigenDialogFragment;
import com.pmi.ispmmx.maya.DialogFragments.OrigenDialogFragment;
import com.pmi.ispmmx.maya.DialogFragments.ParosActivosPorOrigenDialogFragment;
import com.pmi.ispmmx.maya.Interfaces.IDefectoService;
import com.pmi.ispmmx.maya.Interfaces.IFotoService;
import com.pmi.ispmmx.maya.Interfaces.IParoService;
import com.pmi.ispmmx.maya.Interfaces.IShiftLeaderService;
import com.pmi.ispmmx.maya.Interfaces.IVQIService;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.Origen;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.ShadowTransformer;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FOTOS_PERSONAS;

public class ShiftLeaderActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, OrigenDialogFragment.OnInteractionListener,
        ParosActivosPorOrigenDialogFragment.OnInteractionListener,
        DefectoActivosPorOrigenDialogFragment.OnInteractionListener {

    private static final int PICTURE_FROM_CAMARA = 1000;
    private static final int MY_PERMISSIONS_REQUEST_CAMARA = 2000;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 3000;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 4000;

    private int idOrigen_Para_Foto;
    private ImageView _fotoActualizar;


    private Button mButton;
    private ViewPager mViewPager;
    private DrawerLayout _drawer;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private SharedPreferences pref;
    private Retrofit retrofit;
    private IShiftLeaderService shiftLeaderService;
    private IParoService paroService;
    private IDefectoService defectoService;
    private IFotoService fotoService;
    private IVQIService vqiService;

    private boolean mShowingFragments = false;
    private Toolbar _toolbar;
    private ImageView _imgUsuario;
    private TextView _navUsername;
    private TextView _navPosition;

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_leader);
        pref = getSharedPreferences(OperadorPreference.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        elementosUI();
        personalizarHeaderBar();


        mViewPager = (ViewPager) findViewById(R.id.viewPager);


        retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        shiftLeaderService = retrofit.create(IShiftLeaderService.class);
        paroService = retrofit.create(IParoService.class);
        defectoService = retrofit.create(IDefectoService.class);
        fotoService = retrofit.create(IFotoService.class);
        vqiService = retrofit.create(IVQIService.class);

        shiftLeaderService.getCelularByShiftLeader(pref.getInt(OperadorPreference.ID_PERSONA_SHARED_PREF, 0)).enqueue(new Callback<List<WorkCenter>>() {
            @Override
            public void onResponse(@NonNull Call<List<WorkCenter>> call, @NonNull Response<List<WorkCenter>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<WorkCenter> workCenterList = new ArrayList<>();
                        workCenterList = response.body();

                        mCardAdapter = new CardPagerAdapter(new CardPagerAdapter.OnItemClickListener() {
                            @Override
                            public void OnItemClick(WorkCenter workCenter, int position) {
                                Toast.makeText(getApplicationContext(), "OnItemClick", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void OnOrigenClick(Origen origen, int position) {
                                startBSOrigen(origen);
                            }

                            @Override
                            public boolean OnOrigenLongClick(Origen origen, int position) {
                                alertParo(origen);
                                return false;
                            }

                            @Override
                            public void OnBadgeDefectoClick(Origen origen, int position) {
                                retrofitDefectosActivos(origen);
                            }

                            @Override
                            public void OnBadgeParoClick(Origen origen, int position) {
                                retrofitParosActivos(origen);
                            }
                        });
                        for (WorkCenter workCenter : workCenterList) {
                            mCardAdapter.addCardItem(workCenter, getBaseContext());
                        }


                        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

                        mViewPager.setAdapter(mCardAdapter);
                        mViewPager.setPageTransformer(false, mCardShadowTransformer);
                        //mViewPager.setOffscreenPageLimit(3);
                        //mCardShadowTransformer.enableScaling(true);
                        //mFragmentCardShadowTransformer.enableScaling(true);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<WorkCenter>> call, @NonNull Throwable t) {

            }
        });


    }

    private void elementosUI() {
        _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);

        _drawer = findViewById(R.id.drawer_layout);
        NavigationView _navigationView = findViewById(R.id.nav_view);
        _navigationView.setNavigationItemSelectedListener(this);
        View _headerView = _navigationView.getHeaderView(0);

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

        _navUsername.setText(String.format("%s %s %s.", nombre, apellido1, apellido2));
        _navPosition.setText(puesto);

        menuAnimation();
    }

    private void menuAnimation() {
        ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(
                this, _drawer, _toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        _drawer.setDrawerListener(_toggle);
        _toggle.syncState();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
        } else if (id == R.id.item_configuracion) {
            startsettingsActivity();
        } else if (id == R.id.item_acerca_de_maya) {
            startAboutMayaActivity();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void startBSOrigen(Origen origen) {
        BottomSheetDialogFragment newFragment = OrigenDialogFragment.newInstance(origen);
        newFragment.show(getSupportFragmentManager(), "Prueba Chida+" +
                "");

    }

    private void startBSParosActivos(Origen origen, List<Paro> paros) {
        BottomSheetDialogFragment newFragment = ParosActivosPorOrigenDialogFragment.newInstance(origen, paros);
        newFragment.show(getSupportFragmentManager(), newFragment.getTag());

    }

    private void startBSDefectosActivos(Origen origen, List<Defecto> defectos) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment prev = manager.findFragmentByTag("yourTag");

        if (prev != null) {
            ft.remove(prev);
        }

        DefectoActivosPorOrigenDialogFragment newFragment = DefectoActivosPorOrigenDialogFragment.newInstance(origen, defectos);
        newFragment.show(ft, "yourTag");

    }

    private void startPerfilActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void startOrigenActivity(Origen origen) {
        Intent intent = new Intent(this, OrigenActivity.class);
        intent.putExtra("puedeAsignarce", false);
        intent.putExtra("puedeCerrarse", true);
        intent.putExtra("puedeComentarce", false);

        intent.putExtra("idOrigen", origen.getId());
        intent.putExtra("nombreModulo", origen.getModulo().getNombre());
        intent.putExtra("nombreWorkCenter", "");
        startActivity(intent);
    }

    private void startWorkCenterActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void startDefectosActivity() {
        Intent intent = new Intent(this, DefectosActivity.class);
        startActivity(intent);
    }

    private void startParosActivity() {
        Intent intent = new Intent(this, ParosActivity.class);
        startActivity(intent);
    }

    private void startAgregarDefectoActivity(Origen origen) {
        Intent intent = new Intent(this, AgregarDefectoActivity.class);
        intent.putExtra("idOrigen", origen.getId());
        intent.putExtra("nombreModulo", origen.getModulo().getNombre());
        startActivity(intent);
    }

    private void startParoActivity(Paro paro) {
        Intent intent = new Intent(this, ParoActivity.class);

        intent.putExtra("puedeAsignarce", false);
        intent.putExtra("puedeCerrarse", true);
        intent.putExtra("puedeComentarce", true);


        intent.putExtra("nombreModulo", paro.getOrigen().getModulo().getNombreCorto());
        intent.putExtra("nombreWorkCenter", paro.getOrigen().getWorkCenter().getNombreCorto());
        intent.putExtra("idParo", paro.getId());
        startActivity(intent);


    }


    private void startDefectoActivity(Defecto defecto, View foto) {
        Intent intent = new Intent(this, DefectoActivity.class);
        intent.putExtra("idDefecto", defecto.getId());
        intent.putExtra("cerrarDefecto", true);
        intent.putExtra("asignarDefecto", false);

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

    private void startAboutMayaActivity() {
        Intent intent = new Intent(this, AboutMayaActivity.class);
        startActivity(intent);
    }

    private void startsettingsActivity() {

    }

    private void ocultarBottomSheet() {
        //if (newFragment != null)
        //newFragment.dismiss();
    }

    private void retrofitPostParo(Paro paro) throws IOException {
        paroService.postParo(paro).enqueue(new Callback<Paro>() {
            @Override
            public void onResponse(@NonNull Call<Paro> call, @NonNull Response<Paro> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //getWorkCenters();

                    }
                } else {
                    messageDialog(response.errorBody().toString());
                }

            }

            @Override
            public void onFailure(@NonNull Call<Paro> call, @NonNull Throwable t) {
                messageDialog("Por favor!! Validatu conexion a internet");
            }
        });

    }

    private void retrofitParosActivos(final Origen origen) {
        paroService.getParosByOrigen(origen.getId(), true, 100).enqueue(new Callback<List<Paro>>() {
            @Override
            public void onResponse(@NonNull Call<List<Paro>> call, @NonNull Response<List<Paro>> response) {

                if (response.isSuccessful()) {
                    List<Paro> paros = response.body();

                    startBSParosActivos(origen, paros);

                } else {
                    messageDialog(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Paro>> call, @NonNull Throwable t) {
                messageDialog("Por favor!! Validatu conexion a internet");
            }
        });

    }

    private void retrofitDefectosActivos(final Origen origen) {
        defectoService.getDefectosByOrigen(origen.getId(), true, -1000, 0, 100000)
                .enqueue(new Callback<List<Defecto>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Defecto>> call, @NonNull Response<List<Defecto>> response) {
                        if (response.isSuccessful()) {
                            List<Defecto> defectos = response.body();

                            startBSDefectosActivos(origen, defectos);

                        } else {
                            messageDialog(response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Defecto>> call, @NonNull Throwable t) {
                        messageDialog("Por favor!! Validatu conexion a internet");
                    }
                });
    }


    private boolean CheckPermission(String permission) {
        int res = this.checkCallingOrSelfPermission(permission);
        return res == PackageManager.PERMISSION_GRANTED;

    }

    private void OlderVersions() {
        if (CheckPermission(Manifest.permission.CAMERA)) {

        }
        if (CheckPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        }
        if (CheckPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
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

    private void alertParo(final Origen origen) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Seguro que quieres reportar un paro en " + origen.getModulo().getNombre() + " ?");

        alertDialogBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Paro paro = new Paro();
                paro.setIdOrigen(origen.getId());
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

    private void messageDialog(String message) {
        Snackbar.make(findViewById(R.id.coordinator_operador), message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    private void logout() {


        //Starting login activity
        Intent intent = new Intent(ShiftLeaderActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }

    private void remove() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            pref.edit().clear().apply();
        }
    }

    @Override
    public void onClickAgregarFalla(Origen origen) {
        ocultarBottomSheet();
        alertParo(origen);

    }

    @Override
    public void onClickAgregarDefecto(Origen origen) {
        ocultarBottomSheet();
        startAgregarDefectoActivity(origen);

    }

    @Override
    public void onClickImagenOrigen(Origen origen) {
        ocultarBottomSheet();
        startOrigenActivity(origen);

    }

    @Override
    public void onClickCamaraOrigen(Origen origen, ImageView image) {
        _fotoActualizar = image;
        idOrigen_Para_Foto = origen.getId();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }
                    , MY_PERMISSIONS_REQUEST_CAMARA);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, PICTURE_FROM_CAMARA);
            }
        }
    }

    @Override
    public void OnClickDefectosActivos(Origen origen) {
        startDefectosActivity();
    }

    @Override
    public void OnClickDefectosMecanicosPrincipal(Origen origen) {

    }

    @Override
    public void OnClickDefectosElectricosPrincipal(Origen origen) {

    }

    @Override
    public void OnClicFallasMecanicosPrincipal(Origen origen) {

    }

    @Override
    public void OnClickFallasElectricosPrincipal(Origen origen) {

    }

    @Override
    public void onClickParosActivos(Origen origen) {
        startParosActivity();
    }

    @Override
    public void onClickVerDetalle(Origen origen) {

    }


    @Override
    public void onClickParo(Paro paro) {
        startParoActivity(paro);
    }

    @Override
    public void onClickDefecto(Defecto defecto, int position, View foto) {
        startDefectoActivity(defecto, foto);
    }

    @Override
    public void OnClickAgregarDefecto(Origen origen) {
        startAgregarDefectoActivity(origen);
    }

}

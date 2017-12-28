package com.pmi.ispmmx.maya.Activities.Perfiles;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmi.ispmmx.maya.Activities.AboutMayaActivity;
import com.pmi.ispmmx.maya.Activities.AgregarDefectoActivity;
import com.pmi.ispmmx.maya.Activities.DefectoActivity;
import com.pmi.ispmmx.maya.Activities.LoginActivity;
import com.pmi.ispmmx.maya.Activities.ParoActivity;
import com.pmi.ispmmx.maya.Activities.PhotoActivity;
import com.pmi.ispmmx.maya.Activities.VQIActivity;
import com.pmi.ispmmx.maya.Adapters.Pages.OperadorPagerAdapter;
import com.pmi.ispmmx.maya.CircleTransform;
import com.pmi.ispmmx.maya.DefectosActivity;
import com.pmi.ispmmx.maya.DialogFragments.DefectoActivosPorOrigenDialogFragment;
import com.pmi.ispmmx.maya.DialogFragments.OrigenDialogFragment;
import com.pmi.ispmmx.maya.DialogFragments.ParosActivosPorOrigenDialogFragment;
import com.pmi.ispmmx.maya.Fragments.IndicadoresFragment;
import com.pmi.ispmmx.maya.Fragments.LookBookFragment;
import com.pmi.ispmmx.maya.Fragments.WorkCenterFragment;
import com.pmi.ispmmx.maya.Interfaces.IDefectoService;
import com.pmi.ispmmx.maya.Interfaces.IFotoService;
import com.pmi.ispmmx.maya.Interfaces.IOperadoresPorWorkCenterService;
import com.pmi.ispmmx.maya.Interfaces.IParoService;
import com.pmi.ispmmx.maya.Interfaces.IVQIService;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.Origen;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;
import com.pmi.ispmmx.maya.Modelos.Entidades.VQI;
import com.pmi.ispmmx.maya.Activities.ParosActivity;
import com.pmi.ispmmx.maya.Activities.ProfileActivity;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FOTOS_ORIGENES;
import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FOTOS_PERSONAS;

public class OperadorActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        WorkCenterFragment.OnInteractionListener,
        OrigenDialogFragment.OnInteractionListener,
        ParosActivosPorOrigenDialogFragment.OnInteractionListener,
        DefectoActivosPorOrigenDialogFragment.OnInteractionListener,
        IndicadoresFragment.OnInteractionListener,
        LookBookFragment.OnFragmentInteractionListener {

    private static final int PICTURE_FROM_CAMARA = 1000;
    private static final int MY_PERMISSIONS_REQUEST_CAMARA = 2000;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 3000;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 4000;

    private int idOrigen_Para_Foto;
    private ImageView _fotoActualizar;

    private SharedPreferences pref;
    private Retrofit retrofit;

    private WorkCenterFragment workCenterFragment;
    private IndicadoresFragment indicadoresFragment;
    private LookBookFragment lookBookFragment;

    private Toolbar _toolbar;
    private TabLayout _tabLayout;
    private DrawerLayout _drawer;
    private TextView _navUsername;
    private TextView _navPosition;
    private ViewPager _viewPager;
    private ImageView _imgUsuario;


    ///Servicios
    private IOperadoresPorWorkCenterService operadoresPorWorkCenter;
    private IParoService paroService;
    private IDefectoService defectoService;
    private IFotoService fotoService;
    private IVQIService vqiService;

    private WorkCenter workCenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operador);
        pref = getSharedPreferences(OperadorPreference.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        elementosUI();
        personalizarHeaderBar();


        inicializarEntidades();
        iniciarRetrofit();
        createServicios();
        createFragments();
        generarTabs();



    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void inicializarEntidades() {
        workCenter = new WorkCenter();


    }

    private void iniciarRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void createServicios() {
        operadoresPorWorkCenter = retrofit.create(IOperadoresPorWorkCenterService.class);
        paroService = retrofit.create(IParoService.class);
        defectoService = retrofit.create(IDefectoService.class);
        fotoService = retrofit.create(IFotoService.class);
        vqiService = retrofit.create(IVQIService.class);
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


    private void createFragments() {
        workCenterFragment = new WorkCenterFragment();
        workCenterFragment.workCenterList = new ArrayList<>();
        workCenterFragment.iniciarAdapter();

        indicadoresFragment = new IndicadoresFragment();

        lookBookFragment = new LookBookFragment();
    }

    private void generarTabs() {

        _tabLayout.addTab(_tabLayout.newTab().setText("Feed"));
        _tabLayout.addTab(_tabLayout.newTab().setText("Indicadores"));
        _tabLayout.addTab(_tabLayout.newTab().setText("Mantenimiento"));


        _tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        OperadorPagerAdapter operadorPagerAdapter = new OperadorPagerAdapter(getSupportFragmentManager(), _tabLayout.getTabCount(), lookBookFragment, indicadoresFragment, workCenterFragment);

        _viewPager.setAdapter(operadorPagerAdapter);
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

    @Override
    public void refreshWorkCenters() {
        retrofiCallWorkCenters();
    }

    @Override
    public void getWorkCenters() {
        retrofiCallWorkCenters();
    }

    @Override
    public boolean onLongClickOrigen(Origen origen) {
        alertParo(origen);
        return true;
    }

    @Override
    public void onBadgeDefectoClick(Origen origen, int position) {
        retrofitDefectosActivos(origen);
    }

    @Override
    public void onBadgeParoClick(Origen origen, int position) {
        retrofitParosActivos(origen);
    }

    @Override
    public void onClickOrigen(Origen origen) {
        startBSOrigen(origen);

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
    public void onClickParosActivos(Origen origen) {
        startParosActivity();
    }

    @Override
    public void onClickWorkCenter(WorkCenter workCenter) {
        ocultarBottomSheet();
        startWorkCenterActivity();

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

    @Override
    public void onClickVQI() {
        startVQIActivity();
    }

    @Override
    public void onClickCPQI() {
        messageDialog("CPQI en contruccion!! Regresa luego.");
    }

    @Override
    public void onClickPLAN() {
        messageDialog("PLAN ATTAINMENT en contruccion!! Regresa luego.");
    }

    @Override
    public void onClickCRR() {
        messageDialog("CRR en contruccion!! Regresa luego.");
    }

    @Override
    public void hideViews() {
        _toolbar.animate().translationY(-_toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    @Override
    public void showViews() {
        _toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICTURE_FROM_CAMARA:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    updateImagenOrigen(uri);

                }


                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:


                break;
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:


                break;
            case MY_PERMISSIONS_REQUEST_CAMARA:
                String permission_Camara = permissions[0];
                int result_Camara = grantResults[0];
                if (permission_Camara.equals(Manifest.permission.CAMERA)) {
                    if (result_Camara == PackageManager.PERMISSION_GRANTED) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, PICTURE_FROM_CAMARA);
                        }
                    }
                }

                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in;
        try {
            final int IMAGE_MAX_SIZE = 1000000; // 1.2MP
            in = getContentResolver().openInputStream(uri);

            // Decode imgWorkCenter size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            if (in != null) {
                in.close();
            }


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d("", "scale = " + scale + ", orig-width: " + o.outWidth + ", orig-height: " + o.outHeight);

            Bitmap b;
            in = getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an imgWorkCenter
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d("", "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            if (in != null) {
                in.close();
            }

            Log.d("", "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight());
            return b;
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
            return null;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private void updateImagenOrigen(Uri uri) {

        Bitmap elBitmap = getBitmap(getRealPathFromURI(uri));
        Uri tempUri = getImageUri(getApplicationContext(), elBitmap);
        File finalFile = new File(getRealPathFromURI(tempUri));
        retrofitUpdateImageOrigen(finalFile);

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
        /*Intent intent = new Intent(this, OrigenActivity.class);
        intent.putExtra("puedeAsignarce", false);
        intent.putExtra("puedeCerrarse", true);
        intent.putExtra("puedeComentarce", false);

        intent.putExtra("idOrigen", origen.getId());
        intent.putExtra("nombreModulo", origen.getModulo().getNombre());
        intent.putExtra("nombreWorkCenter", "");
        startActivity(intent);*/

        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("idOrigen", origen.getId());
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

    private void startVQIActivity() {
        Intent intent = new Intent(this, VQIActivity.class);
        intent.putExtra("idWorkCenter", workCenter.getId());
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

    private void retrofiCallWorkCenters() {
        operadoresPorWorkCenter.getModulosByWorkCenter(pref.getInt(OperadorPreference.ID_PERSONA_SHARED_PREF, 0)).enqueue(new Callback<WorkCenter>() {
            @Override
            public void onResponse(@NonNull Call<WorkCenter> call, @NonNull Response<WorkCenter> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        workCenter = response.body();
                        retrofiCallVQI();
                        workCenterFragment.llenarInformacion(response.body());
                    }
                } else {
                    messageDialog(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<WorkCenter> call, @NonNull Throwable t) {
                messageDialog("Por favor!! Validatu conexion a internet");
            }
        });
    }

    private void retrofiCallVQI() {

        vqiService.getVQIByWorkCenter(workCenter.getId()).enqueue(new Callback<List<VQI>>() {
            @Override
            public void onResponse(@NonNull Call<List<VQI>> call, @NonNull Response<List<VQI>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        indicadoresFragment.llenarInformacion_VQI(response.body());
                    }
                } else {
                    messageDialog(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<VQI>> call, @NonNull Throwable t) {
                messageDialog("Por favor!! Validatu conexion a internet");
            }
        });
    }

    private void retrofitPostParo(Paro paro) throws IOException {
        paroService.postParo(paro).enqueue(new Callback<Paro>() {
            @Override
            public void onResponse(@NonNull Call<Paro> call, @NonNull Response<Paro> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        getWorkCenters();

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

    private void retrofitUpdateImageOrigen(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        fotoService.postFotoOrigen(body, idOrigen_Para_Foto).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Picasso.with(getBaseContext())
                        .load(URL_FOTOS_ORIGENES + idOrigen_Para_Foto)
                        .error(R.drawable.ic_error_outline_gray)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(_fotoActualizar);

                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

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
        Intent intent = new Intent(OperadorActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }

    private void remove() {
        pref.edit().clear().apply();
    }


}

package com.pmi.ispmmx.maya.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pmi.ispmmx.maya.BuildConfig;
import com.pmi.ispmmx.maya.Interfaces.IMAYAService;
import com.pmi.ispmmx.maya.Modelos.Infopdate;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;

import java.io.File;
import java.net.MalformedURLException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FILE;

public class SplashActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 3000;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 4000;


    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private SharedPreferences pref;

    private IMAYAService imayaService;

    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            return -1;
        }
    }

    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            return "";
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getSharedPreferences(OperadorPreference.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        iniciarRetrofit();
        CheckInfoUpdate();

    }

    private void iniciarRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        imayaService = retrofit.create(IMAYAService.class);
    }

    private void CheckInfoUpdate() {
        imayaService.getInfo().enqueue(new Callback<Infopdate>() {
            @Override
            public void onResponse(@NonNull Call<Infopdate> call, @NonNull Response<Infopdate> response) {
                if (response.isSuccessful()) {
                    int versionCode = getVersionCode(getApplicationContext());
                    if (response.body().getVersionCode() <= versionCode) {
                        Toast.makeText(getApplicationContext(), "Aplicacion al dia!! :D", Toast.LENGTH_LONG).show();
                        if (pref.getBoolean(OperadorPreference.LOGGEDIN_SHARED_PREF, false)) {
                            redireccionar(pref.getString(OperadorPreference.NOMBRE_ENTORNO_SHARED_PREF, ""));
                        } else {
                            logout();
                        }
                    } else if (response.body().getVersionCode() > versionCode) {
                        alertDownload();

                    }


                } else {
                    Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                    logout();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Infopdate> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void downloadUpdates() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{

                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        }
                        , MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            } else {
                try {
                    descarga_Instala();
                } catch (MalformedURLException e) {
                    Toast.makeText(getApplicationContext(), "Update Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Update Error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();


        }
    }

    private void alertDownload() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Existe una nueva version. \n ¿Quieres descargarla?");
        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        downloadUpdates();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (pref.getBoolean(OperadorPreference.LOGGEDIN_SHARED_PREF, false)) {
                            redireccionar(pref.getString(OperadorPreference.NOMBRE_ENTORNO_SHARED_PREF, ""));
                        } else {
                            logout();
                        }
                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void redireccionar(String entorno) {
        Class<?> c;
        if (entorno != null) {
            try {
                if (!entorno.equals("Login")) {
                    entorno = "Perfiles." + entorno;
                }
                c = Class.forName("com.pmi.ispmmx.maya.Activities." + entorno + "Activity");
                Intent intent = new Intent(SplashActivity.this, c);
                //intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                logout();
            }
        }


    }


    private void logout() {

        SharedPreferences.Editor editor = pref.edit();
        editor.clear().apply();
        redireccionar("Login");

    }

    private void descarga_Instala() throws MalformedURLException {

        String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        String fileName = "Maya.apk";
        destination += fileName;
        final Uri uri = Uri.parse("file://" + destination);


        File file = new File(destination);
        if (file.exists())
            file.delete();


        String url = URL_FILE;
        //set downloadmanager
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Actualizacion en progreso");
        request.setTitle(getResources().getString(R.string.app_name));

        //set destination
        request.setDestinationUri(uri);

        // get download service and enqueue file
        final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        final long downloadId = manager.enqueue(request);

        //set BroadcastReceiver to install app when .apk is downloaded
        final String finalDestination = destination;
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {

                Uri uris = FileProvider.getUriForFile(ctxt,
                        BuildConfig.APPLICATION_ID + ".provider",
                        new File(finalDestination));


                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                install.setDataAndType(uris, "application/vnd.android.package-archive");
                //install.setDataAndType(uris, manager.getMimeTypeForDownloadedFile(downloadId));
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(install);

                unregisterReceiver(this);
                finish();
            }
        };
        //register receiver for when .apk download is compete
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                String permission_Write = permissions[0];
                int result_write = grantResults[0];
                if (permission_Write.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (result_write == PackageManager.PERMISSION_GRANTED) {
                        try {
                            descarga_Instala();
                        } catch (MalformedURLException e) {
                            Toast.makeText(getApplicationContext(), "Update Error: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }


                break;
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:


                break;


            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

}

package com.pmi.ispmmx.maya.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FILE;

public class SplashActivity extends AppCompatActivity {
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private SharedPreferences pref;
    private ProgressDialog bar;

    private IMAYAService imayaService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set portrait orientation
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //
        // setContentView(R.drawable.splash);

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
                    downloadUpdates();

                } else {

                }


            }

            @Override
            public void onFailure(@NonNull Call<Infopdate> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException ex) {}
        return 0;
    }

    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException ex) {}
        return "";
    }


    private void downloadUpdates() {
        new DownloadNewVersion().execute();
    }


    private void redireccionar(String entorno) {
        Class<?> c = null;
        if (entorno != null) {
            try {
                if (entorno != "Login") {
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

    void OpenNewVersion(String location) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(SplashActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                new File(location + "Maya.apk"));

        intent.setDataAndType(uri,
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);



    }

    class DownloadNewVersion extends AsyncTask<String, Integer, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bar = new ProgressDialog(SplashActivity.this);
            bar.setCancelable(false);

            bar.setMessage("Downloading...");

            bar.setIndeterminate(true);
            bar.setCanceledOnTouchOutside(false);
            bar.show();

        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

            bar.setIndeterminate(false);
            bar.setMax(100);
            bar.setProgress(progress[0]);
            String msg = "";
            if (progress[0] > 99) {

                msg = "Finishing... ";

            } else {

                msg = "Downloading... " + progress[0] + "%";
            }
            bar.setMessage(msg);

        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            bar.dismiss();

            if (result) {

                Toast.makeText(getApplicationContext(), "Update Done",
                        Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getApplicationContext(), "Error: Try Again",
                        Toast.LENGTH_SHORT).show();

            }

        }


        @Override
        protected Boolean doInBackground(String... arg0) {
            Boolean flag = false;

            try {


                URL url = new URL(URL_FILE);


                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();


                String PATH = Environment.getExternalStorageDirectory()+"/Download/";
                File file = new File(PATH);
                file.mkdirs();

                File outputFile = new File(file,"Maya.apk");
                if(outputFile.exists()){
                    outputFile.delete();
                }


                InputStream is = c.getInputStream();

                int total_size = 111440303;//size of apk

                byte[] buffer = new byte[1024];
                int len1 = 0;
                int per = 0;
                int downloaded=0;

                FileOutputStream fos = new FileOutputStream(outputFile);

                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                    downloaded +=len1;
                    per = (int) (downloaded * 100 / total_size);
                    publishProgress(per);
                }
                fos.close();
                is.close();

                OpenNewVersion(PATH);

                flag = true;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Update Error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();

                flag = false;
            }
            return flag;


        }

    }
}

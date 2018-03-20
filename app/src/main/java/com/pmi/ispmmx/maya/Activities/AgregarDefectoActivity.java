package com.pmi.ispmmx.maya.Activities;

import android.Manifest;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pmi.ispmmx.maya.BuildConfig;
import com.pmi.ispmmx.maya.Interfaces.IDefectoService;
import com.pmi.ispmmx.maya.Interfaces.IFotoService;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;
import com.pmi.ispmmx.maya.Utils.DrawingView;
import com.pmi.ispmmx.maya.Utils.Respuesta.RespuestaServicio;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AgregarDefectoActivity extends AppCompatActivity {
    static final int PICTURE_FROM_CAMARA = 1000;
    private static final int MY_PERMISSIONS_REQUEST_CAMARA = 10;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 20;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 30;
    private static final int MY_PERMISSIONS_REQUEST_MICRO = 40;
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 2000;
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public String photoFileName = "defecto.jpg";
    int id = 1;
    File photoFile;

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    private SharedPreferences pref;
    private ProgressDialog progressDialog;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView _imgDefecto;
    private ImageView _microfono;
    private EditText _txtDescripcion;
    private FloatingActionButton _fab;
    private String nombreModulo;
    private Toolbar toolbar;
    private SeekBar seekBar;
    private int idOrigen;
    private int prioridad;
    ///////Retrofit
    private Retrofit retrofit;
    ////Servicios
    private IFotoService fotoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_defecto);
        elementosUI();
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        crearEventos();
        createRetrofit();

        pref = getSharedPreferences(OperadorPreference.SHARED_PREF_NAME,
                Context.MODE_PRIVATE);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        if (bundle != null) {
            idOrigen = (int) bundle.get("idOrigen");
            nombreModulo = (String) bundle.get("nombreModulo");

        }


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

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), "");

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
                Log.d("", "failed to create directory");
            }

            // Return the file target for the photo based on filename
            File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

            return file;
        }
        return null;
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private void elementosUI() {

        _txtDescripcion = findViewById(R.id.txtDescripcion);
        _imgDefecto = findViewById(R.id.image_defecto);
        progressDialog = new ProgressDialog(AgregarDefectoActivity.this, R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Subiendo defecto");
        seekBar = findViewById(R.id.seekBar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.ic_edit);
        getSupportActionBar().setTitle("Tranquilo, Puede esperar");


        _fab = findViewById(R.id.fab_Enviar);
        _microfono = findViewById(R.id.img_btn_hablar);
    }

    private void crearEventos() {
        _imgDefecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                            }
                            , MY_PERMISSIONS_REQUEST_CAMARA);
                } else {
                    tomarFoto();
                }

            }
        });
        _microfono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{
                                    Manifest.permission.RECORD_AUDIO
                            }
                            , MY_PERMISSIONS_REQUEST_MICRO);
                } else {
                    OlderVersions();
                }
            }
        });

        _fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog(true);
                messageDialog("Subiendo imagen");
                try {
                    agregarDefecto();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        _txtDescripcion.addTextChangedListener(new TextWatcher() {
                                                   public void afterTextChanged(Editable s) {
                                                   }

                                                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                                                   }

                                                   public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                       if (_txtDescripcion.getText().length() > 0) {
                                                           _fab.setEnabled(true);

                                                           _fab.setImageResource(R.drawable.ic_send_white);
                                                       } else {
                                                           _fab.setEnabled(false);
                                                           _fab.setImageResource(R.drawable.ic_edit);
                                                       }
                                                   }
                                               }
        );
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i == 0) {
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    toolbar.setTitle("Tranquilo, Puede esperar");
                    toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
                    prioridad = 1;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        seekBar.setThumbTintList(getResources().getColorStateList(R.color.colorPrimary));
                        seekBar.setProgressBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));

                    }
                } else if (i == 1) {
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                    toolbar.setTitle("Advertencia");
                    toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryText));
                    prioridad = 2;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        seekBar.setThumbTintList(getResources().getColorStateList(R.color.colorYellow));
                        seekBar.setProgressTintList(getResources().getColorStateList(R.color.colorYellow));
                    }
                } else if (i == 2) {
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorRedScale7));
                    toolbar.setTitle("Urgente!!");
                    prioridad = 3;
                    toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        seekBar.setThumbTintList(getResources().getColorStateList(R.color.colorRedScale7));
                        seekBar.setProgressTintList(getResources().getColorStateList(R.color.colorRedScale7));
                    }
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        fotoService = retrofit.create(IFotoService.class);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICTURE_FROM_CAMARA:
                if (resultCode == RESULT_OK) {


                    //Uri uri = data.getData();
                    //Bitmap photo = (Bitmap) data.getExtras().get("data");

                    //Bitmap elBitmap = getBitmap(mCurrentPhotoPath);
                    //Uri tempUri = getImageUri(getApplicationContext(), elBitmap);


                    //File finalFile = new File(getRealPathFromURI(tempUri));
                    //File finalFile = new File(mCurrentPhotoPath);


                    //if (laFoto.exists()) {
                    //fp=file.getAbsolutePath();

                    //} else {
                    //    System.out.println("File Not Found");
                    //}
                    // mDrawingView=new DrawingView(this);
                    // mDrawingPad.addView(mDrawingView);
                    //Drawable d = Drawable.createFromPath(finalFile.getAbsolutePath());
                    //mDrawingPad.setBackgroundDrawable(d);

                    DrawingView mDrawingView = new DrawingView(this);
                    LinearLayout mDrawingPad = findViewById(R.id.view_drawing_pad);
                    //LinearLayout draw=(LinearLayout)findViewById(R.id.imagechida);
                    mDrawingPad.addView(mDrawingView);
                    Bitmap elbit = setPic();
                    Drawable drawable = new BitmapDrawable(getResources(), elbit);
                    //Drawable d = Drawable.createFromPath(mCurrentPhotoPath);
                    mDrawingPad.setBackgroundDrawable(drawable);

                    //imgPhoto.setImageBitmap(bitmap);


                    /*String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;



                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }*/


                }


                break;
            case RECOGNIZE_SPEECH_ACTIVITY:

                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> speech = data
                            .getStringArrayListExtra(RecognizerIntent.
                                    EXTRA_RESULTS);
                    String strSpeech2Text = speech.get(0);

                    _txtDescripcion.setText(strSpeech2Text);
                }

                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }


    }

    public void tomarFoto(){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(AgregarDefectoActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, PICTURE_FROM_CAMARA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                String permission_Write = permissions[0];
                int result = grantResults[0];

                if (permission_Write.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (result == PackageManager.PERMISSION_GRANTED) {

                    } else {


                    }
                }

                break;
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                String permission_Read = permissions[0];
                int result_Read = grantResults[0];

                if (permission_Read.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (result_Read == PackageManager.PERMISSION_GRANTED) {

                    } else {


                    }
                }

                break;
            case MY_PERMISSIONS_REQUEST_CAMARA:
                String permission_Camara = permissions[0];
                int result_Camara = grantResults[0];

                if (permission_Camara.equals(Manifest.permission.CAMERA)) {
                    if (result_Camara == PackageManager.PERMISSION_GRANTED) {
                        tomarFoto();
                    } else {


                    }
                }


                break;
            case MY_PERMISSIONS_REQUEST_MICRO:
                String permission_Mic = permissions[0];
                int result_Mic = grantResults[0];

                if (permission_Mic.equals(Manifest.permission.RECORD_AUDIO)) {
                    if (result_Mic == PackageManager.PERMISSION_GRANTED) {
                        Intent intentActionRecognizeSpeech = new Intent(
                                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                        // Configura el Lenguaje (Español-México)
                        intentActionRecognizeSpeech.putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
                        try {
                            startActivityForResult(intentActionRecognizeSpeech,
                                    RECOGNIZE_SPEECH_ACTIVITY);
                        } catch (ActivityNotFoundException a) {
                            Toast.makeText(getApplicationContext(),
                                    "Tú dispositivo no soporta el reconocimiento por voz", Toast.LENGTH_SHORT).show();
                        }

                    } else {


                    }
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private Bitmap setPic() {
        LinearLayout mDrawingPad = findViewById(R.id.view_drawing_pad);
        // Get the dimensions of the View
        int targetW = mDrawingPad.getWidth();
        int targetH = mDrawingPad.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        //mImageView.setImageBitmap(bitmap);
    }

    private void crearImagen(int IdDefecto) {
        LinearLayout draw = findViewById(R.id.imagechida);
        View layouttobitmap = draw;
        Bitmap bitmap = Bitmap.createBitmap(layouttobitmap.getWidth(), layouttobitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        layouttobitmap.draw(canvas);
        try {
            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/file.png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.close();
            mandarImagen(new File(Environment.getExternalStorageDirectory() + "/file.png"), IdDefecto);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mandarImagen(File file, int DefectoId) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        //RequestBody idDefecto = RequestBody.create(MediaType.parse("text/plain"), ""+DefectoId);
        fotoService.postFotoDefecto(body, DefectoId).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    progressDialog(false);
                    messageDialog("Se subio la imagen correctamente");
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t.getMessage() == "") {
                    progressDialog(false);
                    messageDialog("Error al subir Imagen");
                }
            }
        });

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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1000000; // 1.2MP
            in = getContentResolver().openInputStream(uri);

            // Decode imgWorkCenter size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d("", "scale = " + scale + ", orig-width: " + o.outWidth + ", orig-height: " + o.outHeight);

            Bitmap b = null;
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
            in.close();

            Log.d("", "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight());
            return b;
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
            return null;
        }
    }

    private void agregarDefecto() throws IOException {
        Defecto defecto = new Defecto();

        defecto.setIdOrigen(idOrigen);
        defecto.setIdReportador(pref.getInt(OperadorPreference.ID_PERSONA_SHARED_PREF, 0));
        defecto.setDescripcion(_txtDescripcion.getText().toString());
        if (prioridad == 0) {
            defecto.setPrioridad(1);
        } else {
            defecto.setPrioridad(prioridad);
        }


        Retrofit retrofitAdapter = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HostPreference.URL_BASE)
                .build();

        final IDefectoService defectoServicio = retrofitAdapter.create(IDefectoService.class);
        defectoServicio.postDefecto(defecto).enqueue(new Callback<RespuestaServicio<Defecto>>() {
            @Override
            public void onResponse(Call<RespuestaServicio<Defecto>> call, Response<RespuestaServicio<Defecto>> response) {
                if (response.isSuccessful()) {
                    RespuestaServicio<Defecto> respuesta = response.body();
                    if (respuesta.getEjecucionCorrecta()) {
                        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mBuilder = new NotificationCompat.Builder(AgregarDefectoActivity.this);
                        mBuilder.setContentTitle("Download")
                                .setContentText("Download in progress")
                                .setSmallIcon(R.drawable.ic_flash_off_white_24dp);

                        new Downloader().execute();
                        crearImagen(respuesta.getRespuesta().getId());
                    } else {
                        messageDialog(respuesta.getMensaje());
                    }
                } else {
                    messageDialog(response.errorBody().byteStream().toString());
                }


            }

            @Override
            public void onFailure(Call<RespuestaServicio<Defecto>> call, Throwable t) {

            }
        });
    }

    private void progressDialog(boolean visible) {
        if (visible) {
            _fab.setEnabled(true);
            progressDialog.hide();
        } else {
            _fab.setEnabled(false);
            progressDialog.show();
        }
    }

    private void messageDialog(String message) {
        _fab.setEnabled(true);
        progressDialog.hide();
        Snackbar.make(findViewById(R.id.constraintLayout), message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    private class Downloader extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Displays the progress bar for the first time.
            mBuilder.setProgress(100, 0, false);
            mNotifyManager.notify(id, mBuilder.build());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Update progress
            mBuilder.setProgress(100, values[0], false);
            mNotifyManager.notify(id, mBuilder.build());
            super.onProgressUpdate(values);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int i;
            for (i = 0; i <= 100; i += 5) {
                // Sets the progress indicator completion percentage
                publishProgress(Math.min(i, 100));
                try {
                    // Sleep for 5 seconds
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    Log.d("TAG", "sleep failure");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            mBuilder.setContentText("Download complete");
            // Removes the progress bar
            mBuilder.setProgress(0, 0, false);
            mNotifyManager.notify(id, mBuilder.build());
        }
    }

}

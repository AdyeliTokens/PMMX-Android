package com.pmi.ispmmx.maya.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.pmi.ispmmx.maya.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FOTOS_DEFECTOS;
import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FOTOS_ORIGENES;
import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FOTOS_PERSONAS;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        PhotoView photoView = findViewById(R.id.photo_view);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            try {
                int idOrigen = (int) bundle.get("idOrigen");
                Picasso.with(getBaseContext())
                        .load(URL_FOTOS_ORIGENES + idOrigen)
                        .error(R.drawable.ic_error_outline_gray)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(photoView);
            } catch (Exception e) {

            }

            try {
                int idDefecto = (int) bundle.get("idDefecto");
                Picasso.with(getBaseContext())
                        .load(URL_FOTOS_DEFECTOS + idDefecto)
                        .error(R.drawable.ic_error_outline_gray)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(photoView);
            } catch (Exception e) {

            }

            try {
                int idPersona = (int) bundle.get("idPersona");
                Picasso.with(getBaseContext())
                        .load(URL_FOTOS_PERSONAS + idPersona)
                        .error(R.drawable.ic_error_outline_gray)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(photoView);
            } catch (Exception e) {

            }



        }
    }
}

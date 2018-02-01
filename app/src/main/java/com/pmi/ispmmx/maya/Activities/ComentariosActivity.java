package com.pmi.ispmmx.maya.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pmi.ispmmx.maya.Adapters.Pages.ComentariosPagerAdapter;
import com.pmi.ispmmx.maya.Fragments.ComentariosFragment;
import com.pmi.ispmmx.maya.Interfaces.IComentariosService;
import com.pmi.ispmmx.maya.Modelos.Entidades.Comentario;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComentariosActivity extends AppCompatActivity implements ComentariosFragment.OnInteractionListener {
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private ComentariosFragment comentariosFragment;
    private SharedPreferences pref;

    private Retrofit retrofit;
    private IComentariosService comentariosService;

    private ViewPager _viewPager;
    private TabLayout _tabLayout;
    private Toolbar _toolbar;
    private int idDefecto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        pref = getSharedPreferences(OperadorPreference.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getIntents();
        elementosUI();
        instanciarFragments();
        iniciarRetrofit();
        createTabLayout();


        retrofitGetComentarios();
    }


    private void elementosUI() {
        _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _viewPager = findViewById(R.id.viewPager);
        _tabLayout = findViewById(R.id.tabLayout);
        _tabLayout.setVisibility(View.GONE);

    }

    private void iniciarRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        comentariosService = retrofit.create(IComentariosService.class);
    }

    private void getIntents() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            idDefecto = (int) bundle.get("idDefecto");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void instanciarFragments() {
        comentariosFragment = new ComentariosFragment();
        comentariosFragment.idPersona = pref.getInt(OperadorPreference.ID_PERSONA_SHARED_PREF, 0);
        comentariosFragment.comentarioList = new ArrayList<Comentario>();
        comentariosFragment.iniciarAdapter();
    }

    private void createTabLayout() {

        _tabLayout.addTab(_tabLayout.newTab().setText("Comentarios"));
        _tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        ComentariosPagerAdapter comentariosPagerAdapter = new ComentariosPagerAdapter(getSupportFragmentManager(), _tabLayout.getTabCount(), comentariosFragment);
        _viewPager.setAdapter(comentariosPagerAdapter);
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


    private void retrofitGetComentarios() {
        comentariosService.getComentariosByDefecto(idDefecto).enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    comentariosFragment.comentarioList.clear();
                    for (Comentario comentario : response.body()) {
                        comentariosFragment.comentarioList.add(comentario);
                        comentariosFragment.mAdapter.notifyDataSetChanged();
                        comentariosFragment.bajarscroll();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {

            }
        });

    }

    private void retrofitPostComentarios(Comentario comentario) {
        comentariosService.postComentario(comentario).enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    comentariosFragment.comentarioList.clear();
                    for (Comentario comentario : response.body()) {
                        comentariosFragment.comentarioList.add(comentario);
                        comentariosFragment.mAdapter.notifyDataSetChanged();
                        comentariosFragment.bajarscroll();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {

            }
        });

    }


    @Override
    public void OnClickEnviarComentario(Comentario comentario) {
        comentario.setIdComentador(pref.getInt(OperadorPreference.ID_PERSONA_SHARED_PREF, 0));
        comentario.setIdDefecto(idDefecto);
        retrofitPostComentarios(comentario);
    }
}

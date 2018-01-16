package com.pmi.ispmmx.maya.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pmi.ispmmx.maya.DialogFragments.EntornosDialogFragment;
import com.pmi.ispmmx.maya.Interfaces.ILoginService;
import com.pmi.ispmmx.maya.Modelos.Entidades.Entorno;
import com.pmi.ispmmx.maya.Modelos.Entidades.User;
import com.pmi.ispmmx.maya.Modelos.LoginViewModel;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Respuesta.RespuestaServicio;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class LoginActivity extends AppCompatActivity implements EntornosDialogFragment.Listener {

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private EditText _emailText;
    private EditText _passwordText;
    private Button _loginButton;
    private ProgressDialog progressDialog;
    private ILoginService loginService;
    private User user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciarRetrofit();
        elementosUI();

    }

    private void iniciarRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        loginService = retrofit.create(ILoginService.class);
    }

    private void elementosUI() {
        _emailText = findViewById(R.id.input_email);

        _passwordText = findViewById(R.id.input_password);
        progressDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Validando Usuario...");

        TextView _forgotPassword = findViewById(R.id.link_signup);
        _forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        _loginButton = findViewById(R.id.btn_login);
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog(true);
                String email = _emailText.getText().toString();
                String password = _passwordText.getText().toString();

                if (isValidCredencials(email, password)) {
                    LoginViewModel model = new LoginViewModel();
                    model.setEmail(email);
                    model.setLlave(FirebaseInstanceId.getInstance().getToken());
                    model.setPassword(password);
                    login(model);
                    progressDialog(false);
                }
            }
        });
    }

    private void progressDialog(boolean visible) {
        if (visible) {
            _loginButton.setEnabled(true);
            progressDialog.hide();
        } else {
            _loginButton.setEnabled(false);
            progressDialog.show();
        }
    }

    private void messageDialog(String message) {
        _loginButton.setEnabled(true);
        progressDialog.hide();
        Snackbar.make(findViewById(R.id.scrollViewLayout), message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    private void login(LoginViewModel model) {
        loginService.postLogin(model).enqueue(new Callback<RespuestaServicio<User>>() {
            @Override
            public void onResponse(@NonNull Call<RespuestaServicio<User>> call, @NonNull Response<RespuestaServicio<User>> response) {
                if (response.isSuccessful()) {
                    if(response.body().getEjecucionCorrecta()){
                        if (isValidUser(response.body().getRespuesta())) {
                            onLoginSuccess(response.body().getRespuesta());
                        }
                    }
                    else{
                        messageDialog("Error!! " + response.body().getMensaje());
                    }

                } else {
                    messageDialog("Error!! " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RespuestaServicio<User>> call, @NonNull Throwable t) {
                messageDialog("Error!! " + t.getMessage());
            }
        });
    }

    public void onLoginSuccess(User user) {
        this.user = user;
        _loginButton.setEnabled(true);
        progressDialog.hide();
        if (user.getEntornos().size() == 1) {
            redireccionar(user.getEntornos().get(0).getNombre());
            userPreferences(user.getEntornos().get(0));
        } else {
            EntornosDialogFragment.newInstance(user.getEntornos()).show(getSupportFragmentManager(), "dialog");
        }


    }

    private boolean isValidUser(User user) {
        if (user == null) {
            messageDialog("Error!! Usuario desconocido.");
            return false;
        } else if (user.getEntornos() == null) {
            messageDialog("Error!! Usuario sin entorno dado de alta.");
            return false;
        } else if (user.getPersona() == null) {
            messageDialog("Error!! Usuario sin un perfil de persona");
            return false;
        } else if (user.getPersona().getPuesto() == null) {
            messageDialog("Error!! Usuario sin puesto asignado");
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidCredencials(String email, String password) {
        if (!isValidEmail(email)) {
            messageDialog("Error!! El email no cumple con el formato correcto.");
            return false;
        } else if (!isValidPassword(password)) {
            messageDialog("Error!! El password debe contener al menos 4 caracteres y menos de 10");
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return (password.isEmpty() || password.length() > 4 || password.length() < 10);
    }

    private void redireccionar(String entorno) {
        Class<?> c;
        try {
            if (entorno != "Login") {
                entorno = "Perfiles." + entorno;
            }
            c = Class.forName("com.pmi.ispmmx.maya.Activities." + entorno + "Activity");
            Intent intent = new Intent(LoginActivity.this, c);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            messageDialog(e.getMessage());
        }
    }

    private void userPreferences(Entorno entorno) {
        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(OperadorPreference.SHARED_PREF_NAME,
                Context.MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(OperadorPreference.LOGGEDIN_SHARED_PREF, true);

        editor.putInt(OperadorPreference.ID_USER_SHARED_PREF, user.getId());
        editor.putString(OperadorPreference.EMAIL_USER_SHARED_PREF, user.getEmail());
        editor.putString(OperadorPreference.PASSWORD_USER_SHARED_PREF, user.getPassword());
        editor.putInt(OperadorPreference.ID_PERSONA_SHARED_PREF, user.getPersona().getId());
        editor.putString(OperadorPreference.NOMBRE_PERSONA_SHARED_PREF, user.getPersona().getNombre());
        editor.putString(OperadorPreference.APELLIDO1_PERSONA_SHARED_PREF, user.getPersona().getApellido1());
        editor.putString(OperadorPreference.APELLIDO2_PERSONA_SHARED_PREF, user.getPersona().getApellido2());


        editor.putInt(OperadorPreference.ID_PUESTO_SHARED_PREF, user.getPersona().getPuesto().getId());
        editor.putString(OperadorPreference.NOMBRE_PUESTO_SHARED_PREF, user.getPersona().getPuesto().getNombre());
        editor.putString(OperadorPreference.NOMBRE_ENTORNO_SHARED_PREF, entorno.getNombre());

        editor.apply();

    }

    @Override
    public void onEntornoClicked(Entorno entorno) {
        redireccionar(entorno.getNombre());
        userPreferences(entorno);
    }
}
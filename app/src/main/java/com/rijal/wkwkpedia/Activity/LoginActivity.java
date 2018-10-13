package com.rijal.wkwkpedia.Activity;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rijal.wkwkpedia.AppConfig;
import com.rijal.wkwkpedia.AppController;
import com.rijal.wkwkpedia.R;
import com.rijal.wkwkpedia.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText edtTextLoginUsername,edtTextLoginPassword;
    Button btnLogin,btnRegisterLink;
    String username,password;
    Intent afterlogin;
    SessionManager session;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GetAllEditTextFromLayout();
        GetAllButtonFromLayout();
        session = new SessionManager(getApplicationContext());


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetAllEditTextFromLayout();
                SetStringUsernameValueDariEditTextUsername();
                SetPasswordValueDariEditTextPassword();

                if (username.isEmpty() && password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Field username dan Password Harus di Isi", Toast.LENGTH_SHORT).show();
                }else {
                    doLogin(username, password);

                }
            }
        });
    }

    private void SetPasswordValueDariEditTextPassword() {
        password = edtTextLoginPassword.getText().toString();
    }

    private void SetStringUsernameValueDariEditTextUsername() {
        username = edtTextLoginUsername.getText().toString();
    }

    private void GetAllButtonFromLayout() {
        btnLogin = (Button) findViewById(R.id.btn_Login_action);
        btnRegisterLink = (Button) findViewById(R.id.btn_Login_RegisterLink);
    }

    private void GetAllEditTextFromLayout() {
        edtTextLoginUsername = (EditText) findViewById(R.id.edtText_Login_Username);
        edtTextLoginPassword = (EditText) findViewById(R.id.edtText_Login_password);
    }

    private void doLogin(final String username, final String password) {
        pDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        pDialog.setMessage("Authenticating..");
        pDialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            pDialog.setMessage("Loading...");
                            if (obj.getInt("code") == 302) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONObject userJson = obj.getJSONObject("data");
//                                User user = new User(
//                                        userJson.getInt("id"),
//                                        userJson.getString("username"),
//                                        userJson.getString("name"),
//                                        userJson.getString("identity_number"),
//                                        userJson.getString("address"),
//                                        userJson.getString("phone_number"),
//                                        userJson.getString("email"),
//                                        userJson.getString("post_code"),
//                                        userJson.getString("picture")
//                                );

                                // TODO ambil data ke page selanjutnya

                                //TODO pindah Intent
                                pDialog.dismiss();
                                afterlogin = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(afterlogin);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                pDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        AppController.getInstance(this).addToRequestQueue(stringRequest);
    }
}

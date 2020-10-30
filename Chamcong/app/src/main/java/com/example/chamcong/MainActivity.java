package com.example.chamcong;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chamcong.data.DataHandler;
import com.example.chamcong.model.Nhanvien;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText edtuser, edtpass;
    Button btnLogin;
    TextView tvQmk;
    DataHandler databaseHandler;
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    String URL_LOGIN ="https://chamcong666.000webhostapp.com/getuser.php";
    String chucvu;
    String user;
    String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddControls();
        AddEvents();
        checkPermission();
    }

    void checkPermission() {
        String[] perm_array = {Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_NETWORK_STATE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<String>();
            for (int i=0;i<perm_array.length;i++)
            {
                if (checkSelfPermission(perm_array[i])!= PackageManager.PERMISSION_GRANTED)
                {
                    permissions.add(perm_array[i]);
                }
            }
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 9999);
            } else {

            }
        } else {

        }
    }

    private void AddEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = edtuser.getText().toString().trim();
                pass = edtpass.getText().toString().trim();
                if(user.isEmpty() || pass.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Xin nhập đầy đủ thông tin!",
                            Toast.LENGTH_LONG).show();
                }else {
                    login(user,pass);
                }
            }
        });
        tvQmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,QuenMK.class);
                startActivity(intent);
            }
        });
    }

    private void login(final String username, final String password){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("AAA", response.toString());
                        String message = "";
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("success") == 1) {
                                Nhanvien nv = new Nhanvien();
                                SharedPreferences myaccount = getSharedPreferences("myaccount",MODE_PRIVATE);
                                SharedPreferences.Editor myEditor = myaccount.edit();
                                nv.setsUsername(jsonObject.getString("sUsername"));
                                nv.setsPassname(jsonObject.getString("sPassword"));
                                nv.setsChucvu(jsonObject.getString("sChucvu"));
                                nv.setiManv(jsonObject.getInt("iManv"));
                                message = jsonObject.getString("message");
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                if(nv.getsChucvu().equals("quan ly")){
                                    myEditor.putString("Username",edtuser.getText().toString());
                                    myEditor.putString("Password",edtpass.getText().toString());
                                    myEditor.apply();
                                    Intent intent = new Intent(MainActivity.this, Module_ql.class);
                                    intent.putExtra("manv", String.valueOf(nv.getiManv()));
                                    startActivity(intent);
                                }else if(nv.getsChucvu().equals("nhan vien")){
                                    myEditor.putString("Username",edtuser.getText().toString());
                                    myEditor.putString("Password",edtpass.getText().toString());
                                    myEditor.apply();
                                    Intent intent1 = new Intent(MainActivity.this, Module_nv.class);
                                    intent1.putExtra("manv", String.valueOf(nv.getiManv()));
                                    startActivity(intent1);
                                }
                            } else {
                                message = jsonObject.getString("message");
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Loi",Toast.LENGTH_SHORT).show();
                        Log.e("AAA","Loi:\n" + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AddControls() {
        edtuser = this.findViewById(R.id.editUser);
        edtpass = this.findViewById(R.id.editPassword);
        btnLogin = this.findViewById(R.id.btnOk);
        tvQmk = this.findViewById(R.id.tvQmk);
        SharedPreferences myaccount = getSharedPreferences("myaccount",MODE_PRIVATE);
        String username1 = myaccount.getString("Username","");
        String password1 = myaccount.getString("Password","");
        if(username1.length()!=0 && password1.length()!=0) {
            login(username1, password1);
        }
    }
}

package com.example.chamcong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chamcong.data.DataHandler;
import com.example.chamcong.model.Nhanvien;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Module_nv extends AppCompatActivity {
    DataHandler dataHandler;
    String manv;
    String sTen,sEmail,sChucvu,sUsername,sPassword;
    Nhanvien nv;
    String URL_GETBYID="https://chamcong666.000webhostapp.com/getNVbyID.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_nv);
        AddControls();
    }

    private void AddControls() {
        dataHandler = new DataHandler(Module_nv.this);
        Intent intent = getIntent();
        manv = intent.getStringExtra("manv");
        Log.e("manv",manv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nv,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Thongtin:{
                Xemthongtin();
                return true;
            }
            case R.id.QuetQR:{
                QuetQR();
                return true;
            }
            case R.id.Thoat:{
                Thoat();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Thoat() {
        SharedPreferences myaccount = getSharedPreferences("myaccount",0);
        SharedPreferences.Editor myEditor = myaccount.edit();
        myEditor.remove("Username");
        myEditor.remove("Password");
        myEditor.apply();
        Intent intent = new Intent(Module_nv.this,MainActivity.class);
        startActivity(intent);
    }

    private void QuetQR() {
        Intent intent = new Intent(Module_nv.this,Reader_qr.class);
        intent.putExtra("manv",manv);
        startActivity(intent);
    }

    private void Xemthongtin() {
        Intent intent = getIntent();
        String s = intent.getStringExtra("manv");
        //Toast.makeText(Module_nv.this,s,Toast.LENGTH_LONG).show();
        //nv = dataHandler.getNVByID(s);
        GetNVbyID(s);
        Toast.makeText(Module_nv.this,"Tên nhân viên: " + sTen  + "\nĐịa chỉ Email: " + sEmail
                + "\nChức vụ: " + sChucvu + "\nUsername: " + sUsername  +
                "\nPassword: " + sPassword,Toast.LENGTH_LONG).show();
    }

    private void GetNVbyID(final String id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GETBYID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("AAA", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Nhanvien nv = new Nhanvien();
                            nv.setsTen(jsonObject.getString("sTen"));
                            nv.setsEmail(jsonObject.getString("sEmail"));
                            nv.setsUsername(jsonObject.getString("sUsername"));
                            nv.setsPassname(jsonObject.getString("sPassword"));
                            nv.setsChucvu(jsonObject.getString("sChucvu"));
                            nv.setiManv(jsonObject.getInt("iManv"));
                            sTen = nv.getsTen();
                            sEmail = nv.getsEmail();
                            sChucvu = nv.getsChucvu();
                            sUsername = nv.getsUsername();
                            sPassword = nv.getsPassname();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Module_nv.this,"Loi",Toast.LENGTH_SHORT).show();
                        Log.e("AAA","Loi:\n" + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

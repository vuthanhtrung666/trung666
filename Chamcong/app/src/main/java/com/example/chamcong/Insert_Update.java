package com.example.chamcong;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Insert_Update extends AppCompatActivity {
    EditText edtTen,edtMa,edtEmail,edtChucvu,edtUser,edtPass;
    Button btnLuu,btnThoat;
    Spinner sp;
    ArrayList<String> dschucvu;
    ArrayAdapter<String> dsadapter;
    DataHandler databaseHandler;
    String URL_GETBYID="https://chamcong666.000webhostapp.com/getNVbyID.php";
    String URL_INSERT="https://chamcong666.000webhostapp.com/insert_nv.php";
    String URL_UPDATE="https://chamcong666.000webhostapp.com/update_nv.php";
    String URL_CHECK="https://chamcong666.000webhostapp.com/checkuser.php";
    String URL_SENDMAIL="https://chamcong666.000webhostapp.com/PHPMail.php";
    boolean isupdate;
    boolean checkuser = false;
    String sManv;
    Nhanvien nv;
    int posi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert__update);
        AddControls();
        AddEvents();
    }

    private void AddControls() {
        edtTen = findViewById(R.id.edtTen);
        edtMa = findViewById(R.id.edtMa);
        edtEmail = findViewById(R.id.edtEmail);
        edtChucvu = findViewById(R.id.edtChucvu);
        edtPass = findViewById(R.id.edtPass);
        edtUser = findViewById(R.id.edtUser);
        btnLuu = findViewById(R.id.btnLuu);
        btnThoat = findViewById(R.id.btnThoat);
        sp = findViewById(R.id.spChucvu);
        dschucvu = new ArrayList<>();
        dschucvu.add("quan ly");
        dschucvu.add("nhan vien");
        dsadapter = new ArrayAdapter<String>(Insert_Update.this,R.layout.support_simple_spinner_dropdown_item,dschucvu);
        dsadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp.setAdapter(dsadapter);
        databaseHandler = new DataHandler(Insert_Update.this);
        Intent intent = getIntent();
        isupdate = intent.getBooleanExtra("isupdate",false);
        if(isupdate){
            sManv = intent.getStringExtra("iManv");
            //Toast.makeText(Insert_Update.this,sManv,Toast.LENGTH_LONG).show();
            //nv = databaseHandler.getNVByID(sManv);
            GetNVbyID(sManv);
        }
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
                            edtMa.setText(sManv);
                            edtTen.setText(nv.getsTen());
                            edtEmail.setText(nv.getsEmail());
                            edtChucvu.setText(nv.getsChucvu());
                            int pos = dsadapter.getPosition(nv.getsChucvu());
                            sp.setSelection(pos);
                            edtUser.setText(nv.getsUsername());
                            edtPass.setText(nv.getsPassname());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Insert_Update.this,"Loi",Toast.LENGTH_SHORT).show();
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

    private void update(final String manv,final String tennv,final String email,final String chucvu,final String username,final String password ){
        RequestQueue update = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("update success")){
                            Toast.makeText(Insert_Update.this,"Cập nhập dữ liệu thành công!",
                                    Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(Insert_Update.this,"Lỗi!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Insert_Update.this,"Loi",Toast.LENGTH_SHORT).show();
                        Log.e("AAA","Loi:\n" + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("iManv", manv);
                params.put("sTen", tennv);
                params.put("sEmail", email);
                params.put("sChucvu", chucvu);
                params.put("sUsername", username);
                params.put("sPassword", password);
                return params;
            }
        };
        update.add(stringRequest);
    }

    private void insert(final String manv,final String tennv,final String email,final String chucvu,final String username,final String password ){
        RequestQueue insert = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("insert success")){
                            Toast.makeText(Insert_Update.this,"Thêm dữ liệu thành công!",
                                    Toast.LENGTH_SHORT).show();
                            sendMail(username,email);
                        }else {
                            Toast.makeText(Insert_Update.this,"Lỗi!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Insert_Update.this,"Loi",Toast.LENGTH_SHORT).show();
                        Log.e("AAA","Loi:\n" + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("iManv", manv);
                params.put("sTen", tennv);
                params.put("sEmail", email);
                params.put("sChucvu", chucvu);
                params.put("sUsername", username);
                params.put("sPassword", password);
                return params;
            }
        };
        insert.add(stringRequest);
    }

    private void checkuser(final String username ){
        RequestQueue update = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_CHECK ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("da ton tai tai khoan")){
                            checkuser = true;
                        }else {
                            checkuser = false;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Insert_Update.this,"Loi",Toast.LENGTH_SHORT).show();
                        Log.e("AAA","Loi:\n" + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sUsername", username);
                return params;
            }
        };
        update.add(stringRequest);
    }

    private void sendMail(final String user, final String mail){
        RequestQueue send = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SENDMAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Email sent successfully")){
                            Toast.makeText(getApplicationContext(),"Tài khoản đã được gửi vào Email.",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"Lỗi!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Loi",Toast.LENGTH_SHORT).show();
                        Log.e("AAA","Loi:\n" + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params = new HashMap<>();
                params.put("sUsername", user);
                params.put("sEmail", mail);
                return params;
            }
        };
        send.add(stringRequest);
    }

    private void AddEvents() {
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                posi = i;
                //Toast.makeText(Insert_Update.this,dschucvu.get(posi),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isupdate){
                    //databaseHandler.updateNV(edtTen.getText().toString(),edtEmail.getText().toString(),
                    //        dschucvu.get(posi),edtUser.getText().toString(),edtPass.getText().toString(),
                    //        edtMa.getText().toString());
                    checkuser(edtUser.getText().toString());
                    if(checkuser){

                    }else {
                        update(edtMa.getText().toString(),edtTen.getText().toString(),
                                edtEmail.getText().toString(), dschucvu.get(posi),
                                edtUser.getText().toString(),edtPass.getText().toString());
                        finish();
                    }
                } else {
                    //databaseHandler.insertNV(edtMa.getText().toString(),edtTen.getText().toString(),
                    //        edtEmail.getText().toString(), dschucvu.get(posi),
                    //        edtUser.getText().toString(),edtPass.getText().toString());
                    checkuser(edtUser.getText().toString());
                    if(checkuser){

                    } else {
                        insert(edtMa.getText().toString(),edtTen.getText().toString(),
                                edtEmail.getText().toString(), dschucvu.get(posi),
                                edtUser.getText().toString(),edtPass.getText().toString());
                        finish();
                    }
                }

            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Insert_Update.this,Module_ql.class);
                startActivity(intent);
            }
        });
    }
}

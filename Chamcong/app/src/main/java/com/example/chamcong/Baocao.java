package com.example.chamcong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chamcong.Adapter.list_adapter_bc;
import com.example.chamcong.Adapter.list_adapter_bc_chieu;
import com.example.chamcong.Adapter.list_adapter_bc_sang;
import com.example.chamcong.model.DSBaocao;
import com.example.chamcong.model.Nhanvien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Baocao extends AppCompatActivity {
    LinearLayout layout;
    ListView lst;
    EditText edtDate;
    Button btnDate,btnMonth,btnSang,btnChieu;
    ArrayList<Nhanvien> dsnv;
    ArrayList<DSBaocao> dsnvbc;
    list_adapter_bc la;
    list_adapter_bc_sang lasang;
    list_adapter_bc_chieu lachieu;
    String URL_BYDATE = "https://chamcong666.000webhostapp.com/getNVbydate.php";
    String URL_BYMONTHS = "https://chamcong666.000webhostapp.com/getNVbymonthS.php";
    String URL_BYMONTHC = "https://chamcong666.000webhostapp.com/getNVbymonthC.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baocao);
        AddControls();
        AddEvents();
    }

    private void AddEvents() {
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("AAA",edtDate.getText().toString());
                if(edtDate.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Xin nhập ngày cần tìm",Toast.LENGTH_SHORT).show();
                }else {
                    load_date(edtDate.getText().toString());
                    //la = new list_adapter_bc(dsnv);
                    //lst.setAdapter(la);
                }

                //la.notifyDataSetChanged();
            }
        });
        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setVisibility(View.VISIBLE);
            }
        });
        btnSang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtDate.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Xin nhập tháng cần tìm",Toast.LENGTH_SHORT).show();
                }else {
                    load_month_S(edtDate.getText().toString());
                    //lasang = new list_adapter_bc_sang(dsnvbc);
                    //lst.setAdapter(lasang);
                    layout.setVisibility(View.GONE);
                }
            }
        });
        btnChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtDate.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Xin nhập tháng cần tìm",Toast.LENGTH_SHORT).show();
                }else {
                    load_month_C(edtDate.getText().toString());
                    //lachieu = new list_adapter_bc_chieu(dsnvbc);
                    //lst.setAdapter(lachieu);
                    layout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void load_date(final String date){
        dsnv.clear();
        RequestQueue load_date = Volley.newRequestQueue(this);
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, URL_BYDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(response.equals("empty")){
                                Toast.makeText(getApplicationContext(),"danh sách rỗng",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                JSONObject jobj = new JSONObject(response);
                                JSONArray response1 = jobj.getJSONArray("Nhanvien");
                                Log.e("anyText", String.valueOf(response1));
                                for(int i=0;i<response1.length();i++){

                                    JSONObject object = response1.getJSONObject(i);

                                    dsnv.add(new Nhanvien(
                                            object.getInt("iManv"),
                                            object.getString("sTen"),
                                            object.getString("sEmail"),
                                            object.getString("sSang"),
                                            object.getString("sChieu")
                                    ));
                                    Log.e("AAA","them dsnv");
                                }
                            }

                        } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        //la.notifyDataSetChanged();
                        la = new list_adapter_bc(dsnv);
                        lst.setAdapter(la);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("AAA","loi: "+ error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("sNgay", date);
                return params;
            }
        };
        load_date.add(jsonArrayRequest);
    }

    private void load_month_S(final String date){
        dsnvbc.clear();
        RequestQueue load_date = Volley.newRequestQueue(this);
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, URL_BYMONTHS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(response.equals("empty")){
                                Toast.makeText(getApplicationContext(),"danh sách rỗng",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                JSONObject jobj = new JSONObject(response);
                                JSONArray response1 = jobj.getJSONArray("Nhanvien");
                                Log.e("anyText", String.valueOf(response1));
                                for(int i=0;i<response1.length();i++){

                                    JSONObject object = response1.getJSONObject(i);

                                    dsnvbc.add(new DSBaocao(
                                            object.getInt("iManv"),
                                            object.getString("sTen"),
                                            object.getString("sEmail"),
                                            object.getInt("sSang")
                                    ));
                                    Log.e("AAA","them dsnv sang");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //lasang.notifyDataSetChanged();
                        lasang = new list_adapter_bc_sang(dsnvbc);
                        lst.setAdapter(lasang);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("AAA","loi: "+ error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("sNgay", date);
                return params;
            }
        };
        load_date.add(jsonArrayRequest);
    }

    private void load_month_C(final String date){
        dsnvbc.clear();
        RequestQueue load_date = Volley.newRequestQueue(this);
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, URL_BYMONTHC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(response.equals("empty")){
                                Toast.makeText(getApplicationContext(),"danh sách rỗng",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                JSONObject jobj = new JSONObject(response);
                                JSONArray response1 = jobj.getJSONArray("Nhanvien");
                                Log.e("anyText", String.valueOf(response1));
                                for(int i=0;i<response1.length();i++){

                                    JSONObject object = response1.getJSONObject(i);

                                    dsnvbc.add(new DSBaocao(
                                            object.getInt("iManv"),
                                            object.getString("sTen"),
                                            object.getString("sEmail"),
                                            object.getInt("sChieu")
                                    ));
                                    Log.e("AAA","them dsnv chieu");
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //lachieu.notifyDataSetChanged();
                        lachieu = new list_adapter_bc_chieu(dsnvbc);
                        lst.setAdapter(lachieu);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("AAA","loi: "+ error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("sNgay", date);
                return params;
            }
        };
        load_date.add(jsonArrayRequest);
    }

    private void AddControls() {
        layout = findViewById(R.id.layoutbtn);
        dsnv = new ArrayList<>();
        dsnvbc = new ArrayList<>();
        lst = this.findViewById(R.id.lvdsnv);
        btnSang = findViewById(R.id.btnSang);
        btnChieu = findViewById(R.id.btnChieu);
        btnDate = findViewById(R.id.btnTheodate);
        btnMonth = findViewById(R.id.btnTheomonth);
        edtDate = findViewById(R.id.edtTim);
    }
}

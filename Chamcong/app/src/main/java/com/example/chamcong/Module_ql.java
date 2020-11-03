package com.example.chamcong;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chamcong.Adapter.list_adapter;
import com.example.chamcong.data.DataHandler;
import com.example.chamcong.model.Nhanvien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Module_ql extends AppCompatActivity {
    final int RESULT_NHANVIEN_ACTIVITY = 1;
    String URL_DS ="https://chamcong666.000webhostapp.com/getdata.php";
    String URL_DELETE = "https://chamcong666.000webhostapp.com/deletebyID.php";
    ListView lst;
    String manv;
    ArrayList<Nhanvien> dsnv;
    list_adapter la;
    DataHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_ql);
        AddControls();
    }

    private void AddControls() {
        //databaseHandler = new DataHandler(Module_ql.this);
        dsnv = new ArrayList<>();
        Intent intent = getIntent();
        manv = intent.getStringExtra("manv");
        Log.e("manv",manv);
        load(URL_DS);
        la = new list_adapter(dsnv);
        lst = this.findViewById(R.id.lvnv);
        lst.setAdapter(la);
        this.registerForContextMenu(lst);
    }

    private void load(String url){
        dsnv.clear();
        //dsnv.addAll(databaseHandler.getAllNV());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                dsnv.add(new Nhanvien(
                                        object.getInt("iManv"),
                                        object.getString("sTen"),
                                        object.getString("sEmail"),
                                        object.getString("sChucvu")
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("AAA",dsnv.toString());
                        la.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
                );
        requestQueue.add(jsonArrayRequest);
    }

    private void DeleteNVbyID(final String id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("AAA", response.toString());
                        if(response.equals("delete success")){
                            Toast.makeText(Module_ql.this,"Xóa dữ liệu thành công!",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Module_ql.this,"Lỗi!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Module_ql.this,"Loi",Toast.LENGTH_SHORT).show();
                        Log.e("AAA","Loi:\n" + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("iManv", id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ql,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Themnv:{
                Themnv();
                return true;
            }
            case R.id.Baocao:{
                Baocao();
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
        Intent intent = new Intent(Module_ql.this,MainActivity.class);
        startActivity(intent);
    }

    private void QuetQR() {
        Intent intent = new Intent(Module_ql.this,Reader_qr.class);
        intent.putExtra("manv",manv);
        startActivity(intent);
    }

    private void Baocao() {
        Intent intent = new Intent(Module_ql.this,Baocao.class);
        startActivity(intent);
    }

    private void Themnv() {
        Intent intent = new Intent(Module_ql.this,Insert_Update.class);
        intent.putExtra("isupdate", false);
        startActivityForResult(intent,RESULT_NHANVIEN_ACTIVITY);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ql_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
        String s = String.valueOf(dsnv.get(pos).getiManv());
        switch (item.getItemId()){
            case R.id.edit:{
                //Toast.makeText(Module_ql.this,s,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Module_ql.this, Insert_Update.class);
                intent.putExtra("isupdate", true);
                intent.putExtra("iManv",s);
                startActivityForResult(intent, RESULT_NHANVIEN_ACTIVITY);
                return true;
            }
            case R.id.delete:{
                //databaseHandler.deleteNVById(s);
                DeleteNVbyID(s);
                int i = 0;
                for(int n=0;n<6;n++){
                    if(i==5){
                        load(URL_DS);
                        la = new list_adapter(dsnv);
                        lst.setAdapter(la);
                        break;
                    } else {
                        i++;
                    }
                }
                return true;
            }
            default: return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_NHANVIEN_ACTIVITY) {
            int i = 0;
            for(int n=0;n<11;n++){
                if(i==10){
                    Log.e("AAA","ket qua tra ve");
                    dsnv = new ArrayList<>();
                    load(URL_DS);
                    la = new list_adapter(dsnv);
                    lst.setAdapter(la);
                    break;
                } else {
                    i++;
                }
            }
                //load(URL_DS);
                //la = new list_adapter(dsnv);
                //lst.setAdapter(la);

                // DetailActivity không thành công, không có data trả về.
            }
        }
}


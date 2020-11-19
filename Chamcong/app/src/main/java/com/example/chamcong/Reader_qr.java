package com.example.chamcong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chamcong.model.MaQR;
import com.example.chamcong.model.Nhanvien;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Reader_qr extends AppCompatActivity {
    private IntentIntegrator qrScan;
    TextView tvketqua;
    Button btnScan,btnThoat;
    String URL_GETQR="https://chamcong666.000webhostapp.com/getQR.php";
    String URL_CHAMCONGSANG="https://chamcong666.000webhostapp.com/chamcongsang.php";
    String URL_CHAMCONGCHIEU="https://chamcong666.000webhostapp.com/chamcongchieu.php";
    int hour;
    String manv;
    String date;
    String maqr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_qr);
        qrScan = new IntentIntegrator(Reader_qr.this);
        Intent intent = getIntent();
        manv = intent.getStringExtra("manv");
        Log.e("manv",manv);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(Calendar.getInstance().getTime());
        Log.e("date",date);
        tvketqua = findViewById(R.id.tvketqua);
        btnScan = findViewById(R.id.btnScan);
        btnThoat = findViewById(R.id.btnThoat);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                qrScan.setPrompt("đang quét xin chờ ...");
                qrScan.setCameraId(0);
                qrScan.setBeepEnabled(true);
                qrScan.setOrientationLocked(true);
                qrScan.setBarcodeImageEnabled(true);
                qrScan.initiateScan();

            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getQR(final String sBuoi){
        RequestQueue get = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_GETQR ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("AAA", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            MaQR qr = new MaQR();
                            qr.setsQR(jsonObject.getString("QR"));
                            String getqr = qr.getsQR() + ".0";
                            Log.e("maqr",maqr);
                            if(getqr.equals(maqr)){
                                Log.e("maqr","cham cong");
                                Calendar c = Calendar.getInstance();
                                hour = c.get(Calendar.HOUR_OF_DAY);
                                kiemtratg(hour,date,manv);
                            } else {
                                Toast.makeText(Reader_qr.this,"Sai mã QR",Toast.LENGTH_SHORT).show();
                                Log.e("maqr","Loi cham cong");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Reader_qr.this,"Loi",Toast.LENGTH_SHORT).show();
                        Log.e("AAA","Loi:\n" + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sBuoi", sBuoi);
                return params;
            }
        };
        get.add(stringRequest);
    }

    private void Chamcongsang(final String iManv, final String sNgay, final String sSang ){
        RequestQueue chamcong = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHAMCONGSANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success")){
                            Toast.makeText(Reader_qr.this,"Chấm công thành công!",
                                    Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(Reader_qr.this,"Lỗi!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("iManv", iManv);
                params.put("sNgay",sNgay);
                params.put("sSang",sSang);
                return params;
            }
        };
        chamcong.add(stringRequest);
    }

    private void Chamcongchieu(final String iManv, final String sNgay, final String sChieu ){
        RequestQueue chamcong = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHAMCONGCHIEU,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success")){
                            Toast.makeText(Reader_qr.this,"Chấm công thành công!",
                                    Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(Reader_qr.this,"Lỗi!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("iManv", iManv);
                params.put("sNgay",sNgay);
                params.put("sChieu",sChieu);
                return params;
            }
        };
        chamcong.add(stringRequest);
    }

    private void select(int hour,String QR) {
        if (hour >= 0 && hour <= 12) {
            //dataHandler.updateQR(QR,"sang");
            getQR("sang");
        }
        if (hour > 12 && hour <= 23) {
            //dataHandler.updateQR(QR,"chieu");
            getQR("chieu");
        }
    }

    private void kiemtratg(int hour,String ngay,String id) {
        if (hour >= 0 && hour <= 12) {
            Log.e("maqr","cham cong sang");
            if(hour<=8){
                Chamcongsang(id,ngay,"du");
            } else {
                Chamcongsang(id,ngay,"muon");
            }
        }
        if (hour > 12 && hour <= 23) {
            Log.e("maqr","cham cong chieu");
            if(hour>=17){
                Chamcongchieu(id,ngay,"du");
            } else {
                Chamcongchieu(id,ngay,"som");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                tvketqua.setText("Nội dung của mã QR: "+ result.getContents());

                maqr = result.getContents();
                //Toast.makeText(this, maqr, Toast.LENGTH_LONG).show();
                Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                select(hour,maqr);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}

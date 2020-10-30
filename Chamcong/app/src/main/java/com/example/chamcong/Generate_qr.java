package com.example.chamcong;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chamcong.data.DataHandler;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Generate_qr extends AppCompatActivity {
    ImageView maQR;
    TextView edtTime;
    Button btn;
    Button btnend;
    String QR;
    int hour;
    CountDownTimer Timer;
    int time=0;
    DataHandler dataHandler;
    String URL_UPDATEQR= "https://chamcong666.000webhostapp.com/updateQR.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);
        AddControls();

    }

    private void AddControls() {
        maQR = findViewById(R.id.imgQR);
        btn = findViewById(R.id.button);
        btnend = findViewById(R.id.button2);
        edtTime = (EditText) findViewById(R.id.edtTime);
        dataHandler = new DataHandler(Generate_qr.this);

    }

    private void updateQR(final String sBuoi, final String QR){
        RequestQueue update = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_UPDATEQR ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("update success")){
                            Toast.makeText(Generate_qr.this,"Cập nhập dữ liệu thành công!",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Generate_qr.this,"Lỗi!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Generate_qr.this,"Loi",Toast.LENGTH_SHORT).show();
                        Log.e("AAA","Loi:\n" + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sBuoi", sBuoi);
                params.put("QR", QR);
                return params;
            }
        };
        update.add(stringRequest);
    }

    private void update(String QR,int hour){
        if(hour>=0 && hour<=12){
            //dataHandler.updateQR(QR,"sang");
            updateQR("sang",QR);
        }if(hour>12 && hour<=23){
            //dataHandler.updateQR(QR,"chieu");
            updateQR("chieu",QR);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        AddControls();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timer = new CountDownTimer(3600000,1000) {
                    @Override
                    public void onTick(long l) {
                        time+=1;
                        edtTime.setText(String.valueOf(l/1000));
                        Calendar c = Calendar.getInstance();
                        hour = c.get(Calendar.HOUR_OF_DAY);
                        //Toast.makeText(Generate_qr.this,hour,Toast.LENGTH_SHORT).show();
                        if(edtTime.getText().toString().equals("3599")){
                            QR = String.valueOf(Math.floor(((Math.random() * 899999) + 100000)));
                            update(QR,hour);
                            Toast.makeText(Generate_qr.this,QR,Toast.LENGTH_SHORT).show();
                            // Whatever you need to encode in the QR code
                            //dataHandler.updateQR(QR,);
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                            try {
                                BitMatrix bitMatrix = multiFormatWriter.encode(QR, BarcodeFormat.QR_CODE,250,250);
                                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                maQR.setImageBitmap(bitmap);
                            } catch (WriterException e) {
                                e.printStackTrace();
                            }
                        }
                        if(time==299){
                            QR = String.valueOf(Math.floor(((Math.random() * 899999) + 100000)));
                            Toast.makeText(Generate_qr.this,QR,Toast.LENGTH_SHORT).show();
                            update(QR,hour);
                            time = 0;
                            // Whatever you need to encode in the QR code
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                            try {
                                BitMatrix bitMatrix = multiFormatWriter.encode(QR, BarcodeFormat.QR_CODE,250,250);
                                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                maQR.setImageBitmap(bitmap);
                            } catch (WriterException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(Generate_qr.this,"Het gio",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }.start();

            }
        });
        btnend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timer.cancel();
                edtTime.setText("0");
                maQR.setImageDrawable(null);
                finish();
            }
        });
    }
}

package com.example.chamcong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class QuenMK extends AppCompatActivity {
    EditText edtUser, edtMail;
    Button btnXd, btnThoat;
    String User, Email;
    String URL_SENDMAIL="https://chamcong666.000webhostapp.com/PHPMail.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_m_k);
        AddControls();
        AddEvents();

    }

    private void AddEvents() {
        btnXd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Send();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Send() {
        if(User.isEmpty() || Email.isEmpty()){
            Toast.makeText(getApplicationContext(),"Xin hãy nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT).show();
        } else {
            sendMail(User,Email);
        }
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

    private void AddControls() {
        edtUser = findViewById(R.id.edtUser);
        edtMail = findViewById(R.id.edtMail);
        btnXd = findViewById(R.id.btnXd);
        btnThoat = findViewById(R.id.btnThoat);
        User = edtUser.getText().toString().trim();
        Email = edtMail.getText().toString().trim();
    }
}

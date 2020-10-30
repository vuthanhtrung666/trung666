package com.example.chamcong.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.chamcong.model.MaQR;
import com.example.chamcong.model.Nhanvien;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataHandler extends SQLiteOpenHelper {
    private static String DB_NAME = "Chamcong.db";// Database name
    private final Context context;
    private static String TAG = "DatabaseHandler";
    SQLiteDatabase db;
    private static int version = 1;
    private static String CC_TABLE = "Chamcong";
    private static String DATE = "sNgay";
    private static String TIME = "sGio";

    private static String QR_TABLE = "MaQR";
    private static String BUOI = "sBuoi";
    private static String QR = "sQR";
    private static String NV_TABLE = "Nhanvien";
    private static String ID_NV = "iManv";
    private static String TEN_NV = "sTennv";
    private static String EMAIL = "sEmail";
    private static String CHUCVU = "sChucvu";
    private static String USER = "sUsername";
    private static String PASS = "sPassword";
    public DataHandler(@Nullable Context context) {
        super(context, DB_NAME, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        String CREATE_TABLE_NV = "CREATE TABLE IF NOT EXISTS " + NV_TABLE + "("
                + ID_NV + " INTERGER PRIMARY KEY ," + TEN_NV + " TEXT,"
                + EMAIL + " TEXT,"
                + CHUCVU + " TEXT," + USER + " TEXT," + PASS +" TEXT)";
        db.execSQL(CREATE_TABLE_NV);
        String CREATE_TABLE_QR = "CREATE TABLE IF NOT EXISTS "+QR_TABLE + "("
                + BUOI + " TEXT PRIMARY KEY ," + QR + " TEXT)";
        db.execSQL(CREATE_TABLE_QR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        db.execSQL("DROP TABLE IF EXISTS " + NV_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + QR_TABLE);
        onCreate(db);
    }

    public Nhanvien getNVByUser(String sUsername, String sPassword){
        Nhanvien nv = null;
        db = this.getReadableDatabase();
        String selection = "sUsername=? and sPassword = ?";
        String[] selectionArgs = {sUsername, sPassword};
        Cursor cursor = db.query(NV_TABLE,null,selection, selectionArgs,null,null,null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int Manv = cursor.getInt(0);
            String Tennv = cursor.getString(1);
            String Email = cursor.getString(2);
            String Username = cursor.getString(4);
            String Password = cursor.getString(5);
            String Chucvu = cursor.getString(3);
            nv = new Nhanvien(Manv, Tennv, Email,Chucvu, Username, Password );
        } else {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ID_NV,"1");
            values.put(TEN_NV,"Trung");
            values.put(EMAIL,"vuthanhtrung666@gmail.com");
            values.put(CHUCVU,"quản lý");
            values.put(USER,"trung");
            values.put(PASS,"123");
            long newrowid = db.insert(NV_TABLE,null,values);
            db.close();
        }
        cursor.close();
        return nv;
    }

    public List<Nhanvien> getAllNV() {
        List<Nhanvien> nhanviens = new ArrayList<Nhanvien>();
        db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + NV_TABLE, null);
        int count = cursor.getCount();
        //Đến dòng đầu của tập dữ liệu
        if(count>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int Manv = cursor.getInt(0);
                String Tennv = cursor.getString(1);
                String Email = cursor.getString(2);
                String Username = cursor.getString(4);
                String Password = cursor.getString(5);
                String Chucvu = cursor.getString(3);
                nhanviens.add(new Nhanvien(Manv, Tennv, Email,Chucvu, Username, Password ));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return nhanviens;
    }

    public Nhanvien getNVByID(String sMa) {
        Nhanvien nv = null;
        db = this.getReadableDatabase();
        String selection = "iManv=? ";
        String[] selectionArgs = {sMa};
        Cursor cursor = db.query(NV_TABLE, null, selection, selectionArgs, null, null, null);

        if (cursor.getCount() > 0) {
            Toast.makeText(context,"Lấy thành công!",Toast.LENGTH_LONG).show();
            cursor.moveToFirst();
            int Manv = cursor.getInt(0);
            String Tennv = cursor.getString(1);
            String Email = cursor.getString(2);
            String Username = cursor.getString(4);
            String Password = cursor.getString(5);
            String Chucvu = cursor.getString(3);
            nv = new Nhanvien(Manv, Tennv, Email, Chucvu, Username, Password);
        }
        cursor.close();
        return nv;
    }

    public void updateNV(String sTennv, String sEmail,String sChucvu,String sUsername,String sPassword,String iManv ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEN_NV,sTennv);
        values.put(EMAIL,sEmail);
        values.put(CHUCVU,sChucvu);
        values.put(USER,sUsername);
        values.put(PASS,sPassword);
        int count = db.update(NV_TABLE,values,ID_NV + "=?",new String[]{iManv});
    }

    public void updateQR(String sQR,String sbuoi){
        db = this.getReadableDatabase();
        String selection =" sBuoi =? ";
        String[] selectionArgs = {sbuoi};
        Cursor cursor = db.query(QR_TABLE,null,selection, selectionArgs,null,null,null);

        if (cursor.getCount() > 0) {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(QR,sQR);
            int count = db.update(QR_TABLE,values,BUOI + "=?",new String[]{sbuoi});
        } else {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(BUOI,"sáng");
            values.put(QR,"");
            ContentValues values1 = new ContentValues();
            values.put(BUOI,"chiều");
            values.put(QR,"");
            long newrowid = db.insert(NV_TABLE,null,values);
            long newrowid1 = db.insert(QR_TABLE,null,values1);
            db.close();
        }
        cursor.close();
    }



    public void insertNV(String iManv, String sTennv,String sEmail,String sChucvu,String sUsername,String sPassword ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_NV,iManv);
        values.put(TEN_NV,sTennv);
        values.put(EMAIL,sEmail);
        values.put(CHUCVU,sChucvu);
        values.put(USER,sUsername);
        values.put(PASS,sPassword);
        long newrowid = db.insert(NV_TABLE,null,values);
        db.close();
    }

    public void deleteNVById(String iManv){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM "+ NV_TABLE +" WHERE " + ID_NV +"=?", new String[]{iManv});
    }
}

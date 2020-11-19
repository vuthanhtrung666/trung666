package com.example.chamcong.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chamcong.R;
import com.example.chamcong.model.DSBaocao;
import com.example.chamcong.model.Nhanvien;

import java.util.ArrayList;

public class list_adapter_bc_sang extends BaseAdapter {
    final ArrayList<DSBaocao> nhanvien;
    public list_adapter_bc_sang(ArrayList<DSBaocao> nhanvien) {
        this.nhanvien = nhanvien;
    }

    @Override
    public int getCount() {
        return nhanvien.size();
    }

    @Override
    public DSBaocao getItem(int i) {
        return nhanvien.get(i);
    }

    @Override
    public long getItemId(int i) {
        return nhanvien.get(i).getiManv();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewNV;
        if (view == null) {
            viewNV = View.inflate(viewGroup.getContext(), R.layout.item_baocao_sang, null);
        } else viewNV = view;
        //Bind sữ liệu phần tử vào View
        DSBaocao nv = (DSBaocao) getItem(i);
        ((TextView) viewNV.findViewById(R.id.tvManv)).setText(String.format("%s", nv.getiManv()));
        ((TextView) viewNV.findViewById(R.id.tvTen)).setText(String.format("Tên:%s", nv.getsTen()));
        ((TextView) viewNV.findViewById(R.id.tvEmail)).setText(String.format("Email:%s",nv.getsEmail()));
        ((TextView) viewNV.findViewById(R.id.tvSang)).setText(String.format("Sáng:%s",nv.getsBuoi()));
        return viewNV;
    }
}

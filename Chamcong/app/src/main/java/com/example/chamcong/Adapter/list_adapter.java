package com.example.chamcong.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chamcong.R;
import com.example.chamcong.model.Nhanvien;

import java.util.ArrayList;

public class list_adapter extends BaseAdapter {
 /*   Activity context;
    int resource;
    List<Nhanvien> objects;

    public list_adapter(@NonNull Activity context, int resource, @NonNull List<Nhanvien> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);
        TextView tvManv = row.<TextView>findViewById(R.id.tvManv);
        TextView tvTen= row.<TextView>findViewById(R.id.tvTen);
        TextView tvEmail= row.<TextView>findViewById(R.id.tvEmail);
        TextView tvDiachi= row.<TextView>findViewById(R.id.tvDiachi);
        TextView tvChucvu= row.<TextView>findViewById(R.id.tvChucvu);
        final Nhanvien list1=this.objects.get(position);
        tvManv.setText(list1.getiManv());
        tvTen.setText(list1.getsTennv());
        tvDiachi.setText(list1.getsDiachi());
        tvEmail.setText(list1.getsEmail());
        tvChucvu.setText(list1.getsChucvu());
        return row;
    }*/
 final ArrayList<Nhanvien> nhanvien;

    public list_adapter(ArrayList<Nhanvien> nhanvien) {
        this.nhanvien = nhanvien;
    }

    @Override
    public int getCount() {
        return nhanvien.size();
    }

    @Override
    public Nhanvien getItem(int i) {
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
            viewNV = View.inflate(viewGroup.getContext(), R.layout.item_ql, null);
        } else viewNV = view;
        //Bind sữ liệu phần tử vào View
        Nhanvien nv = (Nhanvien) getItem(i);
        ((TextView) viewNV.findViewById(R.id.tvManv)).setText(String.format("%s", nv.getiManv()));
        ((TextView) viewNV.findViewById(R.id.tvTen)).setText(String.format("Tên:%s", nv.getsTen()));
        ((TextView) viewNV.findViewById(R.id.tvEmail)).setText(String.format("Email:%s",nv.getsEmail()));
        ((TextView) viewNV.findViewById(R.id.tvChucvu)).setText(String.format("Chức vụ:%s",nv.getsChucvu()));
        return viewNV;
    }
}

package com.example.bevasarlobeadando;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private List<Termek> products;
    private Context context;

    public CustomAdapter(List<Termek> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_row, viewGroup, false);

        TextView name = view.findViewById(R.id.name);
        TextView price = view.findViewById(R.id.price);
        TextView qunatMes = view.findViewById(R.id.quantityMesure);
        TextView actPrice = view.findViewById(R.id.actPrice);

        Termek p = products.get(i);
        name.setText(p.getNev());
        price.setText(String.valueOf(p.getEgyseg_ar()).concat(" Ft"));
        qunatMes.setText(String.valueOf(p.getMennyiseg()).concat(p.getMertekegyseg()));
        actPrice.setText(String.valueOf(p.getBrutto_ar()));

        return view;
    }
}

package com.example.ximena.tc6_ximenabolannos;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.ximena.tc6_ximenabolannos.R.*;

/**
 * Created by Ximena on 24/5/2018.
 */

public class CustomList extends BaseAdapter {

    private final Context context;
    private final ArrayList<Product> web;



    public CustomList(Context context, ArrayList<Product> web) {
        System.out.println("Entro");
        this.context = context;
        this.web = web;


    }
    private class ProductViewHolder {
        TextView name;
        TextView price;
        TextView description;
        ImageView image;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        System.out.println("Entro");
        ProductViewHolder productHolder;

        View rowView=view;
        if (rowView == null) {

            System.out.println("Entro");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_single, null, true);
        }else{
            rowView=view;

        }
            ImageView imageView = rowView.findViewById(R.id.img);
            productHolder = new ProductViewHolder();
            productHolder.name = rowView.findViewById(R.id.name);
            productHolder.price = rowView.findViewById(R.id.price);
            productHolder.description = rowView.findViewById(R.id.description);
            productHolder.image = rowView.findViewById(R.id.img);

            rowView.setTag(productHolder);
            Product product = web.get(position);

            if (product != null) {
                productHolder.name.setText(product.getName());
                productHolder.price.setText(product.getPrice());
                productHolder.description.setText(product.getPrice());
                Picasso.get().load(product.image).into(imageView);

            }




        return rowView;
    }




}

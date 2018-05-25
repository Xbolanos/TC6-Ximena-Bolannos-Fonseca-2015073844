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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private DatabaseReference mDatabase;



    public CustomList(Context context, ArrayList<Product> web) {
        System.out.println("Entro");
        this.context = context;
        this.web = web;
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }
    private class ProductViewHolder {
        TextView name;
        TextView price;
        TextView description;
        ImageView image;
        Button delete;
    }


    @Override
    public int getCount() {
        return web.size();
    }

    @Override
    public Object getItem(int position) {

        return web.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View view, ViewGroup parent) {
        System.out.println("Entro");
        ProductViewHolder productHolder;
        final int pos=position;
        View rowView=view;
        if (rowView == null) {

            System.out.println("Entro");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_single, parent, false);
        }else{
            rowView=view;

        }
            ImageView imageView = rowView.findViewById(R.id.img);
            productHolder = new ProductViewHolder();
            productHolder.name = rowView.findViewById(R.id.name);
            productHolder.price = rowView.findViewById(R.id.price);
            productHolder.description = rowView.findViewById(R.id.description);
            productHolder.image = rowView.findViewById(R.id.img);
            productHolder.delete = rowView.findViewById(id.delete);

            rowView.setTag(productHolder);
            final Product product = web.get(position);

            if (product != null) {
                productHolder.name.setText(product.getName());
                productHolder.price.setText(product.getPrice());
                productHolder.description.setText(product.getDescription());
                Picasso.get().load(product.image).into(imageView);

                productHolder.delete.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {


                        mDatabase.child("users").child(product.getUserid()).child("products").child(product.getId()).removeValue();
                        web.remove(web.get(pos));
                        notifyDataSetChanged();




                    }
                });

            }




        return rowView;
    }




}

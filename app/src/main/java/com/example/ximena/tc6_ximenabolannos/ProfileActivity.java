package com.example.ximena.tc6_ximenabolannos;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;
    Context c;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        c=getApplicationContext();

        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views

        buttonLogout = (Button) findViewById(R.id.buttonLogout);


        //adding listener to button
        buttonLogout.setOnClickListener(this);


        // Get a reference to our posts
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users= mDatabase.child("users");
        DatabaseReference userd=users.child(user.getUid());
        userd.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();


                Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();
                ArrayList<Product> products= new ArrayList<Product>();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Product post = postSnapshot.getValue(Product.class);
                    post.setId(postSnapshot.getKey());
                    post.setUserid(user.getUid());
                    products.add(post);



                }
                System.out.println(products.toString());
                ListView listView =  findViewById(R.id.listview);
                CustomList adapter=new CustomList(c, products);
                listView.setAdapter(adapter);




            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });





    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public  void onclickAddView(View view){

        //starting login activity
        startActivity(new Intent(this, AddActivity.class));

    }
}

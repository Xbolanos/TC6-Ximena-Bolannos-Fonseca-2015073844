package com.example.ximena.tc6_ximenabolannos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AddActivity extends AppCompatActivity {
    public static final int GET_FROM_GALLERY = 3;
    FirebaseStorage storage;
    StorageReference storageReference;
    private DatabaseReference mDatabase;
    Bitmap bitmap = null;
    Uri filePath;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://tc6-ximenabolannos.appspot.com/");

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public void onClickAddImage(View view){
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            filePath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                Button buttonimage = (Button) findViewById(R.id.imageButton);
                BitmapDrawable bdrawable = new BitmapDrawable(this.getResources(),bitmap);
                buttonimage.setBackground(bdrawable);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public void addInformation(View view){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/products/");
        final ProgressDialog pd = new ProgressDialog(this);

        pd.setMessage("Uploading....");
        if(filePath != null) {
            pd.show();
            final String productId = mDatabase.push().getKey();
            final TextView name =findViewById(R.id.name);
            final TextView description =findViewById(R.id.description);
            final TextView price =findViewById(R.id.price);
            StorageReference childRef = storageReference.child(name.getText().toString()+".jpg");

            //uploading the image
            UploadTask uploadTask = childRef.putFile(filePath);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    Toast.makeText(AddActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    Product product = new Product(name.getText().toString(),price.getText().toString(),description.getText().toString(),downloadUrl.toString());
                    mDatabase.child(productId).setValue(product);
                    change_ProfileActivity();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(AddActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(AddActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
        }




    }

    public void change_ProfileActivity(){
        finish();
        //starting login activity
        startActivity(new Intent(this, ProfileActivity.class));
    }


}

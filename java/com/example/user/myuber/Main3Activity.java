package com.example.user.myuber;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class Main3Activity extends AppCompatActivity {
    private String id;
    private Intent intent,in;
    private StorageReference mStorageRef;
    private int PICK_IMAGE_REQUEST=122;
    private ImageView imageView;
    private Uri filePath;
    private DatabaseReference myRef;
    private Button image,save;
    FirebaseDatabase mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         mData= FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        setContentView(R.layout.activity_main3);
        imageView=(ImageView) findViewById(R.id.imageView);
        image=(Button) findViewById(R.id.button4);
        save =(Button) findViewById(R.id.button3);
       intent= new Intent(getApplicationContext(),Main2Activity.class);
        in = getIntent();//getting intent        final String id= i.getExtras().getString("id");//get Uid
        id= in.getExtras().getString("id");//get Uid
        myRef = mData.getReference(id);
       loadintodatabase();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadintodatabase();
            }
        });
    }
 private void loadimage()
 {String str="image/"+id;
     final StorageReference riversRef = mStorageRef.child(str);
     if(filePath==null){Log.d("Gfile","nofile");}
    // Task<Uri> downloadUrl = riversRef.getDownloadUrl();
    riversRef.putFile(filePath)
             .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Task<Uri> uri=riversRef.getDownloadUrl();

                   //  myRef.child("Pic").setValue(downloadUrl);
                 }
             })
             .addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception exception) {
                     // Handle unsuccessful uploads
                     // ...
                 }
             });

 }  private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onDestroy() {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        super.onDestroy();


    }

    private void loadintodatabase() {
        String str="image/"+id;
        final StorageReference riversRef = mStorageRef.child(str);
        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
               Log.d("Gfile",String.valueOf(uri));
                myRef.child("pic").setValue(String.valueOf(uri));
                Picasso.get().load(String.valueOf(uri)).into(imageView);// Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                loadimage();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

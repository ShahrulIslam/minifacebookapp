package com.example.user.myuber;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
private ImageButton logot,profile,findfriend,friend;
private EditText Data;
private String Status;
private String id;
private int FLAG;

private Button post,upload;
 private   FirebaseDatabase mData;
private ImageView imageView,imageView1;
    private Uri filePath;
    private List<Statuses>  Userlist = new ArrayList<>();//we will load this string list with my previos stats in database
    private DatabaseReference myRef;
    private StorageReference mStorageRef;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FLAG=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView=(ImageView) findViewById(R.id.pic);
        Data=(EditText) findViewById(R.id.textView);
        logot=(ImageButton) findViewById(R.id.button);
        findfriend=(ImageButton) findViewById(R.id.imageButton1);
        mData= FirebaseDatabase.getInstance();
        post=(Button) findViewById(R.id.button2);
        upload=(Button) findViewById(R.id.button3);

        imageView1=(ImageView) findViewById(R.id.imageView2);
        imageView1.setImageBitmap(null);
         RecyclerView rv= (RecyclerView) findViewById(R.id.recycle);//make a recycler view
        rv.setLayoutManager(new LinearLayoutManager(this));//setlayot of view
        profile=(ImageButton)findViewById(R.id.profile);
        final MyAdapter adapter=new MyAdapter(this, Userlist);//create an adapter for the recycler view and get the list into adapter
        rv.setAdapter(adapter);//make sre the adapter is set to the recycler view of yor interest
        Intent i = getIntent();//getting intent
        mStorageRef = FirebaseStorage.getInstance().getReference();
     id= i.getExtras().getString("id");//get Uid
        myRef = mData.getReference(id);//refer to the uid child in the database
        findfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in1=new Intent(Main2Activity.this, Main4Activity.class);//takes to a new activity
                in1.putExtra("id", id);//puts id with the key


                startActivity(in1);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//onclick listener for the profile button
                Intent in=new Intent(Main2Activity.this, Main3Activity.class);//takes to a new activity
                in.putExtra("id", id);//puts id with the key


                startActivity(in);//starts the activity
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//on click listener for post button this uploads the status in the database under the current users's child named Status
                Status=Data.getText().toString();
                if(!Status.isEmpty())
                {

                   final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());//methid to get string
                    myRef.child("Status").child(currentDateTimeString).child("Post").setValue(Status);//under the current
                    myRef.child("Status").child(currentDateTimeString).child("Image").setValue(null);// uid it will create a child Status and create a child for date and time and then et its value to the text written
                     if(FLAG==1){loadintodatabase(currentDateTimeString);}
                     imageView1.setImageBitmap(null);
                     Data.setText(null);
                     FLAG=0;
                    }

            }
        });
        logot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();//to sign out the  user before returning to the previous activity
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });

          final DatabaseReference myRef2=myRef.child("Status");//get a 2nd reference to th child Status
               myRef2.addValueEventListener(new ValueEventListener() {//add listener to vales of that child

                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {//snapshot holds value
                       myRef2.orderByKey();
                       Userlist.clear();// Result will be holded Here

                       for (DataSnapshot dsp : dataSnapshot.getChildren()) {


                        String post = String.valueOf(  dsp.child("Post").getValue());
                         String Pic = String.valueOf(dsp.child("Image").getValue());
                         String date=String.valueOf(dsp.getKey());
                               Statuses fire = new Statuses(post,date,Pic);

                               Userlist.add(fire);
                               Log.d("Post", String.valueOf(dsp.child("Post").getValue()));
                               Log.d("Pic", String.valueOf(dsp.child("Image").getValue()));
                           Log.d("key", String.valueOf(dsp.getKey()));
                         //populates result with current value os dspand adds it as an element to the arraylist

                       }
                       adapter.notifyDataSetChanged();//each time value of the child is changed the adapter is notified

                 //jst to print in the logcat
                   }


                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               }
               );
        String str2="image/"+id;//make a string for indicating an address in the firebase storage
        StorageReference riversRef1 = mStorageRef.child(str2);//initialize the firebase storage reference with the directory of 'str'
        riversRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),111);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 111 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {      FLAG=1;
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView1.setImageBitmap(bitmap);
                //loadimage();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }}}


    private void loadintodatabase(String str1) {
        final String str7=str1;
        String str="status/"+id+"/"+str1;
        final StorageReference riversRef4 = mStorageRef.child(str);
      //  if(filePath==null){Log.d("Gfile","nofile");}
        // Task<Uri> downloadUrl = riversRef.getDownloadUrl();
        riversRef4.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Task<Uri> uri=riversRef.getDownloadUrl();

                        //  myRef.child("Pic").setValue(downloadUrl);

                        riversRef4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                               // Log.d("Kfile",String.valueOf(uri));
                                //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                myRef.child("Status").child(str7).child("Image").setValue(String.valueOf(uri));
                                //Picasso.get().load(String.valueOf(uri)).into(imageView);// Got the download URL for 'users/me/profile.png'
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });





    }
}

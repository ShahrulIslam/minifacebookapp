package com.example.user.myuber;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Main5Activity extends AppCompatActivity {
    private List<Statuses> Userlist = new ArrayList<>();
    private DatabaseReference myRef;
    private RecyclerView mrecyele;
    private ImageView imageView,imageView1;
    private FirebaseDatabase mData;

    private String strpic;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView=(ImageView) findViewById(R.id.propic);
        setContentView(R.layout.activity_main5);
        mrecyele=(RecyclerView) findViewById(R.id.recycler);
        mrecyele.setLayoutManager(new LinearLayoutManager(this));
        //mrecyele.setAdapter(adp);
        mData=FirebaseDatabase.getInstance();
    i = getIntent();//getting intent
        final MyAdapter adap=new MyAdapter(this, Userlist);


        String id= i.getExtras().getString("id1");
        myRef=mData.getReference(id);
        mrecyele.setAdapter(adap);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              strpic=  String.valueOf(dataSnapshot.child("pic").getValue());
                if(strpic!=null) { Log.d("Main5",strpic);
                    imageView=(ImageView) findViewById(R.id.propic);
              Glide.with(Main5Activity.this)
                        .load(strpic)
                        .into(imageView);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference myRef2=myRef.child("Status");//get a 2nd reference to th child Status
        myRef2.addValueEventListener(new ValueEventListener() {//add listener to vales of that child

                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {//snapshot holds value
                                             myRef2.orderByKey();
                                             Userlist.clear();// Result will be holded Here

                                             for (DataSnapshot dsp : dataSnapshot.getChildren()) {


                                                 String name = String.valueOf(dsp.child("Post").getValue());
                                                 String Pic = String.valueOf(dsp.child("Image").getValue());
                                                 String date=String.valueOf(dsp.getKey());
                                                 Statuses fire = new Statuses(name,date,Pic);
                                                 Userlist.add(fire);
                                                 Log.d("Post", String.valueOf(dsp.child("Post").getValue()));
                                                 Log.d("Pic", String.valueOf(dsp.child("Image").getValue()));
                                                 Log.d("key", String.valueOf(dsp.getKey()));
                                                 //populates result with current value os dspand adds it as an element to the arraylist

                                             }
                                             adap.notifyDataSetChanged();//each time value of the child is changed the adapter is notified

                                             //jst to print in the logcat
                                         }


                                         @Override
                                         public void onCancelled(@NonNull DatabaseError databaseError) {

                                         }
                                     }
        );

      /*  */

    }

    @Override
    protected void onDestroy() {
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);




        super.onDestroy();

    }
}

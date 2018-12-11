package com.example.user.myuber;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {
private RecyclerView recyclerView;
private ArrayList<Friends> friends=new ArrayList<Friends>();
private FirebaseDatabase mData;
private Intent intent;
private String id1;
    private DatabaseReference mRef;
    @Override
    protected void onDestroy() {
        Intent intent=getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        super.onDestroy();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        mData=FirebaseDatabase.getInstance();
        mRef=mData.getReference();
        intent=getIntent();
        id1=intent.getExtras().getString("id");
        recyclerView=(RecyclerView)findViewById(R.id.recycle1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MyAdapter1 adapter=new MyAdapter1(this, friends);//create an adapter for the recycler view and get the list into adapter
        recyclerView.setAdapter(adapter);
         mRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 //friends=new ArrayList<>();
                 for(DataSnapshot dsp:dataSnapshot.getChildren())

                 {Log.d("id1", id1);

                      String email=String.valueOf(dsp.child("email").getValue());
                 String name=String.valueOf(dsp.getKey());
                 String pict=String.valueOf(dsp.child("pic").getValue());
                     Friends friend=new Friends(email,name,pict);
                     if(!id1.equals(friend.getName())) { Log.d("pic1", "got");
                     friends.add(friend);}
                     Log.d("key1", friend.getName());
                     Log.d("email1", friend.getEmail());
                     Log.d("pic1", friend.getPict());


                 }

                 adapter.notifyDataSetChanged();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
    }


}

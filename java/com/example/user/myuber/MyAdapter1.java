package com.example.user.myuber;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter1 extends RecyclerView.Adapter<MyViewHolder1>{
Context mContext;
ArrayList<Friends> friends;

    public MyAdapter1(Context mContext, ArrayList<Friends> friends) {
        this.mContext = mContext;
        this.friends = friends;
    }

    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.itemfriend, parent, false);//this is to tell the layout of each item
        return new MyViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {
    final Friends newfriends=friends.get(position);
     holder.text.setText(newfriends.getEmail());
        holder.text1.setText(newfriends.getName());
        Glide.with(mContext)
                .load(String.valueOf(newfriends.getPict()))
                .into(holder.image);
       holder.rellay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent in=new Intent(mContext, Main5Activity.class);
               String strName =newfriends.getName();
               in.putExtra("id1", strName);

               mContext.startActivity(in);
           }
       });// holder.image.setImageBitmap();
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}

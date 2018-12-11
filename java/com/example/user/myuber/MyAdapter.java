package com.example.user.myuber;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context c;
   List<Statuses> numbers;//two variables one context to get crrent context to start from and another the data to populate

    public MyAdapter(Context c, List<Statuses> numbers) {//Constructor with the two variables
        this.c = c;
        this.numbers = numbers;
    }
// the next three override methods can be generated
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.itemlist, parent, false);//this is to tell the layout of each item
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //BIND DATA
        Statuses mylist = numbers.get(position);
        holder.nameTxt.setText(mylist.getStatus());

        holder.Dates.setText(mylist.getDate());//to set text of thhe textview in the current position to the value of numbers which is a String obtained from
        //Picasso.get().load(String.valueOf(mylist.getPics())).into(holder.img);
        Glide.with(c)
                .load(String.valueOf(mylist.getPics()))
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
      //  int arr = 0;

        return numbers.size();
    }
}
package com.example.user.myuber;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public  class MyViewHolder1 extends RecyclerView.ViewHolder {
    TextView text,text1;
    ImageView image;
    RelativeLayout rellay;
    public MyViewHolder1(View v) {
        super(v);
        rellay=(RelativeLayout) itemView.findViewById(R.id.rellay);
        text=(TextView) v.findViewById(R.id.email1);
        text1=(TextView) v.findViewById(R.id.id);
        image=(ImageView) v.findViewById(R.id.image1);
    }
}

package com.example.user.myuber;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView nameTxt,Dates;
   ImageView img;

    public MyViewHolder(View itemView) {
        super(itemView);

        img= (ImageView) itemView.findViewById(R.id.pics);
        nameTxt = (TextView) itemView.findViewById(R.id.message1);
       Dates = (TextView) itemView.findViewById(R.id.message);

    }

}
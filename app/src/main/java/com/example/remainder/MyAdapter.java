package com.example.remainder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.transition.Hold;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {

    String data1[], data2[] ;
    int images[] ;
    Context context ;

    public MyAdapter(Context ct, String consData1[], String consData2[], int consImages[]){
        this.context = ct ;
        this.data1 = consData1 ;
        this.data2 = consData2 ;
        this.images = consImages ;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.descriptions.setText(data1[position]);
        holder.categories.setText(data2[position]);
        holder.image.setImageResource(images[position]);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DateTime.class) ;
                intent.putExtra("category", data2[position]) ;
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView descriptions, categories ;
        ImageView image ;
        ConstraintLayout constraintLayout ;
        public Holder(@NonNull View itemView) {
            super(itemView);
            descriptions = itemView.findViewById(R.id.describe) ;
            categories = itemView.findViewById(R.id.categories);
            image = itemView.findViewById(R.id.image_view) ;
            constraintLayout = itemView.findViewById(R.id.my_row) ;
        }
    }
}

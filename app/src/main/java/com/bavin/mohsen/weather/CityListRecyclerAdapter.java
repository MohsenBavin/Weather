package com.bavin.mohsen.weather;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CityListRecyclerAdapter
        extends RecyclerView.Adapter<CityListRecyclerAdapter.MyViewHolder>
       {

    ImageView ivDeleteItem;

    List<String> cityNamesList=new ArrayList<>();

    public CityListRecyclerAdapter(List<String> cityNames) {
        cityNamesList=cityNames;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.city_layout_item,viewGroup,false);

        MyViewHolder holder;
        holder=new MyViewHolder( view );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String s=cityNamesList.get(i);
        myViewHolder.textViewCity.setText(s);

    }

    @Override
    public int getItemCount() {
        return cityNamesList.size();
    }




    static class MyViewHolder extends RecyclerView.ViewHolder {
        Context context;
        TextView textViewCity;
        ImageView imageDlte;
        TextView textView;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            textViewCity = itemView.findViewById(R.id.txtCityList_name);
            imageDlte = itemView.findViewById(R.id.img_deleteCity);



            imageDlte.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position =  getAdapterPosition();
                    Intent intent = new Intent(itemView.getContext() , ManageCityActivity.class);
                    intent.putExtra( "mode2",position );
                    intent.putExtra( "mode","delete" );
                    itemView.getContext().startActivity(intent);


                }
            } );
        }
    }
}

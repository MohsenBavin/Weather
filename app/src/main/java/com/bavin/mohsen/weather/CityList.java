package com.bavin.mohsen.weather;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.weather.currentGson.CurrentWeather;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CityList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        //setData();
        List<String> cityNames= new ArrayList<>(  );



        SQLcity db = new SQLcity(this, "city_db", null, 1);
        cityNames = db.getCityArray();

        RecyclerView recyclerViewCountry=findViewById(R.id.recycleCityDay);
        StateCityAdapter adapter =new StateCityAdapter( cityNames);
        recyclerViewCountry.setAdapter(adapter);
        recyclerViewCountry.setLayoutManager(new LinearLayoutManager(CityList.this,LinearLayoutManager.VERTICAL,false));



    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int getRun =
                PreferenceManager.getDefaultSharedPreferences(this).getInt("firstRun",-1) ;
        if(getRun==-1){
            PreferenceManager.getDefaultSharedPreferences( this ).edit().putInt( "firstRun",2 ).apply();

            Intent intentStart=new Intent( CityList.this,MainActivity.class );
            intentStart.putExtra( "mode2",0);
            intentStart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity( intentStart );
            finish();

        }
        else {
            Intent intentEnd=new Intent( CityList.this,MainActivity.class );
            intentEnd.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity( intentEnd );
            finish();
        }
    }


    public class StateCityAdapter extends RecyclerView.Adapter<StateCityAdapter.MyStateViewHolder> {

         List<String> citiesName= new ArrayList<>(  );



        public StateCityAdapter(List<String> citiesName){
            this.citiesName=citiesName;

        }
        @NonNull
        @Override
        public MyStateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.state_city_item,viewGroup,false);

            MyStateViewHolder  holder;
            holder=new MyStateViewHolder( view );
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyStateViewHolder myStateViewHolder, int i) {

            //***************************
            final GenClass genClass =new GenClass();
            AsyncHttpClient client=new AsyncHttpClient();
            String url="http://api.openweathermap.org/data/2.5/weather?q="+citiesName.get( i )+"&units=metric&APPID=4bb8c0a2d5207309282559a6c8cb4670";
            client.get(url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        String report = response.toString();
                        Gson gson = new Gson();
                        CurrentWeather current = gson.fromJson( response.toString(), CurrentWeather.class );
                        String country = current.getSys().getCountry();
                        String city = current.getName();
                        String st = city + "," + country;
                        myStateViewHolder.textCity.setText( st );
                        String icon = current.getWeather().get( 0 ).getIcon();
                        int id_icon = current.getWeather().get( 0 ).getId();
                        int iconId = genClass.getWeatherIcon(icon , id_icon);
                        myStateViewHolder.imageIcon.setImageResource( iconId );
                        String description = current.getWeather().get( 0 ).getDescription();
                        myStateViewHolder.textDescription.setText( description );
                        double temp = current.getMain().getTemp() ;
                        myStateViewHolder.textTemp.setText(String.format("%.0f %s", temp, Html.fromHtml("&#8451;")));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });


            //***************************
        }

        @Override
        public int getItemCount() {
           // return citiesName.size();
            return citiesName.size();
        }

        public class MyStateViewHolder extends RecyclerView.ViewHolder{
            TextView textCity,textDescription,textTemp;
            ImageView imageIcon;
            CardView cardView;


            public MyStateViewHolder(@NonNull View itemView) {
                super( itemView );
                textCity=itemView.findViewById( R.id.txtCityNameDay );
                textDescription=itemView.findViewById( R.id.txtCityDescriptionDay );
                textTemp=itemView.findViewById( R.id.txtCityTempDay );
                imageIcon=itemView.findViewById( R.id.imgStateDay );
                cardView=itemView.findViewById( R.id.cardState );
                cardView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int getRun =
                                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getInt("firstRun",-1) ;
                        if(getRun==-1) {
                            PreferenceManager.getDefaultSharedPreferences( getBaseContext() ).edit().putInt( "firstRun", 2 ).apply();
                            int position = getAdapterPosition();
                            Intent intent = new Intent( CityList.this, MainActivity.class );
                            intent.putExtra( "mode2", position );
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity( intent );
                            finish();
                        }
                        else {

                            int position = getAdapterPosition();
                            Intent intent = new Intent( CityList.this, MainActivity.class );
                            intent.putExtra( "mode2", position );
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity( intent );
                            finish();
                        }

                    }
                } );
            }
        }

    }



}

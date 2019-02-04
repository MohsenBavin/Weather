package com.bavin.mohsen.weather;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bavin.mohsen.weather.currentGson.CurrentWeather;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class SearchCityFragment extends Fragment {
    EditText searchNameCity;
    Button addButton,cancelButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search_city,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      searchNameCity=view.findViewById(R.id.edt_search_city);
      addButton=view.findViewById( R.id.btnAddCity );
      cancelButton=view.findViewById( R.id.btnCancelCity );



      addButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String name=searchNameCity.getText().toString();
              if (name.length()==0){
                 // Toast.makeText(getContext(),"please insert city name ",Toast.LENGTH_LONG).show();
                  Toasty.error(getContext(), "There is no city in your list", Toast.LENGTH_SHORT, true).show();

              }
              else {
                  setData(name);
              }
          }
      });

      cancelButton.setOnClickListener( new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             // startActivity( new Intent( getActivity(),MainActivity.class ) );
              int getRun =
                      PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("firstRun",-1) ;
              if(getRun==-1){
                  Toasty.error(getContext(), "There is no city in your list", Toast.LENGTH_SHORT, true).show();

                  // Toast.makeText( getContext(),"There is no city in your list",Toast.LENGTH_LONG ).show();
              }
              else {
                  getActivity().finish();
              }
          }
      } );
    }

    private void setData( String cityNow) {

        String url="http://api.openweathermap.org/data/2.5/weather?q="+cityNow+"&APPID=4bb8c0a2d5207309282559a6c8cb4670";
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);


                try {
                    Gson gson = new Gson();
                    CurrentWeather current = gson.fromJson(response.toString(), CurrentWeather.class);
                   String city=current.getName();
                   double lon=current.getCoord().getLon();
                   String lonSt=""+lon;
                    double lat=current.getCoord().getLat();
                    String latSt=""+lat;
                    SQLcity db = new SQLcity(getContext(), "city_db", null, 1);
                    //String cityCoun=city+","+country;
                    String cityName = db.getCityNames();
                    if(cityName.contains(city)){
                       // Toast.makeText(getContext(), city+"Has been added", Toast.LENGTH_LONG).show();
                        Toasty.warning(getContext(), city+" has been added before", Toast.LENGTH_SHORT, true).show();

                    }
                    else {
                        db.insertToDB(city,lonSt,latSt);
                        //Toasty.success(getContext(), city+"Has been add successfully", Toast.LENGTH_SHORT, true).show();

                        startActivity( new Intent( getActivity(),CityList.class ) );
                            getActivity().finish();


                    }



                }
                catch (Exception e){

                    Toasty.error(getContext(), "Sorry please check the entered city name or your internet connection", Toast.LENGTH_SHORT, true).show();

                   // Toast.makeText(getContext(),"Sorry Please check your city name or Internet ",Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Toasty.error(getContext(), "Sorry please check the entered city name or your internet connection", Toast.LENGTH_SHORT, true).show();

                //Toast.makeText(getContext(),"Sorry Please check your city name or Internet ",Toast.LENGTH_LONG).show();
            }
        }

        );

    }


}

package com.bavin.mohsen.weather;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.weather.currentGson.CurrentWeather;
import com.bavin.mohsen.weather.forecast.Forecast;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class NowFragment extends Fragment {

    TextView cityName_now , degree_now ,description_now,date_now,lowTemp_now,hiTemp_now
            ,textPressure  ,textHumidity,textWind ,textSunrise,textSunset,texTest;
    ImageView stateIcon_now,locationImg;
    RecyclerView recycler;
    RelativeLayout relText , rel_now ,rel_press,rel_wind,rel_hum ,rel_sun;
    LinearLayout lin_sun;
    FrameLayout frameLayout;
    TextView textTab;
    private String cityLon="";
    private String cityLat="";
    int iconId;
    GenClass genClass;
    int location;
    private String iconMode="01d 02d 03d 04d 09d 10d 11d 13d 50d";



    public static NowFragment newNowFragment(String cityLon , String cityLat ,int location) {
        NowFragment nowFragmentIntc=new NowFragment();
        Bundle args=new Bundle();
        args.putString("lon",cityLon);
        args.putString("lat",cityLat);
        args.putInt( "location",location );
        nowFragmentIntc.setArguments(args);
        return nowFragmentIntc;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cityLon=getArguments().getString("lon");
        this.cityLat=getArguments().getString("lat");
        this.location=getArguments().getInt( "location" );


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View view= inflater.inflate(R.layout.fragment_now, container, false);

     return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        rel_now=view.findViewById( R.id.rlv_now_1 );
        rel_hum=view.findViewById( R.id.rlv_hum );
        rel_sun=view.findViewById( R.id.rlv_sun );
        rel_press=view.findViewById( R.id.rlv_press );
        rel_wind=view.findViewById( R.id.rlv_wind );
        lin_sun=view.findViewById( R.id.lin_sun );
        frameLayout=view.findViewById( R.id.frameLayout );
        cityName_now=view.findViewById(R.id.txtCityNow);
        degree_now=view.findViewById(R.id.txtDegreeNow);
        description_now=view.findViewById(R.id.txtDescriptionNow);
        stateIcon_now=view.findViewById(R.id.imgNowState);
        date_now=view.findViewById(R.id.txtDateNow);
        hiTemp_now=view.findViewById(R.id.hi_temp_now);
        lowTemp_now=view.findViewById(R.id.low_temp_now);
        textPressure=view.findViewById( R.id.txt_press_value );
        textHumidity=view.findViewById( R.id.txt_Hum_value );
        textWind=view.findViewById( R.id.txt_Wind_Speed );
        textTab=view.findViewById( R.id.txttab );
        relText=view.findViewById(R.id.citytab);
        textTab=view.findViewById( R.id.txttab );
        locationImg=view.findViewById( R.id.imgLocation );
        textSunrise=view.findViewById(R.id.txt_sunrise);
        textSunset=view.findViewById( R.id.txt_sunset );
        recycler=view.findViewById( R.id.rcycle_hourly );

        if(location==1) locationImg.setImageResource( R.drawable.my_location_white_36x36 );
        relText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CityList.class));
            }
        });

        setData(cityLon,cityLat);
        setForecastData(cityLon,cityLat);

        //**//


    }


    private void setData(String cityLon , String cityLat) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd   HH:mm");
        String currentDateandTime = sdf.format(new Date());
        date_now.setText(currentDateandTime);

        genClass =new GenClass();

        String url="http://api.openweathermap.org/data/2.5/weather?lat="+cityLat+"&lon="+cityLon+
                "&units=metric&APPID=4bb8c0a2d5207309282559a6c8cb4670";

        AsyncHttpClient client=new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                String report=response.toString();
                Gson gson = new Gson();
                CurrentWeather current = gson.fromJson(response.toString(), CurrentWeather.class);

                String country = current.getSys().getCountry();
                String city=current.getName();
                cityName_now.setText(city+","+country);
                textTab.setText(city );

                String icon = current.getWeather().get(0).getIcon();
                int id_icon= current.getWeather().get(0).getId();
                iconId = genClass.getWeatherIcon(icon , id_icon);
                stateIcon_now.setImageResource(iconId);
                int index = iconMode.indexOf(icon);
                if(index==-1){
                    frameLayout.setBackgroundColor(getResources().getColor(  R.color.nightColor  ));
                    rel_now.setBackgroundColor(getResources().getColor(  R.color.whiteColor  ));
                    rel_press.setBackgroundColor(getResources().getColor(  R.color.whiteColor  ));
                    rel_wind.setBackgroundColor(getResources().getColor(  R.color.whiteColor  ));
                    rel_hum.setBackgroundColor(getResources().getColor(  R.color.whiteColor  ));
                    rel_sun.setBackgroundColor(getResources().getColor(  R.color.whiteColor  ));
                    recycler.setBackgroundColor(getResources().getColor(  R.color.whiteColor  ));

                }else {
                    frameLayout.setBackgroundColor(getResources().getColor(  R.color.dayColor  ));
                    rel_now.setBackgroundColor(getResources().getColor(  R.color.dayCardColor  ));
                    rel_press.setBackgroundColor(getResources().getColor(  R.color.dayCardColor  ));
                    rel_wind.setBackgroundColor(getResources().getColor(  R.color.dayCardColor  ));
                    rel_hum.setBackgroundColor(getResources().getColor(  R.color.dayCardColor  ));
                    rel_sun.setBackgroundColor(getResources().getColor(  R.color.dayCardColor  ));
                    recycler.setBackgroundColor(getResources().getColor(  R.color.dayCardColor  ));

                }



                String description=current.getWeather().get(0).getDescription();
                description_now.setText(description);


                double temp = current.getMain().getTemp();
                degree_now.setText(String.format("%.0f %s", temp, Html.fromHtml("&#8451;")));

                double temp_hi_now=current.getMain().getTempMax();
                hiTemp_now.setText(String.format("%.0f %s", temp_hi_now, Html.fromHtml("&#8451;")));

                double temp_low_now=current.getMain().getTempMin();
                lowTemp_now.setText(String.format("%.0f %s", temp_low_now, Html.fromHtml("&#8451;")));

                double press=current.getMain().getPressure();
                textPressure.setText( String.format("%.0f %s", press, "hPa" ));

                double humid=current.getMain().getHumidity();
                textHumidity.setText( String.format("%.1f %s", humid, " %") );

                double speed=current.getWind().getSpeed();
                textWind. setText( String.format("%.0f %s", speed, "m/s") );
                long sunrise=current.getSys().getSunrise();
                long sunset=current.getSys().getSunset();
                textSunrise.setText( sunTime( sunrise ) );
                textSunset.setText( sunTime( sunset ) );
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toasty.error(getContext(), "Sorry please check your internet connection", Toast.LENGTH_SHORT, true).show();

                //Toast.makeText(getContext(),"Sorry Please check your internet connection ",Toast.LENGTH_LONG).show();
            }
        });
    }

    //****************************************************************************//

    private String sunTime(long sunTimes){
        DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sunTimes * 1000);
        String sunSet=formatter.format(calendar.getTime());
        String[] substrings = sunSet.split(" ");
        return substrings[4];
    }
   //**************************************************************************//
   private void setForecastData(String cityLon , String cityLat) {

       String url="http://api.openweathermap.org/data/2.5/forecast?lat="+cityLat+"&lon="+cityLon+
               "&units=metric&APPID=4bb8c0a2d5207309282559a6c8cb4670";

       AsyncHttpClient client=new AsyncHttpClient();
       client.get(url, new JsonHttpResponseHandler() {
           @Override
           public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               super.onSuccess(statusCode, headers, response);
               List<Double> tempList=new ArrayList<>(  );
               List<Integer> idIconList=new ArrayList<>(  );
               List<String> timeList=new ArrayList<>(  );
               List<String> dateList=new ArrayList<>(  );

                GenClass gen =new GenClass();

                Gson gson = new Gson();

              Forecast forecast = gson.fromJson(response.toString()
                      .replace("list","next"  ), Forecast.class);
              int cnt=forecast.getCnt();
              for(int i=0;i<=cnt-1;i++) {
                  tempList.add( forecast.getNext().get( i ).getMain().getTemp() );

                   String iconSt =forecast.getNext().get(i).getWeather().get( 0 ).getIcon();
                   int id_icon= forecast.getNext().get(i).getWeather().get( 0 ).getId();
                   idIconList.add(gen.getWeatherIcon(iconSt , id_icon));

                  String dateTime=forecast.getNext().get( i ).getDtTxt();
                  String[] substrings = dateTime.split(" ");
                  dateList.add(substrings[0]  );
                  timeList.add( substrings [1]);

               }


               HourRecyclerAdapter adapterhr =new HourRecyclerAdapter(tempList,idIconList,timeList,dateList);
               recycler.setAdapter( adapterhr );
               recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));



           }


           @Override
           public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
               super.onFailure(statusCode, headers, throwable, errorResponse);

               Toasty.error(getContext(), "Sorry please check your internet connection", Toast.LENGTH_SHORT, true).show();

               //Toast.makeText(getContext(),"Sorry Please check your internet connection ",Toast.LENGTH_LONG).show();
           }
       });



   }


   //*****************************************************************************//

    public class HourRecyclerAdapter extends RecyclerView.Adapter<HourRecyclerAdapter.MyHourViewHolder>{

        List<Double> tempList=new ArrayList<>(  );
        List<Integer> idIconList=new ArrayList<>(  );
        List<String> timeList=new ArrayList<>(  );
        List<String> dateList=new ArrayList<>(  );



        public HourRecyclerAdapter(List<Double> tempList,List<Integer> idIconList,List<String> timeList,List<String> dateList){
            this.tempList=tempList;
            this.idIconList=idIconList;
            this.timeList=timeList;
            this.dateList=dateList;
        }

        @NonNull
        @Override
        public MyHourViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from( viewGroup.getContext() )
                    .inflate( R.layout.hourly_item,viewGroup,false );
            MyHourViewHolder holder;
            holder=new MyHourViewHolder( view );
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyHourViewHolder myHourViewHolder, int i) {
            myHourViewHolder.textTemp.setText(String.format("%.0f %s", tempList.get(i), Html.fromHtml("&#8451;")));
            myHourViewHolder.imageIcon.setImageResource( idIconList.get(i) );
            myHourViewHolder.textTime.setText( timeList.get( i ) );
            myHourViewHolder.textDate.setText( dateList.get( i ) );

        }

        @Override
        public int getItemCount() {
            return tempList.size();
        }

        public class MyHourViewHolder extends RecyclerView.ViewHolder{

            TextView textTime,textDate,textTemp;
            ImageView imageIcon;
            public MyHourViewHolder(@NonNull View itemView) {
                super( itemView );
                textTemp=itemView.findViewById( R.id.txt_Temp );
                imageIcon=itemView.findViewById( R.id.img_hourly );
                textTime=itemView.findViewById( R.id.txt_hourly );
                textDate=itemView.findViewById( R.id.txt_date );

            }
        }
    }
}

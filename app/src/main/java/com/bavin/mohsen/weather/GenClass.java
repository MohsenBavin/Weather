package com.bavin.mohsen.weather;

import android.content.Context;

public class GenClass {
    Context context;

    public int getWeatherIcon(String icon , int id_icn) {
        int iconRes=0,idth;
        idth=id_icn/100;
        Boolean isMatch = false;

        switch (idth) {
            case 8:
                if (id_icn==800){
                    if ((isMatch = icon.equals("01d"))==true) iconRes=(R.drawable.w_01d);
                    if ((isMatch = icon.equals("01n"))==true) iconRes=(R.drawable.w_01n);
                    if ((isMatch = icon.equals("02d"))==true) iconRes=(R.drawable.w_02d);
                    if ((isMatch = icon.equals("02n"))==true) iconRes=(R.drawable.w_02n);
                }
                if (id_icn==801){
                    if ((isMatch = icon.equals("02d"))==true) iconRes=(R.drawable.w_02d);
                    if ((isMatch = icon.equals("02n"))==true) iconRes=(R.drawable.w_02n);
                }
                if (id_icn==802){
                    if ((isMatch = icon.equals("03d"))==true) iconRes=(R.drawable.w_03d);
                    if ((isMatch = icon.equals("03n"))==true) iconRes=(R.drawable.w_03n);
                }

                if (id_icn==803 )   iconRes=(R.drawable.w_04dn);
                if (id_icn==804 )   iconRes=(R.drawable.w_04dn);
                break;
            case 7:
                if (id_icn==711 )  iconRes=(R.drawable.smoke);
                if (id_icn==741 )  iconRes=(R.drawable.fog);
                if (id_icn==721 )  iconRes=(R.drawable.haze);
                if (id_icn==761 )  iconRes=(R.drawable.dust);
                else  iconRes=(R.drawable.haze);
                break;

            case 6:
                if (id_icn==601 || id_icn==602  )  iconRes=(R.drawable.snow);
                if (id_icn==616 || id_icn==615  )  iconRes=(R.drawable.rain_snow);
                if (id_icn==611 || id_icn==612  )  iconRes=(R.drawable.sleet);
                if (id_icn==621 || id_icn==622  )  iconRes=(R.drawable.shower);
                if (id_icn==620 )  iconRes=(R.drawable.light_snow);
                if (id_icn==600 )  iconRes=(R.drawable.snow_light);
                else  iconRes=(R.drawable.light_snow);
                break;

            case 5:
                if (id_icn==501 || id_icn==500  )  iconRes=(R.drawable.dizzle_rain);
               // if (id_icn==502 || id_icn==503 || id_icn==504 || id_icn==531  )  iconRes=(R.drawable.rainy);
                if (id_icn==511 )  iconRes=(R.drawable.rain_snow);
               // if (id_icn==600 )  iconRes=(R.drawable.snow_light);
                else iconRes=(R.drawable.rainy);
                break;

            case 3:
                if (id_icn==321){
                    if ((isMatch = icon.equals("09d"))==true) iconRes=(R.drawable.light_dizzle_d);
                    if ((isMatch = icon.equals("09n"))==true) iconRes=(R.drawable.light_dizzle_n);
                }

                if (id_icn==300){
                    if ((isMatch = icon.equals("09d"))==true) iconRes=(R.drawable.light_dizzle_d);
                    if ((isMatch = icon.equals("09n"))==true) iconRes=(R.drawable.light_dizzle_n);
                }

                if (id_icn==301 )   iconRes=(R.drawable.drizzle);
                else iconRes=(R.drawable.dizzle_rain);
                break;

            case 2:
                if (id_icn==200){
                    if ((isMatch = icon.equals("11d"))==true) iconRes=(R.drawable.thunderstorm_d);
                    if ((isMatch = icon.equals("11n"))==true) iconRes=(R.drawable.thunderstorm_n);
                }

                else iconRes=(R.drawable.thunderstorm);

                break;


        }
        return iconRes;
    }


}

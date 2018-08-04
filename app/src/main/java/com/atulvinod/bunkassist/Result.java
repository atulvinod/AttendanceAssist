package com.atulvinod.bunkassist;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Result extends AppCompatActivity {

    public static int mon,tue,wed,thu,fri;
    SharedPreferences pref;
    TextView att,disc;
    static int bunkvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        if(getIntent().getExtras()!=null) {
            Bundle data = getIntent().getExtras();
            String currentDate = data.getString("CURRENT");
            float percent = data.getFloat("PERCENT");
            String startDate = data.getString("START");
            String endDate = data.getString("END");
            if (data.getInt("BUNK") == 0) {
                bunkvalue = 0;
            } else {
                bunkvalue = data.getInt("BUNK");
            }

            pref = getSharedPreferences(MainActivity.CONFIG, 0);
            mon = pref.getInt(MainActivity.MONDAY_KEY, 1);
            tue = pref.getInt(MainActivity.TUE_KEY, 1);
            wed = pref.getInt(MainActivity.WED_KEY, 1);
            thu = pref.getInt(MainActivity.THU_KEY, 1);
            fri = pref.getInt(MainActivity.FRI_KEY, 1);
            att = (TextView) findViewById(R.id.res);
            disc = (TextView) findViewById(R.id.attendanceDisc);
            if (bunkvalue == 0) {
                disc.setText("Your attendance calculated with start of semister at " + startDate.replace(" ", "/") + " and end of semister " + endDate.replace(" ", "/") + " and current date is set as " + currentDate.replace(" ", "/"));
            } else {
                disc.setText("Your attendance calculated with start of semister at " + startDate.replace(" ", "/") + " and end of semister " + endDate.replace(" ", "/") + " and current date is set as " + currentDate.replace(" ", "/") + " with " + bunkvalue + " classes bunked");
            }
            float prediction = attendancePredict(percent, startDate, endDate, currentDate);

            if(prediction<0){
                Toast.makeText(getApplicationContext(),"You are bunking more classes than the classes that will be left",Toast.LENGTH_LONG).show();
                att.setText(":/ %");
                disc.setText("Set a different value of classes to bunk , There are only "+getClassesBetweenTwoDates(dateFormatter(currentDate),dateFormatter(endDate))+" classes that you can bunk");
                return;
            } else if (prediction > 75 && prediction < 90) {
                    att.setTextColor(getResources().getColor(R.color.yellow));

                } else if (prediction > 90) {
                    att.setTextColor(getResources().getColor(R.color.green));
                } else if (prediction < 75) {
                    att.setTextColor(getResources().getColor(R.color.red));
                }
                att.setText(prediction + "%");

            }else{
                finish();
            }



    }

    public static int getClassesBetweenTwoDates(Date startDate, Date endDate) {
        int cycle = 0;
        int classes = 0;
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int workDays = 0;

        //Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 0;
        }

        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }


        do {
            //excluding start date
            cycle++;
            int day = startCal.get(Calendar.DAY_OF_WEEK);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++workDays;
            }
            switch(day){
                case Calendar.MONDAY:
                    classes += mon;
                    break;
                case Calendar.TUESDAY:
                    classes += tue;
                    break;
                case Calendar.WEDNESDAY:
                    classes += wed;
                    break;
                case Calendar.THURSDAY:
                    classes += thu;
                    break;
                case Calendar.FRIDAY:
                    classes += fri;
                    break;
                default:
                    break;

            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);

        } while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()); //excluding end date

        return classes;
    }


    static public int getDecrement(Date now){
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        switch(c.get(Calendar.DAY_OF_WEEK)){
            default:
                return 0;

            case Calendar.MONDAY:
                return mon;


            case Calendar.TUESDAY:
                return tue;


            case Calendar.WEDNESDAY:

                return wed;


            case Calendar.THURSDAY:

                return thu;


            case Calendar.FRIDAY:
                return fri;





        }
    }

    public static float attendancePredict(float percent,String start,String end,String now){


        float attended = Math.round((percent*getClassesBetweenTwoDates(dateFormatter(start), dateFormatter(now)))/100);
        float total = getClassesBetweenTwoDates(dateFormatter(start), dateFormatter(end));
        float left = getClassesBetweenTwoDates(dateFormatter(now),dateFormatter(end))-getDecrement(dateFormatter(now));
        float predict = Math.round(((attended+left-bunkvalue)/total)*100);
       return predict;

    }

    public static Date dateFormatter(String date){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        Date formattedDate = null;
        try{
            formattedDate = myFormat.parse(date);
        }catch(Exception e){
            e.printStackTrace();
        }
        return formattedDate;
    }



}

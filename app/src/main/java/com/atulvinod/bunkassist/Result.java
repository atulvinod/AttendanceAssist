package com.atulvinod.bunkassist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Result extends AppCompatActivity {

    public static int mon,tue,wed,thu,fri;
    public int DELIVERED = 0,ATTENDED = 0,LEFT =0,TOTAL = 0;
    SharedPreferences pref;
    TextView att,disc;
    static int bunkvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Button details = (Button)findViewById(R.id.details);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent det = new Intent(getApplicationContext(),Details.class);
                Bundle b = new Bundle();
                b.putInt("DELIVERED",DELIVERED);
                b.putInt("ATTENDED",ATTENDED);
                b.putInt("TOTAL",TOTAL);
                b.putInt("LEFT",LEFT);
                det.putExtras(b);


                startActivity(det);
            }
        });
        details.setVisibility(View.INVISIBLE);

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
            } else if (prediction >= 75 && prediction <= 90) {
                    att.setTextColor(getResources().getColor(R.color.yellow));

                } else if (prediction >= 90) {
                    att.setTextColor(getResources().getColor(R.color.green));
                } else if (prediction < 75) {
                    att.setTextColor(getResources().getColor(R.color.red));
                }
                details.setVisibility(View.VISIBLE);
                att.setText(prediction + "%");

            }else{
                finish();
            }



    }

    public static int getClassesBetweenTwoDates(Date startDate, Date endDate) {
        Calendar start  = Calendar.getInstance();
        start.setTime(startDate);

        Calendar end  = Calendar.getInstance();
        end.setTime(endDate);
        int SUNDAYS = 0;
        int SATURDAYS =0;
        int CLASS = 0;
        int WORKING=0;
        do{


            switch(start.get(Calendar.DAY_OF_WEEK)){
                case Calendar.MONDAY:
                    CLASS += mon;
                    WORKING++;
                    break;
                case Calendar.TUESDAY:
                    CLASS += tue;
                    WORKING++;
                    break;
                case Calendar.WEDNESDAY:
                    CLASS += wed;
                    WORKING++;
                    break;
                case Calendar.THURSDAY:
                    CLASS += thu;
                    WORKING++;
                    break;
                case Calendar.FRIDAY:
                    CLASS += fri;
                    WORKING++;
                    break;
                case Calendar.SATURDAY:
                    SATURDAYS++;

                    break;
                case Calendar.SUNDAY:
                    SUNDAYS++;
                    break;

            }



            start.add(Calendar.DAY_OF_MONTH, 1);
        }while(start.getTimeInMillis()<=end.getTimeInMillis());
        Log.d("CALCULATE WORKING","WORKING DAYS "+WORKING);

        return CLASS;

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

    public  float attendancePredict(float percent,String start,String end,String now){

        float delivered = getClassesBetweenTwoDates(dateFormatter(start),dateFormatter(now));
        float attended = ((percent*getClassesBetweenTwoDates(dateFormatter(start), dateFormatter(now)))/100);
        float total = getClassesBetweenTwoDates(dateFormatter(start), dateFormatter(end));
        float left = getClassesBetweenTwoDates(dateFormatter(now),dateFormatter(end))-getDecrement(dateFormatter(now));
        float predict = Math.round(((attended+left-bunkvalue)/total)*100);
        Log.d("PREDICT","TOTAL CLASS "+total);
        Log.d("PREDICT","LEFT "+left);
        Log.d("PREDICT","PREDICTION "+predict);
        Log.d("PREDICT","ATTENDED "+attended);
        Log.d("PREDICT","DELIVERED DECREMENTED"+(delivered-getDecrement(dateFormatter(now))));
        Log.d("PREDICT","DELIVERED "+delivered);
//        createDialog("Total Classes "+total+"\n"+"Total Classes left "+left+"\n"+"Classes Attended"+attended+"\n"+"Delivered"+delivered+"\n");
        TOTAL = (int)total;
        LEFT = (int)left;
        ATTENDED = (int)attended;
        DELIVERED = (int)delivered;

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

    public  void createDialog(String text){
        AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
        alert.setTitle("Analysis");
        alert.setCancelable(true);
        alert.setMessage(text);
        alert.create();
        alert.show();
    }



}

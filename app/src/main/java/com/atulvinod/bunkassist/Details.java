package com.atulvinod.bunkassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle b = getIntent().getExtras();
        int LEFT = b.getInt("LEFT");
        int DELIVERED = b.getInt("DELIVERED");
        int TOTAL = b.getInt("TOTAL");
        int ATTENDED = b.getInt("ATTENDED");
        TextView leftView,deliveredView,totalView,attendedView;
        leftView = (TextView)findViewById(R.id.leftCount);
        deliveredView = (TextView)findViewById(R.id.deliveredView);
        totalView = (TextView)findViewById(R.id.totalCount);
        attendedView = (TextView)findViewById(R.id.attendedCount);
        leftView.setText(""+LEFT);
        deliveredView.setText(""+DELIVERED);
        totalView.setText(""+TOTAL);
        attendedView.setText(""+ATTENDED);
    }
}

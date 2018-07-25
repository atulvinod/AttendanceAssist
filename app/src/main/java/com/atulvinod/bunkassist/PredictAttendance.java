package com.atulvinod.bunkassist;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PredictAttendance.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PredictAttendance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PredictAttendance extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button start,end,current,predict;
    String startDate,endDate,currentDateRegistered;
    EditText percent;

    private OnFragmentInteractionListener mListener;

    public PredictAttendance() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PredictAttendance.
     */
    // TODO: Rename and change types and number of parameters
    public static PredictAttendance newInstance(String param1, String param2) {
        PredictAttendance fragment = new PredictAttendance();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_predict_attendance, container, false);
        start = (Button)v.findViewById(R.id.start);
         end = (Button)v.findViewById(R.id.end);
         current = (Button)v.findViewById(R.id.currentDate);
         predict = (Button)v.findViewById(R.id.predict);
        percent = (EditText)v.findViewById(R.id.res);

         predict.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if(startDate!=null&&endDate!=null&&currentDateRegistered!=null&&startDate!=" "&&endDate!=" "&&currentDateRegistered!=" "){

                     if(Result.dateFormatter(currentDateRegistered).compareTo(Result.dateFormatter(startDate))>0&&Result.dateFormatter(currentDateRegistered).compareTo(Result.dateFormatter(endDate))<0){
                         Intent i = new Intent(getContext(), Result.class);
                         Bundle data = new Bundle();

                         data.putFloat("PERCENT", Float.parseFloat(percent.getText().toString()));
                         data.putString("CURRENT", currentDateRegistered);
                         data.putString("START", startDate);
                         data.putString("END", endDate);
                         i.putExtras(data);
                         startActivity(i);

                     }else{
                         Toast.makeText(getContext(),"Current Date Should be in between the semister",Toast.LENGTH_SHORT).show();
                     }


                 }else{
                     Toast.makeText(getContext(),"Set the dates first",Toast.LENGTH_LONG).show();
                 }
             }
         });

         current.setText(today());
         currentDateRegistered = today().replace("/"," ");
         current.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showCurrentPicker();
             }
         });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerStart();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showDatePickerEnd();
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static class DatePickerFragment extends DialogFragment {
        DatePickerDialog.OnDateSetListener ondateSet;
        private int year, month, day;

        public DatePickerFragment() {}

        public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
            ondateSet = ondate;
        }

        @SuppressLint("NewApi")
        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            year = args.getInt("year");
            month = args.getInt("month");
            day = args.getInt("day");
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
        }
    }



    private void showDatePickerStart() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondateStart);
        date.show(getActivity().getFragmentManager(), "Date Picker");
    }
    private void showDatePickerEnd() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondateEnd);
        date.show(getActivity().getFragmentManager(), "Date Picker");
    }

    private void showCurrentPicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(currentDateSet);
        date.show(getActivity().getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener currentDateSet = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Toast.makeText(getContext(),"StartDateSet",Toast.LENGTH_SHORT).show();
            currentDateRegistered = dayOfMonth+" "+monthOfYear+" "+year;
            current.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    };

    DatePickerDialog.OnDateSetListener ondateStart = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Toast.makeText(getContext(),"StartDateSet",Toast.LENGTH_SHORT).show();
            startDate = dayOfMonth+" "+monthOfYear+" "+year;
            start.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    };
    //End date picker
    DatePickerDialog.OnDateSetListener ondateEnd = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Toast.makeText(getContext(),"EndDateSet",Toast.LENGTH_SHORT).show();
            endDate = dayOfMonth+" "+monthOfYear+" "+year;
            end.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

        }
    };

    public String today(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR);
    }
}





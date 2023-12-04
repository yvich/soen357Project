package com.tar.iq.petcare.ui.agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;

import com.tar.iq.petcare.R;
import com.tar.iq.petcare.adapter.ActivityGridAdapter;
import com.tar.iq.petcare.databinding.ActivityAgendaBinding;
import com.tar.iq.petcare.ui.myvet.addmivet.AddMiVet;
import com.tar.iq.petcare.utils.OnSwipeTouchListener;
import com.tar.iq.petcare.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AgendaActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private ActivityAgendaBinding binding;
    SimpleDateFormat showDateFormat = new SimpleDateFormat("MMMM, yyyy");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private List<Date> calendarDatesList = new ArrayList<>();

    private int highlightPosition = -1;
    private Calendar currentDate = Calendar.getInstance(Locale.ENGLISH);
    private String showDate;
    private String dateDialog;
    private ActivityGridAdapter gridAdapter;

    private int offset;
    private Calendar calendar = Calendar.getInstance();
    private boolean isGoPrevious = true;
    private boolean isGoNext = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_agenda);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#FFE4E1"));
        }
        binding.btnToday.setOnClickListener(this);
        binding.btnCalander.setOnClickListener(this);
        binding.calendarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Date mDate = calendarDatesList.get(i);
                Calendar dateCal = Calendar.getInstance();
                dateCal.setTime(mDate);

                String date = dateFormat.format(dateCal.getTime());

                String day = Utils.changeDateFormate("dd-MM-yyyy", "dd", date);
                String mnth = Utils.changeDateFormate("dd-MM-yyyy", "MM", date);
                String showDt = Utils.changeDateFormate("dd-MM-yyyy", "MMMM, yyyy", date);

                String tempDM = day+""+mnth;
                highlightPosition = Integer.parseInt(tempDM);

                int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
                int displayMonth = dateCal.get(Calendar.MONTH) + 1;
                int displayYear = dateCal.get(Calendar.YEAR);
                dateDialog = dayValue+"-"+displayMonth+"-"+displayYear;
            }

        });
        binding.calendarGrid.setOnTouchListener(new OnSwipeTouchListener(AgendaActivity.this){
            @Override
            public void onSwipeLeft() {
                calendarDatesList.clear();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nextWeek();
                    }
                },500);
                //Toast.makeText(OfficerActivity.this, "Go to next", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeRight() {
                calendarDatesList.clear();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        previousWeek();
                    }
                },500);
            }
        });
/*        binding.addagenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddMiVet.class));

            }
        });*/
        calendar = Calendar.getInstance(Locale.ENGLISH);
        calendarDatesList.clear();
        todayActivity();

    }
    private void todayActivity(){

        // get current day
        //int day = currentDate.get(Calendar.DATE);
        //int mnth = currentDate.get(Calendar.MONTH)+1;
        //String tempDM = day+""+mnth;
        Calendar dateCal = Calendar.getInstance();
        String date = dateFormat.format(dateCal.getTime());
        String day = Utils.changeDateFormate("dd-MM-yyyy", "dd", date);
        String mnth = Utils.changeDateFormate("dd-MM-yyyy", "MM", date);
        String tempDM = day+""+mnth;

        highlightPosition = Integer.parseInt(tempDM);
        // get current date
        dateDialog = dateFormat.format(currentDate.getTime());

        // setup week
        //Calendar calendar = Calendar.getInstance();
        offset = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE, -offset);
        showDate = showDateFormat.format(calendar.getTime());
        binding.txtShowDate.setText(showDate);

        for(int i = 0; i < 30; i++){
            System.out.println(calendar.getTime());
            calendarDatesList.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }

        gridAdapter = new ActivityGridAdapter(this, calendarDatesList, highlightPosition);
        binding.calendarGrid.setAdapter(gridAdapter);

    }

    private void setUpGrid(String date){
        dateDialog = date;
        //Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        offset = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE, -offset);
        for(int i = 0; i < 7; i++){
            System.out.println(calendar.getTime());
            calendarDatesList.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }

        String day = Utils.changeDateFormate("dd-MM-yyyy", "dd", date);
        String mnth = Utils.changeDateFormate("dd-MM-yyyy", "MM", date);
        String showDt = Utils.changeDateFormate("dd-MM-yyyy", "MMMM, yyyy", date);
        String tempDM = day+""+mnth;
        highlightPosition = Integer.parseInt(tempDM);
        binding.txtShowDate.setText(showDt);

    }

    private void previousWeek(){
        //Calendar calendar = Calendar.getInstance();
        //offset = offset+7;
        offset = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (isGoPrevious){
            isGoPrevious = false;
            isGoNext = true;
            offset = offset+7;
        }
        calendar.add(Calendar.DATE, -offset);
        for(int i = 0; i < 7; i++){
            System.out.println(calendar.getTime());
            calendar.add(Calendar.DATE, -1);
            calendarDatesList.add(calendar.getTime());
        }

        Collections.reverse(calendarDatesList);

        if (calendarDatesList.size() != 0){
            Date mDate = calendarDatesList.get(0);
            Calendar dateCal = Calendar.getInstance();
            dateCal.setTime(mDate);

            int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
            int displayMonth = dateCal.get(Calendar.MONTH) + 1;
            int displayYear = dateCal.get(Calendar.YEAR);

            dateDialog = dayValue+"-"+displayMonth+"-"+displayYear;
            showDate = showDateFormat.format(dateCal.getTime());
            binding.txtShowDate.setText(showDate);

        }

    }

    private void nextWeek(){
        //Calendar calendar = Calendar.getInstance();
        //offset = offset-7;
        offset = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (isGoNext){
            isGoPrevious = true;
            isGoNext = false;
            offset = offset-7;
        }
        calendar.add(Calendar.DATE, -offset);
        for(int i = 0; i < 7; i++){
            System.out.println(calendar.getTime());
            calendarDatesList.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }

        if (calendarDatesList.size() != 0){
            Date mDate = calendarDatesList.get(0);
            Calendar dateCal = Calendar.getInstance();
            dateCal.setTime(mDate);

            int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
            int displayMonth = dateCal.get(Calendar.MONTH) + 1;
            int displayYear = dateCal.get(Calendar.YEAR);

            dateDialog = dayValue+"-"+displayMonth+"-"+displayYear;
            showDate = showDateFormat.format(dateCal.getTime());
            binding.txtShowDate.setText(showDate);

        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCal = Calendar.getInstance();
        mCal.set(year, month, dayOfMonth);
        String dt = dateFormat.format(mCal.getTime());
        setUpGrid(dt);
        isGoNext = false;
        isGoPrevious = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnToday:
                calendar = Calendar.getInstance(Locale.ENGLISH);
                calendarDatesList.clear();
                todayActivity();
                break;

            case R.id.btnCalander:
                calendarDatesList.clear();
                int day = 1, month = 0, year = 2000;
                Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(dateFormat.parse(dateDialog));
                    day = cal.get(Calendar.DAY_OF_MONTH);
                    month = cal.get(Calendar.MONTH);
                    year = cal.get(Calendar.YEAR);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;


        }
    }

}
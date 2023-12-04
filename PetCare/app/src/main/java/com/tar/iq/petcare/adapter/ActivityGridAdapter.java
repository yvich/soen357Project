package com.tar.iq.petcare.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tar.iq.petcare.R;
import com.tar.iq.petcare.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityGridAdapter extends ArrayAdapter {

    Context context;
    List<Date> calendarDatesList;
    int highlightPosition;
    LayoutInflater mInflater;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public ActivityGridAdapter(Context context, List<Date> calendarDatesList, int highlightPosition){
        super(context, R.layout.activity_calendar_item);
        this.context = context;
        this.calendarDatesList = calendarDatesList;
        this.highlightPosition = highlightPosition;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return calendarDatesList.size();

    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return calendarDatesList.get(position);
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return calendarDatesList.indexOf(item);
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        View view = convertView;
        if (position < calendarDatesList.size()) {
            Date mDate = calendarDatesList.get(position);
            Calendar dateCal = Calendar.getInstance();
            dateCal.setTime(mDate);

            String date = dateFormat.format(dateCal.getTime());

            String day = Utils.changeDateFormate("dd-MM-yyyy", "dd", date);
            String mnth = Utils.changeDateFormate("dd-MM-yyyy", "MM", date);
            String tempDM = day+""+mnth;

            int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
            int displayMonth = dateCal.get(Calendar.MONTH) + 1;
            int displayYear = dateCal.get(Calendar.YEAR);

            //String tempDM = dayValue+""+displayMonth;
            int highlightDate = Integer.parseInt(tempDM);

            if (view == null) {
                view = mInflater.inflate(R.layout.activity_calendar_item, parent, false);
            }
            TextView datesCell = (TextView) view.findViewById(R.id.calendar_date_id);
            //ll_grid_date = (LinearLayout) view.findViewById(R.id.ll_grid_date);
            TextView eventIndicator = (TextView) view.findViewById(R.id.calendarEvent);

            /*

            final String formate = new String("%1$02d");
            String day = String.format(formate, dayValue);
*/

            datesCell.setText(String.valueOf(dayValue));
   /*         if (highlightPosition == highlightDate){
                eventIndicator.setBackgroundColor(Color.parseColor(Utils.getThemeColor(context)));
            }*/

        }
        return view;
    }
}

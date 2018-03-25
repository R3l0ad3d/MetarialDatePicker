package test.net.atshq.metarialdatepicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvDate;

    Calendar myCalendar;
    String dateSt="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDate = findViewById(R.id.tvDateShow);
    }


    void datePiceker(){

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }


        };

        //dialogue popup
        DatePickerDialog dpd =DatePickerDialog.newInstance(date,myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));



        Calendar sunday;
        List<Calendar> weekends = new ArrayList<>();
        int weeks = 10;

        for (int i = 0; i < (weeks * 7) ; i = i + 7) {
            sunday = Calendar.getInstance();
            sunday.add(Calendar.DAY_OF_YEAR, (Calendar.FRIDAY - sunday.get(Calendar.DAY_OF_WEEK)  + i));
            // saturday = Calendar.getInstance();
            // saturday.add(Calendar.DAY_OF_YEAR, (Calendar.SATURDAY - saturday.get(Calendar.DAY_OF_WEEK) + i));
            // weekends.add(saturday);
            weekends.add(sunday);
        }
        Calendar[] disabledDays = weekends.toArray(new Calendar[weekends.size()]);
        dpd.setDisabledDays(disabledDays);
        dpd.setMinDate(myCalendar);
        dpd.show(getSupportFragmentManager(), "Date picker dialog");

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        dateSt=sdf.format(myCalendar.getTime());

        tvDate.setText(dateSt);

        Toast.makeText(getApplicationContext(), ""+dateSt, Toast.LENGTH_SHORT).show();
    }

    public void datePickClick(View view) {
        datePiceker();
    }
}

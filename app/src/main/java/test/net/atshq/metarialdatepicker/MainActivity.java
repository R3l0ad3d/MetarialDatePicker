package test.net.atshq.metarialdatepicker;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private TextView tvDate;
    private TextView tvTime;
    Calendar myCalendar;
    String dateSt="";

    private TimePickerDialog tpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDate = findViewById(R.id.tvDateShow);
        tvTime = findViewById(R.id.tvTimeShow);
    }

    void timePicker(){
        Calendar now = Calendar.getInstance();
                    /*
                    It is recommended to always create a new instance whenever you need to show a Dialog.
                    The sample app is reusing them because it is useful when looking for regressions
                    during testing
                     */
        if (tpd == null) {
            tpd = TimePickerDialog.newInstance(
                    this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                   true
            );
        } else {
            tpd.initialize(
                    this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    now.get(Calendar.SECOND),
                    true);
        }
        tpd.setThemeDark(true);
        tpd.vibrate(true);
        tpd.dismissOnPause(true);
        tpd.enableSeconds(false);
        tpd.setVersion(false ? TimePickerDialog.Version.VERSION_2 : TimePickerDialog.Version.VERSION_1);
        if (false) {
            tpd.setAccentColor(Color.parseColor("#9C27B0"));
        }
        if (true) {
            tpd.setTitle("TimePicker Title");
        }
        /*if (true) {
            if (true) {
                tpd.setTimeInterval(3, 5, 10);
            } else {
                tpd.setTimeInterval(3, 5, 60);
            }
        }
        if (true) {
            Timepoint[] disabledTimes = {
                    new Timepoint(10),
                    new Timepoint(10, 30),
                    new Timepoint(11),
                    new Timepoint(12, 30)
            };
            tpd.setDisabledTimes(disabledTimes);
        }*/
        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getSupportFragmentManager(), "Timepickerdialog");
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

    public void timePickClick(View view) {
        timePicker();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String secondString = second < 10 ? "0"+second : ""+second;
        String time = "You picked the following time: "+hourString+"h"+minuteString+"m"+secondString+"s";
        tvTime.setText(time);
    }
}

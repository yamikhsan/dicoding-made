package com.studio.yami.moviecataloguefinal.activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.studio.yami.moviecataloguefinal.R;
import com.studio.yami.moviecataloguefinal.databinding.ActivityReminderBinding;
import com.studio.yami.moviecataloguefinal.receiver.ReminderReceiver;

import java.util.Calendar;

import static com.studio.yami.moviecataloguefinal.receiver.ReminderReceiver.DAILY_ID;
import static com.studio.yami.moviecataloguefinal.receiver.ReminderReceiver.EXTRA_ID;
import static com.studio.yami.moviecataloguefinal.receiver.ReminderReceiver.RELEASE_ID;

public class ReminderActivity extends AppCompatActivity {

    private ActivityReminderBinding binding;

    private static final String RELEASE = "Release Time";
    private static final String DAILY = "Daily Time";

    private SharedPreferences sharedPre;
    private SharedPreferences.Editor editor;
    private AlarmManager alarmManager;

    private Switch release, daily;
    private TextView releaseTime, dailyTime;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reminder);

        init();

        isAlarmSet(RELEASE_ID, release);
        setTime(releaseTime, RELEASE);

        isAlarmSet(DAILY_ID, daily);
        setTime(dailyTime, DAILY);

        release.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String time = "08:00";
                if (b) {
                    dailyReminder(RELEASE_ID, time);
                    time = "("+time+")";
                } else {
                    cancelAlarm(RELEASE_ID);
                    time = "";
                }
                releaseTime.setText(time);
                editor.putString(RELEASE, time);
                editor.apply();
            }
        });

        daily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String time = "07:00";
                if (b) {
                    dailyReminder(DAILY_ID, time);
                    time = "("+time+")";
                } else {
                    cancelAlarm(DAILY_ID);
                    time = "";
                }
                dailyTime.setText(time);
                editor.putString(DAILY, time);
                editor.apply();
            }
        });

    }

    @SuppressLint("CommitPrefEdits")
    private void init(){
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        sharedPre = this.getSharedPreferences("SharePre", MODE_PRIVATE);
        editor = sharedPre.edit();

        release = binding.switchRelease;
        daily = binding.switchDaily;

        releaseTime = binding.tvReleaseTime;
        dailyTime = binding.tvDailyTime;
    }

    private void setTime(TextView tv, String id){
        String time = sharedPre.getString(id, "empty");
        if(time != null){
            tv.setText(time.matches("empty") ?"" : time);
        }
    }

    private void dailyReminder(int id, String time){
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra(EXTRA_ID, id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);

        String[] timeArr = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArr[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArr[1]));
        calendar.set(Calendar.SECOND, 0);

        if(alarmManager != null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private void cancelAlarm(int id){
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
        pendingIntent.cancel();
        if(alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }
    }

    public void isAlarmSet(int id, Switch s) {
        Intent intent = new Intent(this, ReminderReceiver.class);
        s.setChecked(PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_NO_CREATE) != null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.studio.yami.moviecataloguefinal.receiver;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.studio.yami.moviecataloguefinal.BuildConfig;
import com.studio.yami.moviecataloguefinal.Const;
import com.studio.yami.moviecataloguefinal.R;
import com.studio.yami.moviecataloguefinal.activity.MainActivity;
import com.studio.yami.moviecataloguefinal.model.FilmDiscover;
import com.studio.yami.moviecataloguefinal.model.FilmList;
import com.studio.yami.moviecataloguefinal.server.ApiClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderReceiver extends BroadcastReceiver {

    public static final String EXTRA_ID = "extra_id";

    public static final int RELEASE_ID = 222;
    public static final int DAILY_ID = 111;

    private static final String GROUP_RELEASE = "group release";

    @Override
    public void onReceive(Context context, Intent intent) {

        String dailyTitle = context.getResources().getString(R.string.app_name);
        String dailyMsg = context.getResources().getString(R.string.rem_daily);

        switch (intent.getIntExtra(EXTRA_ID, 0)){
            case RELEASE_ID:
                reminderRelease(context);
                break;
            case DAILY_ID:
                showNotify(context, DAILY_ID, dailyTitle, dailyMsg);
                break;
        }

    }

    private void reminderRelease(final Context context){
        final String releaseMsg = context.getResources().getString(R.string.rem_release);
        String d = currentDate();
        ApiClient client = new ApiClient();

        Map<String, String> query = new HashMap<>();
        query.put(Const.API_KEY, BuildConfig.ApiKey);
        query.put(Const.LANGUAGE, Const.LANG_EN);

        Map<String, String> date = new HashMap<>();
        date.put("primary_release_date.gte", d);
        date.put("primary_release_date.lte", d);
        client.getService().getReleaseToday(date, query).enqueue(new Callback<FilmDiscover>() {
            @Override
            public void onResponse(@NonNull Call<FilmDiscover> call, @NonNull Response<FilmDiscover> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<FilmList> film = response.body().getResult();
                    for (int i=0; i<3; i++){
                        String name = film.get(i).getTitle();
                        showNotify(context, i, name, name + " " + releaseMsg);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FilmDiscover> call, @NonNull Throwable t) {

            }
        });
    }

    private String currentDate(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return format.format(date);
    }

    private void showNotify(Context context, int notifId, String title, String msg){
        String channelId = "Reminder id";
        String channelName = "Reminder name";

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        if (notifId == DAILY_ID){
            builder
                    .setSmallIcon(R.drawable.ic_local_movies_black_48dp)
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setColor(ContextCompat.getColor(context, android.R.color.black))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarm)
                    .setContentIntent(pendingIntent);
        }else {
            builder
                    .setSmallIcon(R.drawable.ic_local_movies_black_48dp)
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setColor(ContextCompat.getColor(context, android.R.color.black))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setGroup(GROUP_RELEASE)
                    .setSound(alarm)
                    .setContentIntent(pendingIntent);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(channelId);

            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if(notificationManager != null){
            notificationManager.notify(notifId, notification);
        }

    }

}

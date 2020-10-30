package smu.mp.mptermproject_cpquick;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {
    NotificationManager mNotificationManager;
    ServiceThread thread;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        //서비스가 생성 될 때 수행할 동작
        super.onCreate();
//        startForegroundService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 백그라운드에서 실행되는 동작들이 들어가는 곳, 서비스가 시작될 때 수행 동작
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //알림 채널 만들기
        String id = "cpq_channel";
        CharSequence name = "cpq";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            int impotance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, impotance);
            //알림채널에 사용할 설정을 구성한다.
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotificationManager.createNotificationChannel(mChannel);
        }


        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    public int startForegroundService() {
        // 백그라운드에서 실행되는 동작들이 들어가는 곳, 서비스가 시작될 때 수행 동작
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //알림 채널 만들기
        String id = "cpq_channel";
        CharSequence name = "cpq";
        if (android.os.Build.VERSION.SDK_INT >= 26){ //android.os.Build.VERSION_CODES.O
            int impotance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, impotance);
            //알림채널에 사용할 설정을 구성한다.
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotificationManager.createNotificationChannel(mChannel);
        }


        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.stopForever();
        thread = null;
    }

    private class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {

            //해당 알림 누르면 수령하세요 액티비티로 넘어가게
            Intent intent = new Intent(NotificationService.this, ThankUActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder builder1 = new Notification.Builder(getApplicationContext(), "cpq_channel")
                    .setContentTitle("주문 완료!")
                    .setContentText("눈송이 고객님 주문하신 음료를 찾아가세요!!")
                    .setSmallIcon(R.drawable.fab)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true) // 알림 터치시 반응 후 삭제
                    .setSound(RingtoneManager
                            .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent);

            mNotificationManager.notify(0, builder1.build());

            //토스트 띄우기
          //  Toast.makeText(NotificationService.this, "뜸?", Toast.LENGTH_LONG).show();
        }
    };
}

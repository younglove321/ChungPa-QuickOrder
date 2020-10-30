package smu.mp.mptermproject_cpquick;

import android.os.Handler;
import android.os.Message;

public class ServiceThread extends  Thread {
    Handler handler;
    boolean isRun = true;

    public ServiceThread(Handler handler) {
        this.handler = handler;
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }


    public void run() {
        //반복적으로 알람 울리게~~
        try {
            //주문완료 후 10초후 알림뜨게 설정
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (isRun) {
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}

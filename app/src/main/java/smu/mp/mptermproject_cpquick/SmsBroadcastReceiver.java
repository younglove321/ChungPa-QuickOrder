package smu.mp.mptermproject_cpquick;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.Date;

public class SmsBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "SmsBroadcastReceiver";
    private String origNumber, message;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"));
        abortBroadcast();
        Bundle bundle = intent.getExtras();

        Object messages[] = (Object[])bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];

        int smsCount = messages.length;
        for(int i=0;i<smsCount;i++){
            smsMessage[i] = SmsMessage.createFromPdu((byte[])messages[i]);
        }

        Date curDate = new Date(smsMessage[0].getTimestampMillis());

        origNumber = smsMessage[0].getOriginatingAddress();
        message = smsMessage[0].getMessageBody().toString();

        Intent intent1 = new Intent(context, SmsDisplayActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_ONE_SHOT);

        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent1.putExtra("number", origNumber);
        intent1.putExtra("message", message);
        intent1.putExtra("timestamp", curDate.toString());

        context.startActivity(intent1);
    }
}

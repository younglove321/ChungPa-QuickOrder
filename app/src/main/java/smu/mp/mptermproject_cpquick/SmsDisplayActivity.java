package smu.mp.mptermproject_cpquick;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class SmsDisplayActivity extends Activity {

    Button btn_close;
    TextView tv_message;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sms_display);
        setTitle("문자 확인 페이지");

        btn_close = (Button)findViewById(R.id.btn_close);
        tv_message = (TextView)findViewById(R.id.tv_message);

        btn_close.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        Intent passedIntent = getIntent();
        if(passedIntent!=null){
            processIntent(passedIntent);
        }
    }

    protected void onNewIntent(Intent intent){
        processIntent(intent);
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent){
        String number = intent.getStringExtra("number");
        String message = intent.getStringExtra("message");
        String timestamp = intent.getStringExtra("timestamp");

        if(number!=null){
            tv_message.setText("["+timestamp+"]\n"+number + "\n\n" + message);
            tv_message.invalidate();
        }
    }
}

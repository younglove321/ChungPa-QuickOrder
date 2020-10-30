package smu.mp.mptermproject_cpquick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;

public class StampActivity extends AppCompatActivity {
    private String cafe;
    int stamp_cnt;
    SQLiteDatabase db;
    Integer[] stamp_imgID ={R.drawable.stamp0, R.drawable.stamp1, R.drawable.stamp2, R.drawable.stamp3,
            R.drawable.stamp4, R.drawable.stamp5, R.drawable.stamp6,
            R.drawable.stamp7, R.drawable.stamp8, R.drawable.stamp9,R.drawable.stamp10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamp);
        setTitle("마이 쿠폰 보기");

        //db를 사용하기 위한 객체 생성
        DBHelper dbHelper;
        dbHelper = new DBHelper(this);

//        getStampData();

        cafe = getIntent().getStringExtra("cafe");

        //해당 cafe의 stamp 개수를 1만큼 증가시키고 검색하여 출력
        db = dbHelper.getWritableDatabase();
        String sql = "UPDATE TABLE_LOG SET stamp = stamp+1 WHERE cafe = " + "'" + cafe + "'" + ";"; //업데이트하는 쿼리
        db.execSQL(sql);
        String sql2 = "SELECT * FROM TABLE_LOG WHERE cafe = " + "'" + cafe + "'" + ";"; //찾는 쿼리
        Cursor cs = db.rawQuery(sql2, null);
        while(cs.moveToNext()) {
            stamp_cnt = cs.getInt(1);
        }

        //스탬프 10개를 모두 모으면 0으로 초기화
        if(stamp_cnt == 10){
            Toast.makeText(this, "축하드립니다! 10개를 모두 모으셨습니다!", Toast.LENGTH_SHORT).show();
            db = dbHelper.getWritableDatabase();
            db.execSQL("UPDATE TABLE_LOG SET stamp = 0 WHERE cafe = " + "'" + cafe + "'" + ";");
        }

        TextView tv_stampinfo = (TextView)findViewById(R.id.tv_stampinfo);
        tv_stampinfo.setText("김눈송님의 " + cafe + "\n 스탬프는 " + stamp_cnt + "개 입니다.");


        ImageView iv_stamp= (ImageView)findViewById(R.id.iv_stamp);
        iv_stamp.setImageResource(stamp_imgID[stamp_cnt]);


        Button btn_goMain = (Button)findViewById(R.id.btn_goMain);
        btn_goMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
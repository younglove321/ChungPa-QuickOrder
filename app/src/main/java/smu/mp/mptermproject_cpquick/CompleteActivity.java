package smu.mp.mptermproject_cpquick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class CompleteActivity extends AppCompatActivity {
    private String cafe;
    SQLiteDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        setTitle("주문 완료");

        cafe = getIntent().getStringExtra("cafe_name");
        switch(cafe){
            case "cafe1":
                cafe = "블루베리 카페";
                break;
            case "cafe2":
                cafe = "순헌관 카페";
                break;
            case "cafe3":
                cafe = "본솔";
                break;
            case "cafe4":
                cafe = "청파맨션";
                break;
        }
        //db를 사용하기 위한 객체 생성
        DBHelper dbHelper;
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        String sql = "UPDATE TABLE_LOG SET visit_cnt = visit_cnt+1 WHERE cafe = " + "'" + cafe + "'" + ";"; //방문횟수 업데이트하는 쿼리
        db.execSQL(sql);
        String sql2 = "SELECT * FROM TABLE_LOG WHERE cafe = " + "'" + cafe + "'" + ";"; //찾는 쿼리
        Cursor cs = db.rawQuery(sql2, null);
        while(cs.moveToNext()) {
            System.out.println("블루베리 방문 횟수 : " + cs.getInt(2));
        }



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
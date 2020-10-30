package smu.mp.mptermproject_cpquick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {
    String hot_or_ice ;
    int countNum ; //메뉴 수량
    int finaltotalPrice; //메뉴 개수에 따른 가격
    String cafe_name,menu_name;
    int menu_price, menu_img;
    Intent intent;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle("주문하기");

      //  final String[] hot_or_ice = {""}; //hot or ice
        Button plus= (Button)findViewById(R.id.order_plusButton); //수량 +
        Button minus = (Button)findViewById(R.id.order_minusButton); //수량 -
        Button goCart = (Button)findViewById(R.id.order_addButton); //담기 버튼

        intent = getIntent(); //인텐트 받기
        cafe_name= (intent).getStringExtra("cafe_name"); //카페명
        menu_name= (intent).getStringExtra("menu_name"); //메뉴명
        menu_price = (intent).getIntExtra("menu_price",0); //메뉴 하나 가격
        menu_img=(intent).getIntExtra("menu_img",0); //메뉴사진


        TextView tv_cafeName = (TextView)findViewById(R.id.order_cafeName);
        String cafe_list[] ={"블루베리 카페","순헌관 카페","본솔","청파맨션"};
        if (cafe_name.equals("cafe1")){
            tv_cafeName.setText(cafe_list[0]);
        }
        else if(cafe_name.equals("cafe2")){
            tv_cafeName.setText(cafe_list[1]);
        }
        else if(cafe_name.equals("cafe3")){
            tv_cafeName.setText(cafe_list[2]);
        }
        else if(cafe_name.equals("cafe4")){
            tv_cafeName.setText(cafe_list[3]);
        }

        TextView tv_menuName = (TextView)findViewById(R.id.order_menuName);
        tv_menuName.setText(menu_name);

        ImageView iv_menuImg = (ImageView)findViewById(R.id.order_menuImg);
        iv_menuImg.setImageResource(menu_img);// 메뉴이름마다 다른 이미지 src


        TextView tv_price = (TextView)findViewById(R.id.order_price);
        tv_price.setText(String.valueOf(menu_price)); //메뉴가격

        final TextView tv_count = (TextView)findViewById(R.id.order_countNum);
        tv_count.setText(String.valueOf(countNum)); //수량 디폴트 값 1

        //hot , ice 선택하는 부분
        RadioGroup group=(RadioGroup)findViewById(R.id.hotice);

        //라디오 그룹 클릭 리스너
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rg_btn1:
                        hot_or_ice = "hot";
                        break;
                    case R.id.rg_btn2:
                        hot_or_ice ="ice";
                        break;
                }
            }
        });

        //수량 증가 감소하는 부분
        plus.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                countNum++;
                tv_count.setText(String.valueOf(countNum)); //바뀐 수량을 써주는 부분
                System.out.println("총 개수??????: "+countNum);

                //총 가격이 바뀜
                finaltotalPrice = countNum* menu_price; //메뉴 하나당 가격에 수량을 곱해준다
                System.out.println("총 가격??????: "+finaltotalPrice);
            }

        });

        minus.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(countNum > 1 ) {
                    countNum--;
                    tv_count.setText(String.valueOf(countNum)); //바뀐 수량을 써주는 부분
                    System.out.println("총 개수??????: "+countNum);

                    //총 가격이 바뀜
                    finaltotalPrice = countNum* menu_price; //메뉴 하나당 가격에 수량을 곱해준다
                    System.out.println("총 가격??????: "+finaltotalPrice);

                }
            }
        });


       // Button order_addButton = (Button)findViewById(R.id.order_addButton);


        //MyCart액티비티로 넘겨주는 부분
        goCart.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                ContentValues addRowValue = new ContentValues();
                addRowValue.put("cafe", cafe_name);
                addRowValue.put("menu", menu_name);
                addRowValue.put("hotice", hot_or_ice);
                addRowValue.put("price", finaltotalPrice);
                addRowValue.put("count",  countNum);

                //db를 사용하기 위한 객체 생성
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                db = dbHelper.getWritableDatabase();
                String sql = String.format("INSERT INTO TABLE_CART VALUES('%s', '%s', '%s', '%d', '%d');", cafe_name, menu_name, hot_or_ice, finaltotalPrice, countNum);
                db.execSQL(sql);

                Intent intent = new Intent(getApplicationContext(), MyCartActivity.class);
                startActivity(intent);
            }
        });
    }


}
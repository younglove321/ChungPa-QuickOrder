package smu.mp.mptermproject_cpquick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyCartActivity extends AppCompatActivity {
    private ListView lv_cart;
    private int sumOfPrice = 0;
    private int idx = 0, cart_cnt;
    private String cafe_name;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        setTitle("장바구니");

        lv_cart = (ListView)findViewById(R.id.lv_cart);

        //db를 사용하기 위한 객체 생성
        DBHelper dbHelper;
        dbHelper = new DBHelper(this);

        db = dbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM TABLE_CART", null);
        ArrayList<CartItemData> cartList = new ArrayList<>();


        while(cs.moveToNext()){

            CartItemData currentData = new CartItemData();
            currentData.cafe = cs.getString(0);
            cafe_name = currentData.cafe; //인자에 담아 switch문에서 비교
            currentData.menu = cs.getString(1);
            currentData.hotice = cs.getString(2);
            currentData.price = cs.getInt(3);
            currentData.count = cs.getInt(4);
            cartList.add(currentData);
            sumOfPrice += currentData.price;
        }

        //어댑터 연결
        MyCartActivity.CartListAdapter lAdapter = new MyCartActivity.CartListAdapter(cartList);
        lv_cart = (ListView) findViewById(R.id.lv_cart);
        lv_cart.setAdapter(lAdapter);

        Button btn_shopping = (Button)findViewById(R.id.btn_shopping);
        btn_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch(cafe_name){
                    case "cafe1":
                        intent = new Intent(getApplicationContext(), MenuListActivity1.class);
                        startActivity(intent);
                        break;
                    case "cafe2":
                        intent = new Intent(getApplicationContext(), MenuListActivity2.class);
                        startActivity(intent);
                        break;
                    case "cafe3":
                        intent = new Intent(getApplicationContext(), MenuListActivity3.class);
                        startActivity(intent);
                        break;
                    case "cafe4":
                        intent = new Intent(getApplicationContext(), MenuListActivity4.class);
                        startActivity(intent);
                        break;
                }

            }
        });

        Button btn_order = (Button)findViewById(R.id.btn_order);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("DELETE FROM TABLE_CART");
                Intent intent = new Intent(getApplicationContext(), CompleteActivity.class);
                intent.putExtra("cafe_name", cafe_name);
                startActivity(intent);

                //서비스 실행
                Intent intent1 = new Intent(getApplicationContext(), NotificationService.class);
                startService(intent1);
//                if (Build.VERSION.SDK_INT >= 26) {
//                    startForegroundService(intent1);
//                } else {
//
//                }
            }
        });
    }

    class CartListAdapter extends BaseAdapter {
        LayoutInflater inflater = null;
        private ArrayList<CartItemData> cart_List = null;
        private int nListCnt = 0;

        public CartListAdapter(ArrayList<CartItemData> cartList)
        {
            cart_List = cartList;
            nListCnt = cartList.size();
        }

        @Override
        public int getCount() { return nListCnt; }

        @Override
        public Object getItem(int position) { return null; }

        @Override
        public long getItemId(int position) { return 0; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            System.out.println(nListCnt);
            if (convertView == null)
            {
                final Context context = parent.getContext();
                if (inflater == null)
                {
                    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                }
                convertView = inflater.inflate(R.layout.cart_list_item, parent, false);
            }

            TextView tv_menu = (TextView) convertView.findViewById(R.id.tv_menu);
            TextView tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            TextView tv_hotice = (TextView) convertView.findViewById(R.id.tv_hotice);

            TextView tv_priceSum = (TextView) findViewById(R.id.tv_priceSum);

            tv_menu.setText(cart_List.get(position).menu);
            String strCount = String.valueOf(cart_List.get(position).count);
            String strPriceSum = String.valueOf(sumOfPrice);
            String strHotIce= cart_List.get(position).hotice;
            tv_hotice.setText(strHotIce);
            tv_count.setText(strCount + " 잔");
            tv_priceSum.setText(strPriceSum + "원");

            return convertView;
        }
    }
}
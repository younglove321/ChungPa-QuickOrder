package smu.mp.mptermproject_cpquick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuListActivity1 extends AppCompatActivity {
    private Button btn_call, btn_share;
    private ListView lv;
    private String phnum = "";
    Integer[] cafe1_menuID ={R.drawable.americano, R.drawable.latte, R.drawable.vanila};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list1);
        setTitle("메뉴 선택");

        //버튼 연결
        //1. 전화 주문 버튼
        btn_call = (Button)findViewById(R.id.btn_call);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phnum = "0263870522";
                String tel = "tel:" + phnum;
                Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(tel));
                startActivity(intent);
            }
        });

        btn_share = (Button)findViewById(R.id.btn_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String text = "블루베리 카페로 놀러오세요~~^^\n블루베리 카페 숙대점 : 02-6387-0522\n\n-청파퀵오더-";
                intent.putExtra(Intent.EXTRA_TEXT, text);
                Intent chooser = Intent.createChooser(intent, "친구에게 공유하기");
                startActivity(chooser);
            }
        });

        //데이터 넣는 곳
        final String[] strMenu = {"아메리카노", "카페라떼", "바닐라라떼"};
        final int[] intPrice = {2000, 2500, 3000};
        int cnt = 0;

        ArrayList<ItemData> mData = new ArrayList<>();
        for(int i=0; i < 3;i++){
            ItemData mItem = new ItemData();
            mItem.strMenu = strMenu[cnt];
            mItem.intPrice = intPrice[cnt];
            mData.add(mItem);
            if(cnt>=strMenu.length) cnt = 0;
            cnt++;
        }

        //어댑터 연결
        final MenuListAdapter lAdapter = new MenuListAdapter(mData);
        lv = (ListView) findViewById(R.id.lv_menu);
        lv.setAdapter(lAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                intent.putExtra("cafe_name", "cafe1");
                intent.putExtra("menu_name", strMenu[position]);
                intent.putExtra("menu_price", intPrice[position]);
                intent.putExtra("menu_img", cafe1_menuID[position]);
                startActivity(intent);
            }
        });
    }

    class MenuListAdapter extends BaseAdapter{
        LayoutInflater inflater = null;
        private ArrayList<ItemData> m_Data = null;
        private int nListCnt = 0;

        public MenuListAdapter(ArrayList<ItemData> mData)
        {
            m_Data = mData;
            nListCnt = m_Data.size();
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
            if (convertView == null)
            {
                final Context context = parent.getContext();
                if (inflater == null)
                {
                    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                }
                convertView = inflater.inflate(R.layout.menu_list_item, parent, false);
            }

            ImageView iv_menu = (ImageView)convertView.findViewById(R.id.iv_menu);
            TextView tv_menu = (TextView) convertView.findViewById(R.id.tv_menu);
            TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);

            iv_menu.setImageResource(cafe1_menuID[position]);
            tv_menu.setText(m_Data.get(position).strMenu);
            tv_price.setText(String.valueOf(m_Data.get(position).intPrice));
            return convertView;
        }
    }

}

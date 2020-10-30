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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuListActivity3 extends AppCompatActivity {
    private Button btn_call, btn_share;
    private ListView lv;
    private String phnum = "";
    Integer[] cafe3_menuID ={R.drawable.green, R.drawable.rasberry, R.drawable.hazelnut};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list3);
        setTitle("메뉴 선택");

        //버튼 연결
        //1. 전화 주문 버튼
        btn_call = (Button)findViewById(R.id.btn_call);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "연결 가능한 전화번호가 없습니다ㅠㅠ", Toast.LENGTH_SHORT).show();
            }
        });

        btn_share = (Button)findViewById(R.id.btn_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String text = "본솔로 놀러오세요~~^^\n\n-청파퀵오더-";
                intent.putExtra(Intent.EXTRA_TEXT, text);
                Intent chooser = Intent.createChooser(intent, "친구에게 공유하기");
                startActivity(chooser);
            }
        });

        //데이터 넣는 곳
        final String[] strMenu = {"청귤 에이드", "라즈베리 에이드", "헤이즐넛 라떼"};
        final int[] intPrice = {3500, 3500, 2500};
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
        final MenuListActivity3.MenuListAdapter lAdapter = new MenuListActivity3.MenuListAdapter(mData);
        lv = (ListView) findViewById(R.id.lv_menu);
        lv.setAdapter(lAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                intent.putExtra("cafe_name", "cafe3");
                intent.putExtra("menu_name", strMenu[position]);
                intent.putExtra("menu_price", intPrice[position]);
                intent.putExtra("menu_img", cafe3_menuID[position]);
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

            iv_menu.setImageResource(cafe3_menuID[position]);
            tv_menu.setText(m_Data.get(position).strMenu);
            tv_price.setText(String.valueOf(m_Data.get(position).intPrice));
            return convertView;
        }
    }

}

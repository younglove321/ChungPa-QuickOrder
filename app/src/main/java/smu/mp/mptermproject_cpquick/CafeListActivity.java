package smu.mp.mptermproject_cpquick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CafeListActivity extends AppCompatActivity {

    public static int[] cafeImg = { R.drawable.cafe01,R.drawable.cafe02, R.drawable.cafe03, R.drawable.cafe04, R.drawable.cafe05, R.drawable.cafe06,R.drawable.cafe07,R.drawable.cafe08,R.drawable.cafe09,R.drawable.cafe10};

    public static String[] cafeNm = {"블루베리", "순헌카페","본솔","청파맨션", "너드카페", "을의 커피", " 이디야 숙대점", "로즈 커피", "마돈나 커피","코피티암"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_list);

        setTitle("카페 선택");

        Button btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyCartActivity.class);
                startActivity(intent);
            }
        });


        final GridView gv = findViewById(R.id.gv_cafelist);
        CafeListAdapter cAdapter = new CafeListAdapter(this,cafeImg, cafeNm);
        gv.setAdapter(cAdapter);

    }


}

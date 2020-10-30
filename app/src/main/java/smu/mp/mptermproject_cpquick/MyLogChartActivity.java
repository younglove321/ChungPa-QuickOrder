package smu.mp.mptermproject_cpquick;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

public class MyLogChartActivity extends AppCompatActivity {
    private String[] cafeArray;
    SQLiteDatabase db;
    private BarChart barChart;
    private int cafe_cnt = 0, i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_log_chart);

        setTitle("마이 청Quick");

        barChart = (BarChart) findViewById(R.id.barchart);

        //db를 사용하기 위한 객체 생성
        DBHelper dbHelper;
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        //데이터베이스에 있는 방문 카페 개수 불러오기
        Cursor cs = db.rawQuery("SELECT COUNT(*) FROM TABLE_LOG", null);
        while(cs.moveToNext()) {
            cafe_cnt = cs.getInt(0);
        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<String>();
        //데이터베이스에서 카페별 방문 횟수 불러오기
        Cursor cs2 = db.rawQuery("SELECT * FROM TABLE_LOG", null);
        while(cs2.moveToNext()) {
            labels.add(cs2.getString(0));
            entries.add(new BarEntry(i, (Integer)cs2.getInt(2)));
            i++;
        }

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(30f);
        l.setXOffset(15f);
        l.setTextSize(15f);

        XAxis x = barChart.getXAxis();
        x.setLabelCount(4);
        x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        x.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(labels.size()>(int)value)
                    return labels.get((int) value);
                else
                    return null;
            }
        });
        x.setTextSize(15f);

        barChart.getAxisRight().setEnabled(false);
        BarDataSet set = new BarDataSet(entries, "카페별 방문 횟수");

        set.setColors(Color.parseColor("#FFD85B"));

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set);

        BarData data = new BarData(set);
        data.setBarWidth(0.4f);
        barChart.setData(data);
        barChart.animateY(5000);
        barChart.invalidate();
    }
}

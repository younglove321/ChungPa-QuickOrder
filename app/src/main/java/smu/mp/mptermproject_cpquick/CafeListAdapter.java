package smu.mp.mptermproject_cpquick;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class CafeListAdapter extends BaseAdapter {

    String [] result;
    int [] cafeID;
    private Context context;
    private LayoutInflater inflater = null;
    public static int[] flags = {0,1,2,3,4,4,4,4,4,4};

    public CafeListAdapter(CafeListActivity cafeListActivity, int[] cafeImg, String[] cafeNm) {

        result = cafeNm;
        cafeID = cafeImg;
        context = cafeListActivity;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return cafeID.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public class Holder{
        TextView tv_cafelist;
        ImageView iv_cafelist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Holder holder = new Holder();
        final View view;
        final int pos = position;

        view = inflater.inflate(R.layout.activity_cafe_list_custom,null);
        holder.tv_cafelist = view.findViewById(R.id.tv_cafelist);
        holder.iv_cafelist = view.findViewById(R.id.iv_cafelist);

        holder.tv_cafelist.setText(result[pos]);
        holder.iv_cafelist.setImageResource(cafeID[pos]);

        holder.iv_cafelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flags[pos] == 0){
                    Intent intent = new Intent(context.getApplicationContext(), MenuListActivity1.class);
                    context.startActivity(intent);
                }
                else if(flags[pos] == 1){
                    Intent intent = new Intent(context.getApplicationContext(), MenuListActivity2.class);
                    context.startActivity(intent);
                }
                else if(flags[pos] == 2){
                    Intent intent = new Intent(context.getApplicationContext(), MenuListActivity3.class);
                    context.startActivity(intent);
                }
                else if(flags[pos] == 3){
                    Intent intent= new Intent(context.getApplicationContext(), MenuListActivity4.class);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context.getApplicationContext(), NotReadyActivity.class);
                    context.startActivity(intent);
                }

            }
        });
        return view;
    }


}



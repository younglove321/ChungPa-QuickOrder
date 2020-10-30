package smu.mp.mptermproject_cpquick;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static Double[] latitudes = {37.545542, 37.546428, 37.545049, 37.545920, 37.545068,37.544626,37.545178,37.544911,37.544444, 37.544820};
    public static Double[] longitudes = {126.965047, 126.964823, 126.966493, 126.967003 ,126.966101,126.968328,126.965810,126.966342,126.966953,126.968497};
    public static String[] cafeNm = {"블루베리", "순헌카페", "본솔", "청파맨션", "너드카페", "핀벨", "로즈 커피", "마돈나 커피","코피티암", "아마스빈"};
    private GoogleMap.OnInfoWindowClickListener infoWindowClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setTitle("청파동 카페 지도");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //숙입역 눈송이 아이콘
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.songe);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap icon_songe = Bitmap.createScaledBitmap(b, 100, 100, false);

        //처음 카메라의 위치 : 숙대 -> 나중에 현재위치로 바꾸기^^
        LatLng sook_sta = new LatLng(37.543633, 126.972177);
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sook_sta, 15));
        mMap.addMarker(new MarkerOptions().position(sook_sta).title("숙대입구역").icon(BitmapDescriptorFactory.fromBitmap(icon_songe)));


        //카페 위치 아이콘
        BitmapDrawable bitmapdraw2 = (BitmapDrawable) getResources().getDrawable(R.drawable.coffee);
        Bitmap b2 = bitmapdraw2.getBitmap();
        Bitmap icon_coffee = Bitmap.createScaledBitmap(b2, 100, 100, false);


        //마커 생성, 리스너 달기
        for (int i = 0; i < latitudes.length; i++) {
            final MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(latitudes[i], longitudes[i]))
                    .title(cafeNm[i])
                    .icon(BitmapDescriptorFactory.fromBitmap(icon_coffee))
                    .snippet("여기를 누르면 해당 카페로 이동!");
            mMap.addMarker(markerOptions);

            markerOptions.getPosition();
            markerOptions.title(cafeNm[i]);

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    if(marker.getTitle().equals(cafeNm[0])){
                        Intent intent = new Intent(getApplicationContext(), MenuListActivity1.class);
                        startActivity(intent);
                    }else if(marker.getTitle().equals(cafeNm[1])){
                        Intent intent = new Intent(getApplicationContext(), MenuListActivity2.class);
                        startActivity(intent);
                    }
                    else if(marker.getTitle().equals(cafeNm[2])){
                        Intent intent = new Intent(getApplicationContext(), MenuListActivity3.class);
                        startActivity(intent);
                    }
                    else if(marker.getTitle().equals(cafeNm[3])){
                        Intent intent= new Intent(getApplicationContext(), MenuListActivity4.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getApplicationContext(), NotReadyActivity.class);
                        startActivity(intent);
                    }

                }

            });

        }

    }
}
package com.tzc.helmiapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.MyLocationStyle;

public class NavigationFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 1;
    MapView mMapView = null;
    AMap aMap = null;
    private AMapLocationClient mLocationClient;
    private String cityCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 先更新隐私合规设置
        MapsInitializer.updatePrivacyShow(requireContext(), true, true);
        MapsInitializer.updatePrivacyAgree(requireContext(), true);
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        // 检查并请求权限
        if (checkLocationPermissions()) {
            initMap(view, savedInstanceState);
            initLocation();
        } else {
            requestLocationPermissions();
        }

        // 动态设置点击监听器
        Button btnNavigate = view.findViewById(R.id.btn_navigate);
        if (btnNavigate != null) {
            btnNavigate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoNavi(v);
                }
            });
        }

        return view;
    }

    private boolean checkLocationPermissions() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermissions() {
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                initMap(requireView(), getArguments());
                initLocation();
            } else {
                // 权限被拒绝，可提示用户
                Toast.makeText(requireContext(), "定位权限被拒绝，无法获取当前位置信息", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initMap(View view, Bundle savedInstanceState) {
        //获取地图控件引用
        mMapView = view.findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        if (mMapView != null) {
            mMapView.onCreate(savedInstanceState);
            if (aMap == null) {
                aMap = mMapView.getMap();
            }
            MyLocationStyle myLocationStyle;
            myLocationStyle = new MyLocationStyle();
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
            myLocationStyle.interval(5000);
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.getUiSettings().setMyLocationButtonEnabled(true);
            aMap.setMyLocationEnabled(true);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        }
    }

    private void initLocation() {
        //初始化定位
        try {
            mLocationClient = new AMapLocationClient(requireContext().getApplicationContext());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    // 定位成功回调信息，设置相关消息
                    cityCode = amapLocation.getCityCode();
                    if (cityCode == null || cityCode.isEmpty()) {
                        Toast.makeText(requireContext(), "未获取到城市代码，使用默认值", Toast.LENGTH_SHORT).show();
                        cityCode = "331000";
                    }
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Toast.makeText(requireContext(), "定位失败，错误码: " + amapLocation.getErrorCode() + ", 错误信息: " + amapLocation.getErrorInfo(), Toast.LENGTH_SHORT).show();
                    cityCode = "331000";
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }

    public void gotoNavi(View view) {
        Intent intent = new Intent(requireContext(), com.tzc.helmiapp.SearchActivity.class);
        intent.putExtra("cityCode", cityCode);
        startActivity(intent);
    }
}
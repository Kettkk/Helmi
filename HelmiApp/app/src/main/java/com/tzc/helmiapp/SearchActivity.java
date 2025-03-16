package com.tzc.helmiapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMapException;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements TextWatcher, Inputtips.InputtipsListener {

    private EditText etSearch;
    private ListView lvSearchResults;
    private ArrayAdapter<String> adapter;
    private Inputtips inputTips;
    private AMapNavi mapNavi;
    private List<Tip> tipList = new ArrayList<>();
    private String cityCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_navigation_search_destination);

        etSearch = findViewById(R.id.et_search);
        etSearch.addTextChangedListener(this);

        lvSearchResults = findViewById(R.id.lv_search_results);
        // 初始化适配器
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        lvSearchResults.setAdapter(adapter);

        inputTips = new Inputtips(this, (InputtipsQuery) null);
        inputTips.setInputtipsListener(this);

        try {
            mapNavi = AMapNavi.getInstance(this);
        } catch (AMapException e) {
            e.printStackTrace();
            Toast.makeText(this, "导航初始化失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        mapNavi.setUseInnerVoice(true, false);

        // 获取传递过来的城市代码
        cityCode = getIntent().getStringExtra("cityCode");
        if (cityCode == null || cityCode.isEmpty()) {
            cityCode = "331000";
        }

        // 添加 ListView 的点击事件监听器
        lvSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 检查 tipList 是否为空以及 position 是否在有效范围内
                if (tipList.isEmpty() || position >= tipList.size()) {
                    Toast.makeText(SearchActivity.this, "数据未准备好，请稍后再试", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Tip data = tipList.get(position);
                    LatLonPoint point = data.getPoint();
                    if (point == null) {
                        Toast.makeText(SearchActivity.this, "该地点无经纬度信息，无法导航", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Poi poi = new Poi(data.getName(), new LatLng(point.getLatitude(), point.getLongitude()), data.getPoiID());
                    AmapNaviParams params = new AmapNaviParams(null, null, poi, AmapNaviType.DRIVER, AmapPageType.ROUTE);
                    AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, null);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SearchActivity.this, "导航启动失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // 文本变化前的操作，这里无需处理
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String keyword = charSequence.toString();
        if (!keyword.isEmpty()) {
            // 创建输入提示查询对象，使用传递过来的城市代码
            InputtipsQuery inputtipsQuery = new InputtipsQuery(keyword, cityCode);
            inputtipsQuery.setCityLimit(true);
            inputTips.setQuery(inputtipsQuery);
            // 发起异步查询
            inputTips.requestInputtipsAsyn();
        } else {
            // 输入为空时清空列表
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // 文本变化后的操作，这里无需处理
    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == 1000) { // 查询成功
            this.tipList = tipList;
            List<String> tipNames = new ArrayList<>();
            for (Tip tip : tipList) {
                tipNames.add(tip.getName());
            }
            // 更新适配器数据
            adapter.clear();
            adapter.addAll(tipNames);
            adapter.notifyDataSetChanged();
        }
    }
}
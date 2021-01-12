package com.example.doanrapphim.TrangChu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doanrapphim.R;
import com.example.doanrapphim.Adapter.AdapterJson;
import com.example.doanrapphim.Adapter.AdapterTopPhim;
import com.example.doanrapphim.Lop.Phim;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class TabSapChieu extends Fragment {

    ViewPager2 viewPager2;
    private final LinkedList<Phim> listPhim = new LinkedList<>();
    String d = "";
    private JSONObject jsonRoot = null;
    private JSONArray jsonArray;
    int l;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabsapchieu, container, false);
        layDsPhim();
        AdapterTopPhim m = new AdapterTopPhim(top5(listPhim),getContext());
        viewPager2 =view.findViewById(R.id.vp1);
        viewPager2.setAdapter(m);
        return view;
    }
    private void layDsPhim(){
        d = new AdapterJson().read(getContext(), R.raw.data);
        try {
            jsonRoot = new JSONObject(d);
            jsonArray = jsonRoot.getJSONArray("phim");
            l = jsonArray.length();
            for (int i = 0; i < l; i++) {
                Phim phim = new Phim();
                phim.setTenphim(jsonArray.getJSONObject(i).getString("tenphim"));
                phim.setHinhanh(jsonArray.getJSONObject(i).getString("hinhanh"));
                phim.setTheloai(jsonArray.getJSONObject(i).getString("theloai"));
                phim.setId(jsonArray.getJSONObject(i).getInt("maphim"));
                phim.setTrangthai(jsonArray.getJSONObject(i).getInt("trangthai"));
                listPhim.add(i,phim);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private LinkedList<Phim> top5(LinkedList<Phim> a) {
        LinkedList<Phim> topPhim = new LinkedList<>();
        int i = 0;
        for (Phim s : a) {
            if(i < 5) {
                if (s.getTrangthai() == 2) {
                    topPhim.add(s);
                    i++;
                }
            }
        }
        return topPhim;
    }
}
package com.example.doanrapphim;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanrapphim.Adapter.MyAdapter;
import com.example.doanrapphim.Adapter.AdapterJson;
import com.example.doanrapphim.Lop.Phim;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;


public class Activity_DanhSachPhim extends AppCompatActivity {
    private final LinkedList<Phim> listPhim = new LinkedList<>();
    String d = "";
    LinearLayout linearLayout;
    RecyclerView recyclerView;
    SearchView searchView;
    Toolbar toolbar;
    private JSONObject jsonRoot = null;
    private JSONArray jsonArray;
    int l;
    TextView textView;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_phim);
        anhxa();
        searchView.clearFocus();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Danh Sách Phim");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layDsPhim();
        myAdapter = new MyAdapter(listPhim, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                linearLayout.setVisibility(View.INVISIBLE);
                return false;
            }
        });

    }
    public void tatca (View view){
        loc("tca");
        }
        public void dachieu (View view){
        loc("dangchieu");
        }
        public void timkiem (View view){
        loc("sapchieu");
        }
        public void anhxa () {
            linearLayout = findViewById(R.id.filter);
            recyclerView = findViewById(R.id.recycle);
            searchView = findViewById(R.id.search);
            toolbar = findViewById(R.id.toolbar);
        }
        private void filter(String s){
          LinkedList<Phim> filter = new LinkedList<>();
          for (Phim c: listPhim){
              if(c.getTenphim().toLowerCase().contains(s.toLowerCase())){
                  filter.add(c);
              }
          }
          myAdapter.filterl(filter);
        }
        private void layDsPhim(){
            d = new AdapterJson().read(getApplicationContext(), R.raw.data);
            try {
                jsonRoot = new JSONObject(d);
                jsonArray = jsonRoot.getJSONArray("phim");
                l = jsonArray.length();
                for (int i = 0; i < l; i++) {
                    Phim phim = new Phim();
                    phim.setTenphim(jsonArray.getJSONObject(i).getString("tenphim"));
                    phim.setHinhanh(jsonArray.getJSONObject(i).getString("hinhanh"));
                    phim.setTheloai(jsonArray.getJSONObject(i).getString("theloai"));
                    phim.setDiem(jsonArray.getJSONObject(i).getInt("diem"));
                    phim.setTuoi(jsonArray.getJSONObject(i).getString("tuoi"));
                    phim.setId(jsonArray.getJSONObject(i).getInt("maphim"));
                    phim.setTrangthai(jsonArray.getJSONObject(i).getInt("trangthai"));
                    listPhim.add(i,phim);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    private void loc(String s){
        LinkedList<Phim> filter = new LinkedList<>();
        switch (s){
            case "tca":{
                for (Phim c: listPhim){
                        filter.add(c);
                    }
                }
                break;
            case "dangchieu":{
                for (Phim c: listPhim){
                    if(c.getTrangthai() == 1)
                    filter.add(c);
                }
                break;
            }
            case "sapchieu" :{
                for (Phim c: listPhim){
                    if(c.getTrangthai() == 2)
                        filter.add(c);
                }
            }
        }
        myAdapter.filterl(filter);
    }
}
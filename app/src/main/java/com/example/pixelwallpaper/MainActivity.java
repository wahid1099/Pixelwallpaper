package com.example.pixelwallpaper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    WallpaperAdapter wallpaperAdapter;
    List<WallpaperModel> wallpaperModelList;
    int pagenumber=1;
    Boolean isScrolling=false;
    int curretItems,totalItems,scrollOutItelms;
    String url="https://api.pexels.com/v1/curated/?page="+pagenumber+"&per_page=80";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recylerView);
        wallpaperModelList=new ArrayList<>();
        wallpaperAdapter=new WallpaperAdapter(this,wallpaperModelList);
        recyclerView.setAdapter(wallpaperAdapter);
        final GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                   curretItems=gridLayoutManager.getChildCount();
                   totalItems=gridLayoutManager.getItemCount();
                   scrollOutItelms=gridLayoutManager.findFirstVisibleItemPosition();
                   if(isScrolling==true && (curretItems+scrollOutItelms==totalItems)){
                       isScrolling=false;
                       fetchwallpaper();

                   }
            }
        });
        fetchwallpaper();
    }
    public  void fetchwallpaper(){
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
            try {
                JSONObject jsonObject=new JSONObject(response);
                JSONArray jsonArray=jsonObject.getJSONArray(  "photos");
            int length=jsonArray.length();
            for (int i=0;i<length;i++){

                JSONObject object = jsonArray.getJSONObject(i);

                int id = object.getInt("id");

                JSONObject objectImages = object.getJSONObject("src");

                String orignalUrl = objectImages.getString("original");
                String mediumUrl = objectImages.getString("medium");

                WallpaperModel wallpaperModel = new WallpaperModel(id,orignalUrl,mediumUrl);
                wallpaperModelList.add(wallpaperModel);

                }
            wallpaperAdapter.notifyDataSetChanged();
            pagenumber++;
            } catch (JSONException e) {

            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization","563492ad6f91700001000001f434f9c6df63401c87a867d30284468e");

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.nav_search){
            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            final EditText editText=new EditText(this);
           editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            alert.setMessage("Enter Catogery e.g Nature");
            alert.setTitle("Seacrh Wallpaper");
            alert.setView(editText);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String query=editText.getText().toString().toLowerCase();
                    url="https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query="+query;

                            wallpaperModelList.clear();
                            fetchwallpaper();

                }
            });
            alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
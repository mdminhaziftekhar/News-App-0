package com.example.newsapp.Activity;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;
import com.example.newsapp.Adapter;
import com.example.newsapp.Api.ApiInterface;
import com.example.newsapp.Api.RetrofitBuilder;
import com.example.newsapp.Model.News;
import com.example.newsapp.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsActivity extends AppCompatActivity    {


    String Date=getDate();
    RecyclerView recyclerView;
    Adapter adapter;
    SwipeRefreshLayout swipe;
    RelativeLayout error;
    LinearLayout linear;
    private static final String myApi="1ad704bc419e4be5909e5fc3495a393a";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Button retry;

         recyclerView=findViewById(R.id.recyclerview);
         swipe=findViewById(R.id.swipe);
         error=findViewById(R.id.error);
         linear=findViewById(R.id.linear);
         retry=findViewById(R.id.retry);
         SearchView searchView=findViewById(R.id.search_bar);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));



         LoadItems("");
         retry.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 LoadItems("");
             }
         });

         swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadItems("");
            }
        });

         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                    LoadItems(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

    }


    public void LoadItems(String k) {

        swipe.setRefreshing(true);
        ApiInterface apiInterface=RetrofitBuilder.getRetrofitBuilder().create(ApiInterface.class);
        Call<News>call;
        if(k.isEmpty())
         call=apiInterface.getData(getCountry(),myApi);
        else
        {
            call=apiInterface.getSearch(k, getLanguage(),"publishedAt",myApi);
        }
            call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                adapter=new Adapter(NewsActivity.this);
                if(response.isSuccessful())
                {
                    adapter.setArticles(response.body().getArticles());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);
                    error.setVisibility(View.GONE);
                    linear.setVisibility(View.VISIBLE);
                }
                else {
                    swipe.setRefreshing(false);
                    Toast.makeText(NewsActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                error.setVisibility(View.VISIBLE);
                linear.setVisibility(View.GONE);
            }
        });
    }
    
    
    public String getDate() {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
        String date=simpleDateFormat.format(calendar.getTime());
        return date ;
    }
    public static   String getCountry(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getCountry());
        return country.toLowerCase();
    }
    public static String getLanguage(){
        Locale locale = Locale.getDefault();
        String language = String.valueOf(locale.getLanguage());
        return language;
    }


}

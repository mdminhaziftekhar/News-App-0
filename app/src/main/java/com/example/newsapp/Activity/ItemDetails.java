package com.example.newsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.newsapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ItemDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Intent intent=getIntent();
        ImageView image=findViewById(R.id.imagecover);
        ProgressBar bar=findViewById(R.id.loadbar);
        TextView title,des;
        title=findViewById(R.id.texttitle);
        des=findViewById(R.id.Description);
        WebView webView=findViewById(R.id.webview);


        if(intent!=null)
        {
            String UrlImage=intent.getStringExtra("Image");
            if(UrlImage!=null)
            {
                Picasso.get().load(UrlImage).into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        bar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
            String t=intent.getStringExtra("Title");
            if(t!=null)
                title.setText(t);

            String d=intent.getStringExtra("Des");
            if(d!=null)
                des.setText(d);

            String Urlweb=intent.getStringExtra("Url");
            if(Urlweb!=null)
            {
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);
                webView.getSettings().setSupportZoom(true);
                webView.getSettings().setBuiltInZoomControls(true);
                webView.getSettings().setDisplayZoomControls(true);
              //  webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(Urlweb);
            }

        }
    }
}
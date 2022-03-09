package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Activity.ItemDetails;
import com.example.newsapp.Model.Article;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.itemHolder> {

    private Context context;
    private List<Article> articles=new ArrayList<>();

    public Adapter(Context context) {
        this.context = context;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public itemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item,parent,false);
        return new itemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemHolder holder, int position) {

        // i used if check item if null or not
        if(articles.get(position).getAuthor()!=null && articles.get(position).getTitle()!=null
                && articles.get(position).getUrlToImage()!=null )
        {
            holder.title.setText(articles.get(position).getTitle());
            holder.authorname.setText(articles.get(position).getAuthor());
            Picasso.get().load(articles.get(position).getUrlToImage())
                    .into(holder.cover, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.bar.setVisibility(View.GONE);
                        }
                        @Override
                        public void onError(Exception e) {

                        }
                    });

            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ItemDetails.class);
                    intent.putExtra("Image",articles.get(position).getUrlToImage());
                    intent.putExtra("Title",articles.get(position).getTitle());
                    intent.putExtra("Url",articles.get(position).getUrl());
                    intent.putExtra("Des",articles.get(position).getDescription());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class  itemHolder extends RecyclerView.ViewHolder{
        TextView title,authorname,publish;
        ImageView cover;
        ProgressBar bar;
        ConstraintLayout item;
        public itemHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            authorname=itemView.findViewById(R.id.authorname);
            cover=itemView.findViewById(R.id.imageView);
            item=itemView.findViewById(R.id.item);
            bar=itemView.findViewById(R.id.progressbar);

        }
    }
}

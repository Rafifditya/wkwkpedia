package com.rijal.wkwkpedia.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rijal.wkwkpedia.Model.Article;
import com.rijal.wkwkpedia.R;

import java.util.ArrayList;

public class ListAdapterArticle extends RecyclerView.Adapter <ListAdapterArticle.ViewHolder>{
    private Context mContext;
    private ArrayList<Article> mListdata;


    public ListAdapterArticle(Context context, ArrayList<Article> mlistdata) {
        this.mContext = context;
        this.mListdata = mlistdata;
    }

    @Override
    public ListAdapterArticle.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ListAdapterArticle.ViewHolder holder, int position) {
        holder.searchingtext.setText(mListdata.get(position).getJudul());
    }

    @Override
    public int getItemCount() {
        return mListdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView searchingtext;


        public ViewHolder(View itemView) {
            super(itemView);
            final Bundle myBundle = new Bundle();
                searchingtext = itemView.findViewById(R.id.tv_searching);
        }


    }
}
package com.rijal.wkwkpedia.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rijal.wkwkpedia.Adapter.ListAdapterArticle;
import com.rijal.wkwkpedia.AppConfig;
import com.rijal.wkwkpedia.AppController;
import com.rijal.wkwkpedia.Model.Article;
import com.rijal.wkwkpedia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    RecyclerView rvArticle;
    ArrayList<Article> listData;
    ProgressBar progressBar;
    ListAdapterArticle adapterArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressBar = (ProgressBar)findViewById(R.id.progressbar_search);
        searchView =(SearchView) findViewById(R.id.searchbar);
        searchView.setQueryHint("Search Article here");

        listData = new ArrayList<>();

        searchView.setQuery("",false);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        rvArticle = (RecyclerView) findViewById(R.id.rv_searching);
        LinearLayoutManager llmArticle = new LinearLayoutManager(this);
        llmArticle.setOrientation(LinearLayoutManager.VERTICAL);
        rvArticle.setLayoutManager(llmArticle);

        adapterArticle = new ListAdapterArticle(getApplicationContext(),listData);
        rvArticle.setAdapter(adapterArticle);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() > 0){
                    progressBar.setVisibility(View.VISIBLE);
                    getSearch(newText);
                }
                return true;
            }
        });


    }

    private void getSearch(final String keyword){
        final StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                AppConfig.SEARCHING,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            //convert response to json object
                            JSONObject obj = new JSONObject(response);

                            //302 = no error on response data
                            if (obj.getInt("code") == 1) {
//                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                //getting the fish from the response
                                JSONArray jsonArraydata = obj.getJSONArray("data");
                                refreshData(jsonArraydata);
                                progressBar.setVisibility(View.INVISIBLE);

                            }
                            else {
                                Toast.makeText(getApplicationContext(), "message", Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException ex){
                            ex.printStackTrace();
                        }

                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "koneksi gagal", Toast.LENGTH_SHORT).show();
            }
        }){
            //TODO METHOD POST PARAMS
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("judul", keyword);
                return params;
            }
        };
        AppController.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void refreshData( JSONArray dataArray) throws JSONException {
        listData.clear();
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject obj = dataArray.getJSONObject(i);
            Log.d("JSON", "valuenya :"+obj);
            listData.add(new Article(
                    obj.getInt("id"),
                    obj.getString("judul"),
                    obj.getString("desciption")
            ));
        }
        adapterArticle.notifyDataSetChanged();
    }
}

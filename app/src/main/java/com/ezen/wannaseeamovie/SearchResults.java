package com.ezen.wannaseeamovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ezen.wannaseeamovie.model2.MovieSearchDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchResults extends AppCompatActivity implements MovieAdapter.selectMovie {
    private String API_KEY = "5427f229ebbce8b92d1224444ade99b8";
    private String search;
    private String search_url = "https://api.themoviedb.org/3/search/movie?api_key="+ API_KEY +"&language=ko-KR&page=1&include_adult=false&query=";

    MovieAdapter adapter;
    RecyclerView recyclerView_result, recyclerView_recommend;
    EditText search_bar;
    TextView noResult;
    ImageView logo_white;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        recyclerView_result = findViewById(R.id.recyclerView_result);
        recyclerView_recommend = findViewById(R.id.recyclerView_recommend);
        noResult = findViewById(R.id.noResult);
        logo_white = findViewById(R.id.logo_white);

        search_bar = findViewById(R.id.search_bar);
        String searchingText = getIntent().getStringExtra("searchingText");
        search_bar.setText(searchingText);

        if(AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        makeRequest();

        logo_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        search_bar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Intent intent = new Intent(getApplicationContext(), SearchResults.class);
                    search_bar = findViewById(R.id.search_bar);
                    search = search_bar.getText().toString();
                    intent.putExtra("searchingText", search);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

    } // onCreate

    void setRecyclerResult(){
        Log.e("test","setRecycler");
        recyclerView_result.setAdapter(adapter);
        recyclerView_result.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    protected void UrlEncoder() {
        try {
            search = URLEncoder.encode(getIntent().getStringExtra("searchingText"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        search_url += search;
    }

    protected void makeRequest() {
        UrlEncoder();

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, search_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            adapter = new MovieAdapter(SearchResults.this);

                            JSONArray results = response.getJSONArray("results");
                            for(int i = 0; i < results.length(); i++){
                                JSONObject object = results.getJSONObject(i);
                                String title = object.getString("title");
                                int id = object.getInt("id");
                                String release_date = object.getString("release_date");
                                String original_language = object.getString("original_language");
                                String poster_path = "https://image.tmdb.org/t/p/original" + object.getString("poster_path");

                                MovieSearchDTO movieSearch = new MovieSearchDTO(title, release_date, original_language, poster_path, id);
                                adapter.addItem(movieSearch);
                            }

                            setRecyclerResult();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    @Override
    public void moveActivity(Intent intent) {
        startActivity(intent);
    }
} // class
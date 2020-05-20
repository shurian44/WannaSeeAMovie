package com.ezen.wannaseeamovie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.bumptech.glide.Glide;
import com.ezen.wannaseeamovie.model2.CastSearchDTO;
import com.ezen.wannaseeamovie.model2.ImageSearchDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailedInfo extends AppCompatActivity implements CastAdapter.selectMovie, ImageAdapter.selectMovie {
    ImageView backdropPath;
    RatingBar ratingBar;
    TextView title, tagLine, basicInfo, basicInfoContents, summary, summaryContents, castNproducer;
    private String API_KEY = "5427f229ebbce8b92d1224444ade99b8";
    private String detail_url = "https://api.themoviedb.org/3/movie/";
    private String poster_url = "https://image.tmdb.org/t/p/original";
    private String cast_url = "";
    private String image_url = "";
    private String movieInfo_url = "";

    private Float rating;
//    static RequestQueue requestQueue;

    RecyclerView recyclerView_cast, recyclerView_screenShot;
    CastAdapter castAdapter;
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_info);

        ratingBar = findViewById(R.id.ratingBar);
        backdropPath = findViewById(R.id.backdropPath);
        title = findViewById(R.id.title);
        tagLine = findViewById(R.id.tagLine);
        basicInfo = findViewById(R.id.basicInfo);
        basicInfoContents = findViewById(R.id.basicInfoContents);
        summary = findViewById(R.id.summary);
        summaryContents = findViewById(R.id.summaryContents);
        castNproducer = findViewById(R.id.castNproducer);

        recyclerView_cast = findViewById(R.id.recyclerView_cast);
        recyclerView_screenShot = findViewById(R.id.recyclerView_screenShot);

        if(AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        movieInfo_url = detail_url + getIntent().getIntExtra("movieID", 0)
                + "?api_key=" + API_KEY + "&language=ko-KR";
        cast_url = detail_url + getIntent().getIntExtra("movieID", 0)
                + "/credits" + "?api_key=" + API_KEY;
        image_url = detail_url + getIntent().getIntExtra("movieID", 0)
                + "/images" + "?api_key=" + API_KEY;

        // api 조회

        makeRequest();

    } // onCreate

    void setRecyclerResult() {
        recyclerView_cast.setAdapter(castAdapter);
        recyclerView_cast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    void setRecyclerImage() {
        recyclerView_screenShot.setAdapter(imageAdapter);
        recyclerView_screenShot.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    protected void makeRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, movieInfo_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            title.setText(response.getString("title")
                                    + "\n" + response.getInt("id"));
                            ratingBar.setRating(Float.parseFloat(response.getString("vote_average"))/2);
                            tagLine.setText("\" " + response.getString("tagline") + " \"");

                            // 장르 parsing
                            JSONArray genres = response.getJSONArray("genres");
                            String genre = "";
                            for(int i = 0; i < genres.length(); i++) {
                                JSONObject genresJSONObject = genres.getJSONObject(i);
                                genre += genresJSONObject.getString("name");
                                if(i != genres.length() - 1) {
                                    genre += " / ";
                                }
                            }

                            // 국가 parsing
                            JSONArray production_countries = response.getJSONArray("production_countries");
                            String country = "";
                            for(int i = 0; i < production_countries.length(); i++) {
                                JSONObject production_countriesJSONObject = production_countries.getJSONObject(i);
                                country += production_countriesJSONObject.getString("name");
                                if(i != production_countries.length() - 1) {
                                    country += " / ";
                                }
                            }

                            // 예산 수익 콤마 삽입.
                            int getBudget = response.getInt("budget");
                            String budget = String.format("%, d", getBudget);
                            int getRevenue = response.getInt("revenue");
                            String revenue = String.format("%, d", getRevenue);

                            // json으로 받은 값들 입력.
                            basicInfoContents.setText("\n원제 : " + response.getString("original_title")
                                    + "\n개봉일 : " + response.getString("release_date")
                                    + "\n상영 시간 : " + response.getString("runtime") + "분"
                                    + "\n장르 : " + genre
                                    + "\n국가 : " + country
                                    + "\n평점 : " + response.getString("vote_average") + "/10"
                                    + "\n제작비 : " + "$ " + budget
                                    + "\n수익금 : " + "$ " + revenue
                                    + "\n인기도 : " + response.getString("popularity"));
                            summaryContents.setText("\n" + response.getString("overview"));
                            Glide.with(getApplicationContext())
                                    .load(poster_url + response.getString("backdrop_path"))
                                    .into(backdropPath);

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

        RequestQueue queue2 = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, cast_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // castAdapter
                            castAdapter = new CastAdapter(DetailedInfo.this);
                            JSONArray cast = response.getJSONArray("cast");
                            for(int i = 0; i < cast.length(); i++) {
                                if(i > 9) break;
                                JSONObject castsJSONObject = cast.getJSONObject(i);
                                String character = castsJSONObject.getString("character");
                                String name = castsJSONObject.getString("name");
                                String profile_path = castsJSONObject.getString("profile_path");
                                int cast_id = castsJSONObject.getInt("cast_id");
                                String credit_id = castsJSONObject.getString("credit_id");
                                int gender = castsJSONObject.getInt("gender");
                                int id = castsJSONObject.getInt("id");
                                int order = castsJSONObject.getInt("order");

                                CastSearchDTO castSearch = new CastSearchDTO(character, name, profile_path, credit_id, cast_id, gender, id, order);
                                castAdapter.addItem(castSearch);
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

        queue2.add(jsonObjectRequest2);

        RequestQueue queue3 = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.GET, image_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // imageAdapter
                            imageAdapter = new ImageAdapter(DetailedInfo.this);
                            JSONArray backdrops = response.getJSONArray("backdrops");
//                            Log.e("test", "backdrops : " + backdrops);
                            for(int i = 0; i < backdrops.length(); i++) {
                                if(i > 9) break;
                                JSONObject imagesJSONObject = backdrops.getJSONObject(i);
                                String file_path = poster_url + imagesJSONObject.getString("file_path");
                                String iso_639_1 = imagesJSONObject.getString("iso_639_1");
                                Double vote_average = imagesJSONObject.getDouble("vote_average");
                                int vote_count = imagesJSONObject.getInt("vote_count");

                                ImageSearchDTO imageSearch = new ImageSearchDTO(file_path, iso_639_1, vote_average, vote_count);
                                imageAdapter.addItem(imageSearch);

                            }

                            setRecyclerImage();

                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue3.add(jsonObjectRequest3);

    }

    @Override
    public void moveActivity(Intent intent) {
        startActivity(intent);
    }

} // class

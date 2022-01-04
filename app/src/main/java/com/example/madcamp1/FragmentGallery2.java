package com.example.madcamp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentGallery2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExamAdapter adapter;
    private ArrayList<ExamItem> examList;
    private RequestQueue requestQueue;

    private EditText search_text; //검색어

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery_2);
        search_text = findViewById(R.id.search_text);

        Button search_btn = findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter.removeAllItem();

                String search_word = "";

                search_word = search_text.getText().toString();

                parseJSON(search_word);
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        examList = new ArrayList<>();

        adapter = new ExamAdapter();
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
    }

    public void EventBtn3(View v) {
        Toast.makeText(getApplicationContext(), "갤러리 탭으로 이동했습니다.", 10).show();
        finish();
    }

    private void parseJSON(String search_word){

        String apiKey = "25082418-2eecb13a478b7c0b566b4024f";

        String url = "https://pixabay.com/api/?key=" + apiKey +  "&q=" +  search_word + "&image_type=photo&pretty=true";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String imageUrl = hit.getString("webformatURL");

                                examList.add(new ExamItem(imageUrl));
                            }

                            adapter = new ExamAdapter(FragmentGallery2.this, examList);

                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

}



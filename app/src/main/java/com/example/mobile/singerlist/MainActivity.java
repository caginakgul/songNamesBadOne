package com.example.mobile.singerlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mobile.singerlist.request.SongService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tvGetSongs;
    RecyclerView recViewSongs;
    private List<SongDetail> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.songList = new ArrayList<>();
        recViewSongs=findViewById(R.id.recViewSongs);
        tvGetSongs=findViewById(R.id.tvGetSongs);
        tvGetSongs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://itunes.apple.com/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                SongService songService = retrofit.create(SongService.class);

                Call<SongModal> call = songService.fetchSongs("search?term=chao&limit=5/");
                call.enqueue(new Callback<SongModal>() {
                    @Override
                    public void onResponse(Call<SongModal> call, Response<SongModal> response) {
                        setRecycler(response.body().getSongList());
                        recViewSongs.setVisibility(View.VISIBLE);
                        tvGetSongs.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<SongModal>call, Throwable t) {
                        recViewSongs.setVisibility(View.GONE);
                        tvGetSongs.setVisibility(View.VISIBLE);
                    }
                });

            }
        });
    }

    private void setRecycler(List<SongDetail> songs){
        songList.addAll(songs);
        SongAdapter songAdapter = new SongAdapter();
        songAdapter.setSongList(songList);
        recViewSongs.setLayoutManager(new LinearLayoutManager(this));
        recViewSongs.setAdapter(songAdapter);
    }
}

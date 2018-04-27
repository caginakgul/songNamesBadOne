package com.example.mobile.singerlist.request;


import com.example.mobile.singerlist.SongModal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by mobile on 23.03.2018.
 */

public interface SongService {
    @GET
    Call<SongModal> fetchSongs(@Url String url);

}

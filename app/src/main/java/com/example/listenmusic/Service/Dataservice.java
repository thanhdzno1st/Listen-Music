package com.example.listenmusic.Service;

import com.example.listenmusic.Models.Banner;
import com.example.listenmusic.Models.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Dataservice {
    @GET("songbanner.php")
    Call<List<Banner>> GetDataBanner();

    @GET("Home_recommend.php")
    Call<List<Song>> GetSong_recommend();
}

package com.example.listenmusic.Service;

import com.example.listenmusic.Models.Banner;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Dataservice {
    @GET("songbanner.php")
    Call<List<Banner>> GetDataBanner();

    @GET("Home_recommend.php")
    Call<List<Song>> GetSong_recommend();

    @GET("Hottiktok_song.php")
    Call<List<Song>> GetSong_hottiktok();

    @GET("home_bolero.php")
    Call<List<Song>> GetSong_bolero();

    @GET("User.php")
    Call<List<User>> GetUser();
}

package com.example.listenmusic.Service;

import com.example.listenmusic.Models.Banner;
import com.example.listenmusic.Models.Playlist;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.Models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @GET("playlist.php")
    Call<List<Playlist>> GetPlayList(@Query("idTaiKhoan") int idTaiKhoan);

    @FormUrlEncoded
    @POST("Add_playlist.php")
    Call<ResponseBody> AddPlaylist(
            @Field("tenPlayList") String tenPlayList,
            @Field("idTaiKhoan") int idTaiKhoan,
            @Field("idBaiHat") String idBaiHat
    );
    @FormUrlEncoded
    @POST("dsbaihat.php")
    Call<List<Song>> Getdsbaihattheobanner(
            @Field("idquangcao") String idquangcao
    );
    @FormUrlEncoded
    @POST("getdsbaihattheonghesi.php")
    Call<List<Song>> Getdsbaihattheonghesi(
            @Field("idnghesi") String idnghesi
    );
}
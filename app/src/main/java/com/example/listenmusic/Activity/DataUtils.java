package com.example.listenmusic.Activity;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenmusic.Adapter.danhsachbaihatAdapter;
import com.example.listenmusic.Models.Song;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.Service.APIservice;
import com.example.listenmusic.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataUtils {

    // Phương thức này có thể được gọi từ bất kỳ Activity nào để lấy bài hát từ quảng cáo
    public static void Getdatabaihat(final Context context, String Idbaihat, final RecyclerView recyclerView, final User user) {
        if (Idbaihat == null || Idbaihat.isEmpty()) {
            Toast.makeText(context, "ID bài hát không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Khởi tạo dịch vụ API
        Dataservice dataservice = APIservice.getService();
        Call<List<Song>> callback = dataservice.Getdsbaihattheobanner(Idbaihat);

        // Gửi yêu cầu đến API
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Song> mangSong = new ArrayList<>(response.body());

                    // Kiểm tra nếu có bài hát
                    if (!mangSong.isEmpty()) {
                        // Lấy ID nghệ sĩ từ bài hát đầu tiên và gọi phương thức tiếp theo để lấy bài hát theo nghệ sĩ
                        String idNgheSi = mangSong.get(0).getIdNgheSi();
                        if (idNgheSi != null && !idNgheSi.isEmpty()) {
                            GetdatadsBaihattheoNghesi(context, idNgheSi, recyclerView, user);
                        } else {
                            Toast.makeText(context, "Không có nghệ sĩ liên quan!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Không có bài hát từ quảng cáo!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "API trả về lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Không thể tải dữ liệu từ quảng cáo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức lấy dữ liệu bài hát theo nghệ sĩ và cập nhật RecyclerView
    public static void GetdatadsBaihattheoNghesi(final Context context, String IdNghesi, final RecyclerView recyclerView, final User user) {
        if (IdNghesi == null || IdNghesi.isEmpty()) {
            Toast.makeText(context, "ID nghệ sĩ không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Khởi tạo dịch vụ API
        Dataservice dataservice = APIservice.getService();
        Call<List<Song>> callback = dataservice.Getdsbaihattheonghesi(IdNghesi);

        // Gửi yêu cầu đến API
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Song> mangSong = new ArrayList<>(response.body());

                    // Kiểm tra nếu có bài hát
                    if (!mangSong.isEmpty()) {
                        // Tạo Adapter và cập nhật RecyclerView
                        danhsachbaihatAdapter adapter = new danhsachbaihatAdapter(context, mangSong, user);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(context, "Không có bài hát nào từ nghệ sĩ!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "API trả về lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Không thể tải dữ liệu từ nghệ sĩ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

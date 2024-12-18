package com.example.listenmusic.widget;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.listenmusic.Activity.MainActivity;
import com.example.listenmusic.R;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.nio.ByteBuffer;

public class FirebaseMessager extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        // kiểm tra
        if(message.getNotification() != null){
            showNotification(message.getNotification().getTitle(),message.getNotification().getBody());
        }


        super.onMessageReceived(message);
    }

    private void showNotification(String title, String body) {
        // Tạo Intent khi người dùng click vào thông báo
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        String channelId = "noti"; // ID của kênh thông báo

        // Tạo PendingIntent để xử lý khi người dùng nhấn vào thông báo
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // Xây dựng thông báo với các tùy chọn như biểu tượng, rung và nội dung
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.goiy2) // Biểu tượng của thông báo
                .setAutoCancel(true) // Tự động huỷ khi người dùng click
                .setVibrate(new long[]{1000, 1000, 1000, 1000}) // Thiết lập thời gian rung
                .setOnlyAlertOnce(true) // Chỉ cảnh báo 1 lần
                .setContentIntent(pendingIntent); // Khi người dùng click vào thông báo, Intent sẽ được gọi

        // Sử dụng custom view để hiển thị thông báo với nội dung tùy chỉnh
        builder.setContent(cutomView(title, body));

        // Lấy NotificationManager để gửi thông báo
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Kiểm tra phiên bản hệ điều hành để tạo kênh thông báo (chỉ áp dụng cho Android 8.0 trở lên)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "web_app", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel); // Tạo kênh thông báo
        }

        // Gửi thông báo
        notificationManager.notify(0, builder.build());
    }



    private RemoteViews  cutomView(String title, String body){
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification );
        remoteViews.setTextViewText(R.id.title_noti, title); // title là đối số truyền vào phương thức
        remoteViews.setTextViewText(R.id.body_noti, body);   // body là đối số truyền vào phương thức
        remoteViews.setImageViewResource(R.id.notification,R.drawable.goiy2);
        return remoteViews;
    }
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        // Lấy token mới
        Log.d("FCM", "New FCM Token: " + token);
    }

    // Nếu muốn lấy token ngay từ FirebaseMessagingService
    private void getFCMToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Lấy token
                    String token = task.getResult();
                    Log.d("FCM", "FCM Token: " + token);
                });
    }
}

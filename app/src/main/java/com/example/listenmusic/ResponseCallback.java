package com.example.listenmusic;

public interface ResponseCallback {
    void onResponse(String response);
    void onError(Throwable throwable);

}

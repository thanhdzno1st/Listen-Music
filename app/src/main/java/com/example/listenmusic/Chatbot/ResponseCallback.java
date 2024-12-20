package com.example.listenmusic.Chatbot;

public interface ResponseCallback {
    void onResponse(String response);
    void onError(Throwable throwable);

}

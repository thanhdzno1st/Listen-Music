package com.example.listenmusic.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.listenmusic.Chatbot.GeminiResp;
import com.example.listenmusic.R;
import com.example.listenmusic.Chatbot.ResponseCallback;
import com.google.ai.client.generativeai.java.ChatFutures;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class ChatBoxDialogFragment extends DialogFragment {

    private TextInputEditText querryEditText;
    private ImageView sendQuerry, appIcon;
    private FloatingActionButton buttonshowDialog;
    private ProgressBar progressBar;
    private LinearLayout chatResponse;
    private ChatFutures chatModel;

    public static ChatBoxDialogFragment newInstance() {
        return new ChatBoxDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chatbox, container, false);

        // Khởi tạo các view
        progressBar = view.findViewById(R.id.progressBar);
        chatResponse = view.findViewById(R.id.chatResponse);
        appIcon = view.findViewById(R.id.appIcon);
        chatModel = getChatModel();
        sendQuerry = view.findViewById(R.id.sendMessage_chat);
        querryEditText = view.findViewById(R.id.querryEditText_chat);
        // Xử lý sự kiện gửi tin nhắn
        sendQuerry.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            appIcon.setVisibility(View.GONE);
            String query = querryEditText.getText().toString();
            querryEditText.setText("");
            chatBody("You", query, ContextCompat.getDrawable(requireContext(), R.drawable.user));

            GeminiResp.getResponse(chatModel, query, new ResponseCallback() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.GONE);
                    chatBody("Chatbox TPBmusic", response, ContextCompat.getDrawable(requireContext(), R.drawable.logotbp_corner));
                }

                @Override
                public void onError(Throwable throwable) {
                    chatBody("Chatbox TPBmusic", "Vui lòng thử lại sau!", ContextCompat.getDrawable(requireContext(), R.drawable.logotbp_corner));
                    progressBar.setVisibility(View.GONE);
                }
            });
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Cài đặt cho Dialog
        if (getDialog() != null && getDialog().getWindow() != null) {
            // Cài đặt kích thước cụ thể cho dialog
            int width = (int) (getResources().getDisplayMetrics().density * 370);  // 350dp
            int height = (int) (getResources().getDisplayMetrics().density * 700); // 600dp
            getDialog().getWindow().setLayout(width, height);
            // Hiển thị dialog ở chính giữa màn hình
            getDialog().getWindow().setGravity(Gravity.CENTER);  // Đặt gravity là CENTER để dialog nằm ở giữa
        }
    }
    private ChatFutures getChatModel() {
        GeminiResp model = new GeminiResp();
        GenerativeModelFutures modelFutures = model.getModel();
        return modelFutures.startChat();
    }

    private void chatBody(String userName, String query, Drawable image) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View view = inflater.inflate(R.layout.chat_message, null);
        TextView name = view.findViewById(R.id.name_chat);
        TextView message = view.findViewById(R.id.message_chat);
        ImageView logo = view.findViewById(R.id.logo_chat);

        name.setText(userName);
        message.setText(query);
        logo.setImageDrawable(image);
        chatResponse.addView(view);

        // Cuộn xuống cuối ScrollView
        ScrollView scrollView = getView().findViewById(R.id.scrollView_chat);
        if (scrollView != null) {
            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
        }
    }
}

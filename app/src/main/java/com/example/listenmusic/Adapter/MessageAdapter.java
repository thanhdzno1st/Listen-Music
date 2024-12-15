package com.example.listenmusic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenmusic.Message;
import com.example.listenmusic.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    List<Message> messageList;
    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(chatView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        if(message.getSentBy().equals(Message.SENT_BY_ME)){
            holder.leftchatView.setVisibility(View.GONE);
            holder.rightchatView.setVisibility(View.VISIBLE);
            holder.rightv.setText(message.getMessage());
        }else{
            holder.leftchatView.setVisibility(View.GONE);
            holder.leftchatView.setVisibility(View.VISIBLE);
            holder.lefttv.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftchatView, rightchatView;
        TextView lefttv,rightv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leftchatView = itemView.findViewById(R.id.left_chat_view);
            rightchatView = itemView.findViewById(R.id.right_chat_view);
            lefttv = itemView.findViewById(R.id.left_chat_text_view);
            rightv = itemView.findViewById(R.id.right_chat_text_view);

        }
    }
}

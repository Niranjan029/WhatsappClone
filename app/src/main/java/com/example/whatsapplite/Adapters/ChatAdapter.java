package com.example.whatsapplite.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapplite.R;
import com.example.whatsapplite.Users.MessageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {
    ArrayList<MessageModel> messageModel;
    Context context;
    String recId ;
    final int SENDER_VIEW_TYPE = 1;
    final int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModel, Context context) {
        this.messageModel = messageModel;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModel, Context context, String recId) {
        this.messageModel = messageModel;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SENDER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver, parent, false);
            return new ReceiverViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (messageModel.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        } else {
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModels = messageModel.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are yopu sure to delete the message ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String senderRoom = FirebaseAuth.getInstance().getUid() + recId ;
                                database.getReference().child("chats").child(senderRoom).child(messageModels.getMessageId()).setValue(null);

                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                            }
                        }).show();

                return false;
            }
        });
        if (holder.getClass() == SenderViewHolder.class) {
            ((SenderViewHolder) holder).SenderMsg.setText(messageModels.getMessage());
        } else {
            ((ReceiverViewHolder) holder).ReceiverMsg.setText(messageModels.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageModel.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView ReceiverMsg, ReceiverTime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            ReceiverMsg = itemView.findViewById(R.id.receiverText);
            ReceiverTime = itemView.findViewById(R.id.receiverTime);
        }


    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView SenderMsg, SenderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            SenderMsg = itemView.findViewById(R.id.senderText);
            SenderTime = itemView.findViewById(R.id.senderTime);
        }

    }
}

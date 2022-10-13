package com.example.whatsapplite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapplite.Adapters.ChatAdapter;
import com.example.whatsapplite.Fragments.chatFragment;
import com.example.whatsapplite.Users.MessageModel;
import com.example.whatsapplite.Users.Users;
import com.example.whatsapplite.databinding.ActivityChatdetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatdetailActivity extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth auth;
    ActivityChatdetailBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatdetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        String senderId = auth.getUid();
        String receiverid = getIntent().getStringExtra("userId");
        String profilePic = getIntent().getStringExtra("ProfilePic");
        String userName = getIntent().getStringExtra("UserName");
        binding.userNametv.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.profile).into(binding.profileImage);






        binding.imgbackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatdetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        final ArrayList<MessageModel> messageModel = new ArrayList<>();
        final ChatAdapter chatadapter = new ChatAdapter(messageModel, this);
        binding.ChatDetailRecyclerView.setAdapter(chatadapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.ChatDetailRecyclerView.setLayoutManager(layoutManager);
        String SenderRoom = senderId + receiverid;
        String ReceiverRoom = receiverid + senderId;


        database.getReference().child("chats").child(SenderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModel.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    MessageModel model = snapshot1.getValue(MessageModel.class);
                    messageModel.add(model);
                }

                chatadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = binding.etMessage.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimestamp(new Date().getTime());
                binding.etMessage.setText("");
                database.getReference().child("chats").child(SenderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("chats").child(ReceiverRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                //Toast.makeText(ChatdetailActivity.this, "message sent", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

    }
}
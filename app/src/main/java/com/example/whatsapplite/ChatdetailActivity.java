package com.example.whatsapplite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.whatsapplite.databinding.ActivityChatdetailBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ChatdetailActivity extends AppCompatActivity {
 FirebaseDatabase database ;
 FirebaseAuth auth ;
 ActivityChatdetailBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatdetailBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_chatdetail);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        String senderId = auth.getUid();
        String receiverid = getIntent().getStringExtra("userId");
        String profilePic = getIntent().getStringExtra("ProfilePic");
        String userName = getIntent().getStringExtra("UserName");
        binding.userNametv.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.profile).into(binding.profileImage);

    }
}
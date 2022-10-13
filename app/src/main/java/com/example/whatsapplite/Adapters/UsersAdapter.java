package com.example.whatsapplite.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapplite.ChatdetailActivity;
import com.example.whatsapplite.R;
import com.example.whatsapplite.Users.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.viewHolder>{

ArrayList<Users> list ;
Context context ;

    public UsersAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
      Users users = list.get(position);
      Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.profile).into(holder.image);
      holder.userName.setText(users.getUsername());

        FirebaseDatabase database = FirebaseDatabase.getInstance();

          database.getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid() + users.getUserid()).orderByChild("timestamp")
                          .limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                          if(snapshot.hasChildren())
                          {
                              for(DataSnapshot snapshot1 : snapshot.getChildren())
                              {
                                  holder.lastMessage.setText(snapshot1.child("message").getValue(String.class));
                              }
                          }
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError error) {

                      }
                  });


      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(context, ChatdetailActivity.class);
              intent.putExtra("userId",users.getUserid()) ;
              intent.putExtra("ProfilePic",users.getProfilepic());
              intent.putExtra("UserName",users.getUsername());
             context.startActivity(intent);
          }
      });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewHolder  extends RecyclerView.ViewHolder
    {
        ImageView image ;
        TextView  userName ;
        TextView lastMessage ;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profileImage);
            userName = itemView.findViewById(R.id.userNameList);
            lastMessage = itemView.findViewById(R.id.lastMessage);
        }
    }
}

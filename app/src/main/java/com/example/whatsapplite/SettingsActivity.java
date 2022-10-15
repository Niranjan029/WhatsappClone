package com.example.whatsapplite;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapplite.Users.Users;
import com.example.whatsapplite.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {
ActivitySettingsBinding binding ;
FirebaseDatabase database ;
FirebaseStorage storage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide() ;
        database = FirebaseDatabase.getInstance() ;
        storage  = FirebaseStorage.getInstance() ;
        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,MainActivity.class));
            }
        });
        database.getReference().child("users")
                .child(FirebaseAuth.getInstance().getUid())
                       .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                Picasso.get().load(user.getProfilepic()).placeholder(R.drawable.profile).into(binding.profileimage);
                binding.etusername.setText(user.getUsername());
                binding.etabout.setText(user.getAbout());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.imgaddprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent();
                 intent.setAction(Intent.ACTION_GET_CONTENT);
                 intent.setType("image/*");
                 startActivityForResult(intent,33);
            }
        });
        
   binding.btnsave.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
              String userName = binding.etusername.getText().toString();
              String about = binding.etabout.getText().toString();

           HashMap<String,Object> obj = new HashMap<>();
           obj.put("username",userName);
           obj.put("about",about);

           database.getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                   .updateChildren(obj);
       }
   });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null)
        {
            Uri sfile = data.getData();
            binding.profileimage.setImageURI(sfile);
            final StorageReference reference = storage.getReference().child("profile_Images").child(FirebaseAuth.getInstance().getUid());
            reference.putFile(sfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profilepic").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(SettingsActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}
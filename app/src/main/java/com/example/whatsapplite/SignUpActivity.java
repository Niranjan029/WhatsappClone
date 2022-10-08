package com.example.whatsapplite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapplite.Users.Users;
import com.example.whatsapplite.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {
ActivitySignupBinding binding ;
FirebaseAuth mAuth ;
FirebaseDatabase database ;
ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We Are Creating Your Account");
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        if(mAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        binding.tvAlreadyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
       binding.btnsignup.setOnClickListener(new View.OnClickListener() {
           @Override

           public void onClick(View view) {
               progressDialog.show();
               if(TextUtils.isEmpty(binding.editname.getText().toString().trim()))
               {
                   binding.editname.setError("Name is required") ;
                   return;
               }
               if(TextUtils.isEmpty(binding.editemail.getText().toString().trim()))
               {
                   binding.editemail.setError("Email is required");
                   return;
               }
               if(TextUtils.isEmpty(binding.editpassword.getText().toString().trim()))
               {
                   binding.editpassword.setError("Password is required");
                   return;
               }
               if(binding.editpassword.getText().toString().trim().length()<6)
               {
                   binding.editpassword.setError("Password is to short");
                   return;
               }


               mAuth.createUserWithEmailAndPassword(binding.editemail.getText().toString().trim(),binding.editpassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       progressDialog.dismiss();
                       if(!task.isSuccessful())
                       {
                           Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }
                       else
                       {
                           Users user = new Users(binding.editemail.getText().toString(),binding.editpassword.getText().toString(),binding.editname.getText().toString());
                           String id = task.getResult().getUser().getUid() ;

                           database.getReference().child("users").child(id).setValue(user);

                           Toast.makeText(SignUpActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),MainActivity.class));
                       }
                   }
               });

           }
       });



    }
}
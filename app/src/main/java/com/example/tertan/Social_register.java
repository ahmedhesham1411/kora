package com.example.tertan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Social_register extends AppCompatActivity {
    AppCompatEditText pass,con_pass,phone;
    String name,email;
    Button continue_btn;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_register);

        auth = FirebaseAuth.getInstance();
        init();

    }


    void init(){
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
       // Toast.makeText(this, name +"\n"+ email, Toast.LENGTH_SHORT).show();

        pass = findViewById(R.id.txt_password_media);
        con_pass = findViewById(R.id.txt_confirm_pass_media);
        phone = findViewById(R.id.txt_phone_media);
        continue_btn = findViewById(R.id.continue_btn);
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_password = pass.getText().toString();
                String txt_con_password = con_pass.getText().toString();
                String txt_phone = phone.getText().toString();

                if (TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_con_password) || TextUtils.isEmpty(txt_phone)){
                    Toast.makeText(Social_register.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else if (txt_password.length() <6){
                    Toast.makeText(Social_register.this, "password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else if (!txt_con_password.equals(txt_con_password) || !txt_password.equals(txt_con_password)){
                    Toast.makeText(Social_register.this, "password doesn't match", Toast.LENGTH_SHORT).show();
                }
                else if (txt_phone.length() != 11){
                    Toast.makeText(Social_register.this, "please enter a valid phone number", Toast.LENGTH_SHORT).show();
                }
                else {
                    register(name,email,txt_password,txt_phone);
                }
            }
        });

    }

    private void register (final String username, final String email, final String password, final String phonee){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userId = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("users").child(userId);

                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("id",userId);
                    hashMap.put("username",username);
                    hashMap.put("imageURL","defult");
                    hashMap.put("Password",password);
                    hashMap.put("email",email);
                    hashMap.put("phone",phonee);
                    hashMap.put("status","owner");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(Social_register.this,Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });
                }
                else {
                    Toast.makeText(Social_register.this, "can't register with this email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}

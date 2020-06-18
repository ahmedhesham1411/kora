package com.example.tertan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tertan.Utils.Constant;
import com.facebook.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity {
    CircleImageView upload_image;
    Button btn_register;
    FirebaseAuth auth;
    DatabaseReference reference;
    AppCompatEditText name,email,password,confirm_password,phone;
    View btnGallery,btnCamera,btnCancel;
    View bottomSheetDialogView;
    AppCompatTextView go_to_login;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        context = this;
        init();
        auth = FirebaseAuth.getInstance();

    }

    public void init(){
        name = findViewById(R.id.txt_name);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        confirm_password = findViewById(R.id.txt_confirm_pass);
        phone = findViewById(R.id.txt_phone);
        btn_register = findViewById(R.id.btn_register);
        upload_image = findViewById(R.id.profile_image);

        go_to_login = findViewById(R.id.go_to_login);
        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = name.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_con_password = confirm_password.getText().toString();
                String txt_phone = phone.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_con_password) || TextUtils.isEmpty(txt_phone)){
                    Constant.ShowAlert("All fields are required",context);
                }
                else if (txt_password.length() <6){
                    Constant.ShowAlert("password must be at least 6 characters",context);
                }
                else if (!txt_con_password.equals(txt_con_password) || !txt_password.equals(txt_con_password)){
                    Constant.ShowAlert("password doesn't match",context);
                }
                else if (txt_phone.length() != 11){
                    Constant.ShowAlert("please enter a valid mobile number",context);
                }
                else if (!isNetworkAvailable()){
                    Constant.ShowAlert("Check Internet Connection",context);
                }
                else {
                    register(txt_username,txt_email,txt_password,txt_phone);
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
                    hashMap.put("status","user");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(Register.this,Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });
                }
                else {
                    Toast.makeText(Register.this, "can't register with this email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

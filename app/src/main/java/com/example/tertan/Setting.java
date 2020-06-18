package com.example.tertan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import static com.example.tertan.Utils.Constant.alertDialog1;

public class Setting extends AppCompatActivity implements View.OnClickListener {

    AppCompatImageView go_back;
    AppCompatTextView txt_profile,txt_about_us,txt_contact_us,txt_terms,txt_logout;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    void init(){
        context = this;
        go_back = findViewById(R.id.go_back);
        txt_profile = findViewById(R.id.txt_profile);
        txt_about_us = findViewById(R.id.txt_about_us);
        txt_contact_us = findViewById(R.id.txt_contact_us);
        txt_terms = findViewById(R.id.txt_terms);
        txt_logout = findViewById(R.id.txt_logout);

        go_back.setOnClickListener(this);
        txt_profile.setOnClickListener(this);
        txt_about_us.setOnClickListener(this);
        txt_contact_us.setOnClickListener(this);
        txt_terms.setOnClickListener(this);
        txt_logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_back:
                finish();

                break;
            case R.id.txt_profile:
                Intent intent = new Intent(Setting.this,Profile.class);
                startActivity(intent);

                break;
            case R.id.txt_about_us:
                Intent intent1 = new Intent(Setting.this,About_us.class);
                startActivity(intent1);

                break;
            case R.id.txt_contact_us:
                Intent intent2 = new Intent(Setting.this,Contact_us.class);
                startActivity(intent2);

                break;
            case R.id.txt_terms:
                Intent intent3 = new Intent(Setting.this,Terms_and_conditions.class);
                startActivity(intent3);

                break;
            case R.id.txt_logout:
              ShowAlert(context);
        }
    }

    public void ShowAlert(final Context context) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_log_out, null, false);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.ErrorDialog);
        builder.setView(view);
        alertDialog1 = builder.create();
        alertDialog1.show();
        alertDialog1.setCancelable(false);

        Button ok,no;
        ok = view.findViewById(R.id.yes_logout);
        no = view.findViewById(R.id.cancel_logout);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent4 = new Intent(v.getContext(),Log_In.class);
                context.startActivity(intent4);
                finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });
    }
}

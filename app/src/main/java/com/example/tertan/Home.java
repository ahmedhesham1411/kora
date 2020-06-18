package com.example.tertan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tertan.Adapter.Stadium_adapter;
import com.example.tertan.Model.Stadium_model;
import com.example.tertan.Model.Stadium_model_original;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements Stadium_adapter.OnClickHander {

    Stadium_adapter stadium_adapter;
    RecyclerView stadium_recycler;
    Button add_staduim;
    RelativeLayout first_layout;
    FirebaseUser fuser;
    DatabaseReference reference;
    List<Stadium_model_original> stadium_model_originals;
    Context context;
    String item_id;
    AppCompatImageView go_to_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        stadium_recycler = findViewById(R.id.home_recycler);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users").child(fuser.getUid());
        go_to_setting = findViewById(R.id.go_to_setting);
        go_to_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Setting.class);
                startActivity(intent);
            }
        });
        getData();
    }

    private void getData(){

        stadium_model_originals = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Stadiums");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                stadium_model_originals.clear();
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()){
                    Stadium_model_original stadium_model_original = Snapshot.getValue(Stadium_model_original.class);
                    stadium_model_originals.add(stadium_model_original);
                        stadium_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        stadium_recycler.setHasFixedSize(true);

                        stadium_adapter = new Stadium_adapter(stadium_model_originals,getApplicationContext(),Home.this);
                        stadium_recycler.setAdapter(stadium_adapter);


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getApplicationContext(), Stadium_Details.class);
        intent.putExtra("item_id",stadium_model_originals.get(position).getItem_id());
        startActivity(intent);
    }
}

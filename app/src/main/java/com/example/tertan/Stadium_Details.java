package com.example.tertan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import com.bumptech.glide.Glide;
import com.example.tertan.Model.Stadium_model_original;
import com.example.tertan.Model.User;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Locale;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;

public class Stadium_Details extends AppCompatActivity {

    FirebaseUser fuser;
    DatabaseReference reference,reference1;
    String user_id,item_id;
    double latitude,longitude;
    AppCompatTextView prof_name,stad_name,hour_price,txt_address,txt_properties;
    CircleImageView prof_img;
    LinearLayoutCompat go_to_maps;
    BottomSheetBehavior bottomSheetBehavior;
    FrameLayout bottomSheet_layout;
    AppCompatImageView stad_img;
    Button txt_hide_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium__details);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        user_id = fuser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Intent intent = getIntent();
        item_id = intent.getStringExtra("item_id");
        init();
        getData();

    }

    void init(){
        stad_name = findViewById(R.id.stad_name);
        hour_price = findViewById(R.id.price);
        txt_address = findViewById(R.id.txt_address);
        txt_properties = findViewById(R.id.details_stadium);
        go_to_maps = findViewById(R.id.go_to_maps);
        bottomSheet_layout = findViewById(R.id.ahmedSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet_layout);
        bottomSheetBehavior.setState(STATE_COLLAPSED);
        bottomSheetBehavior.setHideable(false);
        stad_img = findViewById(R.id.stad_img);
    }


    private void getData(){


        reference = FirebaseDatabase.getInstance().getReference("Stadiums").child(item_id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final Stadium_model_original stadium_model_original = dataSnapshot.getValue(Stadium_model_original.class);
                try {
                    stad_name.setText(stadium_model_original.getStadium_name());
                    hour_price.setText(stadium_model_original.getPrice());
                    txt_properties.setText(stadium_model_original.getProperties());
                    txt_address.setText(stadium_model_original.getStadium_address());
                    Glide.with(getApplicationContext()).load(stadium_model_original.getImageURL()).into(stad_img);
                    go_to_maps.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            latitude = Double.parseDouble(stadium_model_original.getLatitude());
                            longitude = Double.parseDouble(stadium_model_original.getLongitude());
                            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", latitude, longitude);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            startActivity(intent);
                        }
                    });
                }
                catch (Exception e){
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() ==  STATE_EXPANDED){
            bottomSheetBehavior.setState(STATE_COLLAPSED);
        }
        else {
            super.onBackPressed();
        }
    }
}

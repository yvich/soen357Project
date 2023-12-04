package com.tar.iq.petcare.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tar.iq.petcare.R;
import com.tar.iq.petcare.databinding.ActivityMainBinding;
import com.tar.iq.petcare.ui.agenda.AgendaActivity;
import com.tar.iq.petcare.ui.mypet.MiPetActivity;
import com.tar.iq.petcare.ui.myvet.MiVetActivity;
import com.tar.iq.petcare.ui.resources.ResourceActivity;
import com.tar.iq.petcare.utils.SharedPrefUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    private SharedPrefUtils prefUtils;
    ActivityMainBinding binding;


    CircleImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#FFE4E1"));
        }
        prefUtils = new SharedPrefUtils(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("userData");
        databaseReference.child("applicants").child(prefUtils.getString("email", "").toString().replace(".", "")).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("dev", "onDataChange: " + dataSnapshot.child("name").getValue(String.class));
                prefUtils.save("name", dataSnapshot.child("name").getValue(String.class));
                prefUtils.save("profile", dataSnapshot.child("profileImageUrl").getValue(String.class));
                Glide.with(MainActivity.this).load(dataSnapshot.child("profileImageUrl").getValue(String.class)).into(binding.profile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
        binding.resources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResourceActivity.class));

            }
        });
        binding.mivet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MiVetActivity.class));

            }
        });
        binding.mipet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MiPetActivity.class));

            }
        });
        binding.agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AgendaActivity.class));

            }
        });

    }
}
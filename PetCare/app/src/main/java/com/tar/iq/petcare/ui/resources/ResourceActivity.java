package com.tar.iq.petcare.ui.resources;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.tar.iq.petcare.R;
import com.tar.iq.petcare.databinding.ActivityMainBinding;
import com.tar.iq.petcare.databinding.ActivityResourceBinding;
import com.tar.iq.petcare.ui.tips.TipsActivity;

public class ResourceActivity extends AppCompatActivity {
    ActivityResourceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_resource);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#FFE4E1"));
        }
        binding.reptiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ResourceActivity.this, TipsActivity.class);
                myIntent.putExtra("kind", "Reptiles"); //Optional parameters
                startActivity(myIntent);
                finish();
            }
        });
        binding.birds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ResourceActivity.this, TipsActivity.class);
                myIntent.putExtra("kind", "Bird"); //Optional parameters
                startActivity(myIntent);
                finish();
            }
        });
        binding.cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ResourceActivity.this, TipsActivity.class);
                myIntent.putExtra("kind", "Cat"); //Optional parameters
                startActivity(myIntent);
                finish();
            }
        });
        binding.dogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ResourceActivity.this, TipsActivity.class);
                myIntent.putExtra("kind", "Dog"); //Optional parameters
                startActivity(myIntent);
                finish();
            }
        });
    }
}
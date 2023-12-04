package com.tar.iq.petcare.ui.myvet.addmivet;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tar.iq.petcare.R;
import com.tar.iq.petcare.databinding.ActivityAddMiVetBinding;
import com.tar.iq.petcare.model.User;
import com.tar.iq.petcare.utils.SharedPrefUtils;

import java.util.Random;

public class AddMiVet extends AppCompatActivity {
    ActivityAddMiVetBinding binding;
    DatabaseReference databaseReference;
    private SharedPrefUtils prefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_mi_vet);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#FFE4E1"));
        }
        prefUtils = new SharedPrefUtils(this);
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                String key = String.valueOf(rand.nextInt(100000000));
                if (!binding.name.getText().toString().isEmpty() && !binding.phone.getText().toString().isEmpty() && !binding.clinic.getText().toString().isEmpty() && !binding.address.getText().toString().isEmpty()) {
                    Toast.makeText(AddMiVet.this, "Success", Toast.LENGTH_LONG).show();
                    databaseReference = FirebaseDatabase.getInstance().getReference("mivets").child(prefUtils.getString("email", "").replace(".", "")).child(key);
                    User user = new User(binding.name.getText().toString(), binding.address.getText().toString(), binding.clinic.getText().toString(), binding.phone.getText().toString(), key);
                    databaseReference.setValue(user);
                    finish();
                } else {
                    Toast.makeText(AddMiVet.this, "Missing fields!", Toast.LENGTH_LONG).show();

                }


            }
        });
    }
}
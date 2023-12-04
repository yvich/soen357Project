package com.tar.iq.petcare.ui.myvet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tar.iq.petcare.R;
import com.tar.iq.petcare.adapter.PetDataAdapter;
import com.tar.iq.petcare.databinding.ActivityMiVetBinding;
import com.tar.iq.petcare.interfaces.MyInterface;
import com.tar.iq.petcare.model.User;
import com.tar.iq.petcare.ui.myvet.addmivet.AddMiVet;
import com.tar.iq.petcare.ui.resources.ResourceActivity;
import com.tar.iq.petcare.utils.SharedPrefUtils;

import java.util.ArrayList;

public class MiVetActivity extends AppCompatActivity implements MyInterface {
    DatabaseReference databaseReference;
    ArrayList<User> list;
    PetDataAdapter petdataShowAdapter;
    ProgressDialog progressDialog;

    private SharedPrefUtils prefUtils;
    ActivityMiVetBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mi_vet);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#FFE4E1"));
        }
        progressDialog = new ProgressDialog(this);
        list = new ArrayList<>();
        prefUtils = new SharedPrefUtils(this);
        petdataShowAdapter = new PetDataAdapter(this, list, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recycleView.setLayoutManager(linearLayoutManager);
        databaseReference = FirebaseDatabase.getInstance().getReference("mivets");
        databaseReference.child(prefUtils.getString("email", "").toString().replace(".", "")).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Log.i("dev", "dataSnapshot: " + dataSnapshot);
                        User clientInfo = dataSnapshot.getValue(User.class);
                        list.add(clientInfo);
                        binding.recycleView.setAdapter(petdataShowAdapter);
                        petdataShowAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                } else {
                    progressDialog.setTitle(getString(R.string.app_name));
                    progressDialog.setMessage("No Record Found");
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.addvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddMiVet.class));

            }
        });

    }

    @Override
    public void click(User model) {

    }
}
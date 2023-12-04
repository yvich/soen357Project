package com.tar.iq.petcare.ui.mypet.addpets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tar.iq.petcare.R;
import com.tar.iq.petcare.databinding.ActivityAddMiPetsBinding;
import com.tar.iq.petcare.databinding.ActivityAddMiVetBinding;
import com.tar.iq.petcare.databinding.ActivityMiPetBinding;
import com.tar.iq.petcare.model.User;
import com.tar.iq.petcare.ui.myvet.addmivet.AddMiVet;
import com.tar.iq.petcare.utils.SharedPrefUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class AddMiPets extends AppCompatActivity {
    ActivityAddMiPetsBinding binding;
    DatabaseReference databaseReference;
    private SharedPrefUtils prefUtils;
    String[] gender;
    String[] sterlization;
    String[] vet;
    String[] type;
    ArrayList<User> list;

    ArrayAdapter genderArrayAdapter;
    ArrayAdapter sterlizationArrayAdapter;
    ArrayAdapter vetArrayAdapter;
    ArrayAdapter typeArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_mi_pets);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#FFE4E1"));
        }
        list = new ArrayList<>();
        prefUtils = new SharedPrefUtils(this);


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
                    }
                    List<String> names = new ArrayList<>();
                    for (User nameModel : list) {
                        names.add(nameModel.getName());
                    }
                    vetArrayAdapter = new ArrayAdapter(AddMiPets.this, R.layout.spinner, names);
                    binding.vetSpinner.setAdapter(vetArrayAdapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        gender = new String[]{ getString(R.string.male), getString(R.string.female)};
        sterlization = new String[]{getString(R.string.sterlization),getString(R.string.yes), getString(R.string.no)};
        type = new String[]{getString(R.string.dogs), getString(R.string.cat), getString(R.string.reptiles), getString(R.string.birds)};
        genderArrayAdapter = new ArrayAdapter(this, R.layout.spinner, gender);
        binding.genderSpinner.setAdapter(genderArrayAdapter);
        sterlizationArrayAdapter = new ArrayAdapter(this, R.layout.spinner, sterlization);
        binding.sterlizationSpinner.setAdapter(sterlizationArrayAdapter);
        typeArrayAdapter = new ArrayAdapter(this, R.layout.spinner, type);
        binding.typeSpinner.setAdapter(typeArrayAdapter);
        prefUtils = new SharedPrefUtils(this);
        binding.dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMiPets.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String markingDate = dayOfMonth + "-" + monthOfYear + "-" + year;
                        binding.dob.setText(markingDate);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                String key = String.valueOf(rand.nextInt(100000000));
                if (!binding.name.getText().toString().isEmpty() && !binding.breed.getText().toString().isEmpty() && !binding.dob.getText().toString().isEmpty() && !binding.allergies.getText().toString().isEmpty()) {
                    Toast.makeText(AddMiPets.this, "Success", Toast.LENGTH_LONG).show();
                    databaseReference = FirebaseDatabase.getInstance().getReference("mipet").child(prefUtils.getString("email", "").replace(".", "")).child(key);
                    User user = new User(binding.name.getText().toString(), binding.breed.getText().toString(), binding.dob.getText().toString(), binding.allergies.getText().toString(),
                            binding.genderSpinner.getSelectedItem().toString(),binding.typeSpinner.getSelectedItem().toString(),binding.sterlizationSpinner.getSelectedItem().toString(),
                            binding.vetSpinner.getSelectedItem().toString(), key);
                    databaseReference.setValue(user);
                    finish();
                } else {
                    Toast.makeText(AddMiPets.this, "Missing fields!", Toast.LENGTH_LONG).show();

                }


            }
        });
    }
}
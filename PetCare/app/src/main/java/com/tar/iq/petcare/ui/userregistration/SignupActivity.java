package com.tar.iq.petcare.ui.userregistration;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tar.iq.petcare.R;
import com.tar.iq.petcare.model.User;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class SignupActivity extends AppCompatActivity {
    EditText email, password, confirmPassword, name;
    Button signupButton;
    LinearLayout selection;
    public Uri selectedImage;
    StorageReference storageReference;

    CircleImageView image;
    Bitmap bitmap = null;
    public final int PICK_IMAGE = 1;

    FirebaseFirestore db;
    FirebaseAuth auth;
    ProgressDialog pd;

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_email);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#FFE4E1"));
        }
        storageReference = FirebaseStorage.getInstance().getReference();

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        selection = findViewById(R.id.linlogo);
        image = findViewById(R.id.image);
        confirmPassword = findViewById(R.id.et_confirm_password);
        signupButton = findViewById(R.id.btn_signup);
        selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PICK_IMAGE);
            }
        });
        name = findViewById(R.id.name);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        signupButton.setOnClickListener(view -> {
            String strEmail = email.getText().toString();
            String strPassword = password.getText().toString();
            String strConfirmPassword = confirmPassword.getText().toString();
            String names = name.getText().toString();
            if (validate(strEmail, strPassword, strConfirmPassword, names)) {
                signupWithEmail(strEmail, strPassword, names);
            } else {
                Toast.makeText(this, "Enter all details properly", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validate(String strEmail, String strPassword, String strConfirmPassword, String name) {
        return !strEmail.isEmpty() && !strPassword.isEmpty() && strPassword.length() >= 6 && strPassword.equals(strConfirmPassword) && !name.isEmpty()&&selectedImage!=null;
    }

    private void signupWithEmail(String strEmail, String strPassword, String names) {
        pd.setMessage("Please Wait");
        pd.show();
        auth.createUserWithEmailAndPassword(strEmail, strPassword).addOnSuccessListener(authResult -> {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.WEBP, 100, stream);

            final ProgressDialog pdd = new ProgressDialog(this);
            pdd.setTitle("Uploading Data.......");
            pdd.show();
            final String randomkey = UUID.randomUUID().toString();
            StorageReference ref = storageReference.child("image/" + randomkey);
            ref.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Random rand = new Random();
                            String key = String.valueOf(rand.nextInt(100000000));
                            Toast.makeText(SignupActivity.this, "Success", Toast.LENGTH_LONG).show();
                            final DatabaseReference status = FirebaseDatabase.getInstance().getReference("userData").child("applicants");
                            User user = new User(strEmail, names,uri.toString());
                            status.child(strEmail.replace(".", "")).setValue(user);
                            finish();
                        }
                    });
                    pdd.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignupActivity.this, "Failed to upload " + e, Toast.LENGTH_SHORT).show();
                    Log.i("dev", "onFailure: " + e);
                    pdd.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progresspercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    pdd.setMessage("Percentage: " + (int) progresspercent + "%");
                }
            });


        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
             bitmap = (Bitmap) data.getExtras().get("data");
            selectedImage = data.getData();
             if(selectedImage!=null){
//                 image.setImageBitmap(bitmap);
//                 Uri uri = getImageUri(bitmap);
                 Glide.with(this).load(selectedImage).into(image);
                 // bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImage);
                 Toast.makeText(this, "Image has been selected", Toast.LENGTH_SHORT).show();
//                binding.image.setImageBitmap(bitmap);

             }


        } else {
            Log.i("dev", "onActivityResult:resultCode " + resultCode);
            Toast.makeText(this, "Image not selected!", Toast.LENGTH_SHORT).show();


        }
    }
    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        long now = System.currentTimeMillis();
        Date when = new Date(now);
        SimpleDateFormat nameFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
        String fileName = nameFormat.format(when);
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, fileName, null);
        return Uri.parse(path);
    }


}
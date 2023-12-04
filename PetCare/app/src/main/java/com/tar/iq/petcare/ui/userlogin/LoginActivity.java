package com.tar.iq.petcare.ui.userlogin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.tar.iq.petcare.ui.dashboard.MainActivity;
import com.tar.iq.petcare.R;
import com.tar.iq.petcare.ui.userregistration.SignupActivity;
import com.tar.iq.petcare.utils.SharedPrefUtils;


public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button loginButton;
    TextView signupText,forgetPasswordButton;
    FirebaseAuth auth;
    LinearLayout feedback;
    private SharedPrefUtils prefUtils;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#FFE4E1"));
        }
        email = findViewById(R.id.et_email);
//        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        prefUtils = new SharedPrefUtils(this);
        password = findViewById(R.id.et_password);
        feedback = findViewById(R.id.feedback);
        loginButton = findViewById(R.id.btn_login);
        signupText = findViewById(R.id.txt_signup);
        forgetPasswordButton = findViewById(R.id.forgotpass);
        loginButton.setOnClickListener(view -> {
            String strEmail = email.getText().toString();
            String strPassword = password.getText().toString();
            login(strEmail, strPassword);
        });
        signupText.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
        forgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });


    }


    void login(String strEmail, String strPassword) {
        if (strEmail.isEmpty() || strPassword.isEmpty()) {
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                prefUtils.setString("email", strEmail);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    void showRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout = new LinearLayout(this);
        final EditText emailEt = new EditText(this);

        emailEt.setText(email.getText());
        emailEt.setMinEms(14);
        emailEt.setHint("E-mail");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailEt);
        linearLayout.setPadding(30, 20, 30, 10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Recover", (dialog, which) -> {
            String email = emailEt.getText().toString().trim();
            if (!email.isEmpty()) beginRecovery(email);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void beginRecovery(String email) {
        ProgressDialog loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            loadingBar.dismiss();
            if (task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, "Recovery email sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }







}
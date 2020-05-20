package com.example.finalsemprojectbuyer.LoginActivitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalsemprojectbuyer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetEmail extends AppCompatActivity
{
    private EditText et_PasswordEmail;
    private TextView et_PasswordEmailError;
    private boolean et_PasswordEmailErrorFlag;
    private Button btn_resetPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        et_PasswordEmail = findViewById(R.id.etEmail4);
        et_PasswordEmailError = findViewById(R.id.error_email);
        btn_resetPassword = findViewById(R.id.btnResetPassword4);
        firebaseAuth = FirebaseAuth.getInstance();

        et_PasswordEmail.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (et_PasswordEmail.getText().toString().isEmpty())
                {
                    et_PasswordEmailError.setTextColor(Color.parseColor("#DD2C00"));
                    et_PasswordEmailError.setText("Pleas enter your email id");
                    et_PasswordEmailErrorFlag = false;
                }
                else
                {
                    et_PasswordEmailError.setText("looks good");
                    et_PasswordEmailError.setTextColor(Color.parseColor("#00C853"));
                    et_PasswordEmailErrorFlag = true;
                    if (!Patterns.EMAIL_ADDRESS.matcher(et_PasswordEmail.getText().toString().trim()).matches())
                    {
                        et_PasswordEmailError.setTextColor(Color.parseColor("#DD2C00"));
                        et_PasswordEmailError.setText("Invalid email id");
                        et_PasswordEmailErrorFlag = false;
                    }

                }
            }
        });

        btn_resetPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(valid())
                {
                    String userEmail = et_PasswordEmail.getText().toString().trim();
                    if (userEmail.equals(""))
                    {
                        Toast.makeText(PasswordResetEmail.this, "Please Enter your registered Email", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        firebaseAuth
                                .sendPasswordResetEmail(userEmail)

                                .addOnCompleteListener(
                                        new OnCompleteListener<Void>()
                                        {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(PasswordResetEmail.this, "Password Reset Email has been sent", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    startActivity(new Intent(PasswordResetEmail.this, LoginActivity.class));
                                                } else
                                                {
                                                    Toast.makeText(PasswordResetEmail.this, "Please verify your emails", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })

                                .addOnFailureListener(
                                        new OnFailureListener()
                                        {
                                            @Override
                                            public void onFailure(@NonNull Exception e)
                                            {
                                                Toast.makeText(PasswordResetEmail.this, e.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                    }
                }

            }
        });

    }

    private boolean valid()
    {
        if(!et_PasswordEmailErrorFlag)
        {
            return false;
        }
        return true;
    }
}

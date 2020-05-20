package com.example.finalsemprojectbuyer.LoginActivitys;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalsemprojectbuyer.Center;
import com.example.finalsemprojectbuyer.Chat.MessageActivity;
import com.example.finalsemprojectbuyer.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity
{
    private static final String
            TAG = "MainActivity";

    private static final int
            errorDailogRequestCode = 103;

    private TextView
            goToResetPassWord;

    private EditText
            Name;

    private EditText
            Password;

    private boolean
            userEmailErrorFlag,
            userPasswordErrorFlag;

    private TextView
            gotosignup;

    private Button
            Login;

    private FirebaseAuth
            firebaseAuth;

    private ProgressDialog
            progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_login);
        setUpViews();
        if (getIntent().hasExtra("user_id"))
        {
            finish();
            Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
            intent.putExtra("User ID", getIntent().getStringExtra("user_id"));
            startActivity(intent);
            return;
        }
        if (getIntent().hasExtra("product_id"))
        {
//            finish();
//            Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
//            intent.putExtra("User ID", getIntent().getStringExtra("user_id"));
//            startActivity(intent);
//            return;
            Toast.makeText(this, "Product_id "+getIntent().getStringExtra("product_id"), Toast.LENGTH_SHORT).show();
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        if (user != null)
        {
            finish();
            int availabel = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(LoginActivity.this);
            if (availabel == ConnectionResult.SUCCESS)
            {
                Log.d(TAG, "GooglePlayServicesIsWorking");
                Toast.makeText(this, "Google Play Servicess is working", Toast.LENGTH_SHORT).show();
            } else if (GoogleApiAvailability.getInstance().isUserResolvableError(availabel))
            {
                Log.d(TAG, "An Error has occurred but we an fix it");
                Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, availabel, errorDailogRequestCode);
                dialog.show();
            } else
            {
                Toast.makeText(this, "You cant make a map request", Toast.LENGTH_SHORT);

            }
//            startActivity(new Intent(this, MapHolder.class));
            startActivity(new Intent(this, Center.class));

//
        }


        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

        gotosignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(LoginActivity.this, "hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, Reg.class);
                startActivity(intent);
            }
        });

        goToResetPassWord.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(LoginActivity.this, PasswordResetEmail.class));
            }
        });

        Name.addTextChangedListener(new TextWatcher()
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
                if (Name.getText().toString().isEmpty())
                {
                    userEmailErrorFlag = false;
                } else
                {
                    userEmailErrorFlag = true;
                    if (!Patterns.EMAIL_ADDRESS.matcher(Name.getText().toString().trim()).matches())
                    {
                        userEmailErrorFlag = false;
                    }
                }
            }
        });
        Password.addTextChangedListener(new TextWatcher()
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
                if (Password.getText().toString().isEmpty())
                {
                    userPasswordErrorFlag = false;
                    return;
                }

                Pattern userNamePattern = Pattern.compile("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$");
                if (!userNamePattern.matcher(Password.getText().toString().trim()).matches())
                {
                    userPasswordErrorFlag = false;
                } else
                {
                    userPasswordErrorFlag = true;
                }
            }
        });

    }

    private void setUpViews()
    {
        Name = findViewById(R.id.etemailID);
        Password = findViewById(R.id.etPassword);
        Login = findViewById(R.id.btnLogin);
        gotosignup = findViewById(R.id.gotosignup1);
        firebaseAuth = FirebaseAuth.getInstance();
        goToResetPassWord = findViewById(R.id.tvForgotpassword1);
    }

    private void validate(String un, String up)
    {
        if (userEmailErrorFlag && userPasswordErrorFlag)
        {
            progressDialog.setMessage("Happy to see you");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(un, up).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        //Toast.makeText(MainActivity.this, " Login Successful ", Toast.LENGTH_SHORT).show();
                        checkEmailVerification();
                        finish();
                    } else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, " Login Failed ", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        } else
        {
            if (!userPasswordErrorFlag)
            {
                if (Name.getText().toString().trim().isEmpty())
                {
                } else
                {
                }
            }
            if (Password.getText().toString().trim().isEmpty())
            {

            } else
            {

            }
        }

    }

    private void checkEmailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        boolean flag = firebaseUser.isEmailVerified();
        if (flag)
        {
            finish();
            startActivity(new Intent(this, Center.class));
        } else
        {
            startActivity(new Intent(this, Center.class));
        }

    }
}
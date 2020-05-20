package com.example.finalsemprojectbuyer.LoginActivitys;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalsemprojectbuyer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class UpdatePassWord extends AppCompatActivity
{//var declaration
    EditText etNewPassword;
    TextView etnewpasswordError;
    Button changepassword;
    FirebaseUser firebaseUser;
    boolean userPasswordErrorFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass_word);
        //set up views
        etNewPassword = findViewById(R.id.etNewPassword7);
        etnewpasswordError = findViewById(R.id.newPasswordError);
        changepassword = findViewById(R.id.btnChangePassword7);
        //click
        changepassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (userPasswordErrorFlag)
                {
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    firebaseUser.updatePassword(etNewPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(UpdatePassWord.this, "Password Changed successfully", Toast.LENGTH_SHORT).show();
                            } else
                            {
                                etnewpasswordError.setTextColor(Color.parseColor("#DD2C00"));
                                etnewpasswordError.setText("Pleas enter your password");
                                userPasswordErrorFlag = false;
                            }
                        }
                    });
                } else
                {

                }
            }
        });
        etNewPassword.addTextChangedListener(new TextWatcher()
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
                if (etNewPassword.getText().toString().isEmpty())
                {
                    etnewpasswordError.setTextColor(Color.parseColor("#DD2C00"));
                    etnewpasswordError.setText("Pleas enter your password");
                    userPasswordErrorFlag = false;
                    return;
                }

                Pattern userNamePattern = Pattern.compile("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$");
                if (!userNamePattern.matcher(etNewPassword.getText().toString().trim()).matches())
                {
                    userPasswordErrorFlag = false;
                    etnewpasswordError.setTextColor(Color.parseColor("#DD2C00"));
                    etnewpasswordError.setText("Pass word must contain \nUpper case letter(A-Z),\nLower case letter(a-z), \nSpecial character(@#$%^&+=), \nAnd digit(0-9)");
                } else
                {
                    etnewpasswordError.setTextColor(Color.parseColor("#00C853"));
                    userPasswordErrorFlag = true;
                    etnewpasswordError.setText("looks good");
                }
            }
        });
    }

    //supporting methods
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }
}

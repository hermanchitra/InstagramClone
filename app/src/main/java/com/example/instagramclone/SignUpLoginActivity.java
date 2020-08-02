package com.example.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity {
    private EditText edtUsernameSignUp, edtPasswordSignUp, edtUsernameLogin, edtPasswordLogin;
    private Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        edtUsernameSignUp = findViewById(R.id.edtUserNameSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        edtUsernameLogin = findViewById(R.id.edtUsernameLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser user = new ParseUser();
                user.setUsername(edtUsernameSignUp.getText().toString());
                user.setPassword(edtPasswordSignUp.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(SignUpLoginActivity.this, "Signing up user successful!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        } else {
                            FancyToast.makeText(SignUpLoginActivity.this, "Signing up fail. " + e.getLocalizedMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logInInBackground(edtUsernameLogin.getText().toString(), edtPasswordLogin.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null && user != null) {
                            FancyToast.makeText(SignUpLoginActivity.this, "Hi " + user.getUsername() + " logging in successful!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        } else {
                            FancyToast.makeText(SignUpLoginActivity.this, "Logging in fail. " + e.getLocalizedMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                    }   
                });
            }
        });
    }
}

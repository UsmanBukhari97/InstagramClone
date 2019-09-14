package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginEmail, edtLoginPassword;
    private Button btnLoginActivity, btnSignupActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtLoginEmail = findViewById(R.id.edtEmailLoginActivity);
        edtLoginPassword = findViewById(R.id.edtPasswordLoginActivity);

        edtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            // i == keyCode
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                //making sure user pressed the enter key
                if (keyCode == keyEvent.KEYCODE_ENTER &&
                        keyEvent.getAction() == keyEvent.ACTION_DOWN){

                    //we dont pass view we pass btnLoginActivity coz there is a 'is a'relationship between this and button.
                    onClick(btnLoginActivity);

                }
                return false;
            }
        });

        btnSignupActivity = findViewById(R.id.btnSignupActivity);
        btnLoginActivity = findViewById(R.id.btnLoginActivity);

        btnLoginActivity.setOnClickListener(this);
        btnSignupActivity.setOnClickListener(this);

        //again adding this to igore token problems
        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View view) {

        //switching between buttons
        switch (view.getId()){
            case R.id.btnLoginActivity:

                //if email is empty or username or pasword is empty:
                if (edtLoginEmail.getText().toString().equals("") ||
                        edtLoginPassword.getText().toString().equals("")) {
                    FancyToast.makeText(LoginActivity.this,
                            "All fields are required!", Toast.LENGTH_LONG,
                            FancyToast.INFO, false).show();


                } else {


                    //we pass 3 arguments: email, password and login calback
                    ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPassword.getText().toString(),
                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {

                                    if (user != null && e == null) {
                                        FancyToast.makeText(LoginActivity.this,
                                                user.getUsername() + " is Logged in successfully",
                                                Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();


                                        //going to social media activity after logging in:
                                        transitionToSocialMediaActivity();
                                    }

                                }
                            });

                }
            break;

            case R.id.btnSignupActivity:
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
                break;
        }
    }
    public void LoginLayoutIsTapped(View view){

        //whatever goes wrong while touching the screen like the app crashes then we can use try and catch coz whatever goes wrong
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e){
            //will output some values to log
            e.printStackTrace();
        }
    }
    //after logging in going to social media activity
    private void transitionToSocialMediaActivity(){

        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}

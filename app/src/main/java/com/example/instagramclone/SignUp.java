package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEnterEmail, edtEnterUsername, edtEnterPassword;
    private Button btnLogin, btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //activity title
        setTitle("Sign Up");

        edtEnterEmail = findViewById(R.id.edtEnterEmail);
        edtEnterUsername = findViewById(R.id.edtEnterUsername);
        edtEnterPassword = findViewById(R.id.edtEnterPassword);

        //when entering every field when user presses the tick button on keyboard after entering password
        //then user will sign up and wouldnt have to tap sign up button,
        edtEnterPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            // i == keyCode
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                //making sure user pressed the enter key
                if (keyCode == keyEvent.KEYCODE_ENTER &&
                        keyEvent.getAction() == keyEvent.ACTION_DOWN){

                    //we dont pass view we pass btnSignUp coz there is a 'is a'relationship between this and button.
                    onClick(btnSignUp);

                }
                return false;
            }
        });

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        //calling on click listener on buttons
        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);



        //signing up creates token session so we use this to log out this user.
        // itmight cause problems coz of token session
        if (ParseUser.getCurrentUser() != null) {
           // ParseUser.getCurrentUser().logOut();
            //token session when user is signed up or logged in and when we didnt have any activity to transition too.
            //now we have social media activity so we wont logout the current user we will transition now:
            transitionToSocialMediaActivity();
        }



    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnSignUp:

                //when signing up its doing that with just username and password and notshowing enter email error
                //if email is empty or username or pasword is empty:
                if (edtEnterEmail.getText().toString().equals("") ||
                        edtEnterUsername.getText().toString().equals("") ||
                        edtEnterPassword.getText().toString().equals("")) {

                    FancyToast.makeText(SignUp.this,
                            "All fields are required!", Toast.LENGTH_LONG,
                            FancyToast.INFO, false).show();



                } else {


                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtEnterEmail.getText().toString());
                    appUser.setUsername(edtEnterUsername.getText().toString());
                    appUser.setPassword(edtEnterPassword.getText().toString());

                    //while signing up user should wait so we will create a progress dialog
                    //progress dialog dismissed below after signing up button
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + edtEnterUsername.getText().toString());
                    progressDialog.show();


                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this,
                                        appUser.getUsername() + " is signed up", Toast.LENGTH_SHORT,
                                        FancyToast.SUCCESS, false).show();

                                //calling this social media activity to go there
                                transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(SignUp.this,
                                        "There was an error: " + e.getMessage(), Toast.LENGTH_LONG,
                                        FancyToast.ERROR, false).show();
                            }
                            //when sign up progress is done we dismiss the progress dialog
                            progressDialog.dismiss();
                        }
                    });
                } //else bracket ending here. all parse code in else statement when field not empty

                break;
            case R.id.btnLogin:

                //switching to Login Activity
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);

                break;
        }
    }
    //when user taps on empty space the keyboard goes away
    public void rootLayoutTapped(View view){

        //whatever goes wrong while touching the screen like the app crashes then we can use try and catch coz whatever goes wrong
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e){
            //will output some values to log
            e.printStackTrace();
        }
    }
    //after signing up going to social media activity
    private void transitionToSocialMediaActivity(){

        Intent intent = new Intent(SignUp.this, SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }

}

package com.ogre.slide.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ogre.slide.DatabaseHandler;
import com.ogre.slide.R;
import com.ogre.slide.models.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class  LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private static String name;
    private static String phone;
    private static String email;
    private static String password;
    private static int userid;

    public static final String PREFS_NAME = "MyPrefsFile";



    DatabaseHandler db;
    List<User> users;


    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);

                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("hasLoggedIn", true);
        editor.commit();




        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        db=new DatabaseHandler(LoginActivity.this);
                        users=db.getAllUsers();
                        for(User use : users)
                        {
                            String log="ID: "+use.getUserid()+"Name "+use.getName()+"Email "+use.getEmail()+"Password"+use.getPassword();
                            Log.d("User ",log);
                            if(use.getEmail().equals(email)&&use.getPassword().equals(password))
                            {
                                userid=use.getUserid();
                                onLoginSuccess();
                                break;
                            }
                            else
                            {
                                onLoginFailed();
                            }
                        }

                        progressDialog.dismiss();
                    }
                }, 2000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Log.d(TAG, "Login Successfull");
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setVisibility(View.VISIBLE);
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}

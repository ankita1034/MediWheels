package com.ogre.slide.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ogre.slide.DatabaseHandler;
import com.ogre.slide.GMailSender;
import com.ogre.slide.R;
import com.ogre.slide.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private static String name=null;
    private static String email=null;
    private static String phone=null;
    private static String password=null;
    private static String mailSender="akashsesampark@gmail.com";

    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_phone) EditText _phoneText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }


        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        name=_nameText.getText().toString();
        email=_emailText.getText().toString();
        phone=_phoneText.getText().toString();
        password=_passwordText.getText().toString();

        DatabaseHandler db=new DatabaseHandler(SignUpActivity.this);

        Log.i(TAG,"Adding User "+name);
        db.addUser(new User(name, email, phone, password));
        new LongOperation().execute("hey");
        Log.i(TAG,"User Added");

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                        startActivity(intent);
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 1500);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

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

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                Log.i(TAG,"Inside try");
                GMailSender sender = new GMailSender("akashsesampark@gmail.com", "andro_zest@1034");
                sender.sendMail("Registration Completed",
                        "Thanx for registering on Medi Wheels",
                        mailSender,
                        email);
            } catch (Exception e) {
                Log.i("SendMail", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
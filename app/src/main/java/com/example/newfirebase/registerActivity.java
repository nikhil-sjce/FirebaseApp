/*package com.example.newfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registerActivity extends AppCompatActivity {
    EditText email, password;
    Button registerButton;
    ProgressDialog progressdialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);

        mAuth = FirebaseAuth.getInstance();

        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Registering User ......");
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                String passwd = password.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    email.setError("Invalid E-MAIL");
                    email.setFocusable(true);
                } else if (passwd.length() < 6) {
                    password.setError("Password must be at least 6 characters long .");
                    password.setFocusable(true);
                } else {
                    registerUser(mail, passwd);
                }
            }
        });
    }

    private void registerUser(String mail, String passwd) {
        progressdialog.show();

        mAuth.createUserWithEmailAndPassword(mail, passwd)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        progressdialog.dismiss();
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(registerActivity.this, "Registered ... ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(registerActivity.this , profileActivity.class));
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        progressdialog.dismiss();
                        Toast.makeText(registerActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }) .addOnFailureListener(e -> Toast.makeText(registerActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show());


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
 */
package com.example.newfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registerActivity extends AppCompatActivity {
    EditText email,password;
    Button registerButton;
    TextView haveAccount;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Create Account");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        registerButton=findViewById(R.id.registerButton);
        haveAccount=findViewById(R.id.haveAccount);
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Registering User!!");
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString().trim();
                String passwd=password.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.setError("Invalid Email");
                    email.setFocusable(true);
                }
                else if(passwd.length()<6){
                    password.setError("password length should contain at least 6 characters");
                    password.setFocusable(true);
                }
                else{
                    registerUser(mail,passwd);
                }
            }


        });
     /*   rlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

      */
        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerActivity.this , dashboardActivity.class));
                finish();
            }
        });
    }

    private void registerUser(String mail, String passwd) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(mail, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            String hashMail = user.getEmail();
                            String uid = user.getUid();
                            HashMap<Object,String> hashMap = new HashMap<>();
                            hashMap.put("email",hashMail);
                            hashMap.put("uid",uid);
                            hashMap.put("name","");
                            hashMap.put("phone","");
                            hashMap.put("image","");
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("users");
                            reference.child(uid).setValue(hashMap);

                            Toast.makeText(registerActivity.this, "Registered   "+user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(registerActivity.this , dashboardActivity.class));
                            finish();
                        } else{
                            progressDialog.dismiss();
                            Toast.makeText(registerActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(registerActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
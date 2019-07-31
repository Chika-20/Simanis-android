package com.simanisandroid.simanis;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.simanisandroid.simanis.Model.PerawatModel;

public class RegistrasiActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    FirebaseAuth mAuth;
    Button btnRegis;
    EditText newEmail, userName, passWord;
    DatabaseReference perawatRef;
    String uid, email, username, password;
    LoginPreference loginPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//prevent auto focus keyboard

        //get reference database
        perawatRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        //deklarasi
        newEmail = findViewById(R.id.edt_newemail);
        userName = findViewById(R.id.edt_newusername);
        passWord = findViewById(R.id.edt_newpass);
        btnRegis = findViewById(R.id.btn_create);

        //action tombol create
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = newEmail.getText().toString();
                username = userName.getText().toString();
                password = passWord.getText().toString();
                closeKeyboard();
                if (!validateForm()) {
                    return;
                }
                registerUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginPref = new LoginPreference(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    private void registerUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uid = task.getResult().getUser().getUid();
                            writeNewPerawat(uid, email, username, password);
//                            startActivity(new Intent(RegistrasiActivity.this, LoginActivity.class));
//                            finish();
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException e) {
                                newEmail.requestFocus();
                                newEmail.setError("Email already used");
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                newEmail.requestFocus();
                                newEmail.setError("Bad Email format");
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                            }

                            Toast.makeText(RegistrasiActivity.this, "Gagal"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void writeNewPerawat(String uid, String email, String username, String password) {
        PerawatModel perawatModel = new PerawatModel(email, username, password);
        perawatRef.child("Perawat").child(uid).setValue(perawatModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //set loginPref to true
                        loginPref.setLoginStatus(true);
                        //intent halaman login
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistrasiActivity.this, "Gagal"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //validasi email dan password
    private boolean validateForm() {
        boolean result = true;
        String email = newEmail.getText().toString();
        String password = passWord.getText().toString();
        if (email.isEmpty()) {
            newEmail.setError("Please enter e-mail");
            result = false;
        }
        if (password.isEmpty()) {
            passWord.setError("Please enter password");
            result = false;
        }
        if (password.length() < 8 && password.length() > 0) {
            passWord.setError("Password must have at least 8 Character");
            result = false;
        }
        return result;
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager i = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            i.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}

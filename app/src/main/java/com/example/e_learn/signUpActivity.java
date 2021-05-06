package com.example.e_learn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.FirestoreGrpc;

import java.util.HashMap;
import java.util.Map;

public class signUpActivity extends AppCompatActivity {

    Button adminReg;
    EditText adName,adEmail,adPhone,adPass;
    TextView login;
    boolean valid = true;
    FirebaseAuth auth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        adminReg = findViewById(R.id.register);
        adName = findViewById(R.id.name);
        adEmail = findViewById(R.id.email);
        adPass = findViewById(R.id.pass);
        adPhone = findViewById(R.id.phone);
        login = findViewById(R.id.login);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        adminReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(adName);
                checkField(adEmail);
                checkField(adPass);
                checkField(adPhone);

                if(valid){
                    //start the admin reg process
                    String email = adEmail.getText().toString();
                    String pass = adPass.getText().toString();
                    auth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            //to access uid
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(signUpActivity.this, "Account Created",Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("Admins").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();

                            //passing data to firestore
                            userInfo.put("adName",adName.getText().toString());
                            userInfo.put("adEmail",adEmail.getText().toString());
                            userInfo.put("adPhone", adPhone.getText().toString());

                            //specify if the user is admin
                            userInfo.put("isUser","1");

                            df.set(userInfo);

                            startActivity(new Intent(getApplicationContext(),adminActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(signUpActivity.this,"Failed to create an account",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }
        else{
            valid = true;
        }
        return valid;
    }
}
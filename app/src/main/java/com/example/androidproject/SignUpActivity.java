package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    FirebaseFirestore db;

    private MaterialButton Signup_BTN;

    private TextInputEditText first_name_EDT;
    private TextInputEditText last_name_EDT;
    private TextInputEditText email_EDT;
    private TextInputEditText password_EDT;
    private TextInputEditText confirm_password_EDT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();

        Signup_BTN = findViewById(R.id.Signup_BTN);
        Signup_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = first_name_EDT.getText().toString();
                String lastName = last_name_EDT.getText().toString();
                String email = email_EDT.getText().toString();
                String password = password_EDT.getText().toString();
                String confirmPassword = confirm_password_EDT.getText().toString();

                db.collection("User")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot document : task.getResult()){
                                        String emailDB = document.get("Email").toString();
                                        if(emailDB.equals(email) || email_EDT.getText().toString().isEmpty()) {
                                            Toast.makeText(SignUpActivity.this, "User already exist", Toast.LENGTH_SHORT).show();
                                            email_EDT.setText("");
                                            password_EDT.setText("");
                                            confirm_password_EDT.setText("");
                                        }else{
                                            addUserToDataBase(firstName, lastName, email, password);
                                            return;
                                        }
                                    }
                                }
                            }
                        });
            }
        });
    }

    private void addUserToDataBase(String firstName, String lastName, String email, String password) {
        Map<String, Object> user = new HashMap<>();
        user.put("First name", firstName);
        user.put("Last name", lastName);
        user.put("Email", email);
        user.put("Password", password);

        db.collection("User")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(@NonNull DocumentReference documentReference) {
                        Toast.makeText(SignUpActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        moveToLoginPage();
    }

    private void findViews() {
        db = FirebaseFirestore.getInstance();
        first_name_EDT = findViewById(R.id.first_name_EDT);
        last_name_EDT = findViewById(R.id.last_name_EDT);
        email_EDT = findViewById(R.id.email_EDT);
        password_EDT = findViewById(R.id.password_EDT);
        password_EDT.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password_EDT.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirm_password_EDT = findViewById(R.id.confirm_password_EDT);
        confirm_password_EDT.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confirm_password_EDT.setTransformationMethod(PasswordTransformationMethod.getInstance());

    }

    private void moveToLoginPage(){
        Intent intent= new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
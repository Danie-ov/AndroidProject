package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.example.androidproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    FirebaseFirestore db;

    private MaterialButton Login_BTN;
    private MaterialTextView Sign_up_TXF;

    private TextInputEditText email_EDT;
    private TextInputEditText password_EDT;

    private static String firstName;
    private static String lastName;
    private static String email;
    private static String password;

    public static String getFirstName() {
        return firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getEmail() {
        return email;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        initButtons();
        Login_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailDB = email_EDT.getText().toString();
                String passwordDB = password_EDT.getText().toString();

                db.collection("User")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot document : task.getResult()){
                                        email = document.get("Email").toString();
                                        firstName = document.get("First name").toString();
                                        lastName = document.get("Last name").toString();
                                        password = document.get("Password").toString();
                                        if(emailDB.equals(email) && passwordDB.equals(password)) {
                                            Toast.makeText(LoginActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                            moveToMenuPage();
                                            return;
                                        }
                                    }
                                    Toast.makeText(LoginActivity.this, "Wrong details, or not registered yet..", Toast.LENGTH_SHORT).show();
                                    password_EDT.setText("");
                                }else{
                                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void initButtons() {
        Sign_up_TXF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSignupPage();
            }
        });
    }

    private void findViews() {
        db = FirebaseFirestore.getInstance();
        Login_BTN = findViewById(R.id.Login_BTN);
        Sign_up_TXF = findViewById(R.id.Sign_up_TXF);
        email_EDT = findViewById(R.id.email_EDT);
        password_EDT = findViewById(R.id.password_EDT);
        password_EDT.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password_EDT.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    private void moveToSignupPage(){
        Intent intent= new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    private void moveToMenuPage() {
        Intent intent= new Intent(this, NavDrawerMenu.class);
        startActivity(intent);
        finish();
    }

}
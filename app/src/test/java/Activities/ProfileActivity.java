package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.androidproject.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {

    DrawerLayout profileDrawerLayout;

    private MaterialButton Logout_BTN;

    private MaterialTextView first_name_TXV;
    private MaterialTextView last_name_TXV;
    private MaterialTextView email_TXV;

    private ImageView image_user_IMV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViews();
        Logout_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToLoginPage();
            }
        });
        image_user_IMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean pick = true;
                if(pick == true){
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }else pickImage();

                }else{
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }else pickImage();
                }
            }
        });
    }

    private void pickImage() {
        CropImage.activity()
                .start(this);
    }

    private void requestStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

    }

    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private boolean checkStoragePermission() {
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return res2;
    }

    private boolean checkCameraPermission() {
        boolean res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return res1 && res2;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try{
                    InputStream stream = getContentResolver().openInputStream(resultUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    image_user_IMV.setImageBitmap(bitmap);
                }catch(Exception e){
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void moveToLoginPage() {
        Intent intent= new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {

        profileDrawerLayout = findViewById(R.id.nav_drawer_layout_menu);

        Logout_BTN = findViewById(R.id.Logout_BTN);
        first_name_TXV = findViewById(R.id.first_name_TXV);
        last_name_TXV = findViewById(R.id.last_name_TXV);
        email_TXV = findViewById(R.id.email_TXV);
        image_user_IMV = findViewById(R.id.image_user_IMV);

        first_name_TXV.setText(LoginActivity.getFirstName());
        last_name_TXV.setText(LoginActivity.getLastName());
        email_TXV.setText(LoginActivity.getEmail());
    }

    public void ClickMenu(View view){

        NavDrawerMenu.openDrawer(profileDrawerLayout);
    }

    public void ClickLogo(View view){

        NavDrawerMenu.closeDrawer(profileDrawerLayout);
    }

    public void ClickWorkouts(View view){

        NavDrawerMenu.redirectActivity(this, NavDrawerMenu.class);
    }

    public void ClickHistory(View view){

        NavDrawerMenu.redirectActivity(this, HistoryActivity.class);
    }

    public void ClickCharts(View view){

        NavDrawerMenu.redirectActivity(this, ChartsActivity.class);
    }

    public void ClickProfile(View view){

        recreate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        NavDrawerMenu.closeDrawer(profileDrawerLayout);
    }
}
package com.example.mddeloarhossain.firebaseauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 7117; //Any Number
    List<AuthUI.IdpConfig> providers;
    Button btn_sign_out;

    private TextInputLayout textInputEmail, textInputConfirmPassword, textInputPassword;
    private ViewFlipper viewFlipper;
    String emailInput, passwordInput, confirmPasswordInput;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        viewFlipper = findViewById(R.id.view_flipper);


        btn_sign_out = findViewById(R.id.btn_sign_out);
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                btn_sign_out.setEnabled(false);
                                showSignInOptions();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        //Init providers
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(), //Email Builder
                new AuthUI.IdpConfig.PhoneBuilder().build(), //Phone Builder
                new AuthUI.IdpConfig.FacebookBuilder().build(), //Facebook Builder
                new AuthUI.IdpConfig.GoogleBuilder().build() //Google Builder

        );
        showSignInOptions();
    }
    private void showSignInOptions(){

        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .build(),MY_REQUEST_CODE
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){
                //Get User
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //show user email on toast
                Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();

                btn_sign_out.setEnabled(true);
            }
            else {
                Toast.makeText(this, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void previousView(View v) {
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
        viewFlipper.showPrevious();
    }

    public void nextView(View v) {
        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
       /* if(!validateEmail() | !validatePassword() | !validateConfirmPassword()){
            return;
        }*/

        /*String input = emailInput+"\n"+passwordInput+"\n"+confirmPasswordInput;
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();*/
        viewFlipper.showNext();
    }

    public void saveInfo(View view){
        switch (view.getId()){
            case R.id.saveInfoId:
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                break;
        }
    }







}
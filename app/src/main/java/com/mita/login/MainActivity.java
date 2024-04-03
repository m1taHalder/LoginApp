package com.mita.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mita.login.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private GoogleSignInClient signInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //  setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        String webClientToken = "39321802288-p8buflg73a6fri38k2q6mab2lkcadedo.apps.googleusercontent.com";
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientToken)
                .requestEmail()
                .requestProfile()
                .build();
        signInClient = GoogleSignIn.getClient(this, gso);

        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientToken)
                .build();

        signInClient= GoogleSignIn.getClient(this, gso);*/


        binding.googleBtn.setOnClickListener(view -> {
           /* GoogleSignInClient client = GoogleSignIn.getClient(this, gso);
            startActivityForResult(client.getSignInIntent(), 100);*/

            Intent signInIntent = signInClient.getSignInIntent();
            startActivityForResult(signInIntent, 100);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Activity.RESULT_OK != resultCode) {
            Toast.makeText(this, "Code: " + resultCode, Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case 100: {
                try {
                    try {
                        SignInCredential credential = Identity.getSignInClient(this).getSignInCredentialFromIntent(data);
                        String idToken = credential.getGoogleIdToken();
                        if (idToken!=null) {
                            String email = credential.getId();
                            Toast.makeText(this, "Email: " + email, Toast.LENGTH_LONG).show();
                        }
                    } catch (ApiException e) {
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                        throw new RuntimeException(e);
                    }

                } catch (RuntimeException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
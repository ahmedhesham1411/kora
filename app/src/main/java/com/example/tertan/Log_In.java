package com.example.tertan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tertan.Utils.Constant;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Log_In extends AppCompatActivity {


    AppCompatEditText email,pass;
    AppCompatTextView txt_create_account;
    Button btn_login;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    //facebook
    LoginButton loginButton;
    CallbackManager callbackManager;
    String first_name, last_name, id, image_url;
    AppCompatImageView facebook_image,google_image;
    Boolean faceClicked, twitterClicked = false;
    //google
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
    Context context;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            Intent intent = new Intent(Log_In.this,Home.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        init();
        auth = FirebaseAuth.getInstance();
    }

    void init() {
        loginButton = findViewById(R.id.faceBtn);
        email = findViewById(R.id.txt_email1);
        pass = findViewById(R.id.txt_password1);
        btn_login = findViewById(R.id.btn_login);
        google_image = findViewById(R.id.google_image);
        txt_create_account = findViewById(R.id.txt_create_account);
        txt_create_account.setPaintFlags(txt_create_account.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        txt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Register.class);
                startActivity(intent);
            }
        });
        google_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable())
                {signIn();}
                else
                {
                    Constant.ShowAlert("Check Internet Connection",context);
                    //Toast.makeText(Login.this, "net", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_mail = email.getText().toString();
                String txt_password = pass.getText().toString();
                if (TextUtils.isEmpty(txt_mail) || TextUtils.isEmpty(txt_password)) {
                    Constant.ShowAlert("All Fields are Required",context);
                    //Toast.makeText(Login.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else if (!isNetworkAvailable()){
                    Constant.ShowAlert("Check Internet Connection",context);
                }
                else {
                    auth.signInWithEmailAndPassword(txt_mail, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(Log_In.this, Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Constant.ShowAlert("Incorrect Email or password",context);
                            }
                        }
                    });
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        facebook_image = findViewById(R.id.facebook_image);
        facebook_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable())
                {
                    LoginManager.getInstance().logOut();
                    loginButton.performClick();
                    faceClicked = true;
                    twitterClicked = false;
                }
                else
                {
                    Constant.ShowAlert("Check Internet",context);
                    //Toast.makeText(Login.this, "net", Toast.LENGTH_SHORT).show();
                }


            }
        });

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else if (faceClicked == true){
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            //startActivity(new Intent(MainActivity.this, Main2Activity.class));

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            // Build a GoogleSignInClient with the options specified by gso.
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Log_In.this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                Intent intent = new Intent(Log_In.this,Social_register.class);
                intent.putExtra("name",personGivenName + " " + personFamilyName);
                intent.putExtra("email",personEmail);
                startActivity(intent);
                mGoogleSignInClient.signOut();

            }

        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }

    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken==null){

            }
            else {
                getUserProfile(currentAccessToken);
            }
        }
    };

    private void getUserProfile(AccessToken currentAccessToken){

        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    first_name = object.getString("first_name");
                    last_name = object.getString("last_name");
                    String emaila = object.getString("email");
                    id = object.getString("id");
                    image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                    //Toast.makeText(Login.this, emaila, Toast.LENGTH_SHORT).show();
                    AccessToken.getCurrentAccessToken().getPermissions();
                    AccessToken.getCurrentAccessToken().getDeclinedPermissions();

                    Intent intent = new Intent(Log_In.this,Social_register.class);
                    intent.putExtra("name",first_name + " " + last_name);
                    intent.putExtra("email",emaila);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

package com.example.bcake.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.Debug;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.Model.User;
import com.example.bcake.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private Button btnLogin, btnRegister;
    private SignInButton btnLoginGoogle;
    private LoginButton btnLoginFacebook;
    private EditText edtUserName, edtPassWord;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private TextView txtChangePassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        UtilityClass.HideProgressDialog();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLogin();
            }
        });
        intentRegisterActivity();
/*
        loginFaceBook();
*/
        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                gsc = GoogleSignIn.getClient(Login.this,gso);
                loginGoogle();
            }
        });
        txtChangePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentChangePassWord();
            }
        });
    }

    private void intentChangePassWord() {
        Intent intent = new Intent(this,ChangePassword.class);
        startActivity(intent);
    }

    private void loginGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                task.getResult(ApiException.class);
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void loginFaceBook() {
        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null && !accessToken.isExpired()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        btnLoginFacebook.setPermissions("email", "public_profile");
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                                Log.d("DataReturn", jsonObject.optString("email") +"");
                            }
                        });
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(@NonNull FacebookException e) {

            }
        });
    }


    private void UserLogin(){
        if(UtilityClass.checkI(Login.this)){
            checkLogin();
        }
        else{
            Toast.makeText(Login.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkLogin() {
        String userName = edtUserName.getText().toString().trim();
        String passWord = edtPassWord.getText().toString().trim();
        if (userName.equals("") || passWord.equals("")) {
            Toast.makeText(this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        } else if (userName.length() > 50) {
                Toast.makeText(this, "Tài khoản phải ít hơn 50 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (userName.length() < 10) {
            Toast.makeText(this, "Tài khoản phải nhiều hơn 10 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (passWord.length() > 15) {
                Toast.makeText(this, "Mật khẩu phải ít hơn 15 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (passWord.length() < 8) {
            Toast.makeText(this, "Mật khẩu phải nhiều hơn 8 ký tự!", Toast.LENGTH_SHORT).show();
        } else {
            UtilityClass.showProgressDialog(this);
            ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
            Call<User> call = apiservice.Login(userName,passWord);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.body() != null){
                        UtilityClass.saveData(Login.this,"Id_User",String.valueOf(response.body().getId()));
                        UtilityClass.saveData(Login.this,"Address",response.body().getAddress());
                        UtilityClass.saveData(Login.this,"Name",response.body().getName());
                        UtilityClass.saveData(Login.this,"Avatar",response.body().getAvatar());
                        UtilityClass.saveData(Login.this,"yearOfBirth",response.body().getYearOfBirth()+"");
                        UtilityClass.saveData(Login.this,"phoneNumber",response.body().getPhoneNumber());
                        Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        intentMainActivity();
                        UtilityClass.HideProgressDialog();
                    }
                    else{
                        Toast.makeText(Login.this, "Hãy kiểm tra lại tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                        UtilityClass.HideProgressDialog();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable throwable) {
                    Toast.makeText(Login.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }
            });
        }
    }

    private void intentRegisterActivity() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void init(){
        btnLogin = findViewById(R.id.btnlogin);
        btnRegister = findViewById(R.id.btnregister);
        btnLoginFacebook = findViewById(R.id.login_button);
        edtUserName = findViewById(R.id.edt_username);
        edtPassWord = findViewById(R.id.edt_password);
        btnLoginGoogle = findViewById(R.id.google_button);
        txtChangePassWord = findViewById(R.id.txtchangepassword);
    }
    private void intentMainActivity() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }
}
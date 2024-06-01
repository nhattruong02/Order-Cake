package com.example.bcake.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    private Button btnChangePassWord, btnExit;
    private EditText txtUserName,txtPassWord,txtNewPassWord,txtConfirmPassWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentLogin();
            }
        });
        btnChangePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilityClass.checkI(ChangePassword.this)) {
                    UtilityClass.showProgressDialog(ChangePassword.this);
                    ChangePasswordUser();
                }else {
                    Toast.makeText(ChangePassword.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void ChangePasswordUser(){
        String strUserName = txtUserName.getText().toString().trim();
        String strPassWord = txtPassWord.getText().toString().trim();
        String strNewPassWord = txtNewPassWord.getText().toString().trim();
        String strConfirmPassword = txtConfirmPassWord.getText().toString().trim();
        if (strUserName.equals("") || strPassWord.equals("") || strNewPassWord.equals("") || strConfirmPassword.equals("")) {
            Toast.makeText(this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        } else if (strUserName.length() > 50) {
            Toast.makeText(this, "Tài khoản phải ít hơn 50 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (strUserName.length() < 12) {
            Toast.makeText(this, "Tài khoản nhiều hơn 12 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (strPassWord.length() > 15) {
            Toast.makeText(this, "Mật khẩu phải ít hơn 15 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (strPassWord.length() < 8) {
            Toast.makeText(this, "Mật khẩu phải nhiều hơn 8 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (strNewPassWord.length() > 15) {
            Toast.makeText(this, "Mật khẩu phải mới ít hơn 15 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (strNewPassWord.length() < 8) {
            Toast.makeText(this, "Mật khẩu phải mới nhiều hơn 8 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (!strConfirmPassword.equals(strNewPassWord)) {
            Toast.makeText(this, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
        } else {
            ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
            Call<Boolean> call = apiservice.ChangePassWord(strUserName,strNewPassWord);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.body() == true) {
                        UtilityClass.HideProgressDialog();
                        Toast.makeText(ChangePassword.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        intentLogin();
                    }else{
                        Toast.makeText(ChangePassword.this, "Người dùng không tồn tại hãy kiểm tra lại tài khoản và mật khẩu!", Toast.LENGTH_SHORT).show();
                        UtilityClass.HideProgressDialog();

                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                }
            });
        }
    }
    private void intentLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    private void init() {
        btnChangePassWord = findViewById(R.id.btn_change_password);
        btnExit = findViewById(R.id.btn_exit_change_password);
        txtUserName = findViewById(R.id.edt_change_password_username);
        txtPassWord = findViewById(R.id.edt_change_password_old_password);
        txtNewPassWord = findViewById(R.id.edt_change_password_new_password);
        txtConfirmPassWord = findViewById(R.id.edt_change_password_confirm_password);
    }
}
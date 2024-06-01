package com.example.bcake.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private Button btnRegister, btnExit;
    private EditText txtUserName,txtPassWord,txtName,txtYearOfBirth,txtPhoneNumber,txtAddress;
    private Spinner spinnerGender;
    private DatePickerDialog.OnDateSetListener dateOnClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.arr_gender));//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilityClass.checkI(Register.this)) {
                    RegisterAccount();
                }else {
                    Toast.makeText(Register.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentLogin();
            }
        });
        txtYearOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int nam = cal.get(Calendar.YEAR);
                int thang = cal.get(Calendar.MONTH);
                int ngay = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        Register.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateOnClickListener,
                        nam, thang, ngay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateOnClickListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = day + "/" + month + "/" + year;
                txtYearOfBirth.setText(date);
            }
        };
    }
    private boolean checkSpiner(String gender){
        if(gender.equals("Nam")){
            return true;
        }
        else{
            return false;
        }
    }
    private void RegisterAccount(){
        String strName = txtName.getText().toString().trim();
        String strGender = spinnerGender.getSelectedItem().toString().trim();
        String strAddress = txtAddress.getText().toString().trim();
        SimpleDateFormat format = new SimpleDateFormat("dd/m/yyyy");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd");
        String stryearOfBirth = "";
        try {
            stryearOfBirth = format1.format(format.parse(txtYearOfBirth.getText().toString().trim())) +"";
        } catch (ParseException e) {
        }
        String strphoneNumber = txtPhoneNumber.getText().toString().trim();
        String struserName = txtUserName.getText().toString().trim();
        String strpassWord = txtPassWord.getText().toString().trim();
        String strRole = "User";
        if (strName.equals("") || strAddress.equals("") || stryearOfBirth.equals("")
                || strphoneNumber.equals("") || struserName.equals("") || strpassWord.equals("")) {
            Toast.makeText(this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        } else if (struserName.length() > 50) {
            Toast.makeText(this, "Tài khoản phải ít hơn 50 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (strpassWord.length() > 15) {
            Toast.makeText(this, "Mật khẩu phải ít hơn 15 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (strName.length() > 50) {
            Toast.makeText(this, "Tên người dùng phải ít hơn 50 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (strphoneNumber.startsWith("0",0) == true && strphoneNumber.length() > 10) {
            Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
        } else if (strAddress.length() > 100) {
            Toast.makeText(this, "Địa chỉ phải ít hơn 100 ký tự!", Toast.LENGTH_SHORT).show();
        } else {
            UtilityClass.showProgressDialog(Register.this);
            ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
            Call<Integer> call = apiservice.postUser(strName,checkSpiner(strGender),strAddress,
                    stryearOfBirth,strphoneNumber,struserName,strpassWord,strRole);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.body() == 1) {
                        UtilityClass.HideProgressDialog();
                        Toast.makeText(Register.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        intentLogin();
                    }else{
                        Toast.makeText(Register.this, "Người dùng đã tồn tại!", Toast.LENGTH_SHORT).show();
                        UtilityClass.HideProgressDialog();

                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(Register.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }
            });
        }
    }
    private void intentLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void init() {
        btnRegister = findViewById(R.id.btnRegister);
        btnExit = findViewById(R.id.btnExit);
        spinnerGender = findViewById(R.id.sp_gender);
        txtName = findViewById(R.id.edt_name);
        txtUserName = findViewById(R.id.edt_register_username);
        txtPassWord = findViewById(R.id.edt_register_password);
        txtYearOfBirth = findViewById(R.id.edt_yearofbirth);
        txtPhoneNumber = findViewById(R.id.edt_phonenumber);
        txtAddress = findViewById(R.id.edt_address);
    }
}
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
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterShop extends AppCompatActivity {
    private Button btnRegister, btnExit;
    private EditText edtName,edtDescription,edtAddress,edtPhoneNumber,edtAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop);
        init();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilityClass.checkI(RegisterShop.this)) {
                    registerShop();

                }else {
                    Toast.makeText(RegisterShop.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentExit();
            }
        });
    }

    private void registerShop() {
        int Id_User = Integer.parseInt(UtilityClass.retrieveData(this,"Id_User",""));
        String name = edtName.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String avatar = edtAvatar.getText().toString().trim();
        if (name.equals("") || address.equals("") || avatar.equals("") || phoneNumber.equals("")) {
            Toast.makeText(this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        } else if (description.length() > 200) {
            Toast.makeText(this, "Mô tả phải ít hơn 200 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (name.length() > 50) {
            Toast.makeText(this, "Tên cửa hàng phải ít hơn 50 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (phoneNumber.startsWith("0",0) == true && phoneNumber.length() > 10) {
            Toast.makeText(this, "Số điện thoại cửa hàng không hợp lệ!", Toast.LENGTH_SHORT).show();
        } else if (address.length() > 100) {
            Toast.makeText(this, "Địa chỉ cửa hàng phải ít hơn 100 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (avatar.length() > 2000) {
            Toast.makeText(this, "Ảnh đại diện cửa hàng phải ít hơn 2000 ký tự!", Toast.LENGTH_SHORT).show();
        }else {
            UtilityClass.showProgressDialog(this);
            ApiService apiService = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
            Call<Boolean> call = apiService.RegisterShop(Id_User, name, description, address, phoneNumber, avatar);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.body() == true) {
                        Toast.makeText(RegisterShop.this, "Đăng ký cửa hàng thành công", Toast.LENGTH_SHORT).show();
                        UtilityClass.HideProgressDialog();
                    } else {
                        Toast.makeText(RegisterShop.this, "Bạn đã đăng ký cửa hàng trước đó!", Toast.LENGTH_SHORT).show();
                        UtilityClass.HideProgressDialog();

                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(RegisterShop.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }
            });
        }
    }

    private void intentExit(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    private void init(){
        btnRegister = findViewById(R.id.btn_register_shop);
        btnExit = findViewById(R.id.btn_exit_register_shop);
        edtName = findViewById(R.id.edt_register_shop_name);
        edtDescription = findViewById(R.id.edt_register_shop_description);
        edtAddress = findViewById(R.id.edt_register_shop_address);
        edtPhoneNumber = findViewById(R.id.edt_register_shop_phone_number);
        edtAvatar = findViewById(R.id.edt_register_shop_avatar);
    }
}
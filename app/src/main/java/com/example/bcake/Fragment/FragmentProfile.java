package com.example.bcake.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.Controller.BoughtProduct;
import com.example.bcake.Controller.MyGoogleMap;
import com.example.bcake.Controller.Login;
import com.example.bcake.Controller.RegisterShop;
import com.example.bcake.Controller.SearchProduct;
import com.example.bcake.Controller.UtilityClass;
import com.example.bcake.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends Fragment {
    private TextView txtUpdateProfile, txtViewLocation, txtBought, txtLogout, txtContinue, txtName,txtRegister;
    private EditText edtName, edtAddress, edtYearOfBirth, edtPhoneNumber, edtAvatar;
    private Button btnUpdate, btnExit;
    private ImageView imgAvatar;
    private Dialog dialog;
    private DatePickerDialog.OnDateSetListener dateOnClickListener;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        init();
        UtilityClass.showProgressDialog(getActivity());
        txtName.setText(UtilityClass.retrieveData(getActivity(), "Name", "").toString().trim());
        String Avatar = UtilityClass.retrieveData(getActivity(), "Avatar", "").toString().trim();
        UtilityClass.HideProgressDialog();
        if (Avatar.equals("")) {
            imgAvatar.setImageResource(R.drawable.splashscreen);
        } else {
            Picasso.get().load(Avatar).into(imgAvatar);
        }
        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentLogin();
            }
        });
        txtUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
        txtViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentGoogleMap();
            }
        });

        txtBought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BoughtProduct.class);
                startActivity(intent);
            }
        });
        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchProduct.class);
                startActivity(intent);
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentRegisterShop();
            }
        });
        return view;
    }

    private void intentRegisterShop() {
        Intent intent = new Intent(getActivity(), RegisterShop.class);
        startActivity(intent);
    }

    private void intentGoogleMap() {
        Intent intent = new Intent(getActivity(), MyGoogleMap.class);
        startActivity(intent);
    }


    private void updateProfile() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_update_profile);
        btnUpdate = dialog.findViewById(R.id.btn_update_profile);
        btnExit = dialog.findViewById(R.id.btn_exit_update_profile);
        edtName = dialog.findViewById(R.id.edt_update_profile_name);
        edtAddress = dialog.findViewById(R.id.edt_update_profile_address);
        edtAvatar = dialog.findViewById(R.id.edt_update_profile_avatar);
        edtPhoneNumber = dialog.findViewById(R.id.edt_update_profile_phonenumber);
        edtYearOfBirth = dialog.findViewById(R.id.edt_update_profile_yearofbirth);
        edtName.setText(UtilityClass.retrieveData(getActivity(),"Name","").toString().trim());
        edtAvatar.setText(UtilityClass.retrieveData(getActivity(),"Avatar","").toString().trim());
        edtPhoneNumber.setText(UtilityClass.retrieveData(getActivity(),"phoneNumber","").toString().trim());
        edtAddress.setText(UtilityClass.retrieveData(getActivity(),"Address","").toString().trim());
        try{
            SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.ENGLISH);
            Date date = originalFormat.parse(UtilityClass.retrieveData(getActivity(),"yearOfBirth",""));
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            String yearOfBirth = format.format(date);
            edtYearOfBirth.setText(yearOfBirth+"");
        }catch (Exception e){
            Toast.makeText(getActivity(), e+ "", Toast.LENGTH_SHORT).show();
        }
        edtYearOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Dialog_MinWidth, dateOnClickListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateOnClickListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = day + "/" + month + "/" + year;
                edtYearOfBirth.setText(date);
            }
        };
        dialog.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id =Integer.parseInt(UtilityClass.retrieveData(getActivity(),"Id_User",""));
                String name = edtName.getText().toString().trim();
                String address = edtAddress.getText().toString().trim();
                String avatar = edtAvatar.getText().toString().trim();
                String phoneNumber = edtPhoneNumber.getText().toString().trim();
                SimpleDateFormat format = new SimpleDateFormat("dd/m/yyyy");
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd");
                String yearOfBirth = "";
                try {
                    yearOfBirth = format1.format(format.parse(edtYearOfBirth.getText().toString().trim()));
                } catch (ParseException e) {
                }
                if (name.equals("") || address.equals("") || avatar.equals("") || phoneNumber.equals("") || yearOfBirth.equals("")) {
                    Toast.makeText(getActivity(), "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else if (name.length() > 50) {
                    Toast.makeText(getActivity(), "Tên người dùng phải ít hơn 50 ký tự!", Toast.LENGTH_SHORT).show();
                } else if (phoneNumber.startsWith("0",0) == true && phoneNumber.length() > 10) {
                    Toast.makeText(getActivity(), "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
                } else if (address.length() > 100) {
                    Toast.makeText(getActivity(), "Địa chỉ phải ít hơn 50 ký tự!", Toast.LENGTH_SHORT).show();
                } else if (avatar.length() > 2000) {
                    Toast.makeText(getActivity(), "Ảnh đại diện phải ít hơn 2000 ký tự!", Toast.LENGTH_SHORT).show();
                }else {
                    UtilityClass.showProgressDialog(getActivity());
                    ApiService apiService = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
                    Call<Integer> call = apiService.UpdateProfile(id, name, address, yearOfBirth, phoneNumber, avatar);
                    String finalYearOfBirth = yearOfBirth;
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.body() == 1) {
                                UtilityClass.saveData(getActivity(),"Address",address);
                                UtilityClass.saveData(getActivity(),"Name",name);
                                UtilityClass.saveData(getActivity(),"Avatar",avatar);
                                UtilityClass.saveData(getActivity(),"yearOfBirth", finalYearOfBirth);
                                UtilityClass.saveData(getActivity(),"phoneNumber",phoneNumber);
                                txtName.setText(name);
                                if (avatar == null) {
                                    imgAvatar.setImageResource(R.drawable.icon_facebook);
                                } else {
                                    Picasso.get().load(avatar).into(imgAvatar);

                                }
                                Toast.makeText(getActivity(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                UtilityClass.HideProgressDialog();
                            }
                            else{
                                Toast.makeText(getActivity(), "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                                UtilityClass.HideProgressDialog();
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            UtilityClass.HideProgressDialog();
                        }
                    });
                }
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void intentLogin() {
        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
    }

    private void init() {
        txtRegister = view.findViewById(R.id.txt_register_shop);
        txtName = view.findViewById(R.id.txt_menu_name);
        imgAvatar = view.findViewById(R.id.img_menu_avatar);
        txtContinue = view.findViewById(R.id.txt_profile_continue);
        txtUpdateProfile = view.findViewById(R.id.txt_update_profile);
        txtViewLocation = view.findViewById(R.id.txt_profile_location);
        txtBought = view.findViewById(R.id.txt_profile_bought);
        txtLogout = view.findViewById(R.id.txt_profile_logout);
    }
}

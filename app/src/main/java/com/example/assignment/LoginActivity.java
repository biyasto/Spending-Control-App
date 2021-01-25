package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.assignment.dao.DaoTaiKhoan;
import com.example.assignment.model.TaikhoanMatKhau;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private Button btReg, btLogin;
    private AdView mAdView;
    EditText edtTaiKhoan, edtMatKhau;
    CheckBox cbLuuThongTin;
    DaoTaiKhoan tkDao;
    ArrayList<TaikhoanMatKhau> listTK = new ArrayList<>();
    LinearLayout linearLayout;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linearLayout = findViewById(R.id.linearLayoutlogin);
        init();
        animation = AnimationUtils.loadAnimation(this, R.anim.dangnhap_dangky_animation);
        linearLayout.startAnimation(animation);
        layThongTin();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean xetTk = false;
                tkDao = new DaoTaiKhoan(LoginActivity.this);
                String tenTK = edtTaiKhoan.getText().toString();
                String mk = edtMatKhau.getText().toString();
                listTK = tkDao.getALl();
                for (int i = 0; i < listTK.size(); i++) {
                    TaikhoanMatKhau tkx = listTK.get(i);
                    if (tkx.getTenTaiKhoan().matches(tenTK) && tkx.getMatKhau().matches(mk)) {
                        xetTk = true;
                        break;
                    }
                }
                if (xetTk == true) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    luuThongTin();
                    startActivity(new Intent(LoginActivity.this, com.example.assignment.MainActivity.class));
                    overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);

                } else {
                    Toast.makeText(LoginActivity.this, "Tên tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, com.example.assignment.RegisterActivity.class);
                startActivityForResult(i, 999);
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });



    }

    private void luuThongTin() {
        SharedPreferences sharedPreferences = getSharedPreferences("sinhvien", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String ten = edtTaiKhoan.getText().toString();
        String pass = edtMatKhau.getText().toString();
        boolean check = cbLuuThongTin.isChecked();
        if (!check) {
            editor.clear();
        } else {
            editor.putString("tennguoidung", ten);
            editor.putString("matkhau", pass);
            editor.putBoolean("checkstatus", check);
        }
        editor.commit();

    }

    private void layThongTin() {
        SharedPreferences sharedPreferences = getSharedPreferences("sinhvien", MODE_PRIVATE);

        boolean check = sharedPreferences.getBoolean("checkstatus", false);
        if (check) {
            String tenNguoiDung = sharedPreferences.getString("tennguoidung", "");
            String matKhau = sharedPreferences.getString("matkhau", "");
            edtTaiKhoan.setText(tenNguoiDung);
            edtMatKhau.setText(matKhau);
        } else {
            edtTaiKhoan.setText("");
            edtMatKhau.setText("");
        }
        cbLuuThongTin.setChecked(check);
    }

    private void init() {
        edtTaiKhoan = findViewById(R.id.edtUserName);
        edtMatKhau = findViewById(R.id.edtPassword);
        btLogin = findViewById(R.id.btnLogin);
        btReg = findViewById(R.id.btnRegister);
        cbLuuThongTin = findViewById(R.id.cbLuuThongTin);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            String tk = data.getStringExtra("taikhoan");
            String mk = data.getStringExtra("matkhau");
            edtTaiKhoan.setText(tk);
            edtMatKhau.setText(mk);
        }
    }
}



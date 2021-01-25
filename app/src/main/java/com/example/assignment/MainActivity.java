package com.example.assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.assignment.fragment.ChangepasswordFragment;
import com.example.assignment.fragment.GioiThieu_Fragment;
import com.example.assignment.fragment.KhoanChi_Fragment;
import com.example.assignment.fragment.KhoanThu_Fragment;
import com.example.assignment.fragment.ThongKe_Fragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;


    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.frame_layout);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);



        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupDrawerContent(navigationView);
        ThongKe_Fragment searchFragment = new ThongKe_Fragment();
        replaceFragment(searchFragment);


        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nameid);
        SharedPreferences sharedPreferences = getSharedPreferences("sinhvien", MODE_PRIVATE);
        String tenNguoiDung = sharedPreferences.getString("tennguoidung", "");
        navUsername.setText(tenNguoiDung);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }

    private void selectedItemDrawer(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.khoanthu:
                setTitle("KHOẢN THU");
                KhoanThu_Fragment khoanThu_fragment = new KhoanThu_Fragment();
                replaceFragment(khoanThu_fragment);
                break;
            case R.id.khoanchi:
                setTitle("KHOẢN CHI");
                KhoanChi_Fragment khoanChi_fragment = new KhoanChi_Fragment();
                replaceFragment(khoanChi_fragment);
                break;
            case R.id.thongke:
                setTitle("THỐNG KÊ");
                ThongKe_Fragment searchFragment = new ThongKe_Fragment();
                replaceFragment(searchFragment);
                break;
            case R.id.gioithieu:
                setTitle("GIỚI THIỆU");
                GioiThieu_Fragment settingsFragment = new GioiThieu_Fragment();
                replaceFragment(settingsFragment);
                break;

            case R.id.doimatkhau:
                setTitle("ĐỔI MẬT KHẨU");
                ChangepasswordFragment changepasswordFragment = new ChangepasswordFragment();
                replaceFragment(changepasswordFragment);
                break;
            case R.id.logout:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                break;

        }

        item.setChecked(true);

        drawerLayout.closeDrawers();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectedItemDrawer(item);
                return true;
            }
        });
    }

}

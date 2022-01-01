package com.example.madcamp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private long backKeyPressedTime = 0;
    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkVerify();
        }
        else
        {
            startApp();
        }
    }

    public void startApp()
    {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentContact(), "Contact");
        adapter.AddFragment(new FragmentGallery(), "Gallery");
        adapter.AddFragment(new FragmentVoiceRecord(), "Voice Note");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void checkVerify()
    {
        if (
                checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED )
        {
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS))
            {
                // ...
            }
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS },
                    1);
        }
        else
        {
            startApp();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1)
        {
            if (grantResults.length > 0)
            {
                for (int i=0; i<grantResults.length; ++i)
                {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                    {
                        // 하나라도 거부한다면.
                        new AlertDialog.Builder(this).setTitle("알림").setMessage("권한을 허용해주셔야 앱을 이용할 수 있습니다.")
                                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        MainActivity.this.finish();
                                    }
                                }).setNegativeButton("권한 설정", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        .setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                                getApplicationContext().startActivity(intent);
                            }
                        }).setCancelable(false).show();

                        return;
                    }
                }
                startApp();
            }
        }
    }

    @Override
    public void onBackPressed() {

        // Previous Back Button Function
        // super.onBackPressed();

        // 2000 ms = 2 sec
        if (System.currentTimeMillis() > backKeyPressedTime + 1000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "\'뒤로가기\' 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        } else if (System.currentTimeMillis() <= backKeyPressedTime + 1000){
            finish();
        }

    }

}
package tech.digitalcraft.daddysburger.View.Activites;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import tech.digitalcraft.daddysburger.Controller.Interfaces.RefreshViewPager;
import tech.digitalcraft.daddysburger.Model.Shared;
import tech.digitalcraft.daddysburger.R;
import tech.digitalcraft.daddysburger.Controller.ViewPagerAdapter;
import tech.digitalcraft.daddysburger.View.Activites.Menu.QrReader;


public class MainActivity extends AppCompatActivity implements RefreshViewPager {


    private ProgressDialog mProgressDialog;
    ViewPager viewPager;
    ViewPagerAdapter sectionsPagerAdapter;
    BottomNavigationView mBottomNavigationView;
    MenuItem qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressDialog = new ProgressDialog(this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);

        mProgressDialog.setMessage("جاري تسجيل الخروج...");
        mProgressDialog.setCancelable(false);
        setTitle("داديز برجر");

        sectionsPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setSelectedItemId(R.id.home);
        viewPager.setCurrentItem(1);
        viewPager.setEnabled(false);

        Shared.get.mRefreshViewPager = this;

        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 0);
        }


        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                    {
                        viewPager.setCurrentItem(1);
                        setTitle("داديز برجر");

                    }
                    break;
                    case R.id.cart:
                    {

                        viewPager.setCurrentItem(0);
                        setTitle("الطلبات");

                    }
                    break;
                }

                return true;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position)
                {
                    case 0:
                    {

                        mBottomNavigationView.setSelectedItemId(R.id.cart);
                        setTitle("الطلبات");
                    }
                        break;
                    case 1:
                    {
                        mBottomNavigationView.setSelectedItemId(R.id.home);
                        setTitle("داديز برجر");
                    }
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_menu , menu);
        qr = menu.findItem(R.id.qr);
        qr.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
            {

                mProgressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.dismiss();
                        Shared.get.Logout(MainActivity.this);

                    }
                }, 3000);
            }
            break;
            case R.id.qr:
            {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED)
                {
                    Intent i = new Intent(MainActivity.this , QrReader.class);
                    startActivity(i);
                }
                else
                {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, 0);
                }
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void refresh() {


        sectionsPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        mBottomNavigationView.setSelectedItemId(R.id.home);
        viewPager.setCurrentItem(1);
        viewPager.setEnabled(false);
    }
}
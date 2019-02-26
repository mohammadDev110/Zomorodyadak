package ir.zomorodyadak.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import ir.zomorodyadak.R;
import ir.zomorodyadak.adapter.ViewPagerAdapter;
import ir.zomorodyadak.ui.fragment.CategoryFragment;
import ir.zomorodyadak.ui.fragment.HomeFragment;
import ir.zomorodyadak.ui.fragment.SearchFragment;
import ir.zomorodyadak.ui.fragment.AgencyFragment;
import ir.zomorodyadak.ui.widget.NonSwipableViewPager;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private NonSwipableViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavigation();
    }

    private void setupNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        setupViewPager();
    }

    private void setupViewPager() {
        viewPager = findViewById(R.id.vp_main);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new HomeFragment());
        pagerAdapter.addFragment(new CategoryFragment());
        pagerAdapter.addFragment(new SearchFragment());
        pagerAdapter.addFragment(new AgencyFragment());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                viewPager.setCurrentItem(0);
                return true;
            case R.id.nav_products:
                viewPager.setCurrentItem(1);
                return true;
            case R.id.nav_search:
                viewPager.setCurrentItem(2);
                return true;
            case R.id.nav_agency:
                viewPager.setCurrentItem(3);
                return true;
            default:
                return false;
        }
    }
}

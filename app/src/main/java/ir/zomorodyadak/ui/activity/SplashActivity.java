package ir.zomorodyadak.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import ir.zomorodyadak.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupAnimation();
        setupSplash();
    }

    private void setupAnimation() {
        final ImageView logoIv = findViewById(R.id.iv_logo_splash);
        TextView tvAppName = findViewById(R.id.tv_app_name);
        Animation slideInTop, slideInBottom;
        slideInTop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_top);
        logoIv.startAnimation(slideInTop);
        slideInBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_bottom);
        tvAppName.startAnimation(slideInBottom);
    }

    private void setupSplash() {
        new Handler().postDelayed(() -> {
            finish();
            startActivity(new Intent(SplashActivity.this,
                    MainActivity.class));
        }, 3000);
    }
}

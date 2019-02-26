package ir.zomorodyadak.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.google.android.material.card.MaterialCardView;
import com.r0adkll.slidr.Slidr;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.zomorodyadak.R;
import ir.zomorodyadak.adapter.ProductsAdapter;
import ir.zomorodyadak.model.Products.ProductsModel;
import ir.zomorodyadak.server.ApiUtil;
import ir.zomorodyadak.ui.widget.SansBoldTextView;
import ir.zomorodyadak.ui.widget.connection_error.ConnectionError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity implements
        ProductsAdapter.onProductsClick, Callback<List<ProductsModel>> {

    public static final String VIEW_PREF = "view_pref";
    public static SharedPreferences viewPref;
    private static SharedPreferences.Editor viewPrefEdit;
    private RecyclerView productsRecycler;
    private AppCompatImageView viewSettingImage;
    private AppCompatImageView twoColumn, threeColumn;
    private ProductsAdapter productsAdapter;
    private ConnectionError connectionError;
    private String productsId;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        productsRecycler = findViewById(R.id.rv_products);
        viewSettingImage = findViewById(R.id.iv_products_setting);
        Slidr.attach(this);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String productsTitle = bundle.getString("title");
        connectionError = findViewById(R.id.error_products);
        progressBar = findViewById(R.id.pb_products);
        setupApi();
        setupToolbar(productsTitle);
        setupItemViewSetting();
    }

    private void setupApi() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            productsId = bundle.getString("cat_id");
        ApiUtil.getserviceClassSecond().getProducts(productsId).enqueue(this);
    }

    private void setupSetting() {
        viewPref = getSharedPreferences(VIEW_PREF,
                Context.MODE_PRIVATE);
        setupPreviewSetting();
        setupPreviewRecyclerSetting();
        setupSettingClick();
    }

    private void setupSettingClick() {
        viewPrefEdit = viewPref.edit();
        twoColumn.setOnClickListener(view -> {
            if (!(productsRecycler.getLayoutManager() == new
                    GridLayoutManager(getApplicationContext(), 2))) {
                viewPrefEdit.putString(VIEW_PREF, "2").apply();
                twoColumn.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                        R.color.colorPrimary));
                threeColumn.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                        R.color.bottom_nav_disabled_color));
                if (productsAdapter != null) {

                }
                productsRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                        2));
            }
        });
        threeColumn.setOnClickListener(view -> {
            if (!(productsRecycler.getLayoutManager() ==
                    new GridLayoutManager(getApplicationContext(), 3))) {
                viewPrefEdit.putString(VIEW_PREF, "3").apply();
                twoColumn.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                        R.color.bottom_nav_disabled_color));
                threeColumn.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                        R.color.colorPrimary));
                productsRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                        3));
            }
        });
    }

    private void setupPreviewRecyclerSetting() {
        if (viewPref.getString(VIEW_PREF, "2")
                .contains("2")) {
            productsRecycler.setLayoutManager(new GridLayoutManager(ProductsActivity.this,
                    2));
        } else if (viewPref.getString(VIEW_PREF,
                "3").contains("3")) {
            productsRecycler.setLayoutManager(new GridLayoutManager(ProductsActivity.this,
                    3));
        } else {
            productsRecycler.setLayoutManager(new GridLayoutManager(ProductsActivity.this,
                    2));
        }
    }

    private void setupPreviewSetting() {
        twoColumn = findViewById(R.id.iv_two_columns);
        threeColumn = findViewById(R.id.iv_three_column);
        if (viewPref.getString(VIEW_PREF, "2").contains("2")) {
            twoColumn.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                    R.color.colorPrimary));
            threeColumn.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                    R.color.bottom_nav_disabled_color));
        } else if (viewPref.getString(VIEW_PREF, "3").contains("3")) {
            twoColumn.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                    R.color.bottom_nav_disabled_color));
            threeColumn.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                    R.color.colorPrimary));
        } else {
            twoColumn.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                    R.color.colorPrimary));
            threeColumn.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                    R.color.bottom_nav_disabled_color));
        }
    }

    private void setupToolbar(String productsTitle) {
        AppCompatImageView backIv = findViewById(R.id.iv_products_back);
        SansBoldTextView titleTv = findViewById(R.id.tv_products_toolbar);
        titleTv.setText("محصولات " + productsTitle);
        backIv.startAnimation(AnimationUtils.makeInAnimation(getApplicationContext(), false));
        titleTv.startAnimation(AnimationUtils.makeInAnimation(getApplicationContext(), false));
        backIv.setOnClickListener(view -> finish());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void onProductsItemClick(ProductsModel productsModel) {
        String productId = productsModel.getPid();
        String productTitle = productsModel.getPname();
        Intent productsIntent = new Intent(this, ProductActivity.class);
        productsIntent.putExtra("productId", productId);
        productsIntent.putExtra("productTitle", productTitle);
        startActivity(productsIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void setupItemViewSetting() {
        final Animation slideInUp, slideOutUp;
        slideInUp = AnimationUtils.makeInAnimation(getApplicationContext(), true);
        slideOutUp = AnimationUtils.makeOutAnimation(getApplicationContext(), true);
        final MaterialCardView viewColumnContainer = findViewById(R.id.cv_view_type_layout);
        viewSettingImage.setOnClickListener(view -> {
            if (!(viewColumnContainer.getVisibility() == MaterialCardView.VISIBLE)) {
                viewColumnContainer.setVisibility(MaterialCardView.VISIBLE);
                viewColumnContainer.startAnimation(slideInUp);
            } else {
                slideOutUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        viewColumnContainer.setVisibility(MaterialCardView.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                viewColumnContainer.startAnimation(slideOutUp);
            }
        });

    }

    @Override
    public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
        connectionError.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        viewSettingImage.setVisibility(AppCompatImageView.VISIBLE);
        viewSettingImage.startAnimation(AnimationUtils.makeInAnimation(ProductsActivity.this,
                true));
        productsRecycler.setVisibility(View.VISIBLE);
        productsAdapter = new ProductsAdapter(ProductsActivity.this,
                response.body(), this);
        productsRecycler.setAdapter(productsAdapter);
        productsRecycler.setHasFixedSize(true);
        setupSetting();
    }

    @Override
    public void onFailure(Call<List<ProductsModel>> call, Throwable t) {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        connectionError.setVisibility(View.VISIBLE);
        connectionError.onRetryButtonClick(view -> setupApi());
    }
}

package ir.zomorodyadak.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.r0adkll.slidr.Slidr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;
import ir.zomorodyadak.R;
import ir.zomorodyadak.ui.widget.connection_error.ConnectionError;

public class ProductActivity extends AppCompatActivity {

    private ConnectionError connectionError;
    private NestedScrollView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Slidr.attach(this);
        Bundle extra = getIntent().getExtras();
        assert extra != null;
        String productId = extra.getString("productId");
        String productTitle = extra.getString("productTitle");
        String productUrl = "http://zomorodyadak.ir/adrd/product.php?pid=";
        setupToolbar(productTitle);
        connectionError = findViewById(R.id.error_product);
        content = findViewById(R.id.product_container);
        setupWebView(productUrl, productId);
    }

    private void setupWebView(String productUrl, String productId) {
        WebView productWeb = findViewById(R.id.wv_product);
        final ProgressBar productProgress = findViewById(R.id.pb_product);
        productWeb.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                productProgress.setVisibility(View.VISIBLE);
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                content.setVisibility(View.VISIBLE);
                productProgress.setVisibility(View.INVISIBLE);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                connectionError.setVisibility(View.VISIBLE);
                connectionError.onRetryButtonClick(view1 -> {
                    view.loadUrl(productUrl);
                });
                content.setVisibility(View.INVISIBLE);
                productProgress.setVisibility(View.INVISIBLE);
            }
        });
        productWeb.loadUrl(productUrl.concat(productId));
        WebSettings settings = productWeb.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    private void setupToolbar(String productTitle) {
        AppCompatTextView tvTitle = findViewById(R.id.tv_product_toolbar);
        tvTitle.setText(productTitle);
        AppCompatImageView ivBack = findViewById(R.id.iv_product_back);
        ivBack.setOnClickListener(view -> finish());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

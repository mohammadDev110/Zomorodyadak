package ir.zomorodyadak.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.r0adkll.slidr.Slidr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;
import ir.zomorodyadak.R;
import ir.zomorodyadak.ui.widget.CustomToast;
import ir.zomorodyadak.ui.widget.connection_error.ConnectionCallback;
import ir.zomorodyadak.ui.widget.connection_error.ConnectionError;
import ir.zomorodyadak.util.CheckConnection;

public class ShowHomeContentActivity extends AppCompatActivity implements ConnectionCallback {

    private static final String insertUrl = "https://www.novinmohtava.com/parvazproject/c/zy/newswebview.php?naapi=";
    private CheckConnection checkConnection = new CheckConnection(ShowHomeContentActivity.this);
    private ConnectionError error;

    @Override
    protected void onStart() {
        super.onStart();
        if (checkConnection.NetworkConnected()) {
            setupWebView();
        } else {
            error = findViewById(R.id.error_news);
            error.setVisibility(LinearLayout.VISIBLE);
            error.onRetryButtonClick(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Slidr.attach(this);
        Bundle getTitle = getIntent().getExtras();
        String title = getTitle.getString("title");
        setupToolbar(title);
    }

    private void setupWebView() {
        Bundle extra = getIntent().getExtras();
        assert extra != null;
        String id = extra.getString("id");
        assert id != null;
        final String url = insertUrl.concat(id);
        WebView webView = findViewById(R.id.wv_show_home_content);
        final ProgressBar progressBar = findViewById(R.id.pb_show_home_content);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
                progressBar.setProgress(progress);
                if (progress == 100) {
                    NestedScrollView content = findViewById(R.id.nsv_show_home_content);
                    content.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(ProgressBar.GONE);
                }

            }
        });
        webView.loadUrl(url);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    private void setupToolbar(String title) {
        AppCompatTextView titleTv = findViewById(R.id.tv_scroll_toolbar);
        titleTv.setText(title);
        AppCompatImageView ivBack = findViewById(R.id.iv_news_back);
        ivBack.setOnClickListener(view -> finish());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void onRetryCallback(View view) {
        if (checkConnection.NetworkConnected()) {
            setupWebView();
        } else {
            error.setVisibility(LinearLayout.INVISIBLE);
            CustomToast.createToast("ارتباط برقرار نشد!", ShowHomeContentActivity.this);
        }
    }
}

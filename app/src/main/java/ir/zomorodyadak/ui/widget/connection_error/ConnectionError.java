package ir.zomorodyadak.ui.widget.connection_error;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import androidx.annotation.Nullable;
import ir.zomorodyadak.R;

public class ConnectionError extends LinearLayout {
    private MaterialButton retryButton;

    public ConnectionError(Context context) {
        super(context);
        init();
    }

    public ConnectionError(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConnectionError(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.conncetion_error_layout,
                this, true);
        retryButton = view.findViewById(R.id.btn_no_connection);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
    }

    public void onRetryButtonClick(ConnectionCallback callback) {
        retryButton.setOnClickListener(callback::onRetryCallback);
    }
}

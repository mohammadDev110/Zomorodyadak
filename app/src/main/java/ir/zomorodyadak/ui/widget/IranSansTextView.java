package ir.zomorodyadak.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import ir.zomorodyadak.ZomorodApp;

public class IranSansTextView extends androidx.appcompat.widget.AppCompatTextView {
    public IranSansTextView(Context context) {
        super(context);
        setupTextView();
    }

    public IranSansTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupTextView();
    }

    public IranSansTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupTextView();
    }

    private void setupTextView() {
        setTypeface(ZomorodApp.getIranianSansFont(getContext()));
    }
}

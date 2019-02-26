package ir.zomorodyadak.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import ir.zomorodyadak.ZomorodApp;

public class SansBoldTextView extends AppCompatTextView {
    public SansBoldTextView(Context context) {
        super(context);
        setTypeFace();
    }

    public SansBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeFace();
    }

    public SansBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeFace();
    }

    private void setTypeFace() {
        setTypeface(ZomorodApp.getIransansBold(getContext()));
    }
}

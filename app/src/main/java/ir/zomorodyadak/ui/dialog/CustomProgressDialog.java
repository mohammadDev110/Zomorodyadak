package ir.zomorodyadak.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import ir.zomorodyadak.R;

public class CustomProgressDialog extends Dialog {
    public Activity activity;
    public Dialog c;

    public CustomProgressDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_dialog);
    }
}

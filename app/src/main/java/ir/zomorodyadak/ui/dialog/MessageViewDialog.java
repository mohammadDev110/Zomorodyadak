package ir.zomorodyadak.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatTextView;
import ir.zomorodyadak.R;

public class MessageViewDialog extends Dialog {
    public Activity activity;
    private RelativeLayout btn;
    private String message, title;
    private AppCompatTextView messageTxt, titleTxt;

    public MessageViewDialog(Activity activity, String message, String title) {
        super(activity);
        this.activity = activity;
        this.message = message;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.message_dialog);
        if (!(getWindow() == null))
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btn = findViewById(R.id.dialog_btn);
        btn.setOnClickListener(view -> this.dismiss());
        messageTxt = findViewById(R.id.tv_dialog_message);
        titleTxt = findViewById(R.id.tv_dialog_message_title);
        titleTxt.setText(title);
        messageTxt.setText(message);
    }
}

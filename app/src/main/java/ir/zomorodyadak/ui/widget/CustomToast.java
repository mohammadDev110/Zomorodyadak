package ir.zomorodyadak.ui.widget;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ir.zomorodyadak.R;

public class CustomToast {

    public static void createToast(String message, Activity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                activity.findViewById(R.id.toast_root_layout));

        TextView text = layout.findViewById(R.id.tv_toast);
        text.setText(message);

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 10);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}

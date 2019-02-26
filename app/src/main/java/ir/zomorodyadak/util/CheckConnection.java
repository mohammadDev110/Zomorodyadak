package ir.zomorodyadak.util;

import android.content.Context;
import android.net.ConnectivityManager;

public class CheckConnection {
    private Context context;

    public CheckConnection(Context context) {
        this.context = context;
    }

    public boolean NetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }
}

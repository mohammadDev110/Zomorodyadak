package ir.zomorodyadak;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

public class ZomorodApp extends Application {

    private static Typeface iranianSansFont, iransansBold;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Typeface getIranianSansFont(Context context) {
        if (iranianSansFont == null) {
            iranianSansFont = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMobile.ttf");
        }
        return iranianSansFont;
    }

    public static Typeface getIransansBold(Context context) {
        if (iransansBold == null) {
            iransansBold = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMobile_Bold.ttf");
        }
        return iransansBold;
    }
}

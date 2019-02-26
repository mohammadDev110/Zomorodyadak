package ir.zomorodyadak.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import ir.zomorodyadak.R;
import ir.zomorodyadak.ui.widget.IranSansTextView;

public class ZmBottomNav implements View.OnClickListener {
    private AppCompatImageView ivHome, ivProducts, ivSearch, ivUser;
    private IranSansTextView tvHome, tvProducts, tvSearch, tvUser;
    private RelativeLayout rlHome, rlProducts, rlSearch, rlUser;
    private Context context;
    private OnNavigationItemClick listener;
    private Activity activity;

    public ZmBottomNav(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    private void setupViews() {
        ivHome = activity.findViewById(R.id.iv_bottom_bar_home);
        ivProducts = activity.findViewById(R.id.iv_bottom_bar_products);
        ivSearch = activity.findViewById(R.id.iv_bottom_bar_search);
        ivUser = activity.findViewById(R.id.iv_bottom_bar_user);

        tvHome = activity.findViewById(R.id.tv_bottom_bar_home);
        tvProducts = activity.findViewById(R.id.tv_bottom_bar_products);
        tvSearch = activity.findViewById(R.id.tv_bottom_bar_search);
        tvUser = activity.findViewById(R.id.tv_bottom_bar_user);

        rlHome = activity.findViewById(R.id.rl_bottom_nav_home);
        rlProducts = activity.findViewById(R.id.rl_bottom_nav_products);
        rlSearch = activity.findViewById(R.id.rl_bottom_nav_search);
        rlUser = activity.findViewById(R.id.rl_bottom_nav_user);
    }

    public void onNavigationItemSelected(OnNavigationItemClick onNavigationItemClick) {
        setupViews();
        listener = onNavigationItemClick;
        ivHome.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary),
                PorterDuff.Mode.SRC_IN);
        tvHome.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        listener.onHomeItemClick();

        rlHome.setOnClickListener(this);
        rlProducts.setOnClickListener(this);
        rlSearch.setOnClickListener(this);
        rlUser.setOnClickListener(this);
    }

    private void setupEnableColor(AppCompatImageView imageView, IranSansTextView textView) {
        imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
        textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }

    private void setupDisableColor(AppCompatImageView imageView, IranSansTextView textView) {
        imageView.setColorFilter(ContextCompat.getColor(context, R.color.bottom_nav_disabled_color));
        textView.setTextColor(context.getResources().getColor(R.color.bottom_nav_disabled_color));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_bottom_nav_home:
                setupEnableColor(ivHome, tvHome);
                setupDisableColor(ivProducts, tvProducts);
                setupDisableColor(ivSearch, tvSearch);
                setupDisableColor(ivUser, tvUser);
                listener.onHomeItemClick();
                break;
            case R.id.rl_bottom_nav_products:
                setupEnableColor(ivProducts, tvProducts);
                setupDisableColor(ivHome, tvHome);
                setupDisableColor(ivSearch, tvSearch);
                setupDisableColor(ivUser, tvUser);
                listener.onProductsItemClick();
                break;
            case R.id.rl_bottom_nav_search:
                setupEnableColor(ivSearch, tvSearch);
                setupDisableColor(ivHome, tvHome);
                setupDisableColor(ivProducts, tvProducts);
                setupDisableColor(ivUser, tvUser);
                listener.onSearchItemClick();
                break;
            case R.id.rl_bottom_nav_user:
                setupEnableColor(ivUser, tvUser);
                setupDisableColor(ivHome, tvHome);
                setupDisableColor(ivSearch, tvSearch);
                setupDisableColor(ivProducts, tvProducts);
                listener.onUserItemClick();
                break;
        }
    }

    public interface OnNavigationItemClick {
        void onHomeItemClick();
        void onProductsItemClick();
        void onSearchItemClick();
        void onUserItemClick();
    }
}

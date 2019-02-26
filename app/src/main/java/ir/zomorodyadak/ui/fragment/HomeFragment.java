package ir.zomorodyadak.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import ir.zomorodyadak.R;
import ir.zomorodyadak.adapter.HomeNewsAdapter;
import ir.zomorodyadak.model.HomeNewsModel;
import ir.zomorodyadak.server.ApiUtil;
import ir.zomorodyadak.ui.activity.ShowHomeContentActivity;
import ir.zomorodyadak.ui.widget.connection_error.ConnectionError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements HomeNewsAdapter.onHomeNewsClick,
        Callback<List<HomeNewsModel>> {

    private RecyclerView homeRecycler;
    private ProgressBar progressBar;
    private ConnectionError connectionError;

    public HomeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiUtil.getserviceClass().getAllNews().enqueue(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupToolbarTitle(view);
        homeRecycler = view.findViewById(R.id.rv_home_news);
        progressBar = view.findViewById(R.id.pb_home_news);
        connectionError = view.findViewById(R.id.error_home);
        return view;
    }

    private void generateDataList(List<HomeNewsModel> homeNewModels) {
        HomeNewsAdapter homeNewsAdapter = new HomeNewsAdapter(getContext(), homeNewModels,
                this);
        homeRecycler.setVisibility(View.VISIBLE);
        progressBar.setVisibility(ProgressBar.GONE);
        connectionError.setVisibility(ConnectionError.INVISIBLE);
        homeRecycler.setAdapter(homeNewsAdapter);
        YoYo.with(Techniques.ZoomIn.getAnimator()).duration(1000).playOn(homeRecycler);
    }

    @Override
    public void onHomeNewsItemClick(HomeNewsModel homeNewsModel) {
        String itemId = homeNewsModel.getId();
        String newsTitle = homeNewsModel.getTitle();
        Intent intent = new Intent(getContext(),
                ShowHomeContentActivity.class);
        intent.putExtra("id", itemId);
        intent.putExtra("title", newsTitle);
        startActivity(intent);
        if (!(getActivity() == null))
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void setupToolbarTitle(View view) {
        AppCompatTextView titleTxt = view.findViewById(R.id.tv_default_toolbar_title);
        titleTxt.setText("اخبار شرکت");
    }

    @Override
    public void onResponse(Call<List<HomeNewsModel>> call, Response<List<HomeNewsModel>> response) {
        generateDataList(response.body());
    }

    @Override
    public void onFailure(Call<List<HomeNewsModel>> call, Throwable t) {
        call.cancel();
        connectionError.setVisibility(View.VISIBLE);
        connectionError.onRetryButtonClick(view -> ApiUtil.getserviceClass().getAllNews()
                .enqueue(this));
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}

package ir.zomorodyadak.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.zomorodyadak.R;
import ir.zomorodyadak.adapter.CategoryAdapter;
import ir.zomorodyadak.model.CategoryModel;
import ir.zomorodyadak.server.ApiUtil;
import ir.zomorodyadak.ui.widget.connection_error.ConnectionError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment implements Callback<List<CategoryModel>> {


    public CategoryFragment() {
        // Required empty public constructor
    }

    private RecyclerView categoryList;
    private AppCompatTextView toolbarText;
    private Context context;
    private ConnectionError connectionError;
    private ProgressBar loading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        ApiUtil.getserviceClassSecond().getCategoryItems().enqueue(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        categoryList = view.findViewById(R.id.rv_category);
        toolbarText = view.findViewById(R.id.tv_default_toolbar_title);
        connectionError = view.findViewById(R.id.error_category);
        loading = view.findViewById(R.id.category_loading);
        setupToolbar();
        return view;
    }

    private void setupToolbar() {
        toolbarText.setText(context.getString(R.string.title_products));
    }

    @Override
    public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
        loading.setVisibility(View.INVISIBLE);
        connectionError.setVisibility(ConnectionError.INVISIBLE);
        CategoryAdapter adapter = new CategoryAdapter(getContext(), response.body());
        categoryList.setVisibility(View.VISIBLE);
        categoryList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        categoryList.setItemAnimator(new DefaultItemAnimator());
        categoryList.setAdapter(adapter);
    }

    @Override
    public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
        call.cancel();
        loading.setVisibility(View.INVISIBLE);
        connectionError.setVisibility(View.VISIBLE);
        connectionError.onRetryButtonClick(view -> ApiUtil.getserviceClassSecond()
                .getCategoryItems().enqueue(this));
    }
}

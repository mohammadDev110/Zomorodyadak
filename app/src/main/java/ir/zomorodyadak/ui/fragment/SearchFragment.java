package ir.zomorodyadak.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.zomorodyadak.R;
import ir.zomorodyadak.adapter.SearchAdapter;
import ir.zomorodyadak.model.SearchModel;
import ir.zomorodyadak.server.ApiUtil;
import ir.zomorodyadak.ui.activity.ProductActivity;
import ir.zomorodyadak.ui.widget.CustomToast;
import ir.zomorodyadak.ui.widget.connection_error.ConnectionError;
import ir.zomorodyadak.util.endlessrecycler.InfiniteScrollProvide;
import ir.zomorodyadak.util.endlessrecycler.OnLoadMoreListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements Callback<List<SearchModel>>,
        SearchAdapter.OnSearchClick, OnLoadMoreListener {
    private AppCompatImageButton searchBtn;
    private AppCompatEditText editText;
    private RecyclerView recyclerView;
    private RelativeLayout lazyLayout;
    private ProgressBar loading;
    private SearchAdapter adapter;
    private ConnectionError connectionError;
    private int page = 1;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        find(view);
        setupViews();
        return view;
    }

    private void find(View view) {
        searchBtn = view.findViewById(R.id.ib_search);
        editText = view.findViewById(R.id.edt_search);
        recyclerView = view.findViewById(R.id.rv_search);
        lazyLayout = view.findViewById(R.id.lazy_load_layout);
        loading = view.findViewById(R.id.search_loading);
        connectionError = view.findViewById(R.id.error_search);
    }

    private void setupViews() {
        setupSearchClick();
    }

    private void setupSearchClick() {
        editText.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                setupSearch();
                handled = true;
            }
            return handled;
        });

        searchBtn.setOnClickListener(view -> setupSearch());
    }

    private void setupSearch() {
        page = 1;
        recyclerView.setVisibility(RecyclerView.GONE);
        loading.setVisibility(ProgressBar.VISIBLE);
        if (editText.getText() != null) {
            if (!editText.getText().toString().isEmpty()) {
                setupRecycler();
            } else {
                loading.setVisibility(ProgressBar.INVISIBLE);
                YoYo.with(Techniques.Shake).duration(800).playOn(editText);
            }
        }
    }

    private void setupRecycler() {
        adapter = new SearchAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        setupApi();
        setupLoadMore();
    }

    private void setupLoadMore() {
        InfiniteScrollProvide scrollProvide = new InfiniteScrollProvide();
        scrollProvide.attach(recyclerView, this);
    }

    @Override
    public void onLoadMore() {
        lazyLayout.setVisibility(RelativeLayout.VISIBLE);
        setupApi();
    }

    private void setupApi() {
        ApiUtil.getserviceClassSecond().getSearchResults(page,
                Objects.requireNonNull(editText.getText()).toString().trim())
                .enqueue(this);
    }

    @Override
    public void onResponse(Call<List<SearchModel>> call, Response<List<SearchModel>> response) {
        if (response.isSuccessful() && response.code() == 200) {
            if (response.body() != null) {
                recyclerView.setVisibility(RecyclerView.VISIBLE);
                lazyLayout.setVisibility(RelativeLayout.INVISIBLE);
                loading.setVisibility(ProgressBar.INVISIBLE);
                adapter.addList(response.body());
                page += 1;
            }
        }
    }

    @Override
    public void onFailure(Call<List<SearchModel>> call, Throwable t) {
        lazyLayout.setVisibility(RelativeLayout.INVISIBLE);
        loading.setVisibility(ProgressBar.INVISIBLE);
        CustomToast.createToast("خطا در اتصال به سرور!",
                Objects.requireNonNull(getActivity()));
    }

    @Override
    public void viewClicked(SearchModel model) {
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra("productId", model.getPid());
        intent.putExtra("productTitle", model.getPname());
        Objects.requireNonNull(getContext()).startActivity(intent);
    }
}

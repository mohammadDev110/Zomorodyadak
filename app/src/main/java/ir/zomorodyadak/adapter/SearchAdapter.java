package ir.zomorodyadak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import ir.zomorodyadak.R;
import ir.zomorodyadak.model.SearchModel;
import ir.zomorodyadak.ui.widget.IranSansTextView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private Context context;
    private List<SearchModel> modelList = new ArrayList<>();
    private OnSearchClick onSearchClick;

    public SearchAdapter(Context context, OnSearchClick onSearchClick) {
        this.context = context;
        this.onSearchClick = onSearchClick;
    }

    public void addList(List<SearchModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.products_list_item,
                parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bindViews(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView pImage;
        IranSansTextView pTitle;

        SearchViewHolder(View itemView) {
            super(itemView);
            pTitle = itemView.findViewById(R.id.tv_products_item);
            pImage = itemView.findViewById(R.id.iv_products_item);
        }

        void bindViews(final SearchModel searchModel) {
            pTitle.setText(searchModel.getPname());
            Picasso.get().load(searchModel.getPimage()).into(pImage);
            CardView cardView = itemView.findViewById(R.id.cv_products_container);
            cardView.setOnClickListener(view -> onSearchClick.viewClicked(searchModel));
        }
    }

    public interface OnSearchClick {
        void viewClicked(SearchModel model);
    }
}

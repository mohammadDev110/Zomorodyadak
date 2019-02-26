package ir.zomorodyadak.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import ir.zomorodyadak.R;
import ir.zomorodyadak.model.CategoryModel;
import ir.zomorodyadak.ui.activity.ProductsActivity;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<CategoryModel> categoryModels;

    public CategoryAdapter(Context context, List<CategoryModel> categoryModels) {
        this.context = context;
        this.categoryModels = categoryModels;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories_list_item, parent,
                false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bindItem(categoryModels.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView ivCategory;
        private CardView container;

        CategoryViewHolder(View itemView) {
            super(itemView);
            ivCategory = itemView.findViewById(R.id.iv_categories);
            container = itemView.findViewById(R.id.cv_category_container);
        }

        void bindItem(final CategoryModel categoryModel) {
            Picasso.get().load(categoryModel.getImg()).into(ivCategory);
            container.setOnClickListener(view -> {
                Intent i = new Intent(context, ProductsActivity.class);
                i.putExtra("cat_id", categoryModel.getId());
                i.putExtra("title", categoryModel.getTitle());
                context.startActivity(i);
            });
        }
    }
}

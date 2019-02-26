package ir.zomorodyadak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import ir.zomorodyadak.R;
import ir.zomorodyadak.model.Products.ProductsModel;
import ir.zomorodyadak.ui.widget.IranSansTextView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    private Context context;
    private List<ProductsModel> productsModels;
    private onProductsClick onProductsClick;
    private int lastPosition = -1;

    public ProductsAdapter(Context context, List<ProductsModel> productsModels,
                           ProductsAdapter.onProductsClick onProductsClick) {
        this.context = context;
        this.productsModels = productsModels;
        this.onProductsClick = onProductsClick;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.products_list_item, parent,
                false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        holder.bindViews(productsModels.get(position));
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            YoYo.with(Techniques.FadeInUp).duration(300).playOn(view);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ProductsViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return productsModels.size();
    }

    class ProductsViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView pImage;
        IranSansTextView pTitle;

        ProductsViewHolder(View itemView) {
            super(itemView);
            pTitle = itemView.findViewById(R.id.tv_products_item);
            pImage = itemView.findViewById(R.id.iv_products_item);
        }

        void bindViews(final ProductsModel productsModel) {
            pTitle.setText(productsModel.getPname());
            Picasso.get().load(productsModel.getPimage()).into(pImage);
            CardView cardView = itemView.findViewById(R.id.cv_products_container);
            cardView.setOnClickListener(view -> onProductsClick.onProductsItemClick(productsModel));
        }
    }

    public interface onProductsClick {
        void onProductsItemClick(ProductsModel productsModel);
    }
}

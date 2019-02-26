package ir.zomorodyadak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import ir.zomorodyadak.R;
import ir.zomorodyadak.model.HomeNewsModel;

public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.HomeNewsViewHolder> {

    private Context context;
    private List<HomeNewsModel> homeNewModels;
    private HomeNewsAdapter.onHomeNewsClick onHomeNewsItemClick;
    private int lastPosition = -1;

    public HomeNewsAdapter(Context context, List<HomeNewsModel> homeNewModels, onHomeNewsClick onHomeNewsItemClick) {
        this.context = context;
        this.homeNewModels = homeNewModels;
        this.onHomeNewsItemClick = onHomeNewsItemClick;
    }

    @NonNull
    @Override
    public HomeNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_list_item, parent,
                false);

        return new HomeNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeNewsViewHolder holder, int position) {
        holder.bindNews(homeNewModels.get(position));
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f,
                    1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(new Random().nextInt(601));
            view.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull HomeNewsViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        clearAnimation(holder);
    }

    private void clearAnimation(HomeNewsViewHolder holder) {
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return homeNewModels.size();
    }

    class HomeNewsViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView imageItem;
        private TextView title, dataCreated;
        HomeNewsViewHolder(View itemView) {
            super(itemView);
            imageItem = itemView.findViewById(R.id.iv_news_item);
            title = itemView.findViewById(R.id.tv_news_item);
            dataCreated = itemView.findViewById(R.id.tv_data_created_news);
        }
        void bindNews(final HomeNewsModel homeNewsModel) {
            Picasso.get().load(homeNewsModel.getImages().trim()).placeholder(R.drawable.placeholder)
                    .into(imageItem);
            title.setText(homeNewsModel.getTitle());
            dataCreated.setText(homeNewsModel.getCreated());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onHomeNewsItemClick.onHomeNewsItemClick(homeNewsModel);
                }
            });
        }
    }

    public interface onHomeNewsClick {
        void onHomeNewsItemClick (HomeNewsModel homeNewsModel);
    }
}

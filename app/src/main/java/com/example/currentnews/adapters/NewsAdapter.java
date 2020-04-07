package com.example.currentnews.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currentnews.R;
import com.example.currentnews.databinding.NewsItemBinding;
import com.example.currentnews.models.Article;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private final NewsAdapterListener listener;
    private List<Article> articles;
    private LayoutInflater layoutInflater;

    /**
     * Creates a NewsAdapter.
     * @param articles Items that will be displayed in our app
     * @param listener An instance from an interface, that we use to determine how to react
     *                 on certain events such as clicking on our menu items, or list items
     */
    public NewsAdapter(List<Article> articles, NewsAdapterListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which our doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@ling androidx.recyclerview.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new ForecastAdapterViewHolder that holds the View for each list item
     */

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        NewsItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.news_item, viewGroup, false);
        return new NewsViewHolder(binding);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the news details
     * for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param newsViewHolder The ViewHolder which should be updated to represent the
     *                       contents of the item at the given position in the data set.
     * @param position       The position of the item within the adapter's data set.
     */

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {
        newsViewHolder.binding.setNews(articles.get(position));
        newsViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    public void setArticles(List<Article> articles) {
        if (articles != null) {
            this.articles = articles;
            notifyDataSetChanged();
        }
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final NewsItemBinding binding;

        public NewsViewHolder(final NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.ivOptions.setOnClickListener(this);
            this.binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = this.getAdapterPosition();
            if (v instanceof ImageView) {
                listener.onItemOptionsClicked(articles.get(index));
            } else {
                listener.onNewsItemClicked(articles.get(index));
            }
        }
    }
}
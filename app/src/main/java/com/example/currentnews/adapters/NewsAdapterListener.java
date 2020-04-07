package com.example.currentnews.adapters;

import com.example.currentnews.models.Article;

public interface NewsAdapterListener {
    void onNewsItemClicked(Article article);
    void onItemOptionsClicked(Article article);
}

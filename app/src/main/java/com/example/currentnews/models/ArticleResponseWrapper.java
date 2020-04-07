package com.example.currentnews.models;

import com.example.currentnews.models.Article;

import java.util.List;

public class ArticleResponseWrapper {
    private final String status;
    private final int totalResults;
    private final List<Article> articles;

    /**
     * @param status       If the request was successful or not. Options: ok, error.
     * @param totalResults The total number of results available for your request.
     * @param articles     The results of the request.
     */
    public ArticleResponseWrapper(String status, int totalResults, List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }
}

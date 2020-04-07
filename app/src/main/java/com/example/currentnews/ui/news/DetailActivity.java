package com.example.currentnews.ui.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.currentnews.R;
import com.example.currentnews.data.NewsRepository;
import com.example.currentnews.databinding.ActivityDetailBinding;
import com.example.currentnews.models.Article;

public class DetailActivity extends AppCompatActivity {
    public static final String PARAM_ARTICLE = "param-article";
    private ActivityDetailBinding binding;
    private Article article;
    private boolean isSaved;
    private NewsRepository newsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        makeUiFullscreen();
        setupToolbar();
        setupArticleAndListener();
        newsRepository = NewsRepository.getInstance(this);

        getSavedState();

        binding.ivSave.setOnClickListener(v -> {
            if (isSaved) {
                newsRepository.removeSaved(article.id);
            } else {
                newsRepository.save(article.id);
            }
        });
    }

    private void getSavedState() {
        if (article != null) {
            newsRepository.isSaved(article.id).observe(this, aBoolean -> {
                if (aBoolean != null) {
                    isSaved = aBoolean;
                    if (isSaved) {
                        binding.ivSave.setImageResource(R.drawable.ic_saved_item);
                    } else {
                        binding.ivSave.setImageResource(R.drawable.ic_save);
                    }
                }
            });
        }
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void makeUiFullscreen() {
        // When applying fullscreen layout, transparent bar works only for VERSION < 21
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            binding.getRoot().setFitsSystemWindows(true);
        }
        // Make UI fullscreen and make it load stable
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }

    /**
     * Extracts Article from Arguments and Adds button listeners
     */
    private void setupArticleAndListener() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(PARAM_ARTICLE)) {
            final Article article = bundle.getParcelable(PARAM_ARTICLE);
            if (article != null) {
                this.article = article;
                binding.setArticle(article);
                setupShareButton(article);
                setupButtonClickListener(article);
            }
        }
    }

    private void setupShareButton(final Article article) {
        binding.ivShare.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String shareText = article.getTitle() + "\n" + article.getUrl();
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            intent.setType("text/plain");

            startActivity(intent);
        });
    }

    private void setupButtonClickListener(final Article article) {
        binding.btnReadFull.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_enter_transition, R.anim.slide_down_animation);
    }
}
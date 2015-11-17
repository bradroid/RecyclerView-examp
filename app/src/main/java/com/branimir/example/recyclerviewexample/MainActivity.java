package com.branimir.example.recyclerviewexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ArticlesAdapter(this, getArticlesForPage(0)));
    }

    /** Creates mock list of articles, will be replaced with server call in the real implementation */
    private List<Article> getArticlesForPage(int page) {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            articles.add(new Article("Mock article title #" + page + i, "Category"));
        }
        return articles;
    }
}

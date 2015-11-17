package com.branimir.example.recyclerviewexample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final int MOCK_NETWORK_DELAY = 2000;

    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    @Bind(R.id.toolbar) Toolbar toolbar;

    private ArticlesAdapter adapter;
    private Handler handler;
    private boolean loading = false;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArticlesAdapter(this, getArticlesForPage(0));
        recyclerView.setAdapter(adapter);

        handler = new Handler();
        page = 0;
        Paginate.with(recyclerView, callbacks)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(new CustomLoadingListItemCreator())
                .build();
    }

    /** Creates mock list of articles, will be replaced with server call in the real implementation */
    private List<Article> getArticlesForPage(int page) {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            articles.add(new Article("Mock article title #" + page + i, "Category"));
        }
        return articles;
    }

    Paginate.Callbacks callbacks = new Paginate.Callbacks() {
        @Override
        public void onLoadMore() {
            loading = true;
            handler.postDelayed(mockDataRunnable, MOCK_NETWORK_DELAY);
        }

        @Override
        public boolean isLoading() {
            return loading;
        }

        @Override
        public boolean hasLoadedAllItems() {
            return false;
        }
    };

    /** Runnable used to fake asynchronous network request */
    private Runnable mockDataRunnable = new Runnable() {
        @Override
        public void run() {
            page++;
            adapter.add(getArticlesForPage(page));
            loading = false;
        }
    };

    private class CustomLoadingListItemCreator implements LoadingListItemCreator {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.loading_row, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            VH vh = (VH) holder;
            vh.tvLoading.setText(String.format("Total items loaded: %d.\nLoading more...", adapter.getItemCount()));
        }
    }

    static class VH extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_loading_text) TextView tvLoading;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

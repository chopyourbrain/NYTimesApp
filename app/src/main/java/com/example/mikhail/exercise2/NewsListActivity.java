package com.example.mikhail.exercise2;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mikhail.exercise2.DTO.DTO;
import com.example.mikhail.exercise2.DTO.PictureDTO;
import com.example.mikhail.exercise2.DTO.MyResponse;
import com.example.mikhail.exercise2.Database.AppDatabase;
import com.example.mikhail.exercise2.Database.NewsEntity;
import com.example.mikhail.exercise2.Network.RestAPI;
import com.github.glomadrian.loadingballs.BallView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.mikhail.exercise2.State.Loading;

public class NewsListActivity extends AppCompatActivity {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public List<NewsItem> news;
    RecyclerView list;
    BallView ballView;
    String[] data = {"home", "world", "opinion", "national", "politics", "upshot", "nyregion", "business", "technology", "science", "health", "sports", "arts", "books", "movies",
            "theater", "sundayreview", "fashion", "tmagazine", "food", "travel", "magazine", "realestate", "automobiles", "obituaries", "insider"};

    @Nullable
    private final NewsListAdapter.OnItemClickListener clickListener = newsItem -> {
        NewsDetailsActivity.start(this, newsItem);

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        list = findViewById(R.id.recycler);
        ballView = findViewById(R.id.ballview);
        showState(Loading);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                loadNews(data[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "DATABASE_NAME").build();


    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadNews(@NonNull String search) {
        showState(Loading);
        final Disposable searchDisposable = RestAPI.getInstance()
                .news()
                .search(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showNews, this::handleError);
        compositeDisposable.add(searchDisposable);
    }


    public void showNews(@NonNull MyResponse response) {

        List<DTO> newsdto = response.getData();
        news = refactorDTO(newsdto);
        list.setAdapter(new NewsListAdapter(this, news, clickListener));
        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            list.setLayoutManager(new LinearLayoutManager(this));
        else
            list.setLayoutManager(new GridLayoutManager(this, 2));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        showState(State.HasData);
        saveData(refactorToDao(news),getApplicationContext());
    }

 /*   private void checkResponseAndShowState(@NonNull MyResponse response) {
        showNews(response);
      if (!response.isSuccessful()) {
            showState(State.ServerError);
            return;
        }

        final MyResponse body = response.body();
        if (body == null) {
            showState(State.HasNoData);
            return;
        }

        final List<DTO> data = body.getData();
        if (data == null) {
            showState(State.HasNoData);
            return;
        }

        if (data.isEmpty()) {
            showState(State.HasNoData);
            return;
        }
        showNews(body);
        showState(State.HasData);

    } */

    private void handleError(Throwable throwable) {
        if (throwable instanceof IOException) {
            showState(State.NetworkError);
            return;
        }
        showState(State.ServerError);
        Log.d("logd",""+throwable);
    }


    public void showState(@NonNull State state) {

        switch (state) {
            case HasData:
                ballView.setVisibility(View.GONE);
                list.setVisibility(View.VISIBLE);
                break;

            case HasNoData:
                ballView.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
                break;
            case NetworkError:
                ballView.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
                break;

            case ServerError:
                ballView.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
                break;
            case Loading:
                ballView.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
                break;


            default:
                throw new IllegalArgumentException("Unknown state: " + state);
        }
    }

    static private List<NewsItem> refactorDTO(List<DTO> listdto) {
        List<NewsItem> news = new ArrayList<>();
        for (DTO x : listdto) {
            String image = "";
            for (PictureDTO y : x.getMultimedia()) {
                if (y.getFormat().equals("Standard Thumbnail")) {
                    image = y.getUrl();
                    break;
                }
            }
            news.add(new NewsItem(x.getTitle(), image, x.getSection(), x.getPublishedDate().replace('T', ' '), x.getAbstract1(),x.getAbstract1()));
        }
        return news;
    }
    private void saveData(List<NewsEntity> newsList, Context context) {
        AppDatabase db = AppDatabase.getAppDatabase(context);

        db.newsDao().deleteAll();

        NewsEntity[] news = newsList.toArray(new NewsEntity[newsList.size()]);

        db.newsDao().insertAll(news);

    }

    private Observable<List<NewsEntity>> getData(Context context) {
        AppDatabase db = AppDatabase.getAppDatabase(context);

        return db.newsDao().getAll();
    }

    private List<NewsEntity> refactorToDao (List<NewsItem> newsList) {
        List<NewsEntity> news = new ArrayList<>();
        for (NewsItem x : newsList ) {
            NewsEntity k = new NewsEntity();
            k.setTitle(x.getTitle());
            k.setAbstract1(x.getFullText());
            k.setSection(x.getCategory());
            k.setPublished_date(x.getPublishDate());
            k.setUrl(x.getImageUrl());
        }
        return news;
    }

}
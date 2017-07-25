package com.t_nishikawa.internrssreaderapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BookMarkActivity extends AppCompatActivity {

    public static void launchFrom(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), BookMarkActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
    }

    private BookMarkListAdapter bookMarkListAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    MainActivity.launchFrom(BookMarkActivity.this);
                    return true;
                case R.id.navigation_bookmark:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_bookmark);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.book_mark_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<BookMarkDataManager.BookMarkData> list = new ArrayList<>();
        bookMarkListAdapter = new BookMarkListAdapter(list);
        bookMarkListAdapter.setOnClickListener(new BookMarkListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, BookMarkDataManager.BookMarkData bookMarkData) {
                ArticleViewerActivity.launchFrom(BookMarkActivity.this, bookMarkData.title, bookMarkData.url);
            }
        });
        mRecyclerView.setAdapter(bookMarkListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        BookMarkDataManager bookMarkDataManager = new DatabaseManager(getApplicationContext());
        List<BookMarkDataManager.BookMarkData> list = bookMarkDataManager.getBookMarkList();
        bookMarkListAdapter.updateList(list);
    }
}

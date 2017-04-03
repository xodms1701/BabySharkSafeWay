package com.gonigoni.ui.searchresult;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gonigoni.babysharksafeway.R;
import com.gonigoni.glrobal.Session;
import com.gonigoni.ui.ActivityType;
import com.gonigoni.ui.searchresult.adapter.SearchResultRecyclerViewAdapter;
import com.skp.Tmap.TMapPOIItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchResultActivity extends Activity implements SearchResultView {

    @BindView(R.id.backBtn)
    ImageView backBtn;
    @BindView(R.id.toolbarTitleTextView)
    TextView toolbarTitleTextView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private SearchResultPresenter searchResultPresenter = new SearchResultPresenter();
    private SearchResultRecyclerViewAdapter resultRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        searchResultPresenter.attachView(this);
        final boolean flag = Session.getInstanse().isFlag();
        searchResultPresenter.searchResultFlag(flag);


        String query = Session.getInstanse().getStartQuery();
        if (flag) {
            query = Session.getInstanse().getEndQuery();
        }
        searchResultPresenter.getQueryResult(query);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Session session = Session.getInstanse();
        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getBaseContext(), recyclerView,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (session.isFlag()) {
                    session.setEndPOIItem(resultRecyclerViewAdapter.getItem(position));
                    setResult(ActivityType.END_LOT_ACT);
                } else {
                    session.setStartPOIItem(resultRecyclerViewAdapter.getItem(position));
                    setResult(ActivityType.START_LOT_ACT);
                }
                finish();
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        }));
    }



    @OnClick(R.id.backBtn)
    public void clickBackBtn() {
        finish();
    }

    @Override
    public void setToolBarTitleText(String text) {
        toolbarTitleTextView.setText(text);
    }

    @Override
    public void addPOIItemtoListView(ArrayList<TMapPOIItem> items) {

        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).getPOIName());
        }
        resultRecyclerViewAdapter = new SearchResultRecyclerViewAdapter(items);
        recyclerView.setAdapter(resultRecyclerViewAdapter);
        resultRecyclerViewAdapter.notifyDataSetChanged();

    }
}

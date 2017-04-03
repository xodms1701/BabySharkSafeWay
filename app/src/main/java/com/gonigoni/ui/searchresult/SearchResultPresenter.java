package com.gonigoni.ui.searchresult;

import com.gonigoni.data.DataManager;
import com.gonigoni.ui.base.BasePresenter;

import rx.android.schedulers.AndroidSchedulers;

/**
 */

public class SearchResultPresenter extends BasePresenter<SearchResultView> {

    private DataManager dataManager = DataManager.getInstance();

    @Override
    public void attachView(SearchResultView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }


    public void searchResultFlag(boolean flag) {
        if (flag) {
            getMvpView().setToolBarTitleText("출발지 검색 결과");
        } else {
            getMvpView().setToolBarTitleText("목적지 검색 결과");
        }
    }

    public void getQueryResult(String query) {
        dataManager.getMapPoIItems(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((item) -> getMvpView().addPOIItemtoListView(item),
                        (error) -> System.err.println(error),
                        () -> System.out.println("complate"));
    }
}

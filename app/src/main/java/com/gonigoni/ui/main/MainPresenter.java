package com.gonigoni.ui.main;

import com.gonigoni.ui.ActivityType;
import com.gonigoni.ui.base.BasePresenter;
import com.gonigoni.ui.searchresult.SearchResultActivity;
import com.gonigoni.ui.setting.SettingActivity;

/**
 */

public class MainPresenter extends BasePresenter<MainView> {

    @Override
    public void attachView(MainView mvpView) {
        super.attachView(mvpView);
        // T MAP 초기화
        getMvpView().initTMapView();
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void inputDestination(final String input, final int type) {
        if (input.isEmpty()) {
            getMvpView().showWrongInputToast();
        } else {
            if (type == ActivityType.START_LOT_ACT) {
                getMvpView().startActivityForType(SearchResultActivity.class, input, ActivityType.START_LOT_ACT);
            } else {
                getMvpView().startActivityForType(SearchResultActivity.class, input, ActivityType.END_LOT_ACT);
            }
        }
    }

    public void clickSettingBtn() {
        getMvpView().startActivity(SettingActivity.class);
    }
}

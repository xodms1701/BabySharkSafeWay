package com.gonigoni.ui.main;

import com.gonigoni.ui.base.MvpView;

/**
 */

public interface MainView extends MvpView {

    void initTMapView();

    void showWrongInputToast();

    void startActivity(Class activity);

    void startActivityForType(Class activity, String query, int Type);
}

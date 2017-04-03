package com.gonigoni.ui.searchresult;

import com.gonigoni.ui.base.MvpView;
import com.skp.Tmap.TMapPOIItem;

import java.util.ArrayList;

/**
 */

public interface SearchResultView extends MvpView {
    void setToolBarTitleText(String text);
    void addPOIItemtoListView(ArrayList<TMapPOIItem> items);
}

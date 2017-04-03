package com.gonigoni.data;

import com.gonigoni.data.remote.MappingService;
import com.skp.Tmap.TMapPOIItem;

import java.util.ArrayList;

import rx.Observable;

/**

 */

public final class DataManager {

    private MappingService mapingService = new MappingService();

    private DataManager() {
    }

    public static DataManager getInstance() {
        return LazyHolder.dataManager;
    }

    private static class LazyHolder {
        private static final DataManager dataManager = new DataManager();
    }

    public Observable<ArrayList<TMapPOIItem>> getMapPoIItems(String query) {
        return mapingService.getMapPoIItems(query);
    }
}

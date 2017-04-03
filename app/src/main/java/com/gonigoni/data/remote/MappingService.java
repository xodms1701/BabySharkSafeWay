package com.gonigoni.data.remote;


import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPOIItem;

import java.util.ArrayList;

import rx.Observable;

/**
 */

public class MappingService {
    public Observable<ArrayList<TMapPOIItem>> getMapPoIItems(String query) {
        return Observable.create(subscriber -> {
            TMapData tmapdata = new TMapData();
            tmapdata.findAllPOI(query, 100, items -> {
                subscriber.onNext(items);
                subscriber.onCompleted();
            });
        });
    }

}

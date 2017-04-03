package com.gonigoni.glrobal;

import com.skp.Tmap.TMapPOIItem;

/**

 */

public class Session {

    private TMapPOIItem startPOIItem;
    private TMapPOIItem endPOIItem;
    private String startQuery;
    private String endQuery;
    private boolean flag;

    public TMapPOIItem getStartPOIItem() {
        return startPOIItem;
    }

    public void setStartPOIItem(TMapPOIItem startPOIItem) {
        this.startPOIItem = startPOIItem;
    }

    public TMapPOIItem getEndPOIItem() {
        return endPOIItem;
    }

    public void setEndPOIItem(TMapPOIItem endPOIItem) {
        this.endPOIItem = endPOIItem;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getStartQuery() {
        return startQuery;
    }

    public void setStartQuery(String startQuery) {
        this.startQuery = startQuery;
    }

    public String getEndQuery() {
        return endQuery;
    }

    public void setEndQuery(String endQuery) {
        this.endQuery = endQuery;
    }

    private Session() {
    }

    public static Session getInstanse() {
        return LazyHolder.session;
    }

    private static class LazyHolder {
        private static Session session = new Session();
    }

}

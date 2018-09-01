package com.grocery.service.filters;

public class BaseFilter {
    private int _pageIndex = 1;
    private int _pageSize = 10;

    public int get_pageIndex() {
        return _pageIndex;
    }

    public void set_pageIndex(int _pageIndex) {
        this._pageIndex = _pageIndex;
    }

    public int get_pageSize() {
        return _pageSize;
    }

    public void set_pageSize(int _pageSize) {
        this._pageSize = _pageSize;
    }
}

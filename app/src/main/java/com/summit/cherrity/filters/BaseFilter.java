package com.summit.cherrity.filters;

public class BaseFilter {
    private int PageIndex = 1;
    private int PageSize = 10;

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.PageIndex = pageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        this.PageSize = pageSize;
    }
}

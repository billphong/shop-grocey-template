package com.grocery.service.filters;

public class ProductFilter extends BaseFilter {
    private int CateId;

    public int getCateId() {
        return CateId;
    }

    public void setCateId(int cateId) {
        this.CateId = cateId;
    }
}

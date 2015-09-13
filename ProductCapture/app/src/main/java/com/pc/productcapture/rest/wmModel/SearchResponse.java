package com.pc.productcapture.rest.wmModel;

import java.util.List;

public class SearchResponse {
    public final List<Item> items;

    public SearchResponse(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SearchResponse{" +
                "items=" + items +
                '}';
    }
}

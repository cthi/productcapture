package com.pc.productcapture.rest.models;

import java.util.List;

public class SearchResponse {
    List<Product> items;

    @Override
    public String toString() {
        return "SearchResponse{" +
                "items=" + items +
                '}';
    }
}

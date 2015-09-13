package com.pc.productcapture.rest.wmModel;

public class Item {
    public final String name;
    public final double salePrice;
    public final String upc;
    public final String shortDescription;
    public final String thumbnailImage;
    public final String addToCartUrl;

    public Item(String name, double salePrice, String upc, String shortDescription, String
            thumbnailImage, String addToCartUrl) {
        this.name = name;
        this.salePrice = salePrice;
        this.upc = upc;
        this.shortDescription = shortDescription;
        this.thumbnailImage = thumbnailImage;
        this.addToCartUrl = addToCartUrl;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", salePrice=" + salePrice +
                ", upc=" + upc +
                ", shortDescription='" + shortDescription + '\'' +
                ", thumbnailImage='" + thumbnailImage + '\'' +
                ", addToCartUrl='" + addToCartUrl + '\'' +
                '}';
    }
}

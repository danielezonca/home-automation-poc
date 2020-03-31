package org.kie.kogito.homeautomation;

public class ImageData {
    private String data;
    private String image;

    public ImageData() {
    }

    public ImageData(String data, String image) {
        this.data = data;
        this.image = image;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
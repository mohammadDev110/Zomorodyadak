package ir.zomorodyadak.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HomeNewsModel implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("images")
    private String images;
    @SerializedName("created")
    private String created;

    public HomeNewsModel(String id, String title, String images, String created) {
        this.id = id;
        this.title = title;
        this.images = images;
        this.created = created;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

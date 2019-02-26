package ir.zomorodyadak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchModel implements Serializable {
    @SerializedName("pid")
    @Expose
    private String pid;

    @SerializedName("pname")
    @Expose
    private String pname;

    @SerializedName("pimage")
    @Expose
    private String pimage;

    public String getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public String getPimage() {
        return pimage;
    }
}

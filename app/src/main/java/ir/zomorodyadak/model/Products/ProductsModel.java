package ir.zomorodyadak.model.Products;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductsModel implements Serializable {
    @SerializedName("pid")
    private String pid;
    @SerializedName("pname")
    private String pname;
    @SerializedName("pimage")
    private String pimage;

    public ProductsModel(String pid, String pname, String pimage) {
        this.pid = pid;
        this.pname = pname;
        this.pimage = pimage;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}

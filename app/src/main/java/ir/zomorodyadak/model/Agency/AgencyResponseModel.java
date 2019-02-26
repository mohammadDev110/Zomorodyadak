package ir.zomorodyadak.model.Agency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AgencyResponseModel implements Serializable {
    @SerializedName("create")
    @Expose
    private String response;

    public AgencyResponseModel(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}

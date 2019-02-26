package ir.zomorodyadak.model.Agency;

import java.io.Serializable;

public class AgencyBodyModel implements Serializable {

    private final String name;
    private final String code;
    private final String area;
    private final String city;
    private final String mobile;

    public AgencyBodyModel(String name, String code, String area, String city, String mobile) {
        this.name = name;
        this.code = code;
        this.area = area;
        this.city = city;
        this.mobile = mobile;
    }
}

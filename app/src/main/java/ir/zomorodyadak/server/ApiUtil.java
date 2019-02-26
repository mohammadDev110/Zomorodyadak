package ir.zomorodyadak.server;

public class ApiUtil {
    public static ApiService getserviceClass() {
        return ApiClient.getClient().create(ApiService.class);
    }

    public static ApiService getserviceClassSecond() {
        return ApiClient.getClient2().create(ApiService.class);
    }
}

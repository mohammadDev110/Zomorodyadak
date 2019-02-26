package ir.zomorodyadak.server;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL="https://www.novinmohtava.com";
    private static final String BASE_URL_2="https://zomorodyadak.ir";

    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;
    private static String TAG = ApiClient.class.getSimpleName();


    static Retrofit getClient() {


        if (retrofit==null) {
            OkHttpClient client=new OkHttpClient.Builder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

     static Retrofit getClient2() {
        if (retrofit2 == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .build();
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(BASE_URL_2)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit2;
    }
}

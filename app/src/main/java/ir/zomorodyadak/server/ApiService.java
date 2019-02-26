package ir.zomorodyadak.server;

import java.util.List;

import ir.zomorodyadak.model.Agency.AgencyBodyModel;
import ir.zomorodyadak.model.Agency.AgencyResponseModel;
import ir.zomorodyadak.model.CategoryModel;
import ir.zomorodyadak.model.HomeNewsModel;
import ir.zomorodyadak.model.Products.ProductsModel;
import ir.zomorodyadak.model.SearchModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/parvazproject/c/zy/appfp.php")
    Call<List<HomeNewsModel>> getAllNews();

    @POST("/adrd/products.php?")
    Call<List<ProductsModel>> getProducts(@Query("catid") String catId);

    @GET("/adrd/cats.php")
    Call<List<CategoryModel>> getCategoryItems();

    @Headers("Content-Type: application/json")
    @POST("/adrd/addseller.php")
    Call<AgencyResponseModel> addAgency(@Body AgencyBodyModel rawData);

    @POST("/adrd/search.php")
    Call<List<SearchModel>> getSearchResults(@Query("page") int page,
                                             @Query("value") String searchValue);
}

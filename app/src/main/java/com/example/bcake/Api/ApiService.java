package com.example.bcake.Api;


import com.example.bcake.Model.Cart;
import com.example.bcake.Model.Category;
import com.example.bcake.Model.DetailOrder;
import com.example.bcake.Model.Favorite;
import com.example.bcake.Model.Order;
import com.example.bcake.Model.Product;
import com.example.bcake.Model.Review;
import com.example.bcake.Model.Shop;
import com.example.bcake.Model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    public static final String BASE_URL = "https://littlebrushedshed41.conveyor.cloud/";

    @GET("api/Login/Users")
    Call<User> Login(@Query("userName") String userName, @Query("passWord") String passWord);

    @POST("api/Register/Users")
    Call<Integer> postUser(@Query("Name") String Name,
                           @Query("Gender") boolean Gender,
                           @Query("Address") String Address,
                           @Query("yearOfBirth") String yearOfBirth,
                           @Query("phoneNumber") String phoneNumber,
                           @Query("userName") String userName,
                           @Query("passWord") String passWord,
                           @Query("Role") String Role);

    @GET("api/GetProductBestSeller/Products")
    Call<ArrayList<Product>> GetProductBestSeller();

    @GET("api/GetNearByShop/Shops")
    Call<ArrayList<Shop>> GetNearByShop(@Query("Address") String Address);

    @GET("api/GetOrderByUser/Orders")
    Call<ArrayList<Order>> GetOrderByUser(@Query("Id_User") int Id_User);

    @GET("api/GetFavoriteByUser/Favorites")
    Call<ArrayList<Favorite>> GetFavoriteByUser(@Query("Id_User") int Id_User);

    @GET("api/GetCartByUser/Carts")
    Call<ArrayList<Cart>> GetCartByUser(@Query("Id_User") int Id_User);

    @GET("api/GetCategoriesByShop/Categories")
    Call<ArrayList<Category>> GetCategoriesByShop(@Query("Id_Shop") int Id_Shop);

    @GET("api/GetProductBestSellerByShop/Products")
    Call<ArrayList<Product>> GetProductBestSellerByShop(@Query("Id_Shop") int Id_Shop);

    @GET("api/GetNewProductByShop/Products")
    Call<ArrayList<Product>> GetNewProductByShop(@Query("Id_Shop") int Id_Shop);

    @GET("api/GetReviewByProduct/Reviews")
    Call<ArrayList<Review>> GetReviewByProduct(@Query("Id_Product") int Id_Product);

    @GET("api/GetNameProduct/Products")
    Call<ArrayList<String>> GetNameProduct(@Query("Name") String Name);
    @GET("api/GetBoughtProduct/Products")
    Call<ArrayList<Product>> GetBoughtProduct(@Query("Id_User") int Id_User);
    @GET("api/SearchProduct/Products")
    Call<ArrayList<Product>> SearchProductResult(@Query("textSearch") String textSearch);

    @GET("api/GetProductByCategory/Products/{id}")
    Call<ArrayList<Product>> GetProductByCategory(@Path("id") int id);

    @POST("api/AddToCart/Carts")
    Call<Cart> AddToCart(@Query("Id_User") int Id_User,
                         @Query("Id_Product") int Id_Product,
                         @Query("quantity") int quantity,
                         @Query("message") String message);

    @POST("api/AddFavorite/Favorites")
    Call<Favorite> AddToFavorite(@Query("Id_User") int Id_User,
                                 @Query("Id_Product") int Id_Product);

    @POST("api/AddReview/Reviews")
    Call<Boolean> PostReview(@Query("Id_User") int Id_User,
                             @Query("Id_Product") int Id_Product,
                             @Query("numberStar") float numberStar,
                             @Query("comment") String comment,
                             @Query("time") String time);

    @GET("api/GetDetailOrderByOrder/Detail_Order")
    Call<ArrayList<DetailOrder>> GetDetailOrder(@Query("Id_Order") int Id_Order);

    @PUT("api/UpdateProfile/Users")
    Call<Integer> UpdateProfile(@Query("Id_User") int Id_User,
                                @Query("name") String name,
                                @Query("address") String address,
                                @Query("yearOfBirth") String yearOfBirth,
                                @Query("phoneNumber") String phoneNumber,
                                @Query("avatar") String avatar);

    @PUT("api/ChangePassWord/Users")
    Call<Boolean> ChangePassWord(@Query("userName") String userName,
                                @Query("passWord") String passWord);

    @POST("api/RegisterShop/Shops")
    Call<Boolean> RegisterShop(@Query("Id_User") int Id_User,
                               @Query("name") String name,
                               @Query("description") String description,
                               @Query("address") String address,
                               @Query("phoneNumber") String phoneNumber,
                               @Query("avatar") String avatar);

    @POST("api/UserPostOrder/Orders")
    Call<List<Order>> UserPostOrder(@Query("Id_User") int Id_User,
                                    @Body List<Cart> carts,
                                    @Query("paymentMethod") String paymentMethod);

    @PUT("api/PutOrderZalo/Orders")
    Call<Boolean> PutOrderZalo(@Body List<Order> orders,
                               @Query("token") String token);

    @PUT("api/UpdateQuantity/Carts")
    Call<Cart> UpdateQuantityCart(@Query("Id_User") int Id_User,
                                  @Query("Id_Product") int Id_Product,
                                  @Query("quantity") int quantity);

    @DELETE("api/DeleteOrderZalo/Orders")
    Call<Boolean> DeleteOrderZalo(@Body List<Order> orders);

    @DELETE("api/DeleteFavorite/Favorites/{id}")
    Call<Favorite> DeleteFavorite(@Path("id") int id);

    @DELETE("api/DeleteCart/Carts/{id}")
    Call<Cart> DeleteCart(@Path("id") int id);

    @DELETE("api/DeleteOrder/Orders/{id}")
    Call<Order> DeleteOrder(@Path("id") int id);

    @GET("api/GetShops/Shops")
    Call<ArrayList<Shop>> GetShops();

    @GET("api/GetShop/Shops/{id}")
    Call<Shop> GetShopById(@Path("id") int id);

    @GET("api/GetProduct/Products/{id}")
    Call<Product> GetProductById(@Path("id") int id);

    @GET("api/SearchProductByName/Products")
    Call<ArrayList<Product>> GetProductByName(@Query("textSearch") String textSearch);

    @GET("api/SearchProductBelow/Products")
    Call<ArrayList<Product>> GetProductBelow();

    @GET("api/SearchProductMiddle/Products")
    Call<ArrayList<Product>> GetProductMiddle();

    @GET("api/SearchProductUpper/Products")
    Call<ArrayList<Product>> GetProductUpper();

    @GET("api/GetNumberCartByUser/Carts")
    Call<Integer> GetNumberCartByUser(@Query("Id_User") int Id_User);
}

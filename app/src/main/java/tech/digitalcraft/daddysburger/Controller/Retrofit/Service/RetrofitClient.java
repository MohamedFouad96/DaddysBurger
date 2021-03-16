package tech.digitalcraft.daddysburger.Controller.Retrofit.Service;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tech.digitalcraft.daddysburger.Model.APIs.CreateOrder;
import tech.digitalcraft.daddysburger.Model.APIs.CreateOrderResponse;
import tech.digitalcraft.daddysburger.Model.APIs.Login;
import tech.digitalcraft.daddysburger.Model.APIs.LoginResponse;
import tech.digitalcraft.daddysburger.Model.APIs.Menu;
import tech.digitalcraft.daddysburger.Model.APIs.Order;
import tech.digitalcraft.daddysburger.Model.APIs.Register;
import tech.digitalcraft.daddysburger.Model.Orders;

/**
 * Created by mohamedfouad on 10/13/18.
 */
public interface RetrofitClient {


    @POST("/Identity/Login")
    Call<LoginResponse> Login(

            @Header("auth2token") String activationToken,
            @Body Login login

    );

    @POST("/Identity/Register")
    @Headers("Content-Type:application/json")
    Call<Void> Register(

            @Header("auth2token") String activationToken,
            @Body Register register

    );


    @GET("/Order/GetMenu")
    @Headers("Content-Type:application/json")
    Call<List<Menu>> Menu(

            @Header("auth2token") String activationToken,
            @Header("Authorization") String token

    );


    @POST("/Order/CreateOrder")
    @Headers("Content-Type:application/json")
    Call<CreateOrderResponse> CreateOrder(

            @Header("auth2token") String activationToken,
            @Header("Authorization") String token ,
            @Body CreateOrder createOrder

    );


    @GET("/Order/GetOrders")
    @Headers("Content-Type:application/json")
    Call<List<Order>> getOrders(

            @Header("auth2token") String activationToken,
            @Header("Authorization") String token

    );

    @POST("/Order/CompleteOrder")
    @Headers("Content-Type:application/json")
    Call<Void> CompleteOrder(

            @Header("auth2token") String activationToken,
            @Header("Authorization") String token ,
            @Query("orderId") int orderID

    );

    @POST("/Identity/Activate")
    @Headers("Content-Type:application/json")
    Call<LoginResponse> activation(

            @Query("key") String key ,
            @Query("id") String phoneId

    );

}

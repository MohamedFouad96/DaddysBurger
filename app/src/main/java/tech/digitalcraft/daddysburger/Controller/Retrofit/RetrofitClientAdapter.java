package tech.digitalcraft.daddysburger.Controller.Retrofit;


import java.util.List;

import retrofit2.Call;
import tech.digitalcraft.daddysburger.Controller.Retrofit.Service.RetrofitClient;
import tech.digitalcraft.daddysburger.Model.APIs.CreateOrder;
import tech.digitalcraft.daddysburger.Model.APIs.CreateOrderResponse;
import tech.digitalcraft.daddysburger.Model.APIs.Login;
import tech.digitalcraft.daddysburger.Model.APIs.LoginResponse;
import tech.digitalcraft.daddysburger.Model.APIs.Meal;
import tech.digitalcraft.daddysburger.Model.APIs.Meals;
import tech.digitalcraft.daddysburger.Model.APIs.Menu;
import tech.digitalcraft.daddysburger.Model.APIs.Order;
import tech.digitalcraft.daddysburger.Model.APIs.Register;
import tech.digitalcraft.daddysburger.Model.Shared;


/**
 * Created by mohamedfouad on 10/13/18.
 */
public class RetrofitClientAdapter {

    private static RetrofitClient mRetrofitClient = ServiceGenerator.createService(RetrofitClient.class);


    public static Call<LoginResponse> activation(String key  , String phoneId)
    {

        return mRetrofitClient.activation(key , phoneId);

    }

    public static Call<LoginResponse> login(String username  , String pass)
    {


       return mRetrofitClient.Login(Shared.get.ACTIVATION_TOKEN,new Login(username,pass,true));

    }


    public static Call<Void> register(String username , String email , String phone  , String pass)
    {


        return mRetrofitClient.Register(Shared.get.ACTIVATION_TOKEN,new Register(username , username, email , pass,1));

    }



    public static Call<List<Menu>> getMenu()
    {


        return mRetrofitClient.Menu(Shared.get.ACTIVATION_TOKEN,"Bearer " + Shared.get.TOKEN);

    }

    public static Call<CreateOrderResponse> createOrder(int orderType, int table , int paymentMethod , String address, List<Meals> meals)
    {


        return mRetrofitClient.CreateOrder(Shared.get.ACTIVATION_TOKEN,"Bearer " + Shared.get.TOKEN, new CreateOrder(orderType,table,address,paymentMethod,meals));

    }


    public static Call<List<Order>> getOrders()
    {


        return mRetrofitClient.getOrders(Shared.get.ACTIVATION_TOKEN,"Bearer " + Shared.get.TOKEN);

    }

    public static Call<Void> compelteOrder(int orderID)
    {


        return mRetrofitClient.CompleteOrder(Shared.get.ACTIVATION_TOKEN,"Bearer " + Shared.get.TOKEN, orderID);

    }


}

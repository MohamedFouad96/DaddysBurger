package tech.digitalcraft.daddysburger.Controller.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.digitalcraft.daddysburger.Model.Shared;

/**
 * Created by mohamedfouad on 10/12/18.
 */


public class ServiceGenerator {


    public static String BASE_URL = Shared.get.BASE_URL;

    static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());


    static Retrofit retrofit = builder.build();


    public static void changeBaseUrl(String newApiBaseUrl) {

        BASE_URL = newApiBaseUrl;

        builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL);


        retrofit = builder.build();


    }



    public static <S> S createService(Class<S> serviceClass)
    {

        return  retrofit.create(serviceClass);

    }

}

package tech.digitalcraft.daddysburger.Controller.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mohamedfouad on 10/12/18.
 */


public class ServiceGenerator {


    public static String BASE_URL = "http://bsnextdeveloping-001-site2.itempurl.com";

    static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());


    static Retrofit retrofit = builder.build();


    public static void changeBaseUrl(String newApiBaseUrl) {

        BASE_URL = newApiBaseUrl;

        builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL);

    }



    public static <S> S createService(Class<S> serviceClass)
    {

        return  retrofit.create(serviceClass);

    }

}

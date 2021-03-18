package tech.digitalcraft.daddysburger.Model;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import tech.digitalcraft.daddysburger.Controller.Interfaces.RefreshViewPager;
import tech.digitalcraft.daddysburger.Controller.SqlLite.DatabaseHelper;
import tech.digitalcraft.daddysburger.Model.APIs.Meal;
import tech.digitalcraft.daddysburger.Model.APIs.Menu;
import tech.digitalcraft.daddysburger.Model.APIs.OrderMeals;
import tech.digitalcraft.daddysburger.View.Activites.UserAuth.Startup;

import static android.content.Context.MODE_PRIVATE;

public enum Shared {

    get;

    // TODO: Change BASE_URL to remote URL from visual studio Conveyor extension

    public String CHECK_KEY = "check" ,
     ONE_TIME_CHECKER_KEY = "checkEmail" ,
    USERNAME_KEY = "username" ,
            ACTIVATION_TOKEN_KEY = "ac_token" ,
    TOKEN_KEY = "token" ,
            ACTIVATION_TOKEN ="" ,
    USER_TYPE = "userType",
    USERNAME = "" ,
    TOKEN = "",
    BASE_URL="http://192.168.1.7:45455",
            PHONE = "" ,
                    PASSWORD = "";


    public List<Menu> mMenu = new ArrayList<>();
    public List<Meal> mCheckoutMeals = new ArrayList<>();
    public List<OrderMeals> mOrderMeals = new ArrayList<>();
    public Meal mMeal;
    public RefreshViewPager mRefreshViewPager;
    private DatabaseHelper databaseHelper;



    public void Logout(Activity mContext)
    {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences("File", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();

        mEditor.putBoolean(CHECK_KEY, false);
        mEditor.putString(TOKEN_KEY,"");
        mEditor.putString(USERNAME_KEY,"");

        mEditor.commit();

        databaseHelper = new DatabaseHelper(mContext);

        databaseHelper.checkout();

        Intent login = new Intent(mContext , Startup.class);
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(login);
        mContext.finish();
    }
}

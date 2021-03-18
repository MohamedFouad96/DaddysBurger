package tech.digitalcraft.daddysburger.View.Activites.Restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.digitalcraft.daddysburger.Controller.Interfaces.MealsController;
import tech.digitalcraft.daddysburger.Controller.Interfaces.ResturantOrdersController;
import tech.digitalcraft.daddysburger.Controller.RecycleView.MealsItemsAdapter;
import tech.digitalcraft.daddysburger.Controller.RecycleView.OrdersAdapter;
import tech.digitalcraft.daddysburger.Controller.Retrofit.RetrofitClientAdapter;
import tech.digitalcraft.daddysburger.Model.APIs.Order;
import tech.digitalcraft.daddysburger.Model.Shared;
import tech.digitalcraft.daddysburger.R;
import tech.digitalcraft.daddysburger.View.Activites.MainActivity;
import tech.digitalcraft.daddysburger.View.Activites.Ordering.Ordering;

public class RestaurantOrders extends AppCompatActivity implements ResturantOrdersController {

    private ProgressDialog mProgressDialog;

    TextView alert;
    RecyclerView ordersRecyclerView;
    OrdersAdapter mOrdersAdapter;
    RecyclerView.LayoutManager mOrdersLayoutManager;
    ProgressBar progressBar;
    MenuItem qr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_orders);

        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        mProgressDialog = new ProgressDialog(this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);


        mProgressDialog.setMessage("جاري تسجيل الخروج...");
        mProgressDialog.setCancelable(false);

        setTitle("الطلبات");


        ordersRecyclerView = findViewById(R.id.orders);
        alert = findViewById(R.id.alert);
        progressBar = findViewById(R.id.progressBar2);
        mOrdersLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        ordersRecyclerView.setLayoutManager(mOrdersLayoutManager);

        ordersRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        RetrofitClientAdapter.getOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {

                switch (response.code())
                {
                    case 200:
                    {

                        if (response.body().size() > 0)
                        {
                            progressBar.setVisibility(View.GONE);
                            ordersRecyclerView.setVisibility(View.VISIBLE);
                            mOrdersAdapter = new OrdersAdapter(response.body() , RestaurantOrders.this , RestaurantOrders.this);
                            ordersRecyclerView.setAdapter(mOrdersAdapter);

                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            alert.setVisibility(View.VISIBLE);

                        }

                    }
                    break;
                    default:
                    {

                        System.out.println("code: " + response.code());
                        Toast.makeText(RestaurantOrders.this, "صلاحية الجلسة انتهت, اعد تسجيل الدخول", Toast.LENGTH_LONG).show();
                    //    Shared.get.Logout(RestaurantOrders.this);
                    }
                    break;
                }

            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(RestaurantOrders.this, "يوجد مشكلة بشبكة الانترنت", Toast.LENGTH_SHORT).show();

            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
            {

                mProgressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.dismiss();
                        Shared.get.Logout(RestaurantOrders.this);

                    }
                }, 3000);
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void refreshAlertMessage() {

        alert.setVisibility(View.VISIBLE);

    }
}
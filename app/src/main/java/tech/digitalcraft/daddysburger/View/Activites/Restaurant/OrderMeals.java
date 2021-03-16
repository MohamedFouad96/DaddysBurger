package tech.digitalcraft.daddysburger.View.Activites.Restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import tech.digitalcraft.daddysburger.Controller.RecycleView.OrdersMealsAdapter;
import tech.digitalcraft.daddysburger.Model.Shared;
import tech.digitalcraft.daddysburger.R;

public class OrderMeals extends AppCompatActivity {


    RecyclerView mOrderMealsrecyclerView;
    RecyclerView.LayoutManager mOrdersLayoutManager;
    OrdersMealsAdapter mOrdersMealsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_meals);

        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        setTitle(getIntent().getExtras().getString("name"));

        mOrderMealsrecyclerView = findViewById(R.id.meals);
        mOrdersLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        mOrderMealsrecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        mOrdersMealsAdapter = new OrdersMealsAdapter(Shared.get.mOrderMeals,this);

        mOrderMealsrecyclerView.setLayoutManager(mOrdersLayoutManager);
        mOrderMealsrecyclerView.setAdapter(mOrdersMealsAdapter);

    }
}
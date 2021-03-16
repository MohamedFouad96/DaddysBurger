package tech.digitalcraft.daddysburger.View.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import tech.digitalcraft.daddysburger.Controller.Interfaces.MealsController;
import tech.digitalcraft.daddysburger.Controller.RecycleView.MealsItemsAdapter;
import tech.digitalcraft.daddysburger.Controller.SqlLite.DatabaseHelper;
import tech.digitalcraft.daddysburger.Model.APIs.Meal;
import tech.digitalcraft.daddysburger.Model.Shared;
import tech.digitalcraft.daddysburger.R;
import tech.digitalcraft.daddysburger.View.Activites.Ordering.Ordering;


public class OrdersFragment extends Fragment implements MealsController {



    TextView totalPrice , alertMessage;
    LinearLayout layout;
    Button checkout;

    RecyclerView ordersRecyclerView;
    MealsItemsAdapter mMealsItemsAdapter;
    RecyclerView.LayoutManager mMealsLayoutManager;

    DatabaseHelper mDatabaseHelper;
    Cursor mOrdersCursor;

    List<Meal> mMeals;

    float TOTOAL_PRICE = 0;

    public static OrdersFragment newInstance() {
        OrdersFragment fragment = new OrdersFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_orders, container, false);

        TOTOAL_PRICE = 0;

        totalPrice = root.findViewById(R.id.total);
        alertMessage = root.findViewById(R.id.alert);
        layout = root.findViewById(R.id.layout);
        checkout = root.findViewById(R.id.checkout);
        ordersRecyclerView = root.findViewById(R.id.orders);

        mMealsLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        mDatabaseHelper = new DatabaseHelper(getActivity());
        mMeals = new ArrayList<>();

        mOrdersCursor = mDatabaseHelper.getItems();

        if (mOrdersCursor.getCount() > 0) {
            layout.setVisibility(View.VISIBLE);
            ordersRecyclerView.setVisibility(View.VISIBLE);
            alertMessage.setVisibility(View.GONE);
            checkout.setVisibility(View.VISIBLE);

            while (mOrdersCursor.moveToNext()) {
                mMeals.add(new Meal(mOrdersCursor.getInt(0),
                        mOrdersCursor.getInt(5),
                        mOrdersCursor.getString(1),
                        mOrdersCursor.getString(4),
                        "" + mOrdersCursor.getFloat(3),
                        "" + mOrdersCursor.getInt(2),
                        mOrdersCursor.getInt(6) ));

                System.out.println("Item: " + mOrdersCursor.getInt(3));

                TOTOAL_PRICE += (mOrdersCursor.getFloat(3)* mOrdersCursor.getInt(6));
            }
            Shared.get.mCheckoutMeals = mMeals;

            totalPrice.setText(TOTOAL_PRICE + " جنية");

            mMealsItemsAdapter = new MealsItemsAdapter(mMeals, getActivity(), 1 , this);

            ordersRecyclerView.setLayoutManager(mMealsLayoutManager);
            ordersRecyclerView.setAdapter(mMealsItemsAdapter);

        }
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity() , Ordering.class);
                i.putExtra("total", TOTOAL_PRICE);
                startActivity(i);
            }
        });

        return root;
    }

    @Override
    public void refreshTotalPrice() {

        if (mMeals.size() > 0) {
            for (int i = 0; i < mMeals.size(); i++) {

                TOTOAL_PRICE += Float.parseFloat(mMeals.get(i).getPrice())*mMeals.get(i).getQty();

                totalPrice.setText(TOTOAL_PRICE + " جنية");

            }
        }
        else
        {
            layout.setVisibility(View.GONE);
            ordersRecyclerView.setVisibility(View.GONE);
            alertMessage.setVisibility(View.VISIBLE);
            checkout.setVisibility(View.GONE);
        }
        TOTOAL_PRICE = 0;

    }
}
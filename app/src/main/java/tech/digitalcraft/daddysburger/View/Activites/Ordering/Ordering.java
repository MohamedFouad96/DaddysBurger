package tech.digitalcraft.daddysburger.View.Activites.Ordering;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.digitalcraft.daddysburger.Controller.Retrofit.RetrofitClientAdapter;
import tech.digitalcraft.daddysburger.Controller.SqlLite.DatabaseHelper;
import tech.digitalcraft.daddysburger.Model.APIs.CreateOrderResponse;
import tech.digitalcraft.daddysburger.Model.APIs.Meals;
import tech.digitalcraft.daddysburger.Model.Shared;
import tech.digitalcraft.daddysburger.R;

public class Ordering extends AppCompatActivity {

    TextView totalPrice, tableNumber;
    Button inRest,outRest,cash,card,checkout;
    ImageButton incTable,decTable;
    EditText address,phone;

    LinearLayout layout , layout1;
    ProgressDialog mProgressDialog;

    List<Meals> mMeals;

    int TABLE_NUM = 1 , ORDER_TYPE = 1 , PAYMENT_METHOD = 1;
    float TOTAL_PRICE = 0;

    private DatabaseHelper databaseHelper;


    String ADDRESS ="TABLE", PHONE = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);
        setTitle("إستكمال الطلب");


        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        mMeals = new ArrayList<>();
        totalPrice = findViewById(R.id.total);
        tableNumber = findViewById(R.id.num);
        inRest = findViewById(R.id.inRest);
        outRest = findViewById(R.id.delv);
        cash = findViewById(R.id.cash);
        card = findViewById(R.id.card);
        checkout = findViewById(R.id.checkout);
        incTable = findViewById(R.id.inc);
        decTable = findViewById(R.id.dec);
        address = findViewById(R.id.qty);
        phone = findViewById(R.id.phone);
        layout = findViewById(R.id.layout);
        layout1 = findViewById(R.id.layout1);

        TOTAL_PRICE = getIntent().getExtras().getFloat("total");
        totalPrice.setText(TOTAL_PRICE + " جنية");

        mProgressDialog = new ProgressDialog(this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);

        mProgressDialog.setMessage("جاري الطلب...");
        mProgressDialog.setCancelable(false);
        databaseHelper = new DatabaseHelper(this);


        for (int i = 0; i < Shared.get.mCheckoutMeals.size(); i++) {

            mMeals.add(new Meals(Shared.get.mCheckoutMeals.get(i).getId() ,Shared.get.mCheckoutMeals.get(i).getQty() ,""));
        }
        inRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ORDER_TYPE = 1;
                layout.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);
                inRest.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                outRest.setBackgroundTintList(getResources().getColorStateList(R.color.yellow_200));

            }
        });


        outRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ADDRESS = "TABLE";
                PHONE = "";
                address.setText("");
                phone.setText("");
                ORDER_TYPE = 2;
                layout.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.GONE);
                inRest.setBackgroundTintList(getResources().getColorStateList(R.color.yellow_200));
                outRest.setBackgroundTintList(getResources().getColorStateList(R.color.green));
            }
        });


        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TABLE_NUM = 1;
                tableNumber.setText("1");
                PAYMENT_METHOD = 1;
                card.setBackgroundTintList(getResources().getColorStateList(R.color.yellow_200));
                cash.setBackgroundTintList(getResources().getColorStateList(R.color.green));
            }
        });


        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PAYMENT_METHOD = 2;
                card.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                cash.setBackgroundTintList(getResources().getColorStateList(R.color.yellow_200));
            }
        });


        incTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TABLE_NUM < 15)
                    ++TABLE_NUM;

                tableNumber.setText(TABLE_NUM+"");
            }
        });

        decTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TABLE_NUM == 1)
                {
                    tableNumber.setText(TABLE_NUM+"");

                }
                else
                {
                    --TABLE_NUM;
                    tableNumber.setText(TABLE_NUM+"");

                }
            }
        });


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

mProgressDialog.show();
                ADDRESS = address.getText().toString();
                PHONE = address.getText().toString();

                if (ORDER_TYPE == 2)
                {
                    if (ADDRESS.isEmpty() || PHONE.isEmpty())
                        Toast.makeText(Ordering.this, "تأكد من إدخال جميع البيانات المطلوبة", Toast.LENGTH_SHORT).show();
                    {

                        createOrder();

                    }
                }
                else
                {
                    createOrder();

                }

            }

        });

    }


    public void createOrder()
    {
        RetrofitClientAdapter.createOrder(ORDER_TYPE,TABLE_NUM,PAYMENT_METHOD,ADDRESS, mMeals).enqueue(new Callback<CreateOrderResponse>() {
            @Override
            public void onResponse(Call<CreateOrderResponse> call, Response<CreateOrderResponse> response) {

                mProgressDialog.dismiss();

                switch (response.code())
                {
                    case 200:
                    {

                        if (response.body().getStatus())
                        {
                            if (PAYMENT_METHOD == 1)
                            {
                                Toast.makeText(Ordering.this, "تم الطلب بنجاح...", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else
                            {
                                Intent i = new Intent(Ordering.this , Payment.class);
                                i.putExtra("total",TOTAL_PRICE);
                                startActivity(i);
                                finish();
                            }

                            databaseHelper.checkout();
                            Shared.get.mRefreshViewPager.refresh();
                        }
                        else
                        {
                            Toast.makeText(Ordering.this, "هذه الطاولة غير متاحة الان", Toast.LENGTH_SHORT).show();

                        }


                    }
                    break;
                    default:
                    {
                        try {
                            Log.d("err", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(Ordering.this, "يوجد مشكلة قم بالمحاولة في وقت لاحق", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<CreateOrderResponse> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(Ordering.this, "يوجد مشكلة بشبكة الانترنت", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
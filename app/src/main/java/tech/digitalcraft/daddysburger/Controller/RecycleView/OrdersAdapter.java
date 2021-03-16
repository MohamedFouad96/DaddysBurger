package tech.digitalcraft.daddysburger.Controller.RecycleView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.digitalcraft.daddysburger.Controller.Interfaces.ResturantOrdersController;
import tech.digitalcraft.daddysburger.Controller.Retrofit.RetrofitClientAdapter;
import tech.digitalcraft.daddysburger.Model.APIs.Order;
import tech.digitalcraft.daddysburger.Model.Shared;
import tech.digitalcraft.daddysburger.R;
import tech.digitalcraft.daddysburger.View.Activites.Restaurant.OrderMeals;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.viewHolder> {


    List<Order> mOrders;
    Context mContext;
    View view;
    ProgressDialog mProgressDialog;
    ResturantOrdersController resturantOrdersController;


    public OrdersAdapter(List<Order> orders, Context mContext , ResturantOrdersController resturantOrdersController) {
        this.mOrders = orders;
        this.mContext = mContext;
        this.resturantOrdersController = resturantOrdersController;

        mProgressDialog = new ProgressDialog(mContext,R.style.Theme_AppCompat_DayNight_Dialog_Alert);

        mProgressDialog.setMessage("جاري التحديث...");
        mProgressDialog.setCancelable(false);
    }





    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_orders_item , parent , false);

        return new OrdersAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        holder.clientName.setText(mOrders.get(position).getClientName());

        if (mOrders.get(position).getOrderTypeId() == 1)
            holder.address.setText("طاولة رقم " +mOrders.get(position).getTableId());
        else
        holder.address.setText(mOrders.get(position).getAddressDetails());

        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgressDialog.show();


                RetrofitClientAdapter.compelteOrder(mOrders.get(position).getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        mProgressDialog.dismiss();


                        switch (response.code())
                        {
                            case 200:
                            {
                                Toast.makeText(mContext, "تمت العملية", Toast.LENGTH_SHORT).show();
                                mOrders.remove(position);
                                notifyDataSetChanged();

                                if (mOrders.size() == 0)
                                {
                                    resturantOrdersController.refreshAlertMessage();
                                }
                            }
                            break;
                            default:
                            {
                                try {
                                    System.out.println("err: " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(mContext, "يوجد مشكلة قم بالمحاولة في وقت لاحق", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        mProgressDialog.dismiss();
                        Toast.makeText(mContext, "يوجد مشكلة بشبكة الانترنت", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Shared.get.mOrderMeals = mOrders.get(position).getMeals();
                Intent i = new Intent(mContext , OrderMeals.class);
                i.putExtra("name", mOrders.get(position).getClientName());
                mContext.startActivity(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mOrders.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder
    {

        TextView clientName , address, phone;
        Button complete;


        public viewHolder(@NonNull View itemView) {
            super(itemView);


            clientName = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.qty);
            phone = itemView.findViewById(R.id.phone);
            complete = itemView.findViewById(R.id.delv);


        }
    }
}



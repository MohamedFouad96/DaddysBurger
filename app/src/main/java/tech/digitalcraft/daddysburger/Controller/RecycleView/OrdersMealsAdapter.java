package tech.digitalcraft.daddysburger.Controller.RecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.digitalcraft.daddysburger.Controller.Retrofit.RetrofitClientAdapter;
import tech.digitalcraft.daddysburger.Model.APIs.Meal;
import tech.digitalcraft.daddysburger.Model.APIs.OrderMeals;
import tech.digitalcraft.daddysburger.R;

public class OrdersMealsAdapter extends RecyclerView.Adapter<OrdersMealsAdapter.viewHolder> {


    List<OrderMeals> mOrders;
    Context mContext;
    View view;


    public OrdersMealsAdapter(List<OrderMeals> orders, Context mContext) {
        this.mOrders = orders;
        this.mContext = mContext;



    }





    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_orders_meals_item , parent , false);

        return new OrdersMealsAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        holder.itemTitle.setText(mOrders.get(position).getTitle());
        holder.qty.setText(" عدد الوجبات: "  + mOrders.get(position).getQuantity());


    }



    @Override
    public int getItemCount() {
        return mOrders.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder
    {

        TextView itemTitle , qty;


        public viewHolder(@NonNull View itemView) {
            super(itemView);


            itemTitle = itemView.findViewById(R.id.title);
            qty = itemView.findViewById(R.id.qty);

        }
    }
}



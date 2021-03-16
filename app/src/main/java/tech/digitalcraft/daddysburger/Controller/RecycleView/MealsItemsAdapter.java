package tech.digitalcraft.daddysburger.Controller.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.digitalcraft.daddysburger.Controller.Helpers;
import tech.digitalcraft.daddysburger.Controller.Interfaces.MealsController;
import tech.digitalcraft.daddysburger.Controller.SqlLite.DatabaseHelper;
import tech.digitalcraft.daddysburger.Model.APIs.Meal;
import tech.digitalcraft.daddysburger.Model.Shared;
import tech.digitalcraft.daddysburger.R;
import tech.digitalcraft.daddysburger.View.Activites.Menu.MealDetails;

public class MealsItemsAdapter extends RecyclerView.Adapter<MealsItemsAdapter.viewHolder> {


    List<Meal> mMeals;
    Context mContext;
    View view;
    int img, TYPE ,QTY = 1;
    DatabaseHelper mDatabaseHelper;

    MealsController mMealsController;


    public MealsItemsAdapter(List<Meal> mMeals, Context mContext , int type) {
        this.mMeals = mMeals;
        this.mContext = mContext;
        this.TYPE = type;
        mDatabaseHelper = new DatabaseHelper(mContext);

    }
    public MealsItemsAdapter(List<Meal> mMeals, Context mContext , int type, MealsController mealsController) {
        this.mMeals = mMeals;
        this.mContext = mContext;
        this.TYPE = type;
        mDatabaseHelper = new DatabaseHelper(mContext);
        mMealsController = mealsController;

    }




    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_item , parent , false);

        return new MealsItemsAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.itemName.setText(mMeals.get(position).getTitle());
        holder.itemPrice.setText(mMeals.get(position).getPrice() + " جنية ");



        String imgID;

        if(TYPE == 1)
        {
            img = Integer.parseInt(mMeals.get(position).getImageUrl());
            holder.quantity.setText(mMeals.get(position).getQty()+"");
        }
        else
        {
//            String [] arr = mMeals.get(position).getImageUrl().split("es/");
//            imgID = arr[1];

            imgID = mMeals.get(position).getImageUrl();

            img = Helpers.getImageIdByString(imgID);

        }




        holder.itemImg.setImageResource(img);

        if (TYPE == 0)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Shared.get.mMeal = mMeals.get(position);

                    Intent i = new Intent(mContext , MealDetails.class);
                    i.putExtra("img",  img);
                    mContext.startActivity(i);


                }
            });

        }


        holder.incQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (QTY < 12)
                    ++QTY;

                holder.quantity.setText(QTY+"");


            }
        });

        holder.decQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (QTY == 1)
                {
                    holder.quantity.setText(QTY+"");

                }
                else
                {
                    --QTY;
                    holder.quantity.setText(QTY+"");

                }


            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mDatabaseHelper.deleteItem(mMeals.get(position).getId()))
                {

                    Toast.makeText(mContext, " تم حذف الطلب..", Toast.LENGTH_LONG).show();

                    mMeals.remove(position);
                    notifyDataSetChanged();

                    mMealsController.refreshTotalPrice();

                }
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDatabaseHelper.insertItems(mMeals.get(position).getId(),
                        img,
                        mMeals.get(position).getTitle(),
                        mMeals.get(position).getDescription(),
                        Float.parseFloat(mMeals.get(position).getPrice()),
                        mMeals.get(position).getCategoryId() ,
                        QTY))
                {

                    Shared.get.mRefreshViewPager.refresh();
                    Toast.makeText(mContext, "تم إضافة الوجبة الي الطلبات الخاصة بك", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(mContext, "يوجد مشكلة بإضافة هذا المنتج", Toast.LENGTH_LONG).show();

            }
        });
    }



    @Override
    public int getItemCount() {
        return mMeals.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder
    {

        ImageView itemImg;
        TextView itemName,itemPrice,quantity;
        ImageButton delete,incQty,decQty;
        Button add;
        LinearLayout layout;


        public viewHolder(@NonNull View itemView) {
            super(itemView);

            itemImg = itemView.findViewById(R.id.img);
            itemName = itemView.findViewById(R.id.title);
            itemPrice = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.num);
            delete = itemView.findViewById(R.id.del);
            incQty = itemView.findViewById(R.id.inc);
            decQty = itemView.findViewById(R.id.dec);
            add = itemView.findViewById(R.id.add);
            layout = itemView.findViewById(R.id.layout);


            if (TYPE == 1)
            {
                layout.setVisibility(View.VISIBLE);
                add.setVisibility(View.GONE);
            }

        }
    }
}



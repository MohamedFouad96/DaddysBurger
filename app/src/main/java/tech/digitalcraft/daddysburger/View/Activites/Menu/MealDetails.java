package tech.digitalcraft.daddysburger.View.Activites.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tech.digitalcraft.daddysburger.Controller.SqlLite.DatabaseHelper;
import tech.digitalcraft.daddysburger.Model.Shared;
import tech.digitalcraft.daddysburger.R;

public class MealDetails extends AppCompatActivity {


    ImageView itemImage;
    TextView itemName , itemPrice , itemDesc, itemQty;
    ImageButton incQty, decQty;
    Button add;

    int QTY = 1;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        itemImage = findViewById(R.id.img);
        itemName = findViewById(R.id.title);
        itemPrice = findViewById(R.id.price);
        itemDesc = findViewById(R.id.desc);
        itemQty = findViewById(R.id.num);
        incQty = findViewById(R.id.inc);
        decQty = findViewById(R.id.dec);
        add = findViewById(R.id.add);

        databaseHelper = new DatabaseHelper(this);
        itemImage.setImageResource(getIntent().getIntExtra("img" , R.drawable.beef_1));
        itemName.setText(Shared.get.mMeal.getTitle());
        itemPrice.setText(Shared.get.mMeal.getPrice() + " جنية");
        itemDesc.setText(Shared.get.mMeal.getDescription());


        incQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (QTY < 12)
                ++QTY;

                itemQty.setText(QTY+"");
            }
        });

        decQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (QTY == 1)
                {
                    itemQty.setText(QTY+"");

                }
                else
                {
                    --QTY;
                    itemQty.setText(QTY+"");

                }
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(databaseHelper.insertItems(Shared.get.mMeal.getId(),
                        getIntent().getIntExtra("img" , R.drawable.beef_1),
                        Shared.get.mMeal.getTitle(),
                        Shared.get.mMeal.getDescription(),
                        Float.parseFloat(Shared.get.mMeal.getPrice()),
                        Shared.get.mMeal.getCategoryId() ,
                        QTY))
                {
                    finish();
                    Shared.get.mRefreshViewPager.refresh();
                    Toast.makeText(MealDetails.this, "تم إضافة الوجبة الي الطلبات الخاصة بك", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(MealDetails.this, "يوجد مشكلة بإضافة هذا المنتج", Toast.LENGTH_LONG).show();


            }
        });


    }
}
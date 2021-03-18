package tech.digitalcraft.daddysburger.View.Activites.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import tech.digitalcraft.daddysburger.Controller.RecycleView.MealsItemsAdapter;
import tech.digitalcraft.daddysburger.Model.Shared;
import tech.digitalcraft.daddysburger.R;

public class Meals extends AppCompatActivity {


    RecyclerView mealsReyclerView;
    MealsItemsAdapter mMealsRecyclerviewAdapter;
    RecyclerView.LayoutManager mMealsLayoutManager;


    private int TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        TYPE = getIntent().getExtras().getInt("type");

        mealsReyclerView = findViewById(R.id.meals);
        mMealsLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);


        if (Shared.get.mMenu.size() > 0) {

            switch (TYPE) {
                case 0: {
                    setTitle("بيف برجر");
                    mMealsRecyclerviewAdapter = new MealsItemsAdapter(Shared.get.mMenu.get(0).getMeals(), this, 0);

                }
                break;
                case 1: {
                    setTitle("ساندوتش فراخ");
                    mMealsRecyclerviewAdapter = new MealsItemsAdapter(Shared.get.mMenu.get(1).getMeals(), this, 0);
                }
                break;
                case 2: {
                    setTitle("هوت دوج");
                    mMealsRecyclerviewAdapter = new MealsItemsAdapter(Shared.get.mMenu.get(2).getMeals(), this, 0);
                }
                break;
                case 3: {
                    setTitle("اطباق جانبيه");
                    mMealsRecyclerviewAdapter = new MealsItemsAdapter(Shared.get.mMenu.get(3).getMeals(), this, 0);
                }
                break;
            }

            mealsReyclerView.setLayoutManager(mMealsLayoutManager);
            mealsReyclerView.setAdapter(mMealsRecyclerviewAdapter);


        }

    }
}
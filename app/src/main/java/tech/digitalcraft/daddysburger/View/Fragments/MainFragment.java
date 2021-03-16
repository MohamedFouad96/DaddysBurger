package tech.digitalcraft.daddysburger.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.digitalcraft.daddysburger.Controller.Retrofit.RetrofitClientAdapter;
import tech.digitalcraft.daddysburger.Model.APIs.Menu;
import tech.digitalcraft.daddysburger.Model.Shared;
import tech.digitalcraft.daddysburger.R;
import tech.digitalcraft.daddysburger.View.Activites.Menu.Meals;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private LinearLayout layout;
    private ImageButton Beef, Chicken ,HotDog , SideDishes;
    private ProgressBar loading;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        layout = root.findViewById(R.id.layout);
        Beef = root.findViewById(R.id.beef);
        Chicken = root.findViewById(R.id.chicken);
        HotDog = root.findViewById(R.id.hotdog);
        SideDishes = root.findViewById(R.id.sidedishes);
        loading = root.findViewById(R.id.progressBar);

        menu ();


        Intent i = new Intent(getContext() , Meals.class);

        Beef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i.putExtra("type",0);
                startActivity(i);
            }
        });

        Chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("type",1);
                startActivity(i);
            }
        });

        HotDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("type",2);
                startActivity(i);
            }
        });

        SideDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("type",3);
                startActivity(i);
            }
        });



        return root;
    }


    public void menu () {



        RetrofitClientAdapter.getMenu().enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {

                switch (response.code())
                {
                    case 200:
                    {

                        layout.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.INVISIBLE);

                        Shared.get.mMenu = response.body();


                    }
                    break;
                    default:
                    {
                        Toast.makeText(getContext(), "صلاحية الجلسة انتهت, اعد تسجيل الدخول", Toast.LENGTH_LONG).show();
                        Shared.get.Logout(getActivity());
                    }
                    break;
                }
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {

                Toast.makeText(getContext(), "يوجد مشكلة بشبكة الانترنت", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
package tech.digitalcraft.daddysburger.Controller;

import tech.digitalcraft.daddysburger.R;

public class Helpers {

    static public int getImageIdByString(String imgID)
    {

        int img =  R.drawable.beef_1;
        switch (imgID)
        {
            case "beef_1":
                img = R.drawable.beef_1;

                break;
            case "beef_2":
                img = R.drawable.beef_2;
                break;
            case "beef_3":
                img = R.drawable.beef_3;
                break;
            case "beef_4":
                img = R.drawable.beef_4;
                break;
            case "beef_5":
                img = R.drawable.beef_5;
                break;
            case "beef_6":
                img = R.drawable.beef_6;
                break;
            case "chicken_1":
                img = R.drawable.chicken_1;
                break;
            case "chicken_2":
                img = R.drawable.chicken_2;
                break;
            case "chicken_3":
                img = R.drawable.chicken_3;
                break;
            case "hotdog_1":
                img = R.drawable.hotdog_1;
                break;
            case "hotdog_2":
                img = R.drawable.hotdog_2;
                break;
            case "side_1":
                img = R.drawable.side_1;
                break;
            case "side_2":
                img = R.drawable.side_2;
                break;
            case "side_3":
                img = R.drawable.side_3;
                break;
            case "side_4":
                img = R.drawable.side_4;
                break;

        }

        return img;
    }
}

package munchies.example.com.munchies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by shwet on 11/25/2015.
 */
public class restaurantMain extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_main);
    }

    public void goBackToRestaurantMain(View v) {
        Intent gobacktomain = new Intent(getBaseContext(), MapsActivity.class);
        startActivity(gobacktomain);
    }
}

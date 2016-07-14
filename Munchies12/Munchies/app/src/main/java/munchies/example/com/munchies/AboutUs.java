package munchies.example.com.munchies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by manan on 11/19/15.
 */
public class AboutUs extends AppCompatActivity {

    private ImageView Icon;
    //private TextView AppName;


    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);



        Icon = (ImageView)findViewById(R.id.appIcon);
        Icon.setImageResource(R.mipmap.ic_launcher1);

      //  AppName = (TextView)findViewById(R.id.appName);



    }
public void feedback(View v){
    Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
    intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
    intent.setData(Uri.parse("mailto:Munchies@gmail.com")); // or just "mailto:" for blank
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
    startActivity(intent);
}
    public void loveme(View v){
        Toast.makeText(getApplicationContext(), "Please Rate Us on App-store",
                Toast.LENGTH_LONG).show();
    }
    }

package munchies.example.com.munchies;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;

import common.logger.Log;

/**
 * Created by shwet on 11/29/2015.
 */
public class RestoDishes extends Activity {

    String rest_name;
    String yoo;
    Double kk;
    String kkl;
    int count=0;
    String key = "Book1.csv";
    String key2 = "final.csv";
    private TransferUtility transferUtility;
    String csvFile ;
    BufferedReader br = null;
    String line = "";
    String[] dishes = new String[40];
    String[] rating = new String[40];
    String cvsSplitBy = ",";
    TextView rest_dish1,rest_dish2,rest_dish3,rest_rating1,rest_rating2,rest_rating3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_dishes);
        rest_name=getIntent().getExtras().getString("restaurant_name");

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        getApplicationContext(),
                        Constants.COGNITO_POOL_ID, // Identity Pool ID
                        Regions.US_EAST_1 // Region
                );
                AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
                TransferUtility transferUtility = new TransferUtility(s3, getApplicationContext());
                File file = new File(Environment.getExternalStorageDirectory().toString() + "/" + key);
                String s=Environment.getExternalStorageDirectory().toString() + "/" + key;
              //  Log.d("pathh", s);
                TransferObserver observer = transferUtility.download(Constants.BUCKET_NAME, key, file);
                File file2 = new File(Environment.getExternalStorageDirectory().toString() + "/" + key2);
                String s2=Environment.getExternalStorageDirectory().toString() + "/" + key2;
               // Log.d("pathhh", s2);
                TransferObserver observer2 = transferUtility.download(Constants.BUCKET_NAME, key2, file2);
                             try {
                    return "aa";
                } catch (Exception e) {
                    return "bb";
                }
            }
            protected void onPostExecute(String bb) {

                try {

                    File sdcard = Environment.getExternalStorageDirectory();
                    File file1 = new File(sdcard,"Book1.csv");
                    File file3 = new File(sdcard,"final.csv");
                    BufferedReader br = new BufferedReader(new FileReader(file1));
                    BufferedReader br2 = new BufferedReader(new FileReader(file3));
                    rest_dish1=(TextView)findViewById(R.id.dish1);
                    rest_dish2=(TextView)findViewById(R.id.dish2);
                    rest_dish3=(TextView)findViewById(R.id.dish3);
                    rest_rating1=(TextView)findViewById(R.id.rating1);
                    rest_rating2=(TextView)findViewById(R.id.rating2);
                    rest_rating3=(TextView)findViewById(R.id.rating3);

                    while ((line = br2.readLine()) != null) {

                        // use comma as separator
                        String[] country2 = line.split(cvsSplitBy);
                        Log.d("country",country2[6]);
                        if(country2[6].toString().equalsIgnoreCase(rest_name)) {
                            yoo = country2[0];
                            Log.d("rest_name",rest_name);
                        }
                    }
                    while ((line = br.readLine()) != null) {

                        // use comma as separator
                        String[] country = line.split(cvsSplitBy);
                        Log.d("country 0",country[0]);
                        Log.d("country 1",country[1]);
                        Log.d("country 2",country[2]);
                        if(country[0].toString().equalsIgnoreCase(yoo)) {
                            dishes[count]=country[1];
                            Log.d("zzzz",country[1]);
                            kk=Double.parseDouble(country[2]);
                            kk=Math.round(kk * 100.0) / 100.0*10;
                            Log.d("intttt",Double.toString(kk));
                            kkl=Double.toString(kk);
                        //    Log.d("kkl",kkl);
                            rating[count]=kkl;
                            count +=1;
                        }
                    }
                    if(count==0)
                    {
                        Toast.makeText(getApplicationContext(), "Sorry.No Ratings to Display", Toast.LENGTH_SHORT).show();
                    }

                    rest_dish1.setText(dishes[0]);
                    rest_dish2.setText(dishes[1]);
                    rest_dish3.setText(dishes[2]);
                    rest_rating1.setText(rating[0]);
                    rest_rating2.setText(rating[1]);
                    rest_rating3.setText(rating[2]);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.execute();

    }





}

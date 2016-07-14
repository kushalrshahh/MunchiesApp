package munchies.example.com.munchies;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YelpSearchListActivity extends ActionBarActivity {


	String searchTerm;
	Double Latitude,Longitude;
	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ArrayAdapter<String> mAdapter;
	private ActionBarDrawerToggle mDrawerToggle;
	private String mActivityTitle;
	String business_name;
	String urll;
	TextView restaurant_name,rating,phonenumber,categories;
	ImageView img;
	Bitmap bitmap;
	ProgressDialog pDialog;
	String image_urll;
	class Business {
		final String name;
		final String phoneno;
		final String categories;
		final String url;
		final String image_url;
		final String rating;


		public Business(String name, String url,String image_url,String rating,String phoneno,String categories) {
			this.name = name;
			this.url = url;
			this.image_url=image_url;
			this.rating=rating;
			this.phoneno=phoneno;
			this.categories=categories;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
       setContentView(R.layout.yelp_details);

		mDrawerList = (ListView)findViewById(R.id.navList);mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		mActivityTitle = getTitle().toString();

		addDrawerItems();
		setupDrawer();

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
        Intent intent = getIntent();
        searchTerm = intent.getExtras().getString("restaurant_name");
        Latitude = intent.getExtras().getDouble("Latitude");
		Longitude = intent.getExtras().getDouble("Longitude");
		img = (ImageView)findViewById(R.id.img);

        setProgressBarIndeterminateVisibility(true);
        new AsyncTask<Void, Void, Business>() {
        	@Override
        	protected Business doInBackground(Void... params) {
                String businesses = Yelp.getYelp(YelpSearchListActivity.this).search(searchTerm, Latitude, Longitude,1);
				Log.d("businessesssss",businesses);
				try {
                	return processJson(businesses);
                } catch (JSONException e) {
						return new Business("a","a","a","a","a","a");
                }
        	}
			protected void onPostExecute(Business bb) {
				//setTitle("Tacos Found");
				setProgressBarIndeterminateVisibility(false);
				//getListView().setAdapter(new ArrayAdapter<Business>(YelpSearchListActivity.this, android.R.layout.simple_list_item_1, businesses));
				Log.d("name",bb.name);
				Log.d("url",bb.url);
				urll=bb.url;
				Log.d("image_url",bb.image_url);
				Log.d("rating",bb.rating);

			//	img = (ImageView)findViewById(R.id.imageView);
				//InputStream is = (InputStream) new URL(urll,"").getContent();
				//	Drawable d = Drawable.createFromStream(is, "src name");
				//	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urll));
				//	startActivity(browserIntent);
				rating=(TextView)findViewById(R.id.rating);
				rating.setText(bb.rating);
				restaurant_name=(TextView)findViewById(R.id.restname);
				restaurant_name.setText(bb.name);
				image_urll=bb.image_url;
				phonenumber=(TextView)findViewById(R.id.phoneno);
				phonenumber.setText(bb.phoneno);
				categories=(TextView)findViewById(R.id.categories);
				categories.setText(bb.categories);
				new LoadImage().execute(image_urll);

			}

        }.execute();

    }


	private class LoadImage extends AsyncTask<String, String, Bitmap> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(YelpSearchListActivity.this);
			pDialog.setMessage("Loading Image ....");
			pDialog.show();

		}
		protected Bitmap doInBackground(String... args) {
			try {
				bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		protected void onPostExecute(Bitmap image) {

			if(image != null){
				img.setImageBitmap(image);
				pDialog.dismiss();

			}else{

				pDialog.dismiss();
				Toast.makeText(YelpSearchListActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

			}
		}
	}

	Business processJson(String jsonStuff) throws JSONException {
		JSONObject json = new JSONObject(jsonStuff);
		JSONArray businesses = json.getJSONArray("businesses");
		JSONObject business = businesses.getJSONObject(0);
		Log.d("Sushi",business.toString());
		Log.d("Sushibusid",business.optString("business_id"));
		Log.d("Sushiid",business.optString("id"));
		Business b = new  Business(business.optString("name"), business.optString("url"),business.optString("image_url"),business.optString("rating"),business.optString("phone"),business.optString("categories"));
		business_name=business.optString("name");
		Log.d("namzzzz",business.optString("name"));
		Log.d("addrzzz",business.optString("location"));

		return b;
	}
	public void View_Dishes(View view)
	{
		Intent in = new Intent(this,RestoDishes.class);
		in.putExtra("restaurant_name",business_name);
		startActivity(in);
	}
	public void Website_URL(View view)
	{
		Intent intent_dishes = new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse(urll));
		startActivity(intent_dishes);
	}

	private void addDrawerItems() {
		String[] osArray = { "Home", "Search Restaurant", "About Us" };
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
		mDrawerList.setAdapter(mAdapter);

		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch( position )
				{
					case 0:  Intent newActivity = new Intent(getApplicationContext(), MainActivityfb.class);
						startActivity(newActivity);
						break;
					case 1:  Intent newActivity1 = new Intent(getApplicationContext(), PlaceCompleteMainActivity.class);
						startActivity(newActivity1);
						break;
					case 2:  Intent newActivity2 = new Intent(getApplicationContext(), AboutUs.class);
						startActivity(newActivity2);
						break;

				}
			}
		});
	}

	private void setupDrawer() {
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setTitle("Munchies");
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getSupportActionBar().setTitle(mActivityTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};

		mDrawerToggle.setDrawerIndicatorEnabled(true);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		// Activate the navigation drawer toggle
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}

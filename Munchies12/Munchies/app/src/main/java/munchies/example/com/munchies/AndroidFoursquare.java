package munchies.example.com.munchies;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AndroidFoursquare extends ListActivity {
	String venuesList;

	// the foursquare client_id and the client_secret

	// ============== YOU SHOULD MAKE NEW KEYS ====================//
	final String CLIENT_ID = "QSZKN32R3GOKGKR3X43EUSH3ULK1AKSKEOUFT3OVIMU4AKGI";
	final String CLIENT_SECRET = "DOYMDIE0OHSLD4IXKCWQIYOBS404PDNFDT2JRTK3THNGAWDM";
	public String urll;

	// we will need to take the latitude and the logntitude from a certain point
	// this is the center of New York

	Double latitude ;
	Double longtitude;

	ArrayAdapter<String> myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activityfoursquaremain);
		Intent in = getIntent();
		latitude=in.getExtras().getDouble("Latitude");
		longtitude=in.getExtras().getDouble("Longitude");


		// start the AsyncTask that makes the call for the venus search.
		new fourquare().execute();
		//Intent intent_map = new Intent(android.content.Intent.ACTION_VIEW,
			//	Uri.parse(urll));
		//startActivity(intent_map);
	}

	private class fourquare extends AsyncTask<View, Void, String> {

		String temp;

		@Override
		protected String doInBackground(View... urls) {
			// make Call to the url
			temp = makeCall("https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815&ll="+ String.valueOf(latitude)+","+ String.valueOf(longtitude)+"&limit=1");
			return temp;
		}

		@Override
		protected void onPreExecute() {
			// we can start a progress bar here
		}

		@Override
		protected void onPostExecute(String result) {
			if (temp == null) {
				// we have an error to the call
				// we can also stop the progress bar
			} else {
				// all things went right

				// parseFoursquare venues search result

				venuesList = (String) parseFoursquare(temp);
				System.out.println(venuesList);
				if(urll!=null) {
					Intent intent_map = new Intent(android.content.Intent.ACTION_VIEW,
							Uri.parse(urll));
					startActivity(intent_map);
				}
				else Toast.makeText(AndroidFoursquare.this, "Menu does not exist.", Toast.LENGTH_SHORT).show();




				// set the results to the list
				// and show them in the xml
				//myAdapter = new ArrayAdapter<String>(AndroidFoursquare.this, R.layout.row_layout, R.id.listText, listTitle);
				//setListAdapter(myAdapter);
			}
		}
	}

	public static String makeCall(String url) {

		// string buffers the url
		StringBuffer buffer_string = new StringBuffer(url);
		String replyString = "";

		// instanciate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		// instanciate an HttpGet
		HttpGet httpget = new HttpGet(buffer_string.toString());

		try {
			// get the responce of the httpclient execution of the url
			HttpResponse response = httpclient.execute(httpget);
			InputStream is = response.getEntity().getContent();

			// buffer input stream the result
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(20);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			// the result as a string is ready for parsing
			replyString = new String(baf.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// trim the whitespaces
		return replyString.trim();
	}

	private  String parseFoursquare(final String response) {

		ArrayList<FoursquareVenue> temp = new ArrayList<FoursquareVenue>();
		try {

			// make an jsonObject in order to parse the response
			JSONObject jsonObject = new JSONObject(response);

			// make an jsonObject in order to parse the response
			if (jsonObject.has("response")) {
				if (jsonObject.getJSONObject("response").has("venues")) {

					JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("venues");

					for (int i = 0; i < 1; i++) {
						FoursquareVenue poi = new FoursquareVenue();
						if (jsonArray.getJSONObject(i).has("menu")) {
							poi.setName(jsonArray.getJSONObject(i).getString("name"));
							poi.setidd(jsonArray.getJSONObject(i).getString("id"));
							Log.d("caszahh", jsonArray.getJSONObject(i).getString("id").toString());
							System.out.println("iddddddd" + jsonArray.getJSONObject(i));
							System.out.println("menuuuuu" + jsonArray.getJSONObject(i).getJSONObject("menu").getString("url"));
							if(jsonArray.getJSONObject(i).getJSONObject("menu").has("url")) {
								urll = jsonArray.getJSONObject(i).getJSONObject("menu").getString("url");

							}
							else urll= "";



						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		//return temp;
		return urll;
	}

}

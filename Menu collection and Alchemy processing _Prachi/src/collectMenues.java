import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;

import javax.rmi.CORBA.Util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONException;
import org.json.JSONObject;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;




public class collectMenues {
	/*1  */ //final String CLIENT_ID = "QSZKN32R3GOKGKR3X43EUSH3ULK1AKSKEOUFT3OVIMU4AKGI";
		//final String CLIENT_SECRET = "DOYMDIE0OHSLD4IXKCWQIYOBS404PDNFDT2JRTK3THNGAWDM";
	
/*2	*/final String CLIENT_ID = "XIEQPW551D0RZOF0PB2OCYWBB22NZL3RAKGCQPJ0LIOVW2TM";
	final String CLIENT_SECRET = "KSXF2BCZCXAMQXXVZYAEBBD42RVZHUJLJEBOE3TTON0H4I4N";
	
/*3*/	//final String CLIENT_ID = "ZTKCO2QQ4ANE2HXKPDFNQW3TUIBKUGV5CZILQLSJK0S4FSKO";
		//final String CLIENT_SECRET = "W0RIHCXOADUD2EPZIBGY12NIA1KP4DW0F35C2BMWKV5OXIOU";
	
	public String get_venueId(String latlong){
		//String temp = httpGet("https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815&ll="+"40.3505527"+","+"-79.8868138"+"&limit=1"+"&section=food");
		String temp = httpGet("https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815&ll="+latlong+"&limit=1"+"&section=food");
		return temp;
	}
	public String get_latLong(String addr) throws JSONException{
		addr = addr.replaceAll(" ", "%20");
		String latlong = httpGet("https://maps.googleapis.com/maps/api/geocode/json?address="+addr+"&key=AIzaSyDYb5ji-8nGsgl0h-ER3UW_1I2sKk7eJts");
		JSONObject obj = new JSONObject(latlong);
	    Double lat =  obj.getJSONArray("results").getJSONObject(0).optJSONObject("geometry").getJSONObject("location").getDouble("lat");
		Double longi= obj.optJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng"); 
		String res = lat+","+longi;
		return res;
	}
	public String get_menues(String id){
		//String menues = httpGet("https://api.foursquare.com/v2/venues/"+id+"/menu?oauth_token=LGY1ITAR5MBHYZXKRRKVDFXKJAUT43ATZT1GBCFKB2NLMSYY&v=20140627");
		String menues = httpGet("https://api.foursquare.com/v2/venues/"+id+"/menu?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET +"&v=20140627");
		return menues;
	}
	public static void main(String args[]) throws JSONException, IOException{
		collectMenues  m = new collectMenues();
		String source = "/home/cloudera/Desktop/rest.csv";
        String destination="/home/cloudera/Desktop/rest1.csv";
		m.updateFile(source, destination);
	}
	public String convert_json(String menues) throws JSONException{
		JSONObject obj = new JSONObject(menues);
		JSONObject obj1 = new JSONObject(menues);
		StringBuffer menuList = null;
		String str = "";
		//System.out.println(obj.getJSONObject("response").getJSONObject("menu").getJSONObject("menus"));
		JSONObject object = obj.getJSONObject("response").getJSONObject("menu").getJSONObject("menus");
		int count = obj.getJSONObject("response").getJSONObject("menu").getJSONObject("menus").getInt("count");
		//System.out.println(count);
		for(int j=0;j<count;j++){
			//System.out.println(object.getJSONArray("items").optJSONObject(j).getJSONObject("entries").getInt("count"));
			int cnt_dish = object.getJSONArray("items").optJSONObject(j).getJSONObject("entries").getInt("count");
			JSONObject obj_dish = new JSONObject(object.getJSONArray("items").optJSONObject(j).getJSONObject("entries"));
		//	System.out.println(object.getJSONArray("items").optJSONObject(j).getJSONObject("entries").optJSONArray("items"));
			for(int k=0;k<cnt_dish;k++){
				//System.out.println(object.getJSONArray("items").optJSONObject(j).getJSONObject("entries").optJSONArray("items").getJSONObject(k).getJSONObject("entries").getInt("count"));
				int no_of_menus = object.getJSONArray("items").optJSONObject(j).getJSONObject("entries").optJSONArray("items").getJSONObject(k).getJSONObject("entries").getInt("count");
				for(int x=0;x<no_of_menus;x++){
					
					//System.out.println(object.getJSONArray("items").optJSONObject(j).getJSONObject("entries").optJSONArray("items").getJSONObject(k).getJSONObject("entries").optJSONArray("items").optJSONObject(x).getString("name"));
					String temp = object.getJSONArray("items").optJSONObject(j).getJSONObject("entries").optJSONArray("items").getJSONObject(k).getJSONObject("entries").optJSONArray("items").optJSONObject(x).getString("name");
					str = str+","+temp;
				
				}
				
				
				//System.out.println(obj_dish.getJSONArray("items"));
				//System.out.println(obj_dish.optJSONArray("items").optJSONObject(k).getJSONObject("entries").getInt("count"));
			}
		}
			return str;
		
	}
	public void updateFile(String source, String destination) throws IOException, JSONException{
		 CSVReader reader = new CSVReader(new FileReader(source));
	        CSVWriter writer = new CSVWriter(new FileWriter(destination), ',');
	        String[] entries = null;
	        collectMenues m = new collectMenues();
	        
	        while ((entries = reader.readNext()) != null) {
	        	int i =0;
	             String []temp = new String[13];
	             String result="";
	        	for(i=0;i< entries.length;i++){
	        		temp[i] = entries[i];	  
	        		String addr = entries[3];
	        		String latlong = m.get_latLong(addr);
	        		String venueId = m.get_venueId(latlong);
	        		JSONObject obj = new JSONObject(venueId);
	        		String id = obj.getJSONObject("response").getJSONArray("venues").getJSONObject(0).getString("id");
	        		String menues = m.get_menues(id);
	        		result = m.convert_json(menues);
	        	}
	        	
	        	temp[i] = result; 
	        	System.out.println(result);
	        	writer.writeNext(temp);
	        	
	        	/*for(int j =0;j<temp.length;j++){
	        		//System.out.print(temp[j]);
	        	}*/
	        	
	        	//System.out.println("Size of entries:"+" "+entries.length+" "+"Size of temp: "+ " "+temp.length);
	        }
	        writer.close();
		
	}
	public static String httpGet(String urlStr){
		  URL url;
		  StringBuilder sb = new StringBuilder();
		  String line;
		  try {
			  url = new URL(urlStr);
			  HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			 if (conn.getResponseCode() != 200) {
				 throw new IOException(conn.getResponseMessage());
			}

			// Buffer the result into a string
			BufferedReader rd = new BufferedReader(
			new InputStreamReader(conn.getInputStream()));
			
			while ((line = rd.readLine()) != null) {
				    sb.append(line);
			}
		    rd.close();
	   	    conn.disconnect();				  
		  } catch (MalformedURLException e) {
			e.printStackTrace();
		  	} 
		  	catch (IOException e) {
			e.printStackTrace();
		  	}
		  return sb.toString();
		}
}

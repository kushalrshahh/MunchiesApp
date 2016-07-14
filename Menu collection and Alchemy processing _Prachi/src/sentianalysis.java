


import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class sentianalysis {
	public static String httpPost(URI uri, String paramVal)  {
		  URL url;
		  StringBuilder sb = new StringBuilder();
		try {
				//url = new URL(uri);
				HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
				 conn.setRequestMethod("POST");
				 conn.setDoOutput(true);
				 conn.setDoInput(true);
				 conn.setUseCaches(false);
				 conn.setAllowUserInteraction(false);
				 conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

				  // Create the form content
				 if (paramVal != null){
				  OutputStream out = conn.getOutputStream();
				  Writer writer = new OutputStreamWriter(out);
				   writer.write(paramVal);
				   writer.close();
				  out.close();
				 }

				  if (conn.getResponseCode() != 200) {
				    throw new IOException(conn.getResponseMessage());
				  }

				  // Buffer the result into a string
				  BufferedReader rd = new BufferedReader(
				      new InputStreamReader(conn.getInputStream()));
				  
				  String line;
				  while ((line = rd.readLine()) != null) {
				    sb.append(line);
				  }
				  rd.close();
				  conn.disconnect();
				 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}	
	
	public String analyze(String text) throws URISyntaxException, JSONException{
		String apikey = "3a9c144a53d1f64eeee5dccf610846b06e3017a7";
		URI uri = buildUri(text);
		JSONObject obj=new JSONObject();
		 obj.put("apikey", apikey);
		 obj.put("text", text);
		 obj.put("outputMode", "json");
		 String response = httpPost(uri,obj.toString());
		 return response;
	}
	
	/*public static void main(String args[]) throws IOException, IOException, Exception{
		String text = "His recommendation were to try the ruben fish sandwich";
		String apikey = "3a9c144a53d1f64eeee5dccf610846b06e3017a7";
		URI uri = buildUri(text);
		JSONObject obj=new JSONObject();
		 obj.put("apikey", apikey);
		 obj.put("text", text);
		 obj.put("outputMode", "json");
		 String response = httpPost(uri,obj.toString());
		 System.out.println("received response is:"+ " "+response);
	}*/
         
	/*	 HttpPost httpPost = new HttpPost(uri);
         httpPost.setHeader("Content-Type", ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
         ResponseHandler<String> responseHandler = new BasicResponseHandler();
         HttpClient httpclient = new DefaultHttpClient();
         String responseBody = httpclient.execute(httpPost, responseHandler);
         System.out.println("response is:" + " " +responseBody);
		*/
		

	
private static URI buildUri(String text) throws URISyntaxException {

        URIBuilder builder = new URIBuilder();
        builder.setScheme("http")
                .setHost("access.alchemyapi.com")
                .setPath("/calls/text/TextGetTextSentiment")
                .setParameter("apikey", "3a9c144a53d1f64eeee5dccf610846b06e3017a7")
                .setParameter("text", text)
                .setParameter("outputMode", "json");

        return builder.build();
    }
	


}

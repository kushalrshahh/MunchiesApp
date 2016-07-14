

import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.JSONException;
import org.json.JSONObject;




public class munchiesMapper extends Mapper<LongWritable, Text, UserWritable,DoubleWritable >  {
	public void map(LongWritable key, Text value, Context context)throws IOException, InterruptedException 
	{
		String line = value.toString();
		 String[] array = line.split(",");
		 	
		 String bid = array[0];
		 String dishName = array[1];
		 String comment = array[2];	
		 double score =0;
		 
		 // Call alchemy
		 sentianalysis senti = new sentianalysis();
		 try {
			String response = senti.analyze(comment);
			 JSONObject jsonObj;
			 jsonObj =  new JSONObject(response);
			 System.out.println("current dish"+" "+bid+" "+dishName);
			// System.out.println("received output:"+response);
			 String type = jsonObj.getJSONObject("docSentiment").getString("type");
			 if(type.equals("neutral") ){
				  score = 0;
			  }
			 else{
				 score = jsonObj.getJSONObject("docSentiment").getDouble("score");
			 }
			
			// JSONObject obj = new JSONObject(str);
			// double score = obj.getDouble("score");
			 
			  UserWritable uw = new UserWritable(bid,dishName);
			 
			  context.write(new UserWritable(bid,dishName), new DoubleWritable(score));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (com.amazonaws.util.json.JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}
}


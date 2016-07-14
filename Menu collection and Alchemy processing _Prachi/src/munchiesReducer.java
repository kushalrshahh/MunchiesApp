

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class munchiesReducer extends Reducer<UserWritable,DoubleWritable, Text, DoubleWritable> {
	public void reduce(UserWritable key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException 
	{
		double avg = 0.00;
		int num = 0;
		double sum = 0;
		for (DoubleWritable value : values) 
		{
			
			sum = sum + value.get();
			
			if(value.get()!=0){
				num++;
				System.out.println("inside if");
			}
			
			
		}
		//Calculate avg
		avg = sum/num;
	
		context.write(new Text(key.toString()), new DoubleWritable(avg));
	}
	
}


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class munchies {
	public static void main(String[] args) throws Exception 
	{
		if (args.length != 2) 
		{
			System.err.println("Usage: Munchies <input path> <output path>");
			System.exit(-1);
		}
		
		Job job = new Job();
		
		job.setJarByClass(munchies.class);
		job.setJobName("munchies");
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setMapperClass(munchiesMapper.class);
		job.setReducerClass(munchiesReducer.class);
		job.setOutputKeyClass(UserWritable.class);
		job.setOutputValueClass(DoubleWritable.class);
				
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		}
}

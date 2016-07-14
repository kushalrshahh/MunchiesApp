

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;





import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

import com.google.common.collect.ComparisonChain;

public class UserWritable implements WritableComparable<UserWritable> {

	  private String business_id;
	  private String dish_name;

	  public UserWritable(String bid, String dishName) {
		// TODO Auto-generated constructor stub
		 
		  business_id = bid;
		  dish_name = dishName;
		  System.out.println("inside Userwritable"+" "+bid+" "+dish_name);
	}
	  
	  public UserWritable(){
		  business_id = new String("");
		  dish_name = new String("");
	  }
	@Override
	  public void readFields(DataInput in) throws IOException {
	    //business_id = in.readUTF();
	    //dish_name = in.readUTF();
		this.business_id = WritableUtils.readString(in);
		this.dish_name = WritableUtils.readString(in);
	  }

	  @Override
	 /* public void write(DataOutput out) throws IOException {
	    out.writeUTF(business_id);
	    out.writeUTF(dish_name);
	  }*/
	  public void write(DataOutput out) throws IOException {
		  WritableUtils.writeString(out, business_id);
		  WritableUtils.writeString(out, dish_name);
	  }

	  @Override
	  public int compareTo(UserWritable o) {
		  if(o==null)
			  return 0;
	    return ComparisonChain.start().compare(business_id, o.business_id)
	        .compare(dish_name, o.dish_name).result();
	  }
	  @Override
	  public String toString() {
	  return business_id.toString() + "	" + dish_name.toString();
	  }

	}
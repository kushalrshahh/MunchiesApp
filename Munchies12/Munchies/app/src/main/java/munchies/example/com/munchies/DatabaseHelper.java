package munchies.example.com.munchies;

/**
 * Created by shwet on 11/29/2015.
 */

import android.app.Activity;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper{


    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://shwetmaroo.cntknzmd26ov.us-west-1.rds.amazonaws.com:3306/shwetmaroo?user=shwetmaroo&password=shwetmaroo";
    private static Statement stmt=null;
    private Connection connection = null;





    public Connection createConn(){
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER).newInstance();
            connection = DriverManager.getConnection(DB_URL);
            Log.d("connectionnn",connection+"");
            connection.setAutoCommit(false);
            System.out.println("Connected to DB");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            Log.d("errorrrr", ".....Class com.mysql.jdbc.Driver not found!");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.d("errorrrr", "Illegal access");
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            Log.d("errorrrr", "instantiation exc eption");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void getDishes(String restaurant_name) {
      //  List<Tweets> tc = new ArrayList<Tweets>();
        try {
            if (connection == null) {
                connection = createConn();
            }
            stmt = connection.createStatement();
            String sql = "SELECT  dishName,dishRating  FROM Restaurant_dishes where restaurantName='"+ restaurant_name +"' limit " + 10;
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
             //   Tweets t=new Tweets(rs.getLong("userid"),  rs.getString("username"), rs.getString("status"), rs.getDouble("lat"),rs.getDouble("longi"), hashTag);
             //   tc.add(t);
                System.out.println(rs.getString("dishName")+rs.getString("dishRating"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
      //  return tc;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}

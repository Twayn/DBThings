package client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
 
public class GetMultiThread {
	
	private static final String url = "jdbc:postgresql://localhost/test";
	private static final String login = "postgres";
	private static final String password = "12345";
	
	private static Connection connection = null;
  
    public static void main(String args[]) throws SQLException, InterruptedException
    {
		connection = DriverManager.getConnection(url, login, password);
		ResultSet counts;
		PreparedStatement preparedStatement = connection.prepareStatement(
	               "SELECT COUNT(*) FROM goods ");
	   	counts = preparedStatement.executeQuery();
	   	counts.next();
	   	int count = counts.getInt(1);
	   	System.out.println(count);
	   	
	   	ExecutorService executor = Executors.newCachedThreadPool();
		  for (int i = 1; i < 50; i++) executor.execute(new RequestThread(count, i));
		  executor.awaitTermination(2, TimeUnit.SECONDS);
		  RequestThread.printAvarage();
		  
    }
}
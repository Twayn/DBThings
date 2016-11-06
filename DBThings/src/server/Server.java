package server;

import static spark.Spark.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Server {
	
	private static final String url = "jdbc:postgresql://localhost/test";
	private static final String login = "postgres";
	private static final String password = "12345";
	private static Connection connection;
	
	static
	{
		try {
			connection = DriverManager.getConnection(url, login, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    public static void main(String[] args) throws SQLException {
    	    	
    	System.out.println("Server is listening");
    	get("/select/:name", (request, response) -> {
    		
    		PreparedStatement ps = connection.prepareStatement(
	                "SELECT g.name, g.price, g.description, g.path_to_image, c.name as category_name "
	                + "FROM goods g "
	                + "LEFT JOIN categories c on g.category_id = c.id "
	                + "where g.id = " +  Integer.valueOf(request.params(":name")));
    		
    		ResultSet result = ps.executeQuery();
    	
    		result.next();
    		
    		String[] strings = new String[] {
					result.getString("name"),
					result.getString("price"),
					result.getString("description"),
					result.getString("path_to_image"),
					result.getString("category_name")};
    			   		
    	    return Arrays.toString(strings);
    	});
    	
    	stop();
    }
}

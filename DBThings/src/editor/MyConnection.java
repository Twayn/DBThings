package editor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyConnection {
	
	private static final String url = "jdbc:postgresql://localhost/test";
	private static final String login = "postgres";
	private static final String password = "12345";
	
	public static java.sql.Connection GetConnection () {
		try {
			return DriverManager.getConnection(url, login, password);
		} catch (SQLException e) {
			throw new RuntimeException("ConnectionError");
		}
	}
	public static int getNextId(){
		try {
			Connection connection = DriverManager.getConnection(url, login, password);
			String selectMax = "SELECT MAX(id) as id FROM goods";
			PreparedStatement pStatement = connection.prepareStatement(selectMax);
			ResultSet resultSet = pStatement.executeQuery();
			resultSet.next();
			return resultSet.getInt("id")+1;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Get next id error");
		}
	}
	
	public static void addNewRow(String id, String name, String price, String description, String image, String category){
		try {
			Connection connection = DriverManager.getConnection(url, login, password);
			String insert = "INSERT INTO public.goods(id, price, name, description, path_to_image, category_id) " + 
							"VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pStatement = connection.prepareStatement(insert);
			pStatement.setInt(1, Integer.valueOf(id));
	        pStatement.setDouble(2, Double.valueOf(price));
	        pStatement.setString(3, name);
	        pStatement.setString(4, description);
	        pStatement.setString(5, image);
	        pStatement.setInt(6, Integer.valueOf(category));
	        
			pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Insert Error");
		}
	}
	
	public static void editRow(String id, String name, String price, String description, String image, String category){
		try {
			Connection connection = DriverManager.getConnection(url, login, password);
			String insert = "UPDATE goods SET id=?, price=?, name=?, description=?, path_to_image=?, category_id=? " +
			" WHERE id = ? "; 
			PreparedStatement pStatement = connection.prepareStatement(insert);
			pStatement.setInt(1, Integer.valueOf(id));
	        pStatement.setDouble(2, Double.valueOf(price));
	        pStatement.setString(3, name);
	        pStatement.setString(4, description);
	        pStatement.setString(5, image);
	        pStatement.setInt(6, Integer.valueOf(category));
	        pStatement.setInt(7, Integer.valueOf(id));
	        
			pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Edit Error");
		}
	}
	
	public static void deleteRow (int i, ShopTable table) {
		try {
			Connection connection = DriverManager.getConnection(url, login, password);
			String delete = "DELETE FROM goods WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setInt(1, table.getModel().getId(table.getSelectedRow()));
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Delete Error");
		}
	}
}

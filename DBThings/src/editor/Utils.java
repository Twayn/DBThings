package editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import editor.MyConnection;

public class Utils {
	
	private static Connection connection = null;
	
	private static final String category = "Категория";
	private static final int minCategoryCount = 20;
	private static final int baseCategoryCount = 10;
	
	/** Random generate some goods
	 * 
	 * @param size - number of generated goods
	 * @return list full of goods
	 * @throws Exception
	 */
	public static List<GoodsNode> generate (int size, int categoryCount) throws Exception {
		int i = 1;
		GoodsNode node;
		List<GoodsNode> list = new LinkedList<GoodsNode>();
		
		while (i<size){
			node = new GoodsNode();
			node.setId(i);
			node.setCategoryId(ThreadLocalRandom.current().nextInt(1, categoryCount+1));
			node.setPrice(ThreadLocalRandom.current().nextDouble(500000.0));
			node.setName("Name "+i);
			node.setDescription("Description "+i); 
			node.setPathToImage("C:\\Img\\"+ i + ".jpg");
			list.add(node);
			i++;
		}
		
		return list;
	}
	
	/**Insert random goods from list to 'goods' table*/
	public static void insertGoods (List<GoodsNode> list) throws SQLException {
        connection = MyConnection.GetConnection();
        
        PreparedStatement preparedStatement = null;
        System.out.println("Size:" + list.size());
        
        for (GoodsNode node : list){
        	preparedStatement = connection.prepareStatement(
                    "INSERT INTO goods values(?, ?, ?, ?, ?, ?)");
            preparedStatement.setDouble(1, node.getPrice());
            preparedStatement.setString(2, node.getName());
            preparedStatement.setInt(3, node.getId());
            preparedStatement.setString(4, node.getDescription());
            preparedStatement.setString(5, node.getPathToImage());
            preparedStatement.setInt(6, node.getCategoryId());
           
            preparedStatement.executeUpdate();
        }
          
          if (connection != null) {
              connection.close();
          }	       
          System.out.println("Insert Complete");
	}
	
	/**Insert random generated category hierarchy to 'categories' table*/
	public static void insertCategories (int numberOfCategories) throws Exception {
		if (numberOfCategories < minCategoryCount){
			throw new Exception("Число категорий дожно быть не меньше: " + minCategoryCount);
		}
		connection = MyConnection.GetConnection();
        System.out.println("Соединение установлено");
        PreparedStatement preparedStatement = null;
        List<CategoriesNode> categoriesList = new ArrayList<CategoriesNode>();
        CategoriesNode categoryNode;
        
		for (int i = 0; i<baseCategoryCount; i++){ //Generate root categories
			categoryNode = new CategoriesNode(i+1, 0, category+" "+Integer.valueOf(i+1));
			categoriesList.add(categoryNode);
	    }
		for (int i = baseCategoryCount; i<numberOfCategories; i++){ //Generate other categories
			categoryNode = new CategoriesNode(i+1, ThreadLocalRandom.current().nextInt(1, i), "");
            categoriesList.add(categoryNode);
		}
		for(int i = 0; i<baseCategoryCount; i++){	//Give to childrens recursive names
			renameChildrensRecursive(categoriesList.get(i), categoriesList);
		}
		
//		for(CategoriesNode node:categoriesList){
//			System.out.println(node.getId());
//			System.out.println(node.getParentId());
//			System.out.println(node.getName());
//		}
		
		for (CategoriesNode node:categoriesList){ // insert all categories to DB
			preparedStatement = connection.prepareStatement(
                    "INSERT INTO categories values(?, ?, ?)");
            preparedStatement.setInt(1, node.getId());
            preparedStatement.setInt(2, node.getParentId()); 
            preparedStatement.setString(3, node.getName());
           
            preparedStatement.executeUpdate();
		}
		  
          if (connection != null) {
              connection.close();
          }	        
	}
	
	public static void renameChildrensRecursive (CategoriesNode node, List<CategoriesNode> list) {
		int parentId = node.getId();
		String name = node.getName();
		int iter = 1;
		for(CategoriesNode catNode : list){
			if (catNode.getParentId() == parentId){
				catNode.setName(name + "." + iter);
				renameChildrensRecursive(catNode, list);
				iter++;
			}
		}
	}
	
	public static void generateImages (int count) {
      for (int k = 0; k < count; k++) {
			try {
	            BufferedImage bi = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
	            for (int i = 0; i < 128; i++) {
	            	for (int j = 0; j < 128; j++) {
						bi.setRGB(i, j, ThreadLocalRandom.current().nextInt(0, 16777216 + 1));
					}	
				}
	            File outputfile = new File("C:\\Img\\" +k +".jpg");
	            ImageIO.write(bi, "jpg", outputfile);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			System.out.println(k);
		}
	}
	
	@Deprecated
	public static void select () throws SQLException {       
       
        connection = MyConnection.GetConnection();
        System.out.println("Connection Established");
        
        PreparedStatement preparedStatement = null;
        
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM goods where id > ? and id < ?");
        preparedStatement.setInt(1, 0);
        preparedStatement.setInt(2, 20000);
        
        ResultSet result2 = preparedStatement.executeQuery();
        
        System.out.println("PreparedStatement: ");
        while (result2.next()) {
            System.out.println("№: " + result2.getRow()
                    + "\t № in DB " + result2.getInt("id")
                    + "\t" + result2.getString("name")
                    +"\t" + result2.getString("description")
                    +"\t" + result2.getString("path_to_image")
                    + "\t" + result2.getString("price"));
        }
       
          if (connection != null) {
              connection.close();
          }	
        
	}
	
}

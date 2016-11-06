package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ButtonPanel extends JPanel {
	private static final long serialVersionUID = 6216215462672821523L;
	private static ShopTable table;
	    	
	public ButtonPanel(ShopTable table){
		ButtonPanel.table = table;
	
		JButton addButton = new JButton("Add Row");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ShopTableEditor(table.getModel());
			}
		});
		
		JButton changeButton = new JButton("Edit Row");
		changeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1)
				new ShopTableEditor(table.getModel(), table.getSelectedRow());
			}
		});
		
		JButton deleteButton = new JButton("Delete Row");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1){
					MyConnection.deleteRow(table.getSelectedRow(), table);
					table.getModel().removeRow(table.getSelectedRow());
					table.getModel().fireTableDataChanged();
				}
			}
		});
		
		JButton downloadButton = new JButton("Download from DB");
		downloadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DownloadDataThread newThread = new DownloadDataThread();
		            newThread.start();
			}       
		});
		
		JButton clearButton = new JButton("Clear memory");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.getModel().clearData();
				table.getModel().fireTableDataChanged();
			}       
		});
		
		add(addButton);
		add(deleteButton);
		add(changeButton);
		add(downloadButton);
		add(clearButton);
	}
	

	
    static class DownloadDataThread extends Thread {
    	Connection connection = MyConnection.GetConnection();
        PreparedStatement preparedStatement = null;
        ResultSet counts = null;
        ResultSet result = null;
        
        int count = 0;
    	int number = 20;
    	int lower = 0;
    	int upper = 0;
        
        @Override
        public void run() {
	        try {
	        	preparedStatement = connection.prepareStatement(
		                "SELECT COUNT(*) FROM goods ");
	        	counts = preparedStatement.executeQuery();
	        	counts.next();
	        	count = counts.getInt(1);
	        	while (upper <= count){
	        		lower = upper;
	        		upper+=number;
	        		preparedStatement = connection.prepareStatement(
			                "SELECT g.id, g.name, g.price, g.description, g.path_to_image, c.name as category_name "
			                + "FROM goods g "
			                + "LEFT JOIN categories c on g.category_id = c.id "
			                + "where g.id > ? and g.id < ? ");
			        preparedStatement.setInt(1, lower);
			        preparedStatement.setInt(2, upper+1);
			        
			        result = preparedStatement.executeQuery();
			        
			        while (result.next()) {
						table.getModel().addRow(new String[] {
								result.getString("id"),
								result.getString("name"),
								result.getString("price"),
								result.getString("description"),
								result.getString("path_to_image"),
								result.getString("category_name")
								});	
					}
			        
			        SwingUtilities.invokeLater(new Runnable() {
	                    @Override
	                    public void run() {
	     			        table.getModel().fireTableDataChanged();
	                    }
	                });
	        		
	        	}
	   
	        } catch (Exception exception){
	        	exception.printStackTrace();
	        }
        }
    }
	
}

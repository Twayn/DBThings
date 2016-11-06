package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**Simple DataBase Editor*/
@SuppressWarnings("unused")
public class App extends JFrame{
	
	private static final long serialVersionUID = 4951224126459696947L;
	private static final int categoryCount = 80;
	
	public App () {
		super("Окошенько");
		setSize(400, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		TableModel model = new TableModel();
		ShopTable table = new ShopTable(model);
		table.setRowHeight(35);
		ButtonPanel buttonPanel = new ButtonPanel(table);
		
		JScrollPane tableScroll = new JScrollPane(table);
		tableScroll.setPreferredSize(new Dimension(600, 800));
		
		add(tableScroll);
		add(buttonPanel, BorderLayout.NORTH);
		setVisible(true);
		pack();
	}
	
	public static void main(String[] args) throws Exception {
		App app = new App();
		
		//Utils.insertCategories(categoryCount);
		//Utils.insertGoods(Utils.generate(200000, categoryCount));
		//Utils.generateImages(200001);
	}
}

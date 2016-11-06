package editor;

import javax.swing.JTable;

public class ShopTable extends JTable{
	private static final long serialVersionUID = -455014471945004034L;

	public ShopTable(TableModel tableModel) {
		super(tableModel);
	}
	
	public TableModel getModel() {
		return (TableModel) super.getModel();
	}
	
}

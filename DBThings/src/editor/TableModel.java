package editor;

import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

	private static final long serialVersionUID = -1073610288029710710L;
	private int columnCount = 7;
	private List<String[]> dataList = new LinkedList<String[]>() ;
	
	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public int getRowCount() {
		return dataList.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if (arg1 == 6){
			return new ImageIcon("C:\\Img\\" + arg0 +".jpg");
		}
		if (arg1 == 0){
			return arg0;
		}
		else {
			String[] str = dataList.get(arg0);
			return str[arg1];
		}
	}
	
	@Override
	public String getColumnName(int columnIndex){
		switch (columnIndex){
		case 0: return "number";
		case 1: return "name";
		case 2: return "price";
		case 3: return "description";
		case 4: return "path to image";
		case 5: return "category name";
		case 6: return "image";
		}
		return "";
	
	}
	
	@Override
    public Class<?> getColumnClass(int column)
    {
        return getValueAt(0, column).getClass();
    }
	
	public void removeRow(int rowIndex){
		dataList.remove(rowIndex);
	}
	public void addRow(String[] row){
		dataList.add(row);
	}
	public String[] getRowData(int row){
		return dataList.get(row);
	}
	public void replaceRow (String[] data, int row){
		dataList.set(row, data);
	}
	public void clearData () {
		dataList.clear();
	}
	public int getId (int rowIndex) {
		return Integer.valueOf(dataList.get(rowIndex)[0]);
	}
}
